package com.beksar.market.services.ads.service

import com.beksar.market.services.category.models.dto.CategoryResponse


interface AdvertCategoryService {
    fun bind(advertId: String, categoryId: String)
    fun unBind(advertId: String, categoryId: String)
    fun categories(advertId: String): List<CategoryResponse>
}