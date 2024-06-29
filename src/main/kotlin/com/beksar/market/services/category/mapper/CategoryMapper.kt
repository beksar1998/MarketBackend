package com.beksar.market.services.category.mapper

import com.beksar.market.services.category.models.dto.CategoryResponse
import com.beksar.market.services.category.models.entity.CategoryEntity

fun CategoryEntity.toResponse(child: List<CategoryResponse> = emptyList()): CategoryResponse {
    return CategoryResponse(
        id = this.id,
        title = this.title,
        parentId = this.parentId,
        child = child,
        photo = this.photo
    )
}