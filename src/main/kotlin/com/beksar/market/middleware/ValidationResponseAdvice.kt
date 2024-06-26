package com.beksar.market.middleware

import com.beksar.market.core.errors.ValidationException
import com.beksar.market.core.models.exceptions.ValidationResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ValidationResponseAdvice {

    @ExceptionHandler(ValidationException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleErrorResponse(e: ValidationException): ValidationResponse {
        return ValidationResponse(message = e.message, errors = e.errors)
    }
}