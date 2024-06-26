package com.beksar.market.middleware

import com.beksar.market.core.errors.UnauthorizedException
import com.beksar.market.core.models.exceptions.UnauthorizedResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class UnauthorizedResponseAdvice {

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleErrorResponse(e: UnauthorizedException): UnauthorizedResponse {
        return UnauthorizedResponse(message = e.message)
    }
}