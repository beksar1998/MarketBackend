package com.beksar.market.services.sso.services.impl

import com.beksar.market.core.extentions.httpError
import com.beksar.market.services.sso.errors.AuthErrors
import com.beksar.market.services.sso.repositories.UserRepository
import com.beksar.market.services.sso.repositories.relations.UserRoleRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
) : UserDetailsService {


    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: userRepository.findByPhone(email)
            ?: httpError(AuthErrors.UserNotFound)


        val roles = userRoleRepository
            .findAllByUserId(user.id ?: httpError(AuthErrors.UserNotFound))
            .mapNotNull { it.role }

        val authorities = roles.map { SimpleGrantedAuthority(it.name) }

        return User(
            user.email.orEmpty().ifBlank { user.phone },
            user.password,
            authorities
        )
    }
}