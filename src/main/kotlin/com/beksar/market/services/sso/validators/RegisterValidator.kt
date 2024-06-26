package com.beksar.market.services.sso.validators

import com.beksar.market.core.errors.ValidationBuilder
import com.beksar.market.core.extentions.validationError
import com.beksar.market.services.sso.models.dto.register.RegisterEmailConfirmRequest
import com.beksar.market.services.sso.models.dto.register.RegisterEmailRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneConfirmRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneRequest


fun RegisterEmailRequest.validate() {
    val validator = ValidationBuilder()
    if (this.name.isNullOrBlank()) {
        validator.setError("name", "name mus not be empty or null")
    }
    //TODO check email
    if (this.email.isNullOrBlank()) {
        validator.setError("email", "name mus not be empty or null")
    }
    //TODO pass check
    if (this.password.isNullOrBlank()) {
        validator.setError("password", "password mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}


fun RegisterEmailConfirmRequest.validate() {
    val validator = ValidationBuilder()
    //TODO check email
    if (this.email.isNullOrBlank()) {
        validator.setError("email", "email mus not be empty or null")
    }
    if (this.confirmCode == null || this.confirmCode == 0) {
        validator.setError("confirmCode", "name mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun RegisterPhoneRequest.validate() {
    val validator = ValidationBuilder()

    if (this.phone.isNullOrBlank()) {
        validator.setError("phone", "email mus not be empty or null")
    } else if (this.phone.length != "77052190119".length) {
        validator.setError("phone", "phone length is no 11")
    }

    if (this.name.isNullOrBlank()) {
        validator.setError("name", "name mus not be empty or null")
    }
    //TODO pass check
    if (this.password.isNullOrBlank()) {
        validator.setError("password", "password mus not be empty or null")
    }

    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun RegisterPhoneConfirmRequest.validate() {
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
