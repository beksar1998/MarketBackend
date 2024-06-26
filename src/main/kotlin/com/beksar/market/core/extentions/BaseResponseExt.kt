package com.beksar.market.core.extentions

import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.core.models.paging.PagingParams
import org.springframework.data.domain.*

/**
 * перевод в базовый ответ для пользователя
 */
fun <T> T.response(): BaseResponse<T> {
    return BaseResponse(this)
}


fun <T, K> Page<T>.paging(
    filter: (T) -> Boolean = { true },
    map: (T) -> K,
): BasePageResponse<K> {

    return BasePageResponse(
        size = this.size,
        totalPages = this.totalPages,
        totalElements = this.totalElements,
        data = this.content.filter(filter).map(map)
    )
}

fun PagingParams.toPageable(sort: Sort? = null): Pageable {
    return if (sort == null) PageRequest.of(page, size)
    else PageRequest.of(page, size, sort)
}

fun <T> List<T>.toPage(): Page<T> {
    return PageImpl(this)
}

fun String.direction(): Sort.Direction? {
    return when (this) {
        "ASC" -> Sort.Direction.ASC
        "asc" -> Sort.Direction.ASC
        "DESC" -> Sort.Direction.DESC
        "desc" -> Sort.Direction.DESC
        else -> null
    }
}