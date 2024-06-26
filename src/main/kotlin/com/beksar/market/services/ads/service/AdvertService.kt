package com.beksar.market.services.ads.service

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.SearchPagingParams
import com.beksar.market.services.ads.models.dto.AddOrUpdateAdvertRequest
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.entity.AdvertStatus

interface AdvertService {
    fun delete(advertId: String)
    fun add(request: AddOrUpdateAdvertRequest)
    fun update(advertId: String, request: AddOrUpdateAdvertRequest)
    fun advert(advertId: String): AdvertResponse
    fun adverts(searchParams: SearchPagingParams): BasePageResponse<AdvertResponse>
    fun changeStatus(id: String, status: AdvertStatus)
}