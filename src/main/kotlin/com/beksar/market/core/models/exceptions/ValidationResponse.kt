package com.beksar.market.core.models.exceptions

class ValidationResponse(val message: String, val errors: Map<String, List<String>>)