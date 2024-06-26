package com.beksar.market.core.errors

class ValidationException(override val message: String, val errors: Map<String, List<String>>) : RuntimeException()

class ValidationError(val message: String, val errors: Map<String, List<String>>)


class ValidationBuilder() {
    private val errors = mutableMapOf<String, List<String>>()

    fun setError(key: String, value: String) {
        if (errors.containsKey(key)) {
            val values = errors[key].orEmpty()
            errors[key] = values + listOf(value)
        } else {
            errors[key] = listOf(value)
        }
    }

    fun hasErrors() = errors.isNotEmpty()

    fun error() = ValidationError("Validation", errors)

}