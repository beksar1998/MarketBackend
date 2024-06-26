package com.beksar.market.services.telegram.service.impl

import com.beksar.market.services.telegram.bot.TelegramBot
import com.beksar.market.services.telegram.repository.UsersRepository
import com.beksar.market.services.telegram.service.TelegramBotService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class TelegramBotServiceImpl(private val repository: UsersRepository, private val telegramBot: TelegramBot) :
    TelegramBotService {
    override fun sendMessage(chatId: String, message: String) {
        val tgMessage = SendMessage()
        tgMessage.text = message
        tgMessage.enableHtml(true)
        tgMessage.chatId = chatId
        telegramBot.execute(tgMessage)
    }

    override fun sendMessage(message: String) {
        val users = repository.findAll().mapNotNull { it.chatId }
        users.forEach {
            val tgMessage = SendMessage()
            tgMessage.text = message
            tgMessage.enableHtml(true)
            tgMessage.chatId = it.toString()
            telegramBot.execute(tgMessage)
        }
    }
}