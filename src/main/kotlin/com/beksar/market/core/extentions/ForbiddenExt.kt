@file:JvmName("UnauthorizedResponseExtKt")

package com.beksar.market.core.extentions

import com.beksar.market.core.errors.ForbiddenException

fun forbidden(message: String = "FORBIDDEN"): Nothing =
    throw ForbiddenException(message = message)