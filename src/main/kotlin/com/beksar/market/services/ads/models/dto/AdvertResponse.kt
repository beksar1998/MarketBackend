package com.beksar.market.services.ads.models.dto

import com.beksar.market.services.ads.models.entity.AdvertStatus
import java.util.Date

class AdvertResponse(
    val id : String,
    val description : String,
    val status: AdvertStatus,
    val date : Date
)