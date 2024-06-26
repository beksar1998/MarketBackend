package com.beksar.market.services.category.service

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.category.models.dto.AddOrUpdateCategoryRequest
import com.beksar.market.services.category.models.dto.CategoryResponse


interface CategoryService {

    fun categories(
        parentId: String?,
        recursive: Boolean,
        pagingParams: PagingParams
    ): BasePageResponse<CategoryResponse>

    fun category(id: String, recursive: Boolean): CategoryResponse

    fun delete(id: String)

    fun update(id: String, request: AddOrUpdateCategoryRequest)

    fun add(request: AddOrUpdateCategoryRequest)

    fun categoriesById(ids: List<String>): List<CategoryResponse>
}