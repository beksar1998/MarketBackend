package com.beksar.market.services.ads.service.impl

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.SearchFilterParams
import com.beksar.market.services.ads.service.AdvertSearchService
import org.springframework.stereotype.Service


@Service
class AdvertSearchServiceImpl : AdvertSearchService {

    override fun search(params: SearchFilterParams): BasePageResponse<AdvertResponse> {
        throw RuntimeException()
    }
}