package com.beksar.market.services.sso.core.annotations

import io.swagger.v3.oas.annotations.security.SecurityRequirement

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@SecurityRequirement(name = "Bearer Authentication")
annotation class Authentication


