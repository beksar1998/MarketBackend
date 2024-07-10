package com.beksar.market.services.ads.service

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.AdvertSearchResponse
import com.beksar.market.services.ads.models.dto.SearchFilterParams

interface AdvertSearchService {

    fun search(params: SearchFilterParams): BasePageResponse<AdvertSearchResponse>
    fun myAdverts(userId: String, paging: PagingParams): BasePageResponse<AdvertSearchResponse>
}