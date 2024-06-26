package com.beksar.market.services.telegram.bot

import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
class TelegramBotInit(private val telegramBot: TelegramBot) {
    @EventListener(classes = [ContextRefreshedEvent::class])
    fun init() {
        val bot = TelegramBotsApi(DefaultBotSession::class.java)
        try {
            bot.registerBot(telegramBot)
        } catch (e: Exception) {
            println()
        }


    }
}