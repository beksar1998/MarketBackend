package com.beksar.market.services.telegram.models.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator


@Entity(name = "telegram_users")
class TelegramUserEntity(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String? = null,
    val chatId: Long? = null,
    val userName: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
)