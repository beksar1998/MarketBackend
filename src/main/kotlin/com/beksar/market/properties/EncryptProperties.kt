package com.beksar.market.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "encrypt")
class EncryptProperties(
    var strength: Int = 10
)