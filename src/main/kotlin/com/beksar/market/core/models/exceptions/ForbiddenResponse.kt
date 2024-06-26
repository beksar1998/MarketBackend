package com.beksar.market.core.models.exceptions

class ForbiddenResponse(
    val code: Int = 403,
    val message: String
)