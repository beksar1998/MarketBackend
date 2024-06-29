package com.beksar.market.services.banner.repository

import com.beksar.market.services.banner.models.entity.BannerEntity
import com.beksar.market.services.banner.models.entity.BannerType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<BannerEntity, String> {

    fun findAllByType(type: BannerType): List<BannerEntity>
    fun findAllByType(type: BannerType, pageable: Pageable): Page<BannerEntity>


}