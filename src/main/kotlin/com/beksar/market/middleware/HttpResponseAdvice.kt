package com.beksar.market.middleware

import com.beksar.market.core.errors.HttpException
import com.beksar.market.core.models.exceptions.HttpResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class HttpResponseAdvice {

    @ExceptionHandler(HttpException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleErrorResponse(e: HttpException): HttpResponse {
        return HttpResponse(message = e.message, errors = e.errors)
    }
}