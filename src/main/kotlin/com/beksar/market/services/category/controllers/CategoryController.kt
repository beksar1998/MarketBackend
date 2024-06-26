package com.beksar.market.services.category.controllers

import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.category.models.dto.AddOrUpdateCategoryRequest
import com.beksar.market.services.category.models.dto.CategoryResponse
import com.beksar.market.services.category.service.CategoryService
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.market.services.sso.core.annotations.Roles
import com.beksar.market.services.sso.core.roles.Role
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Paths
import javax.imageio.ImageIO


@RestController
@RequestMapping("api/v1/category")
class CategoryController(
    private val service: CategoryService
) {


    @GetMapping
    fun categories(
        @Param("recursive") recursive: Boolean,
        @Param("parentId") parentId: String?,
        @Param("") pagingParams: PagingParams
    ): BasePageResponse<CategoryResponse> {
        return service.categories(
            recursive = recursive,
            parentId = parentId,
            pagingParams = pagingParams
        )
    }


    @GetMapping("{id}")
    fun category(
        @PathVariable id: String,
        @Param("recursive") recursive: Boolean
    ): BaseResponse<CategoryResponse> {
        return service.category(id = id, recursive = recursive).response()
    }

    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PostMapping
    fun add(
        @RequestBody addOrUpdateCategoryRequest: AddOrUpdateCategoryRequest
    ): BaseResponse<Boolean> {
        service.add(addOrUpdateCategoryRequest)
        return true.response()
    }

    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @PutMapping("{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody addOrUpdateCategoryRequest: AddOrUpdateCategoryRequest
    ): BaseResponse<Boolean> {
        service.update(id, addOrUpdateCategoryRequest)
        return true.response()
    }


    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String): BaseResponse<Boolean> {
        service.delete(id)
        return true.response()
    }


}