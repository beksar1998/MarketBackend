package com.beksar.market.services.ads.models.dto

import java.util.Date

class AdvertSearchResponse(
    val id: String,
    val description: String,
    val photos: List<String>,
    val date: Date,
    val isFavourite : Boolean
)