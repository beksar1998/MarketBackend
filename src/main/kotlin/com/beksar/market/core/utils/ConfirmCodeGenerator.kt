package com.beksar.market.core.utils

import kotlin.random.Random

object ConfirmCodeGenerator {

    fun generate() = 1111//andom.nextInt(100000, 999999)


    fun register(
        email: String?,
        phone: String?,
        code: Int
    ): String {
        return "<b> ** Регистрация ** </b>\n" +
                "Email <b>$email</b>\n" +
                "Phone <b>$phone</b>\n" +
                "SMS <b>$code</b>\n"
    }

    fun registerPhone(
        phone: String?,
        code: Int
    ): String {
        return "<b> ** Регистрация(телефон) ** </b>\n" +
                "Phone <b>$phone</b>\n" +
                "SMS <b>$code</b>\n"
    }

    fun resetPhone(
        phone: String?,
        code: Int
    ): String {
        return "<b> ** Восстановление(телефон) ** </b>\n" +
                "Phone <b>$phone</b>\n" +
                "SMS <b>$code</b>\n"
    }

    fun authPhone(
        phone: String?,
        code: Int
    ): String {
        return "<b> ** Авторизация(телефон) ** </b>\n" +
                "Phone <b>$phone</b>\n" +
                "SMS <b>$code</b>\n"
    }

    fun confirmPhone(phone: String?, code: Int): String {
        return "<b> ** Подтверждение(телефон) ** </b>\n" +
                "Phone <b>$phone</b>\n" +
                "SMS <b>$code</b>\n"
    }

    fun resetEmail(email: String?, code: Int): String {
        return "<b> ** Подтверждение(email) ** </b>\n" +
                "Email <b>$email</b>\n" +
                "SMS <b>$code</b>\n"
    }


    fun confirm(
        email: String?,
        code: Int,
        confirm: Int,
        message : String,
    ): String {
        return "<b> ** Подтверждение ** </b>\n" +
                "Email <b>$email</b>\n" +
                "Confirm <b>$confirm</b>\n" +
                "SMS <b>$code</b>\n" +
                "Message <b>$message</b>\n"
    }

}