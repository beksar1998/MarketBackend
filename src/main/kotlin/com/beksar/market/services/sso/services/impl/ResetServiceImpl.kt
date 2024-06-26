package com.beksar.market.services.sso.services.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.httpError
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.core.utils.ConfirmCodeGenerator
import com.beksar.market.services.sso.errors.AuthErrors
import com.beksar.market.services.sso.models.dto.reset.*
import com.beksar.market.services.sso.repositories.UserRepository
import com.beksar.market.services.sso.services.ResetService
import com.beksar.market.services.telegram.service.TelegramBotService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ResetServiceImpl(
    private val userRepository: UserRepository,
    private val telegramBotService: TelegramBotService,
    private val passwordEncoder: PasswordEncoder
) : ResetService {

    override fun resetPasswordEmail(request: ResetPasswordEmailRequest): Boolean {
        val user = userRepository.findByEmail(request.email.orEmpty()).checkNotNull(AuthErrors.UserNotFound)
        val entity = user.copy(emailConfirmCodeDate = Date(), emailConfirmCode = ConfirmCodeGenerator.generate())
        userRepository.saveWithException(entity)

        //Send Confirmation Email
        val confirmCode = entity.emailConfirmCode.checkNotNull(AuthErrors.EmailConfirmationCodeError)
        telegramBotService.sendMessage(ConfirmCodeGenerator.resetEmail(request.email, confirmCode))
        return true
    }

    override fun resetPasswordEmailConfirm(request: ResetPasswordEmailConfirmRequest): Boolean {
        val user = userRepository.findByEmail(request.email.orEmpty()).checkNotNull(AuthErrors.UserNotFound)

        if (user.emailConfirmCode != request.confirmCode) {
            httpError(AuthErrors.WrongEmailConfirmationCode)
        } else if (Date((user.emailConfirmCodeDate?.time ?: 0) + (10 * 60 * 1000)) < Date()) {
            httpError(AuthErrors.EmailConfirmationCodeDateExpired)
        }
        val newUser = user.copy(
            isEmailConfirmed = true,
            password = passwordEncoder.encode(request.password),
            emailConfirmCodeDate = null,
            emailConfirmCode = null
        )
        userRepository.saveWithException(newUser)
        return true
    }

    override fun resetPasswordPhone(request: ResetPasswordPhoneRequest): Boolean {
        val user = userRepository.findByPhone(request.phone.orEmpty()).checkNotNull(AuthErrors.UserNotFound)
        val entity = user.copy(phoneConfirmCodeDate = Date(), phoneConfirmCode = ConfirmCodeGenerator.generate())
        userRepository.saveWithException(entity)

        //Send Confirmation Email
        val confirmCode = entity.phoneConfirmCode ?: httpError(AuthErrors.PhoneConfirmationCodeError)
        telegramBotService.sendMessage(ConfirmCodeGenerator.resetPhone(request.phone, confirmCode))
        return true
    }


    override fun resetPasswordPhoneConfirmCheck(request: ResetPasswordPhoneConfirmCheckRequest): Boolean {
        val user = userRepository.findByPhone(request.phone.orEmpty()).checkNotNull(AuthErrors.UserNotFound)
        if (user.phoneConfirmCode != request.code) {
            httpError(AuthErrors.WrongPhoneConfirmationCode)
        } else if (Date((user.phoneConfirmCodeDate?.time ?: 0) + (10 * 60 * 1000)) < Date()) {
            httpError(AuthErrors.PhoneConfirmationCodeDateExpired)
        }
        return true
    }

    override fun resetPasswordPhoneConfirm(request: ResetPasswordPhoneConfirmRequest): Boolean {
        val user = userRepository.findByPhone(request.phone.orEmpty()).checkNotNull(AuthErrors.UserNotFound)

        if (user.phoneConfirmCode != request.code) {
            httpError(AuthErrors.WrongPhoneConfirmationCode)
        } else if (Date((user.phoneConfirmCodeDate?.time ?: 0) + (10 * 60 * 1000)) < Date()) {
            httpError(AuthErrors.PhoneConfirmationCodeDateExpired)
        }
        val newUser = user.copy(
            isPhoneConfirmed = true,
            password = passwordEncoder.encode(request.password),
            phoneConfirmCodeDate = null,
            phoneConfirmCode = null
        )
        userRepository.saveWithException(newUser)
        return true
    }
}