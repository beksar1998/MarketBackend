package com.beksar.market.services.ads.repository

import com.beksar.market.services.ads.models.entity.relations.AdvertCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AdvertCategoryRepository : JpaRepository<AdvertCategoryEntity, String> {

    fun deleteByAdvertIdAndCategoryId(advertId: String, categoryId: String)

    fun findByAdvertIdAndCategoryId(advertId: String, categoryId: String): AdvertCategoryEntity

    fun findAllByAdvertId(advertId: String): List<AdvertCategoryEntity>

    fun findAllByCategoryId(categoryId: String): List<AdvertCategoryEntity>
}