package com.beksar.market.services.telegram.service.impl

import com.beksar.market.core.extentions.paging
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.core.extentions.toPageable
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.telegram.mapper.toUserResponse
import com.beksar.market.services.telegram.models.dto.UsersResponse
import com.beksar.market.services.telegram.models.entity.TelegramUserEntity
import com.beksar.market.services.telegram.repository.UsersRepository
import com.beksar.market.services.telegram.service.UsersService
import org.springframework.stereotype.Service

@Service
class UsersServiceImpl(private val repository: UsersRepository) : UsersService {
    override fun users(pagingParams: PagingParams): BasePageResponse<UsersResponse> {
        val users = repository.findAll(pagingParams.toPageable())
        return users.paging { it.toUserResponse() }
    }

    override fun addUser(chatId: Long?, userName: String?, firstName: String?, lastName: String?) {
        if (repository.existsByChatId(chatId ?: 0) || repository.existsByUserName(userName.orEmpty())) {
            return
        }

        val user = TelegramUserEntity(
            chatId = chatId,
            userName = userName,
            lastName = lastName,
            firstName = firstName
        )
        repository.saveWithException(user)
    }


}