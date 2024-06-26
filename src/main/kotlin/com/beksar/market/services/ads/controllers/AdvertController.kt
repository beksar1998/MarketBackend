package com.beksar.market.services.ads.controllers


import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.core.models.paging.SearchPagingParams
import com.beksar.market.services.ads.models.dto.AddOrUpdateAdvertRequest
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.entity.AdvertStatus
import com.beksar.market.services.ads.service.AdvertService
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.market.services.sso.core.annotations.Roles
import com.beksar.market.services.sso.core.roles.Role
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/advert")
class AdvertController(private val service: AdvertService) {


    @GetMapping
    fun adverts(
        @Param("") searchPagingParams: SearchPagingParams
    ): BasePageResponse<AdvertResponse> {
        return service.adverts(searchPagingParams)
    }


    @GetMapping("{advertId}")
    fun advert(@PathVariable advertId: String): BaseResponse<AdvertResponse> {
        return service.advert(advertId).response()
    }

    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PostMapping
    fun add(
        @RequestBody addOrUpdateAdvertRequest: AddOrUpdateAdvertRequest
    ): BaseResponse<Boolean> {
        service.add(addOrUpdateAdvertRequest)
        return true.response()
    }

    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PutMapping("{advertId}")
    fun update(
        @PathVariable advertId: String,
        @RequestBody addOrUpdateAdvertRequest: AddOrUpdateAdvertRequest
    ): BaseResponse<Boolean> {
        service.update(advertId, addOrUpdateAdvertRequest)
        return true.response()
    }

    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PutMapping("{advertId}/status")
    fun updateStatus(
        @PathVariable advertId: String,
        @Param("status") status: AdvertStatus
    ): BaseResponse<Boolean> {
        service.changeStatus(advertId, status)
        return true.response()
    }


    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @DeleteMapping("{advertId}")
    fun delete(@PathVariable advertId: String): BaseResponse<Boolean> {
        service.delete(advertId)
        return true.response()
    }
}