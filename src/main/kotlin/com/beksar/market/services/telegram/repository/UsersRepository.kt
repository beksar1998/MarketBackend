package com.beksar.market.services.telegram.repository

import com.beksar.market.services.telegram.models.entity.TelegramUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<TelegramUserEntity, String> {
    fun existsByChatId(id: Long): Boolean
    fun existsByUserName(userName: String): Boolean
}