package com.beksar.market.services.telegram.mapper

import com.beksar.market.services.telegram.models.dto.UsersResponse
import com.beksar.market.services.telegram.models.entity.TelegramUserEntity

fun TelegramUserEntity.toUserResponse(): UsersResponse {
    return UsersResponse(
        userName = this.userName.orEmpty(),
        lastName = this.lastName.orEmpty(),
        firstName = this.firstName.orEmpty(),
    )
}