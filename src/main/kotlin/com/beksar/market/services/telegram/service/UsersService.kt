package com.beksar.market.services.telegram.service

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.telegram.models.dto.UsersResponse

interface UsersService {

    fun users(pagingParams: PagingParams): BasePageResponse<UsersResponse>
    fun addUser(chatId: Long?, userName: String?, firstName: String?, lastName: String?)


}