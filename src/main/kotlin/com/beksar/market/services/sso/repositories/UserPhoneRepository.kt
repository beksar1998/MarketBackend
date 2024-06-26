package com.beksar.market.services.sso.repositories

import com.beksar.market.services.sso.models.entity.relations.UserPhoneEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserPhoneRepository : JpaRepository<UserPhoneEntity, String> {


    fun findAllByUserIdIn(userIds: List<String>): List<UserPhoneEntity>
    fun findAllByUserId(userId: String): List<UserPhoneEntity>

    fun existsByPhone(phone: String): Boolean
    fun existsByPhoneAndUserId(phone: String,userId: String): Boolean

}