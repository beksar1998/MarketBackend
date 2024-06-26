package com.beksar.market.core.models.exceptions

class HttpResponse(val message: String, val errors: Map<String, List<String>>)