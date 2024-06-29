package com.beksar.market.services.banner.models.dto

import com.beksar.market.services.banner.models.entity.BannerType

class AddBannerRequest(
    val title: String,
    val type: BannerType,
    val photo: String
)