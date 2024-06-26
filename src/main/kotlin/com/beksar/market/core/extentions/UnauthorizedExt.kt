package com.beksar.market.core.extentions

import com.beksar.market.core.errors.UnauthorizedException

fun unauthorized(message: String = "UNAUTHORIZED"): Nothing = throw UnauthorizedException(message = message)