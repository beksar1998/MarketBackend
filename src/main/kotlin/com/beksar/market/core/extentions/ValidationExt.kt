package com.beksar.market.core.extentions

import com.beksar.market.core.errors.ValidationError
import com.beksar.market.core.errors.ValidationException

fun validationError(
    message: String,
    errors: Map<String, List<String>>
): Nothing =
    throw ValidationException(message = message, errors = errors)

fun validationError(validationError: ValidationError): Nothing =
    throw ValidationException(message = validationError.message, errors = validationError.errors)