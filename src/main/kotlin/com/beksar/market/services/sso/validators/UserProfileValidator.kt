package com.beksar.market.services.sso.validators

import com.beksar.market.core.errors.ValidationBuilder
import com.beksar.market.core.extentions.validationError
import com.beksar.market.services.sso.models.dto.profile.ChangePasswordRequest
import com.beksar.market.services.sso.models.dto.profile.ProfileEditRequest

fun ProfileEditRequest.validate() {
    val validator = ValidationBuilder()
    //TODO check email
    if (this.name.isNullOrBlank()) {
        validator.setError("name", "name mus not be empty or null")
    }
    if (this.surname.isNullOrBlank()) {
        validator.setError("surname", "surname mus not be empty or null")
    }
    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}

fun ChangePasswordRequest.validate() {
    val validator = ValidationBuilder()
    //TODO check email

    if (this.oldPassword.isNullOrBlank()) {
        validator.setError("oldPassword", "oldPassword mus not be empty or null")
    }
    if (this.newPassword.isNullOrBlank()) {
        validator.setError("newPassword", "newPassword mus not be empty or null")
    }
    if (this.confirmNewPassword.isNullOrBlank()) {
        validator.setError("confirmNewPassword", "confirmNewPassword mus not be empty or null")
    }

    if (this.oldPassword == this.newPassword) {
        validator.setError("newPassword", "newPassword == oldPassword")
    }

    if (this.newPassword != this.confirmNewPassword) {
        validator.setError("oldPassword", "newPassword != confirmNewPassword")
    }

    if (validator.hasErrors()) {
        validationError(validator.error())
    }
}