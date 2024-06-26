package com.beksar.market.services.ads.controllers

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.AdvertSearchResponse
import com.beksar.market.services.ads.models.dto.SearchFilterParams
import com.beksar.market.services.ads.service.AdvertSearchService
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/advert/search")
class AdvertSearchController(private val service: AdvertSearchService) {


    @GetMapping("")
    fun search(
        @Param("") params: SearchFilterParams
    ): BasePageResponse<AdvertSearchResponse> {
        return service.search(params)
    }
}