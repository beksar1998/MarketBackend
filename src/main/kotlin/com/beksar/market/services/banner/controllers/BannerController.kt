package com.beksar.market.services.banner.controllers

import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.banner.models.dto.AddBannerRequest
import com.beksar.market.services.banner.models.dto.BannerResponse
import com.beksar.market.services.banner.models.dto.UpdateBannerRequest
import com.beksar.market.services.banner.models.entity.BannerType
import com.beksar.market.services.banner.service.BannerService
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/banner")
class BannerController(private val service: BannerService) {


    @GetMapping
    fun banners(
        @Param("type") type: BannerType,
        @Param("") pagingParams: PagingParams
    ): BasePageResponse<BannerResponse> {
        return service.banners(type, pagingParams)
    }

    @GetMapping("mobile")
    fun mobile(@Param("type") type: BannerType): BaseResponse<List<BannerResponse>> {
        return service.banners(type).response()
    }


    @PostMapping
    fun add(
        @RequestBody request: AddBannerRequest
    ): BaseResponse<Boolean> {
        service.add(request)
        return true.response()
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody request: UpdateBannerRequest
    ): BaseResponse<Boolean> {
        service.update(id, request)
        return true.response()
    }

    @DeleteMapping("{id}")
    fun mobile(@PathVariable("id") id: String): BaseResponse<Boolean> {
        service.delete(id)
        return true.response()
    }


}