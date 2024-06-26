package com.beksar.market.services.ads.mapper

import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.entity.AdvertEntity

fun AdvertEntity.toResponse(photos: List<String>): AdvertResponse {
    return AdvertResponse(
        id = id,
        title = title,
        description = description,
        photos = photos
    )
}