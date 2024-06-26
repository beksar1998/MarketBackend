package com.beksar.market.services.ads.repository

import com.beksar.market.services.ads.models.entity.AdvertEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AdvertRepository : JpaRepository<AdvertEntity, String> {

    fun findAllByTitleContainingIgnoreCase(name: String,pageable: Pageable): Page<AdvertEntity>

    fun findAllByIdIn(ids: List<String>, pageable: Pageable): Page<AdvertEntity>
}
