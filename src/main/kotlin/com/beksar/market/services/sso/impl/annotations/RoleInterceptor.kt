package com.beksar.market.services.sso.impl.annotations

import com.beksar.market.services.sso.core.jwt.JWTService
import com.beksar.market.core.extentions.forbidden
import com.beksar.market.core.extentions.unauthorized
import com.beksar.market.services.sso.core.annotations.Roles
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class RoleInterceptor(private val jwtService: JWTService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        if (handler is HandlerMethod) {
            val roles: Roles? = handler.getMethodAnnotation(Roles::class.java)
            if (roles != null) {
                val token = parseJwt(request) ?: unauthorized()
                val userRoles = jwtService.roles(token)
                val hasRole = userRoles.any { roles.roles.map { it.name }.contains(it) }
                if (!hasRole) forbidden()
            }
        }
        return true
    }


    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""
        return jwtService.parseJWT(headerAuth)
    }
}