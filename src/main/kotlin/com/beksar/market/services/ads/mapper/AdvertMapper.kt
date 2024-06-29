package com.beksar.market.services.ads.mapper

import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.AdvertSearchResponse
import com.beksar.market.services.ads.models.entity.AdvertEntity

fun AdvertEntity.toResponse(): AdvertResponse {
    return AdvertResponse(
        id = id,
        description = description,
        status = status,
        date = date,
    )
}


fun AdvertEntity.toSearchResponse(
    isFavourite: Boolean,
    photos: List<String>
): AdvertSearchResponse {
    val advert = this
    return AdvertSearchResponse(
        id = advert.id,
        description = advert.description,
        photos = photos,
        date = advert.date,
        isFavourite = isFavourite
    )
}