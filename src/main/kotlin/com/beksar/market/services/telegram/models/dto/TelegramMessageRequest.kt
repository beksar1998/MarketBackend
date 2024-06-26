package com.beksar.market.services.telegram.models.dto

class TelegramMessageRequest(
    val chatId: String?,
    val message: String?
)

class TelegramAllMessageRequest(
    val message: String?
)