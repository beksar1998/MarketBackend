package com.beksar.market.services.ads.service

import com.beksar.market.services.ads.models.dto.AddPhotoRequest

interface AdvertPhotoService {
    fun uploadPhoto(advertId: String, addPhotoRequest: AddPhotoRequest)
    fun delete(name: String)
}