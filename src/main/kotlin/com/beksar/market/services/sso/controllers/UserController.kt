package com.beksar.market.services.sso.controllers

import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.sso.models.dto.user.CreateUserRequest
import com.beksar.market.services.sso.models.dto.user.UserFullResponse
import com.beksar.market.services.sso.services.UserService
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.core.swagger.TokenController
import com.beksar.market.core.models.paging.SearchPagingParams
import com.beksar.market.services.sso.core.annotations.Roles
import com.beksar.market.services.sso.core.roles.Role
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/user")
@TokenController

class UserController(private val service: UserService) {

    @GetMapping
    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    fun users(@Param("") pagingParams: SearchPagingParams): BasePageResponse<UserFullResponse> {
        return service.users(pagingParams)
    }

    @GetMapping("{id}")
    @Authentication
    @Roles(Role.SUPER_ADMIN, Role.ADMIN)
    fun user(@PathVariable id: String): BaseResponse<UserFullResponse> {
        return service.user(id).response()
    }


    @PostMapping
    @Authentication
    @Roles(Role.SUPER_ADMIN)
    fun createUser(@RequestBody request: CreateUserRequest): BaseResponse<String> {
        return service.createUser(request).response()
    }


    @GetMapping("admin")
    fun createAdmin() {
        service.createAdmin()
    }


    @PutMapping("{id}/lock")
    fun lock(@PathVariable id: String): BaseResponse<Boolean> {
        service.lock(id)
        return true.response()
    }

    @PutMapping("{id}/unlock")
    fun unlock(@PathVariable id: String): BaseResponse<Boolean> {
        service.unlock(id)
        return true.response()
    }


    @PostMapping("{id}/manager")
    fun addManager(@PathVariable id: String): BaseResponse<Boolean> {
        service.addManager(id)
        return true.response()
    }

    @DeleteMapping("{id}/manager")
    fun deleteManager(@PathVariable id: String): BaseResponse<Boolean> {
        service.deleteManager(id)
        return true.response()
    }

    @DeleteMapping("{id}/user")
    fun deleteUser(@PathVariable id: String): BaseResponse<Boolean> {
        service.deleteUser(id)
        return true.response()
    }


}