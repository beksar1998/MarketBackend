package com.beksar.market.services.ads.controllers

import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.ads.models.dto.AddPhotoRequest
import com.beksar.market.services.ads.service.AdvertPhotoService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/advert/photo")
class AdvertPhotoController(private val service: AdvertPhotoService) {


    @PostMapping("{productId}")
    fun uploadPhoto(
        @PathVariable productId: String,
        @RequestBody addPhotoRequest: AddPhotoRequest,
    ): BaseResponse<Boolean> {
        service.uploadPhoto(productId, addPhotoRequest)
        return true.response()
    }

    @DeleteMapping("{name}")
    fun deletePhoto(@PathVariable name: String): BaseResponse<Boolean> {
        service.delete(name)
        return true.response()
    }


}