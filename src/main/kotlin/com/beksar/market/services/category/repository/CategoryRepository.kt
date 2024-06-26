package com.beksar.market.services.category.repository

import com.beksar.market.services.category.models.entity.CategoryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<CategoryEntity, String> {

    fun findAllByParentIdIsNull(pageable: Pageable): Page<CategoryEntity>

    fun findAllByIdIn(ids: List<String>): List<CategoryEntity>
    fun findAllByIdIn(ids: List<String>, pageable: Pageable): Page<CategoryEntity>

    fun findAllByParentIdIn(parentIds: List<String>): List<CategoryEntity>
    fun findAllByParentIdIn(parentIds: List<String>, pageable: Pageable): Page<CategoryEntity>

    fun findAllByParentId(parentId: String): List<CategoryEntity>
    fun findAllByParentId(parentId: String, pageable: Pageable): Page<CategoryEntity>
}