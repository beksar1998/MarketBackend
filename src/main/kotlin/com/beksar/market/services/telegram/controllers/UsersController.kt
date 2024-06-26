package com.beksar.market.services.telegram.controllers

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.telegram.models.dto.UsersResponse
import com.beksar.market.services.telegram.service.UsersService
import jakarta.ws.rs.GET
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/users")
class UsersController(private val service: UsersService) {


    @GET
    fun users(@Param("") pagingParams: PagingParams): BasePageResponse<UsersResponse> {
        return service.users(pagingParams)
    }

}