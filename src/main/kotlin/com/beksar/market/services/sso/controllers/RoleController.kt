package com.beksar.market.services.sso.controllers

import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.services.RolesService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/role")
class RoleController(private val service: RolesService) {

    @GetMapping
    fun users(): BaseResponse<List<Role>> {
        return service.roles().response()
    }


}