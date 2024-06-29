package com.beksar.market.services.sso.services.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.httpError
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.core.utils.ConfirmCodeGenerator
import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.errors.AuthErrors
import com.beksar.market.services.sso.mapper.toEntity
import com.beksar.market.services.sso.models.dto.register.RegisterEmailConfirmRequest
import com.beksar.market.services.sso.models.dto.register.RegisterEmailRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneConfirmRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneRequest
import com.beksar.market.services.sso.models.entity.relations.UserPhoneEntity
import com.beksar.market.services.sso.models.entity.relations.UserRoleEntity
import com.beksar.market.services.sso.repositories.UserPhoneRepository
import com.beksar.market.services.sso.repositories.UserRepository
import com.beksar.market.services.sso.repositories.relations.UserRoleRepository
import com.beksar.market.services.sso.services.RegisterService
import com.beksar.market.services.telegram.service.TelegramBotService
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*


@Service
class RegisterServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userRoleRepository: UserRoleRepository,
    private val telegramBotService: TelegramBotService,
    private val userPhoneRepository: UserPhoneRepository,
    private val javaMailSender: MailSender
) : RegisterService {

    private fun sendCode(email: String, code: String) {
        try {
            val message = SimpleMailMessage()
            message.setTo(email)
            message.subject = "ProMobileService"
            message.text = "Ваш код ${code}"
            javaMailSender.send(message)
        } catch (e: Exception) {

        }

    }


    override fun registerEmail(request: RegisterEmailRequest): Boolean {
        val user = userRepository.findByEmail(request.email.orEmpty())
        if (user != null && !user.isEmailConfirmed) {
            val phone = userPhoneRepository.existsByPhoneAndUserId(request.phone.orEmpty(), user.id)
            if (phone) {
                val entity = user.copy(
                    emailConfirmCode = ConfirmCodeGenerator.generate(),
                    emailConfirmCodeDate = Date(),
                    password = passwordEncoder.encode(request.password)
                )
                val new = userRepository.saveWithException(entity)
                telegramBotService.sendMessage(
                    ConfirmCodeGenerator.register(
                        request.email,
                        request.phone,
                        new.emailConfirmCode ?: 0
                    )
                )
                sendCode(request.email.orEmpty(), new.emailConfirmCode.toString())
                return true
            }
        }

        if (user != null) httpError(AuthErrors.UserAlreadyExists)
        else if (user != null && !user.isEmailConfirmed) httpError(AuthErrors.UserNotConfirmed)
        else if (userPhoneRepository.existsByPhone(request.phone.orEmpty())) httpError(AuthErrors.PhoneExists)

        val entity = request.toEntity(password = passwordEncoder.encode(request.password))
        val new = userRepository.saveWithException(entity)

        userRoleRepository.saveWithException(
            UserRoleEntity(
                role = Role.USER,
                userId = new.id
            )
        )
        if (!request.phone.isNullOrBlank()) {
            userPhoneRepository.saveWithException(
                UserPhoneEntity(
                    userId = new.id,
                    phone = request.phone
                )
            )
        }


        //Send Confirmation Email
        val confirmCode = entity.emailConfirmCode.checkNotNull(AuthErrors.EmailConfirmationCodeError)
        telegramBotService.sendMessage(ConfirmCodeGenerator.register(request.email, request.phone, confirmCode))
        sendCode(request.email.orEmpty(), confirmCode.toString())
        return true
    }


    override fun confirmEmail(request: RegisterEmailConfirmRequest): Boolean {
        val user = userRepository.findByEmail(request.email.orEmpty()).checkNotNull(AuthErrors.UserAlreadyExists)
        telegramBotService.sendMessage(
            ConfirmCodeGenerator.confirm(
                request.email,
                request.confirmCode ?: 0,
                user.emailConfirmCode ?: 0,
                "StartConfirm"
            )
        )
        if (user.isEmailConfirmed) {
            httpError(AuthErrors.EmailConfirmed)
        } else if (user.emailConfirmCodeDate == null) {
            httpError(AuthErrors.EmailConfirmationDateNotSet)
        } else if (user.emailConfirmCode != request.confirmCode) {
            telegramBotService.sendMessage(
                ConfirmCodeGenerator.confirm(
                    request.email,
                    request.confirmCode ?: 0,
                    user.emailConfirmCode ?: 0,
                    "WrongEmailConfirmationCode"
                )
            )
            httpError(AuthErrors.WrongEmailConfirmationCode)
        } else if (Date(user.emailConfirmCodeDate.time + (10 * 60 * 1000)) < Date()) {
            httpError(AuthErrors.EmailConfirmationDateExpired)
        }

        telegramBotService.sendMessage(
            ConfirmCodeGenerator.confirm(
                request.email,
                request.confirmCode ?: 0,
                user.emailConfirmCode ?: 0,
                "success"
            )
        )

        val newUser =
            user.copy(
                isEmailConfirmed = true,
                emailConfirmCode = null,
                emailConfirmCodeDate = null,
            )
        userRepository.saveWithException(newUser)


        return true
    }

    override fun registerPhone(request: RegisterPhoneRequest): Boolean {
        val user = userRepository.findByPhone(request.phone.orEmpty())
        if (user != null) httpError(AuthErrors.UserAlreadyExists)
        else if (user != null && !user.isPhoneConfirmed) httpError(AuthErrors.UserNotConfirmed)

        val entity = request.toEntity(password = passwordEncoder.encode(request.password))
        val new = userRepository.saveWithException(entity)

        userRoleRepository.saveWithException(
            UserRoleEntity(
                role = Role.USER,
                userId = new.id
            )
        )

        //Send Confirmation Email
        val confirmCode = entity.phoneConfirmCode.checkNotNull(AuthErrors.EmailConfirmationCodeError)
        telegramBotService.sendMessage(ConfirmCodeGenerator.registerPhone(request.phone, confirmCode))


        return true
    }

    override fun confirmPhone(request: RegisterPhoneConfirmRequest): Boolean {
        val user = userRepository.findByPhone(request.phone.orEmpty()).checkNotNull(AuthErrors.UserNotFound)
        val time = Date(Date().time + (10 * 60 * 1000))
        println(time)
        if (user.isPhoneConfirmed) {
            httpError(AuthErrors.PhoneConfirmed)
        } else if (user.phoneConfirmCode != request.code) {
            httpError(AuthErrors.WrongPhoneConfirmationCode)
        } else if (user.phoneConfirmCodeDate == null) {
            httpError(AuthErrors.PhoneConfirmationDateNotSet)
        } else if (Date(user.phoneConfirmCodeDate.time + (10 * 60 * 1000)) < Date()) {
            httpError(AuthErrors.PhoneConfirmationDateExpired)
        }
        val newUser = user.copy(
            isPhoneConfirmed = true,
            phoneConfirmCode = null,
            phoneConfirmCodeDate = null,
        )
        userRepository.saveWithException(newUser)

        return true
    }
}