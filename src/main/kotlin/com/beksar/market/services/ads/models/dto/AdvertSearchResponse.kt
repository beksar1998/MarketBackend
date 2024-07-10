package com.beksar.market.services.ads.models.dto

import com.beksar.market.services.ads.models.entity.AdvertStatus
import java.util.Date

class AdvertSearchResponse(
    val id: String,
    val description: String,
    val photos: List<String>,
    val date: Date,
    val isFavourite: Boolean,
    val viewed: Int,
    val status: AdvertStatus
)