package com.beksar.market.services.ads.service

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.SearchPagingParams
import com.beksar.market.services.ads.models.dto.AddOrUpdateAdvertRequest
import com.beksar.market.services.ads.models.dto.AdvertResponse

interface AdvertService {
    fun delete(productId: String)
    fun add(request: AddOrUpdateAdvertRequest)
    fun update(advertId: String, request: AddOrUpdateAdvertRequest)
    fun product(advertId: String): AdvertResponse
    fun products(searchParams: SearchPagingParams): BasePageResponse<AdvertResponse>
}