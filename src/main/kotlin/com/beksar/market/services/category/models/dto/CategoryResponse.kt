package com.beksar.market.services.category.models.dto

data class CategoryResponse(
    val id: String,
    val title: String,
    val parentId: String?,
    val photo: String,
    val child: List<CategoryResponse>
)