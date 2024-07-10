package com.beksar.market.services.ads.controllers

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.AdvertSearchResponse
import com.beksar.market.services.ads.models.dto.SearchFilterParams
import com.beksar.market.services.ads.service.AdvertSearchService
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.market.services.sso.core.jwt.JWTService
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/advert")
class AdvertSearchController(
    private val service: AdvertSearchService,
    private val jwtService: JWTService
) {


    @GetMapping("search")
    fun search(
        @Param("") params: SearchFilterParams
    ): BasePageResponse<AdvertSearchResponse> {
        return service.search(params)
    }

    @Authentication
    @GetMapping("my")
    fun my(@Param("") paging: PagingParams): BasePageResponse<AdvertSearchResponse> {
        val userId = jwtService.userId
        return service.myAdverts(userId, paging)
    }
}