package com.beksar.market.core.extentions

const val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

fun String.isValidEmail() =
    this.isNotEmpty() && this.matches(emailRegex.toRegex())