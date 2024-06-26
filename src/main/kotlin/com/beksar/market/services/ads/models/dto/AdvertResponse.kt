package com.beksar.market.services.ads.models.dto

import com.beksar.market.services.ads.models.entity.AdvertStatus

class AdvertResponse(
    val id : String,
    val description : String,
    val status: AdvertStatus
)