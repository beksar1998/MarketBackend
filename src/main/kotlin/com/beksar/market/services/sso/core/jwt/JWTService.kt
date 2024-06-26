package com.beksar.market.services.sso.core.jwt

import com.beksar.market.services.sso.core.roles.Role
import io.jsonwebtoken.Claims

interface JWTService {

    val token : String?
    val userId: String
    val userIdOrNull: String?
    fun generate(id: String, phone: String, email: String, roles: List<Role>): String
    fun getSubject(token: String): String
    fun getClaims(token: String): Claims
    fun parseJWT(header: String): String?
    fun validateJwtToken(token: String): Claims
    fun roles(token: String): List<String>
    fun roles(): List<String>
}