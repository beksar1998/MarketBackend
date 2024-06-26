package com.beksar.market.services.telegram.service

interface TelegramBotService {

    fun sendMessage(chatId: String, message: String)
    fun sendMessage(message: String)

}