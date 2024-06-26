package com.beksar.market.services.sso.errors

import com.beksar.market.core.errors.HttpError


object AuthErrors {
    object ERROR : HttpError("Ошибка авторизации", mapOf())
    object UserNotFound : HttpError("Пользователь не найден", mapOf())
    object UserBlocked : HttpError("Пользователь заблокирован", mapOf())
    object UserCreateError : HttpError("Ошибка при создании пользователя", mapOf())
    object EmailNotConfirmed : HttpError("Email не подтвержден", mapOf())
    object WrongPassword : HttpError("Неверный пароль", mapOf())
    object WrongSMSCode : HttpError("Неверный смс", mapOf())
    object PhoneNotConfirmed : HttpError("Телефон не подтвержден", mapOf())
    object SMSNotSent : HttpError("Смс не отправлен", mapOf())
    object SMSConfirmationDateExpired : HttpError("Истекло верся", mapOf())
    object RefreshUserNotFound : HttpError("Пользовател не найден(обновление)", mapOf())
    object RefreshTokenTimeExpired : HttpError("Время истекло", mapOf())
    object WrongOldPassword : HttpError("Старый пароль неверный", mapOf())
    object WrongEmailConfirmationCode : HttpError("Неверный код(email)", mapOf())
    object WrongPhoneConfirmationCode : HttpError("Неверный код(телефон)", mapOf())
    object EmailConfirmationCodeDateExpired : HttpError("Время истекло(email)", mapOf())
    object PhoneConfirmationCodeDateExpired : HttpError("Время истекло(телефон)", mapOf())
    object EmailConfirmationCodeError : HttpError("Неверный код(email)", mapOf())
    object PhoneConfirmationCodeError : HttpError("Неверный код(телефон)", mapOf())
    object UserNotConfirmed : HttpError("Пользователь не подтвежден", mapOf())
    object UserAlreadyExists : HttpError("Пользователь существует", mapOf())
    object PhoneConfirmed : HttpError("Телефон подтвержден", mapOf())
    object PhoneExists : HttpError("Телефон существует", mapOf())
    object PhoneConfirmationDateNotSet : HttpError("Время не установлено", mapOf())
    object PhoneConfirmationDateExpired : HttpError("Дата пройдена", mapOf())

    object EmailConfirmed : HttpError("Email подтвержден", mapOf())
    object EmailConfirmationDateNotSet : HttpError("Время не установлено", mapOf())
    object EmailConfirmationDateExpired : HttpError("Дата пройдена", mapOf())

    object RoleExist : HttpError("Роль существует", mapOf())
}



