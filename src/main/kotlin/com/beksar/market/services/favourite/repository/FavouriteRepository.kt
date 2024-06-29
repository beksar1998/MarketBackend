package com.beksar.market.services.favourite.repository

import com.beksar.market.services.favourite.models.entity.FavouriteEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface FavouriteRepository : JpaRepository<FavouriteEntity, String> {

    fun deleteByUserIdAndAdvertId(userId: String, advertId: String)

    fun findAllByUserId(userId: String, pageable: Pageable): Page<FavouriteEntity>
    fun findAllByUserId(userId: String): List<FavouriteEntity>

}