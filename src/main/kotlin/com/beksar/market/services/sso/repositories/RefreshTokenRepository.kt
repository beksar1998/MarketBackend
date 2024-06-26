package com.beksar.market.services.sso.repositories

import com.beksar.market.services.sso.models.entity.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, String> {
    fun findByUserIdAndRefreshToken(userId: String, refreshToken: String): RefreshTokenEntity?

    @Transactional
    @Modifying
    @Query("DELETE FROM refresh_token WHERE userId = ?1")
    fun deleteAllByUserId(id: String)
}