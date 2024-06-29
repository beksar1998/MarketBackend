package com.beksar.market.services.favourite.controllers

import com.beksar.core.swagger.TokenController
import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.AdvertSearchResponse
import com.beksar.market.services.favourite.service.FavouriteService
import com.beksar.market.services.sso.core.jwt.JWTService
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*


@TokenController
@RestController
@RequestMapping("api/v1/favourite")
class FavouriteController(
    private val service: FavouriteService,
    private val jwtService: JWTService
) {


    @GetMapping
    fun favourites(@Param("") pagingParams: PagingParams): BasePageResponse<AdvertSearchResponse> {
        val userId = jwtService.userId
        return service.favourites(userId,pagingParams)
    }

    @PostMapping("{advertId}")
    fun add(@PathVariable advertId: String): BaseResponse<Boolean> {
        val userId = jwtService.userId
        service.add(userId, advertId)
        return true.response()

    }

    @DeleteMapping("{advertId}")
    fun delete(@PathVariable advertId: String): BaseResponse<Boolean> {
        val userId = jwtService.userId
        service.delete(userId, advertId)
        return true.response()

    }
}