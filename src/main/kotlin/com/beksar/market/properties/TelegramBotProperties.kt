package com.beksar.market.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "telegram")
class TelegramBotProperties(
    var name: String = "Market",
    var token: String = "",
)