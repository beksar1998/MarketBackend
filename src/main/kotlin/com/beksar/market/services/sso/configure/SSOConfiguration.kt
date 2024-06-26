package com.beksar.market.services.sso.configure

import com.beksar.market.properties.EncryptProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SSOConfiguration(
    private val encryptProperties: EncryptProperties
) {
    @Bean
    fun bCryptPasswordEncoder(): PasswordEncoder = BCryptPasswordEncoder(encryptProperties.strength)
}