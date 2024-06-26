package com.beksar.market.services.sso.services.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.httpError
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.core.utils.ConfirmCodeGenerator
import com.beksar.market.services.sso.errors.AuthErrors
import com.beksar.market.services.models.dto.auth.AuthSmsRequest
import com.beksar.market.services.sso.core.jwt.JWTService
import com.beksar.market.services.sso.core.user.UserStatus
import com.beksar.market.services.sso.mapper.toProfile
import com.beksar.market.services.sso.models.dto.auth.AuthEmailRequest
import com.beksar.market.services.sso.models.dto.auth.AuthPhoneRequest
import com.beksar.market.services.sso.models.dto.auth.AuthSmsConfirmRequest
import com.beksar.market.services.sso.models.dto.profile.AuthProfileResponse
import com.beksar.market.services.sso.repositories.UserPhoneRepository
import com.beksar.market.services.sso.repositories.UserRepository
import com.beksar.market.services.sso.services.AuthService
import com.beksar.market.services.sso.services.RefreshTokenService
import com.beksar.market.services.sso.services.RolesService
import com.beksar.market.services.telegram.service.TelegramBotService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JWTService,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenService: RefreshTokenService,
    private val rolesService: RolesService,
    private val telegramBotService: TelegramBotService,
    private val userPhoneRepository: UserPhoneRepository
) : AuthService {


    override fun authEmail(request: AuthEmailRequest): AuthProfileResponse {
        val entity = userRepository.findByEmail(request.email.orEmpty()).checkNotNull(AuthErrors.UserNotFound)
        return when {
            !entity.isEmailConfirmed -> httpError(AuthErrors.EmailNotConfirmed)
            !passwordEncoder.matches(request.password.orEmpty(), entity.password) -> httpError(AuthErrors.WrongPassword)
            entity.status == UserStatus.BLOCKED -> httpError(AuthErrors.UserBlocked)
            else -> {

                val roles = rolesService.userRoles(entity.id ?: httpError(AuthErrors.UserNotFound))

                val jwt = jwtService.generate(
                    id = entity.id,
                    email = entity.email.orEmpty(),
                    phone = entity.phone.orEmpty(),
                    roles = roles
                )
                val refreshToken =
                    refreshTokenService.createRefreshToken(entity.id)
                val phones = userPhoneRepository.findAllByUserIdIn(listOfNotNull(entity.id)).map { it.phone }
                entity.toProfile(token = jwt, refreshToken = refreshToken, roles = roles, phones = phones)
            }
        }
    }

    override fun authPhone(request: AuthPhoneRequest): AuthProfileResponse {
        val entity = userRepository.findByPhone(request.phone.orEmpty()).checkNotNull(AuthErrors.UserNotFound)
        val res = passwordEncoder.matches(request.password.orEmpty(), entity.password)
        println(res)
        return when {
            !entity.isPhoneConfirmed -> httpError(AuthErrors.PhoneNotConfirmed)
            !passwordEncoder.matches(request.password.orEmpty(), entity.password) -> httpError(AuthErrors.WrongPassword)
            else -> {

                val roles = rolesService.userRoles(entity.id ?: httpError(AuthErrors.UserNotFound))

                val jwt = jwtService.generate(
                    id = entity.id,
                    email = entity.email.orEmpty(),
                    phone = entity.phone.orEmpty(),
                    roles = roles
                )
                val refreshToken =
                    refreshTokenService.createRefreshToken(entity.id)
                val phones = userPhoneRepository.findAllByUserIdIn(listOfNotNull(entity.id)).map { it.phone }
                entity.toProfile(token = jwt, refreshToken = refreshToken, roles = roles, phones = phones)
            }
        }
    }

    override fun authPhoneSms(request: AuthSmsRequest): Boolean {
        val entity = userRepository.findByPhone(request.phone.orEmpty()).checkNotNull(AuthErrors.UserNotFound)
        return when {
            !entity.isPhoneConfirmed -> httpError(AuthErrors.PhoneNotConfirmed)
            else -> {

                //TODO NOT SEND OR BLOCK
                val sms = ConfirmCodeGenerator.generate()
                val new = entity.copy(smsAuthCodeDate = Date(), smsAuthCode = sms)
                userRepository.saveWithException(new)
                telegramBotService.sendMessage(ConfirmCodeGenerator.authPhone(request.phone, sms))
                true
            }
        }
    }

    override fun authPhoneSmsConfirm(request: AuthSmsConfirmRequest): AuthProfileResponse {
        val entity = userRepository.findByPhone(request.phone.orEmpty()).checkNotNull(AuthErrors.UserNotFound)
        return when {
            !entity.isPhoneConfirmed -> httpError(AuthErrors.PhoneNotConfirmed)
            entity.smsAuthCode == null -> httpError(AuthErrors.SMSNotSent)
            Date(
                (entity.smsAuthCodeDate?.time ?: 0) + (10 * 60 * 1000)
            ) < Date() -> httpError(AuthErrors.SMSConfirmationDateExpired)

            entity.smsAuthCode != request.code -> httpError(AuthErrors.WrongSMSCode)
            entity.smsAuthCode == request.code -> {
                val newUser = entity.copy(smsAuthCode = null, smsAuthCodeDate = null)
                userRepository.saveWithException(newUser)
                val roles = rolesService.userRoles(entity.id ?: httpError(AuthErrors.UserNotFound))
                val jwt = jwtService.generate(
                    id = entity.id,
                    email = entity.email.orEmpty(),
                    phone = entity.phone.orEmpty(),
                    roles = roles
                )
                val refreshToken =
                    refreshTokenService.createRefreshToken(entity.id)
                val phones = userPhoneRepository.findAllByUserIdIn(listOfNotNull(entity.id)).map { it.phone }
                entity.toProfile(token = jwt, refreshToken = refreshToken, roles = roles, phones = phones)
            }

            else -> httpError(AuthErrors.ERROR)
        }
    }
}