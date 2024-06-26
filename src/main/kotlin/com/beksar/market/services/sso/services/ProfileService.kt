package com.beksar.market.services.sso.services

import com.beksar.market.services.sso.models.dto.firebase.AddFirebaseTokenRequest
import com.beksar.market.services.sso.models.dto.profile.ChangePasswordRequest
import com.beksar.market.services.sso.models.dto.profile.ProfileEditRequest
import com.beksar.market.services.sso.models.dto.profile.ProfileResponse
import com.beksar.market.services.sso.models.dto.user.UserPhoneResponse

interface ProfileService {
    fun profile(id: String): ProfileResponse
    fun edit(id: String, request: ProfileEditRequest): ProfileResponse
    fun changePassword(id: String, request: ChangePasswordRequest): Boolean
    fun confirmEmail(userId: String): Boolean
    fun confirmPhone(userId: String): Boolean

    fun addFirebaseToken(userId: String, request: AddFirebaseTokenRequest): Boolean
    fun removePhoto(userId: String)
    fun deleteUser(userId: String): Boolean
    fun addPhone(userId: String, phone: String)
    fun deletePhone(phoneId: String)
    fun phones(userId: String): List<UserPhoneResponse>
}