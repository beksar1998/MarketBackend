package com.beksar.market.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

private const val DAY = 86400000L

@Component
@ConfigurationProperties(prefix = "jwt")
class JWTProperties(
    var secret: String = "PseudoSecret-Pseudosecret-IMPORTANT-Please-Use-Ur-Own-Key-IMPORTANT",
    var expired: Long = DAY * 31 * 12 * 2,
    var refreshTime: Long = DAY * 31 * 12 * 4,
    var issuerURL: String = "issuerURL",
)