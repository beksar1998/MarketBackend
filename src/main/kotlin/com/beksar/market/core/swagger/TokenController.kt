package com.beksar.core.swagger

import io.swagger.v3.oas.annotations.security.SecurityRequirement

@SecurityRequirement(name = "Bearer Authentication")
annotation class TokenController
