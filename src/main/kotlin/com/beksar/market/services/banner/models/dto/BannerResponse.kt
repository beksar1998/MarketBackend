package com.beksar.market.services.banner.models.dto

import com.beksar.market.services.banner.models.entity.BannerType

class BannerResponse(
    val id: String,
    val type: BannerType,
    val title: String,
    val photo: String
)