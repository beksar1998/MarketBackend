package com.beksar.market.services.sso.services.impl


import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.httpError
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.core.utils.ConfirmCodeGenerator
import com.beksar.market.services.sso.errors.AuthErrors
import com.beksar.market.services.sso.mapper.toProfile
import com.beksar.market.services.sso.models.dto.firebase.AddFirebaseTokenRequest
import com.beksar.market.services.sso.models.dto.profile.ChangePasswordRequest
import com.beksar.market.services.sso.models.dto.profile.ProfileEditRequest
import com.beksar.market.services.sso.models.dto.profile.ProfileResponse
import com.beksar.market.services.sso.models.dto.user.UserPhoneResponse
import com.beksar.market.services.sso.models.entity.relations.UserPhoneEntity
import com.beksar.market.services.sso.repositories.UserPhoneRepository
import com.beksar.market.services.sso.repositories.UserRepository
import com.beksar.market.services.sso.services.ProfileService
import com.beksar.market.services.sso.services.RolesService
import com.beksar.market.services.telegram.service.TelegramBotService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service()
class ProfileServiceImpl(
    private val repository: UserRepository,
    private val rolesService: RolesService,
    private val telegramBotService: TelegramBotService,
    private val passwordEncoder: PasswordEncoder,
    private val userPhoneRepository: UserPhoneRepository
) : ProfileService {

    override fun profile(id: String): ProfileResponse {
        val entity = repository.findById(id).checkNotNull(AuthErrors.UserNotFound)
        val roles = rolesService.userRoles(id)
        val phones = userPhoneRepository.findAllByUserIdIn(listOfNotNull(entity.id)).map { it.phone }
        return entity.toProfile(roles = roles, phones = phones)
    }

    override fun edit(id: String, request: ProfileEditRequest): ProfileResponse {
        val entity = repository.findById(id).checkNotNull(AuthErrors.UserNotFound)
        val new = entity.copy(name = request.name, surname = request.surname, photo = request.photo)
        repository.saveWithException(new)
        val roles = rolesService.userRoles(id)
        val phones = userPhoneRepository.findAllByUserIdIn(listOfNotNull(entity.id)).map { it.phone }
        return new.toProfile(roles = roles, phones = phones)

    }

    override fun changePassword(id: String, request: ChangePasswordRequest): Boolean {
        val entity = repository.findById(id).checkNotNull(AuthErrors.UserNotFound)

        if (!passwordEncoder.matches(request.oldPassword, entity.password)) {
            httpError(AuthErrors.WrongOldPassword)

        }
        val new = entity.copy(password = passwordEncoder.encode(request.newPassword))
        repository.saveWithException(new)
        return true

    }

    override fun confirmEmail(userId: String): Boolean {
        val user = repository.findById(userId).checkNotNull(AuthErrors.UserNotFound)
        if (user.isEmailConfirmed) {
            httpError(AuthErrors.EmailConfirmed)
        }
        val newUser = user.copy(
            emailConfirmCode = ConfirmCodeGenerator.generate(),
            emailConfirmCodeDate = Date(),
        )
        repository.saveWithException(newUser)
        return true
    }

    override fun confirmPhone(userId: String): Boolean {
        val user = repository.findById(userId).checkNotNull(AuthErrors.UserNotFound)
        if (user.isPhoneConfirmed) {
            httpError(AuthErrors.PhoneConfirmed)
        }
        val sms = ConfirmCodeGenerator.generate()
        val newUser = user.copy(
            phoneConfirmCode = sms,
            phoneConfirmCodeDate = Date()
        )
        repository.saveWithException(newUser)
        telegramBotService.sendMessage(ConfirmCodeGenerator.confirmPhone(newUser.phone,sms))
        return true

    }

    override fun addFirebaseToken(userId: String, request: AddFirebaseTokenRequest): Boolean {
        val entity = repository.findById(userId).checkNotNull(AuthErrors.UserNotFound)
        val newEntity = entity.copy(fcmToken = request.token.orEmpty())
        repository.saveWithException(newEntity)
        return true
    }


    override fun removePhoto(userId: String) {
        val entity = repository.findById(userId).checkNotNull(AuthErrors.UserNotFound)
        val newEntity = entity.copy(photo = null)
        repository.saveWithException(newEntity)
    }


    override fun deleteUser(userId: String): Boolean {
        repository.deleteById(userId)
        return true
    }

    override fun addPhone(userId: String, phone: String) {
        if (userPhoneRepository.existsByPhone(phone)) httpError(AuthErrors.PhoneExists)
        userPhoneRepository.saveWithException(UserPhoneEntity(userId = userId, phone = phone))
    }

    override fun deletePhone(phoneId: String) {
        userPhoneRepository.deleteById(phoneId)
    }

    override fun phones(userId: String): List<UserPhoneResponse> {
        return userPhoneRepository.findAllByUserId(userId).map {
            UserPhoneResponse(
                id = it.id,
                phone = it.phone
            )
        }
    }
}