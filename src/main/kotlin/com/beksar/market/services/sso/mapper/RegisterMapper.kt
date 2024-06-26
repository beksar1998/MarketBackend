package com.beksar.market.services.sso.mapper

import com.beksar.market.core.utils.ConfirmCodeGenerator
import com.beksar.market.services.sso.models.dto.register.RegisterEmailRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneRequest
import com.beksar.market.services.sso.models.dto.user.CreateUserRequest
import com.beksar.market.services.sso.models.entity.UserEntity
import java.util.*

fun RegisterEmailRequest.toEntity(password: String): UserEntity {
    return UserEntity(
        email = this.email,
        name = this.name,
        emailConfirmCode = ConfirmCodeGenerator.generate(),
        emailConfirmCodeDate = Date(),
        password = password,
    )
}


fun RegisterPhoneRequest.toEntity(password: String): UserEntity {
    return UserEntity(
        phone = this.phone,
        name = this.name,
        phoneConfirmCode = ConfirmCodeGenerator.generate(),
        phoneConfirmCodeDate = Date(),
        password = password,
    )
}

fun CreateUserRequest.toEntity(password: String): UserEntity {
    return UserEntity(
        phone = this.phone,
        name = this.name,
        surname = this.surname,
        password = password,
    )
}