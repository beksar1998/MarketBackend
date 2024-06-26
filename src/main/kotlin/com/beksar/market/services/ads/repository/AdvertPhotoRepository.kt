package com.beksar.market.services.ads.repository

import com.beksar.market.services.ads.models.entity.relations.AdvertPhotoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AdvertPhotoRepository : JpaRepository<AdvertPhotoEntity, String> {

    fun findAllByAdvertIdIn(advertIds: List<String>): List<AdvertPhotoEntity>

    fun findByPhoto(photo: String): AdvertPhotoEntity
}