package com.beksar.market.services.sso.impl.jwt


import com.beksar.market.core.extentions.unauthorized
import com.beksar.market.properties.JWTProperties
import com.beksar.market.services.sso.core.jwt.JWTService
import com.beksar.market.services.sso.core.roles.Role
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*

@Service
class JWTServiceImpl(private val jwtProperties: JWTProperties, private val httpServletRequest: HttpServletRequest) :
    JWTService {

    override val token: String?
        get() = parseJWT(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))

    private val secretKey: Key
        get() = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    override val userId: String
        get() = validateJwtToken(token.orEmpty()).userId() ?: unauthorized()
    override val userIdOrNull: String?
        get() = try {
            if (token.isNullOrBlank()) null else userId
        }catch (e:Exception){
            null
        }


    override fun generate(id: String, phone: String, email: String, roles: List<Role>): String {
        val claims: HashMap<String, Any?> = HashMap()
        claims["email"] = email
        claims["phone"] = phone
        claims["userId"] = id
        claims["roles"] = roles

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer(jwtProperties.issuerURL)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.expired))
            .signWith(secretKey)
            .compact()
    }

    override fun getSubject(token: String): String {
        return getClaims(token).subject
    }

    override fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    override fun parseJWT(header: String): String? {
        return if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            header.substring(7, header.length)
        } else null
    }

    override fun validateJwtToken(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).body
        } catch (e: SignatureException) {
            unauthorized("Invalid JWT signature")
        } catch (e: MalformedJwtException) {
            unauthorized("Invalid JWT token")
        } catch (e: ExpiredJwtException) {
            unauthorized("JWT token is expired")
        } catch (e: UnsupportedJwtException) {
            unauthorized("JWT token is unsupported")
        } catch (e: IllegalArgumentException) {
            unauthorized("JWT claims string is empty")
        } catch (e: Exception) {
            unauthorized("Invalid JWT")
        }
    }

    override fun roles(token: String): List<String> {
        return validateJwtToken(token).roles().orEmpty()
    }


    override fun roles(): List<String> {
        return validateJwtToken(token.orEmpty()).roles().orEmpty()
    }

    private fun Claims.email(): String? {
        return this["email"] as? String
    }

    private fun Claims.phone(): String? {
        return this["phone"] as? String
    }

    private fun Claims.roles(): List<String>? {
        return this["roles"] as List<String>?
    }

    private fun Claims.userId(): String? {
        return this["userId"] as? String
    }
}