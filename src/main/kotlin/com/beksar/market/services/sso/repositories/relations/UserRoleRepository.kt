package com.beksar.market.services.sso.repositories.relations

import com.beksar.market.services.sso.models.entity.relations.UserRoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository : JpaRepository<UserRoleEntity, String> {

    fun findAllByUserId(id: String): List<UserRoleEntity>
    fun findAllByUserIdIn(id: List<String>): List<UserRoleEntity>

}