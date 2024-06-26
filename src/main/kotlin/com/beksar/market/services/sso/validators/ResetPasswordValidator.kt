package com.beksar.market.services.sso.validators

import com.beksar.market.core.errors.ValidationBuilder
import com.beksar.market.core.extentions.validationError
import com.beksar.market.services.sso.models.dto.reset.ResetPasswordEmailConfirmRequest
import com.beksar.market.services.sso.models.dto.reset.ResetPasswordEmailRequest
import com.beksar.market.services.sso.models.dto.reset.ResetPasswordPhoneConfirmRequest
import com.beksar.market.services.sso.models.dto.reset.ResetPasswordPhoneRequest


fun ResetPasswordEmailConfirmRequest.validate() {
    val validator = ValidationBuilder()

    //TODO check email
    if (this.email.isNullOrBlank()) {
        validator.setError("email", "name mus not be empty or null")
    }
    if (this.password.isNullOrBlank()) {
        validator.setError("password", "password mus not be empty or null")
    }
    if (this.confirmNewPassword.isNullOrBlank()) {
        validator.setError("confirmNewPassword", "confirmNewPassword mus not be empty or null")
    }
    if (this.password != this.confirmNewPassword) {
        validator.setError("password", "password != confirmNewPassword")
    }
    if (this.confirmCode == null || this.confirmCode == 0) {
        validator.setError("confirmCode", "name mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun ResetPasswordEmailRequest.validate() {
    val validator = ValidationBuilder()

    //TODO check email
    if (this.email.isNullOrBlank()) {
        validator.setError("email", "name mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun ResetPasswordPhoneConfirmRequest.validate() {
    val validator = ValidationBuilder()

    //TODO check phone
    if (this.phone.isNullOrBlank()) {
        validator.setError("phone", "phone mus not be empty or null")
    }
    if (this.password.isNullOrBlank()) {
        validator.setError("password", "password mus not be empty or null")
    }
    if (this.confirmNewPassword.isNullOrBlank()) {
        validator.setError("confirmNewPassword", "confirmNewPassword mus not be empty or null")
    }
    if (this.password != this.confirmNewPassword) {
        validator.setError("password", "password != confirmNewPassword")
    }
    if (this.code == null) {
        validator.setError("code", "code mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun ResetPasswordPhoneRequest.validate() {
    val validator = ValidationBuilder()

    //TODO check phone
    if (this.phone.isNullOrBlank()) {
        validator.setError("phone", "phone mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}