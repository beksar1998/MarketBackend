package com.beksar.market.services.banner.service

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.banner.models.dto.AddBannerRequest
import com.beksar.market.services.banner.models.dto.BannerResponse
import com.beksar.market.services.banner.models.dto.UpdateBannerRequest
import com.beksar.market.services.banner.models.entity.BannerType

interface BannerService {

    fun banners(type: BannerType): List<BannerResponse>

    fun banners(type: BannerType, page: PagingParams): BasePageResponse<BannerResponse>

    fun banner(bannerId: String): BannerResponse

    fun add(banner: AddBannerRequest)

    fun update(bannerId: String, banner: UpdateBannerRequest)

    fun delete(bannerId: String)

}