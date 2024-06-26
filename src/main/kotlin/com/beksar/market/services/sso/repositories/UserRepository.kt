package com.beksar.market.services.sso.repositories

import com.beksar.market.services.sso.models.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?
    fun findByPhone(phone: String): UserEntity?


    fun findAllByIdIn(ids: List<String>): List<UserEntity>


    @Query(
        value = "select * from users where email like %?1% or name like %?1%",
        nativeQuery = true
    )
    fun search(
        search: String,
        pageable: Pageable
    ): Page<UserEntity>

}
