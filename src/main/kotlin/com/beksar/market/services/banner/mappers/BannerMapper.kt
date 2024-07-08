package com.beksar.market.services.banner.mappers

import com.beksar.market.services.banner.models.dto.BannerResponse
import com.beksar.market.services.banner.models.entity.BannerEntity

fun BannerEntity.toResponse(): BannerResponse {
    return BannerResponse(
        id = this.id,
        title = this.title,
        photo = this.photo,
        type = this.type
    )
}