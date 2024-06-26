package com.beksar.market.services.telegram.controllers

import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.telegram.models.dto.TelegramAllMessageRequest
import com.beksar.market.services.telegram.models.dto.TelegramMessageRequest
import com.beksar.market.services.telegram.service.TelegramBotService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/telegram")
class TelegramBotController(private val service: TelegramBotService) {



    //TODO VALIDATORS
    @PostMapping("message")
    fun sendMessage(request: TelegramMessageRequest): BaseResponse<Boolean> {
        service.sendMessage(request.chatId.orEmpty(), request.message.orEmpty())
        return true.response()
    }

    @PostMapping("message/all")
    fun sendMessage(request: TelegramAllMessageRequest): BaseResponse<Boolean> {
        service.sendMessage(request.message.orEmpty())
        return true.response()
    }


}