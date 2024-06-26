package com.beksar.market.services.ads.controllers


import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.core.models.paging.SearchPagingParams
import com.beksar.market.services.ads.models.dto.AddOrUpdateAdvertRequest
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.service.AdvertService
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.market.services.sso.core.annotations.Roles
import com.beksar.market.services.sso.core.roles.Role
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/product")
class AdvertController(private val service: AdvertService) {


    @GetMapping
    fun products(
        @Param("") searchPagingParams: SearchPagingParams
    ): BasePageResponse<AdvertResponse> {
        return service.products(searchPagingParams)
    }


    @GetMapping("{productId}")
    fun product(@PathVariable productId: String): BaseResponse<AdvertResponse> {
        return service.product(productId).response()
    }

    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PostMapping
    fun add(
        @RequestBody addOrUpdateProductRequest: AddOrUpdateAdvertRequest
    ): BaseResponse<Boolean> {
        service.add(addOrUpdateProductRequest)
        return true.response()
    }

    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PutMapping("{productId}")
    fun update(
        @PathVariable productId: String,
        @RequestBody addOrUpdateProductRequest: AddOrUpdateAdvertRequest
    ): BaseResponse<Boolean> {
        service.update(productId, addOrUpdateProductRequest)
        return true.response()
    }


    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @DeleteMapping("{productId}")
    fun delete(@PathVariable productId: String): BaseResponse<Boolean> {
        service.delete(productId)
        return true.response()
    }
}