package com.beksar.market.services.sso.mapper

import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.models.dto.profile.AuthProfileResponse
import com.beksar.market.services.sso.models.dto.profile.ProfileResponse
import com.beksar.market.services.sso.models.entity.UserEntity


fun UserEntity.toProfile(roles: List<Role>, phones: List<String>): ProfileResponse {
    return ProfileResponse(
        name = this.name.orEmpty(),
        surname = this.surname.orEmpty(),
        email = this.email.orEmpty(),
        phone = this.phone.orEmpty(),
        roles = roles,
        photo = this.photo.orEmpty(),
        status = this.status,
        phones = phones,
    )
}

fun UserEntity.toProfile(
    token: String,
    refreshToken: String,
    roles: List<Role>,
    phones: List<String>
): AuthProfileResponse {
    return AuthProfileResponse(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        surname = this.surname.orEmpty(),
        email = this.email.orEmpty(),
        phone = this.phone.orEmpty(),
        token = token,
        refreshToken = refreshToken,
        roles = roles,
        status = this.status,
        phones = phones
    )
}
