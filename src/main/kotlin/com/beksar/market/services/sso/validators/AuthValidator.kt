package com.beksar.market.services.sso.validators

import com.beksar.market.core.errors.ValidationBuilder
import com.beksar.market.core.extentions.validationError
import com.beksar.market.services.models.dto.auth.AuthSmsRequest
import com.beksar.market.services.sso.models.dto.auth.AuthEmailRequest
import com.beksar.market.services.sso.models.dto.auth.AuthPhoneRequest
import com.beksar.market.services.sso.models.dto.auth.AuthSmsConfirmRequest


fun AuthEmailRequest.validate() {
    val validator = ValidationBuilder()
    //TODO check email
    if (this.email.isNullOrBlank()) {
        validator.setError("email", "email mus not be empty or null")
    }
    if (this.password.isNullOrBlank()) {
        validator.setError("password", "password mus not be empty or null")
    }

    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun AuthPhoneRequest.validate() {
    val validator = ValidationBuilder()
    if (this.phone.isNullOrBlank()) {
        validator.setError("phone", "phone mus not be empty or null")
    }
    if (this.password.isNullOrBlank()) {
        validator.setError("password", "password mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun AuthSmsRequest.validate() {
    val validator = ValidationBuilder()
    //TODO check email
    if (this.phone.isNullOrBlank()) {
        validator.setError("phone", "phone mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun AuthSmsConfirmRequest.validate() {
    val validator = ValidationBuilder()
    if (this.phone.isNullOrBlank()) {
        validator.setError("phone", "phone mus not be empty or null")
    }
    if (this.code == null) {
        validator.setError("code", "code mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}