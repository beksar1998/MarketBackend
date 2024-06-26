package com.beksar.market.services.ads.mapper

import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.entity.AdvertEntity

fun AdvertEntity.toResponse(): AdvertResponse {
    return AdvertResponse(
        id = id,
        description = description,
        status = status
    )
}