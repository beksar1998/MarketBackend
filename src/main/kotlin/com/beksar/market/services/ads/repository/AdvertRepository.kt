package com.beksar.market.services.ads.repository

import com.beksar.market.services.ads.models.entity.AdvertEntity
import com.beksar.market.services.ads.models.entity.AdvertStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AdvertRepository : JpaRepository<AdvertEntity, String> {

    fun findAllByDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        name: String,
        nameRU: String,
        nameEN: String,
        pageable: Pageable
    ): Page<AdvertEntity>

    fun findAllByDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndStatus(
        name: String,
        nameRU: String,
        nameEN: String,
        status: AdvertStatus,
        pageable: Pageable
    ): Page<AdvertEntity>

    fun findAllByIdIn(ids: List<String>, pageable: Pageable): Page<AdvertEntity>
    fun findAllByStatus(status : AdvertStatus, pageable: Pageable): Page<AdvertEntity>
}
