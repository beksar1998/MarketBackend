package com.beksar.market.services.favourite.service

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.AdvertSearchResponse

interface FavouriteService {
    fun favourites(userId: String, pagingParams: PagingParams): BasePageResponse<AdvertSearchResponse>
    fun add(userId: String, advertId: String)
    fun delete(userId: String, advertId: String)
}