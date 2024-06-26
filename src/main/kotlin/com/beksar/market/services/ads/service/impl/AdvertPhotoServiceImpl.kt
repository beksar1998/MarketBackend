package com.beksar.market.services.ads.service.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.services.ads.models.dto.AddPhotoRequest
import com.beksar.market.services.ads.models.entity.relations.AdvertPhotoEntity
import com.beksar.market.services.ads.repository.AdvertPhotoRepository
import com.beksar.market.services.ads.service.AdvertPhotoService
import org.springframework.stereotype.Service

@Service
class AdvertPhotoServiceImpl(private val repository: AdvertPhotoRepository) : AdvertPhotoService {

    override fun uploadPhoto(advertId: String, addPhotoRequest: AddPhotoRequest) {
        val photos = addPhotoRequest.photos.map {
            AdvertPhotoEntity(
                advertId = advertId,
                photo = it
            )
        }
        repository.saveWithException(photos)
    }

    override fun delete(name: String) {
        val photo = repository.findByPhoto(name).checkNotNull()
        repository.deleteById(photo.id)
    }


}