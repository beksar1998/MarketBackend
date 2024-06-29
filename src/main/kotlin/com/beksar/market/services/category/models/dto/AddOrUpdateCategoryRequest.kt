package com.beksar.market.services.category.models.dto

class AddOrUpdateCategoryRequest(
    val title: String,
    val parentId: String?,
    val photo: String
)