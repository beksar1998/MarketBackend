package com.beksar.market.services.ads.models.dto

import com.beksar.market.core.models.paging.PagingParams


class SearchFilterParams(
    val search: String? = null,
    val categoryId: String? = null,
    val sortField: String? = null,
    val sortDirection: String? = null,
    override val page: Int,
    override val size: Int
) : PagingParams(page, size)
