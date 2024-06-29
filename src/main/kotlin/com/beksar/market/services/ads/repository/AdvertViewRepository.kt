package com.beksar.market.services.ads.repository

import com.beksar.market.services.ads.models.entity.relations.AdvertViewEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AdvertViewRepository : JpaRepository<AdvertViewEntity, String> {

    fun findAllByAdvertIdIn(advertId: List<String>): List<AdvertViewEntity>
    fun findAllByAdvertIdIn(advertId: List<String>, pageable: Pageable): Page<AdvertViewEntity>
}