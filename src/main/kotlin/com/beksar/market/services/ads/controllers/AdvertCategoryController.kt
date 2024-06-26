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
@RequestMapping("api/v1/product")
class AdvertCategoryController(private val service: AdvertCategoryService) {

    @Authentication
    @GetMapping("{productId}/category")
    fun binds(
        @PathVariable productId: String
    ): BaseResponse<List<CategoryResponse>> {
        return service.categories(productId).response()
    }


    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PostMapping("{productId}/category/{categoryId}")
    fun bind(
        @PathVariable productId: String,
        @PathVariable categoryId: String
    ): BaseResponse<Boolean> {
        service.bind(productId, categoryId)
        return true.response()
    }


    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @DeleteMapping("{productId}/category/{categoryId}")
    fun unBind(
        @PathVariable productId: String,
        @PathVariable categoryId: String
    ): BaseResponse<Boolean> {
        service.unBind(productId, categoryId)
        return true.response()
    }
}