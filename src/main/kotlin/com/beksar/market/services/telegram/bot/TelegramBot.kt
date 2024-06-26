package com.beksar.market.services.telegram.bot

import com.beksar.market.properties.TelegramBotProperties
import com.beksar.market.services.telegram.service.UsersService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TelegramBot(
    private val properties: TelegramBotProperties,
    private val usersService: UsersService
) : TelegramLongPollingBot(properties.token) {
    override fun getBotUsername(): String {
        return properties.name
    }

    override fun onUpdateReceived(update: Update?) {
        val user = update?.message?.from
        usersService.addUser(update?.message?.chatId, user?.userName, user?.firstName, user?.lastName)
    }
}