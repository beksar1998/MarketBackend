package com.beksar.market.core.extentions

import com.beksar.market.core.errors.CommonError
import com.beksar.market.core.errors.HttpError
import com.beksar.market.core.errors.HttpException

fun httpError(
    message: String,
    errors: Map<String, List<String>>
): Nothing =
    throw HttpException(message = message, errors = errors)

fun httpError(httpError: HttpError = CommonError): Nothing =
    throw HttpException(message = httpError.message, errors = httpError.errors)