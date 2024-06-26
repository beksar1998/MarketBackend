package com.beksar.market.services.sso.services

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.core.models.paging.SearchPagingParams
import com.beksar.market.services.sso.models.dto.user.CreateUserRequest
import com.beksar.market.services.sso.models.dto.user.UserFullResponse
import org.springframework.data.domain.Page

interface UserService {
    fun users(searchPagingParams: SearchPagingParams): BasePageResponse<UserFullResponse>
    fun users(ids: List<String>): Page<UserFullResponse>
    fun createUser(request: CreateUserRequest): String
    fun findUserByPhone(phone: String): UserFullResponse
    fun findById(userId: String): UserFullResponse

    fun getUserFCMToken(userId: String): String?

    fun createAdmin()
    fun user(id: String): UserFullResponse
    fun lock(id: String)
    fun unlock(id: String)
    fun addManager(id: String)
    fun deleteManager(id: String)

    fun deleteUser(id: String)
}