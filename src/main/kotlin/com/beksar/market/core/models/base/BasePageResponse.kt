package com.beksar.market.core.models.base

class BasePageResponse<T>(
    val data: List<T>,
    val size: Int,
    val totalPages: Int,
    val totalElements: Long,
)

fun <T> List<T>.toPageResponse(): BasePageResponse<T> {
    return BasePageResponse(
        data = this,
        size = this.size,
        totalPages = 1,
        totalElements = this.size.toLong()
    )
}