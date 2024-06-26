package com.beksar.market.services.sso.impl.annotations

import com.beksar.market.services.sso.core.jwt.JWTService
import com.beksar.market.core.extentions.unauthorized
import com.beksar.market.services.sso.core.annotations.Authentication
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthenticationInterceptor(private val jwtService: JWTService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        if (handler is HandlerMethod) {
            val authentication: Authentication? = handler.getMethodAnnotation(Authentication::class.java)
            if (authentication != null) {
                val token = parseJwt(request)
                if (token.isNullOrBlank()) {
                    unauthorized()
                }
            }
        }
        return true
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""
        return jwtService.parseJWT(headerAuth)
    }
}