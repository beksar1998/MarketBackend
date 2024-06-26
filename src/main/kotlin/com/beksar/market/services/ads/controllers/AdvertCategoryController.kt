package com.beksar.market.services.ads.controllers


import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.ads.service.AdvertCategoryService
import com.beksar.market.services.category.models.dto.CategoryResponse
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.market.services.sso.core.annotations.Roles
import com.beksar.market.services.sso.core.roles.Role
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/advert")
class AdvertCategoryController(private val service: AdvertCategoryService) {

    @Authentication
    @GetMapping("{advertId}/category")
    fun binds(
        @PathVariable advertId: String
    ): BaseResponse<List<CategoryResponse>> {
        return service.categories(advertId).response()
    }


    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PostMapping("{advertId}/category/{categoryId}")
    fun bind(
        @PathVariable advertId: String,
        @PathVariable categoryId: String
    ): BaseResponse<Boolean> {
        service.bind(advertId, categoryId)
        return true.response()
    }


    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @DeleteMapping("{advertId}/category/{categoryId}")
    fun unBind(
        @PathVariable advertId: String,
        @PathVariable categoryId: String
    ): BaseResponse<Boolean> {
        service.unBind(advertId, categoryId)
        return true.response()
    }
}