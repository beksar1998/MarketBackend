package com.beksar.market.core.models.paging

class SearchPagingParams(
    val search: String,
    override val page: Int,
    override val size: Int,
    val sortField: String? = null,
    val sortDirection: String? = null,
) : PagingParams(page, size)