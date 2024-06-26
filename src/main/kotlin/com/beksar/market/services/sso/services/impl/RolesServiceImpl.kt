package com.beksar.market.services.sso.services.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.httpError
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.services.sso.errors.AuthErrors
import com.beksar.market.services.sso.models.entity.relations.UserRoleEntity
import com.beksar.market.services.sso.repositories.UserRepository
import com.beksar.market.services.sso.repositories.relations.UserRoleRepository
import com.beksar.market.services.sso.services.RolesService
import com.beksar.market.services.sso.core.roles.Role
import org.springframework.stereotype.Service

@Service
class RolesServiceImpl(
    private val userRoleRepository: UserRoleRepository,
    private val userRepository: UserRepository
) : RolesService {


    override fun roles(): List<Role> {
        return Role.values().map { it }
    }

    override fun userRoles(id: String): List<Role> {
        val userRoles = userRoleRepository.findAllByUserId(id)
        val roles = roles()
        return userRoles.mapNotNull { userRole ->
            roles.find { it == userRole.role }
        }
    }

    override fun addRole(userId: String, role: Role) {
        val user = userRepository.findById(userId).checkNotNull(AuthErrors.UserNotFound)
        val roles = userRoleRepository.findAllByUserId(userId)
        if (roles.any { it.role == role }) {
            httpError(AuthErrors.RoleExist)
        }
        val userRole = UserRoleEntity(
            userId = userId,
            role = role
        )
        userRoleRepository.saveWithException(userRole)
    }
}