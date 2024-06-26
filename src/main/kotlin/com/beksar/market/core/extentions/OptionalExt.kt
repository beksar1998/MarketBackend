package com.beksar.market.core.extentions


import com.beksar.market.core.errors.HttpError
import com.beksar.market.core.errors.NotNullError
import java.util.*

fun <T> Optional<T>.checkNotNull(httpError: HttpError = NotNullError): T {
    if (!isPresent) {
        httpError(httpError)
    }
    return get()
}

fun <T> T?.checkNotNull(httpError: HttpError = NotNullError): T {
    if (this == null) {
        httpError(httpError)
    }
    return this
}