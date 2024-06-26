package com.beksar.market.services.sso.core.annotations

import com.beksar.market.services.sso.core.roles.Role
import io.swagger.v3.oas.annotations.security.SecurityRequirement

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@SecurityRequirement(name = "Bearer Authentication")
annotation class Roles(vararg val roles: Role)
