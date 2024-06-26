package com.beksar.market.services.sso.services.impl

import com.beksar.market.core.extentions.*
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.core.models.paging.SearchPagingParams
import com.beksar.market.services.sso.errors.AuthErrors
import com.beksar.market.services.sso.mapper.toEntity
import com.beksar.market.services.sso.mapper.toUserFullResponse
import com.beksar.market.services.sso.models.dto.user.CreateUserRequest
import com.beksar.market.services.sso.models.dto.user.UserFullResponse
import com.beksar.market.services.sso.models.entity.relations.UserRoleEntity
import com.beksar.market.services.sso.repositories.UserRepository
import com.beksar.market.services.sso.repositories.relations.UserRoleRepository
import com.beksar.market.services.sso.services.UserService
import com.beksar.market.services.sso.validators.validate
import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.core.user.UserStatus
import com.beksar.market.services.sso.models.entity.relations.UserPhoneEntity
import com.beksar.market.services.sso.repositories.UserPhoneRepository
import org.springframework.data.domain.Page
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userPhoneRepository: UserPhoneRepository
) : UserService {

    override fun users(searchPagingParams: SearchPagingParams): BasePageResponse<UserFullResponse> {
        val users = userRepository.search(searchPagingParams.search, searchPagingParams.toPageable())
        val ids = users.content.mapNotNull { it.id }
        val roles = userRoleRepository.findAllByUserIdIn(ids)
        val phones = phones(ids)
        return users.paging { user ->
            user.toUserFullResponse(
                roles = roles.filter { it.userId == user.id }.mapNotNull { it.role },
                phones = phones.filter { it.userId == user.id }.map { it.phone }
            )
        }
    }

    override fun user(id: String): UserFullResponse {
        val roles = userRoleRepository.findAllByUserId(id)
        val phones = phones(listOf(id))
        val user = userRepository.findById(id).checkNotNull().toUserFullResponse(
            roles = roles.mapNotNull { it.role },
            phones = phones.map { it.phone }
        )
        return user
    }

    override fun lock(id: String) {
        val user = userRepository.findById(id).checkNotNull()
        userRepository.saveWithException(user.copy(status = UserStatus.BLOCKED))
    }

    override fun unlock(id: String) {
        val user = userRepository.findById(id).checkNotNull()
        userRepository.saveWithException(user.copy(status = UserStatus.ACTIVE))
    }



    override fun addManager(id: String) {
        val user = userRoleRepository.findAllByUserId(id)
        if (!user.any { it.role == Role.MANAGER }) {
            userRoleRepository.saveWithException(
                UserRoleEntity(
                    role = Role.MANAGER,
                    userId = id,
                )
            )
        }
    }

    override fun deleteManager(id: String) {
        val user = userRoleRepository.findAllByUserId(id)
        val userRoleId = user.find { it.role == Role.MANAGER }?.id ?: return
        userRoleRepository.deleteById(userRoleId)
    }

    override fun users(ids: List<String>): Page<UserFullResponse> {
        val users = userRepository.findAllByIdIn(ids)
        val roles = userRoleRepository.findAllByUserIdIn(ids)
        val phones = phones(ids)
        return users.map { user ->
            user.toUserFullResponse(
                roles = roles.filter { it.userId == user.id }.mapNotNull { it.role },
                phones = phones.filter { it.userId == user.id }.map { it.phone })
        }.toPage()
    }


    override fun createUser(request: CreateUserRequest): String {
        request.validate()
        val user = if (!request.phone.isNullOrBlank()) {
            userRepository.findByPhone(request.phone)
        } else if (!request.email.isNullOrBlank()) {
            userRepository.findByEmail(request.email)
        } else {
            httpError(AuthErrors.ERROR)
        }
        if (user != null) {
            httpError(AuthErrors.UserAlreadyExists)
        }


        val entity = request.toEntity(password = passwordEncoder.encode(request.password))
        val new = userRepository.saveWithException(entity)


        request.roles?.forEach {
            userRoleRepository.saveWithException(
                UserRoleEntity(
                    role = it,
                    userId = new.id
                )
            )
        }

        return new.id ?: httpError(AuthErrors.UserCreateError)
    }

    override fun findUserByPhone(phone: String): UserFullResponse {
        //TODO ADD ROLES
        val user = userRepository.findByPhone(phone)
        val phones = phones(listOfNotNull(user?.id))
        return user?.toUserFullResponse(
            roles = emptyList(),
            phones = phones.map { it.phone })
            ?: httpError(AuthErrors.UserNotFound)
    }

    override fun findById(userId: String): UserFullResponse {
        val phones = phones(listOf(userId))
        return userRepository
            .findById(userId)
            .checkNotNull()
            .toUserFullResponse(
                roles = emptyList(),
                phones = phones.map { it.phone })

    }

    override fun getUserFCMToken(userId: String): String? {
        return userRepository.findById(userId).getOrNull()?.fcmToken ?: httpError(AuthErrors.UserNotFound)
    }

    override fun createAdmin() {
        val request = CreateUserRequest(
            name = "Admin",
            surname = "Admin",
            phone = "77777777777",
            password = "12345678",
            email = "admin@admin.admin",
            roles = listOf(Role.ADMIN, Role.SUPER_ADMIN)

        )
        val entity = request.toEntity(password = passwordEncoder.encode(request.password))
            .copy(isPhoneConfirmed = true, isEmailConfirmed = true)
        val new = userRepository.saveWithException(entity)


        userRoleRepository.saveWithException(
            UserRoleEntity(
                role = Role.ADMIN,
                userId = new.id
            )
        )
    }

    private fun phones(ids: List<String>): List<UserPhoneEntity> {
        return userPhoneRepository.findAllByUserIdIn(ids)
    }


    override fun deleteUser(id: String) {
        val user = userRepository.findById(id).checkNotNull()
        val phones = phones(listOfNotNull(id))
        userPhoneRepository.deleteAllById(phones.map { it.id })
        userRoleRepository.deleteById(user.id)
        userRepository.deleteById(user.id)
    }
}