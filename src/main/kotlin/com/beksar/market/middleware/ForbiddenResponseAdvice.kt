package com.beksar.market.middleware

import com.beksar.market.core.errors.ForbiddenException
import com.beksar.market.core.models.exceptions.ForbiddenResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ForbiddenResponseAdvice {

    @ExceptionHandler(ForbiddenException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleErrorResponse(e: ForbiddenException): ForbiddenResponse {
        return ForbiddenResponse(message = e.message)
    }
}