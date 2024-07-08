package com.beksar.market.services.favourite.repository

import com.beksar.market.services.favourite.models.entity.FavouriteEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface FavouriteRepository : JpaRepository<FavouriteEntity, String> {

    @Transactional
    fun deleteByUserIdAndAdvertId(userId: String, advertId: String)

    fun findAllByUserId(userId: String, pageable: Pageable): Page<FavouriteEntity>
    fun findAllByUserId(userId: String): List<FavouriteEntity>

}