package com.beksar.market.core.models.paging

import com.beksar.market.services.ads.models.entity.AdvertStatus

class SearchPagingParams(
    val search: String,
    override val page: Int,
    override val size: Int,
    val sortField: String? = null,
    val sortDirection: String? = null,
    val status: AdvertStatus? = null,
) : PagingParams(page, size)