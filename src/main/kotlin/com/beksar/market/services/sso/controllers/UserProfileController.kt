package com.beksar.market.services.sso.controllers


import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.sso.models.dto.profile.ChangePasswordRequest
import com.beksar.market.services.sso.models.dto.profile.ProfileEditRequest
import com.beksar.market.services.sso.models.dto.profile.ProfileResponse
import com.beksar.market.services.sso.services.ProfileService
import com.beksar.market.services.sso.validators.validate
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.market.services.sso.core.jwt.JWTService
import com.beksar.market.services.sso.models.dto.user.AddPhoneRequest
import com.beksar.market.services.sso.models.dto.user.UserPhoneResponse
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/profile")
class UserProfileController(private val service: ProfileService, private val jwtService: JWTService) {


    @GetMapping
    @Authentication
    fun profile(): BaseResponse<ProfileResponse> {
        return service.profile(jwtService.userId).response()

    }

    @PutMapping
    @Authentication
    fun edit(@RequestBody request: ProfileEditRequest): BaseResponse<ProfileResponse> {
        request.validate()
        return service.edit(jwtService.userId, request).response()
    }


    @PutMapping("change-password")
    @Authentication
    fun changePassword(@RequestBody request: ChangePasswordRequest): BaseResponse<Boolean> {
        request.validate()
        return service.changePassword(jwtService.userId, request).response()
    }

    @DeleteMapping("photo")
    @Authentication
    fun removePhoto() {
        val userId = jwtService.userId
        service.removePhoto(userId)
    }


    @GetMapping("{userId}/confirm/email")
    fun confirmEmail(@PathVariable userId: String): BaseResponse<Boolean> {
        return service.confirmEmail(userId).response()
    }

    @GetMapping("{userId}/confirm/phone")
    fun confirmPhone(@PathVariable userId: String): BaseResponse<Boolean> {
        return service.confirmPhone(userId).response()
    }


    @DeleteMapping
    @Authentication
    fun deleteAccount(): BaseResponse<Boolean> {
        val userId = jwtService.userId
        return service.deleteUser(userId).response()
    }


    @GetMapping(("phone/{userId}"))
    @Authentication
    fun phones(
        @PathVariable userId: String,
    ): BaseResponse<List<UserPhoneResponse>> {
        return service.phones(userId).response()
    }


    @PostMapping(("phone/{userId}"))
    @Authentication
    fun addPhone(
        @PathVariable userId: String,
        @RequestBody addPhoneRequest: AddPhoneRequest
    ): BaseResponse<Boolean> {
        service.addPhone(userId, addPhoneRequest.phone.orEmpty())
        return true.response()
    }

    @DeleteMapping("phone/{phoneId}")
    @Authentication
    fun deletePhone(@PathVariable phoneId: String): BaseResponse<Boolean> {
        service.deletePhone(phoneId)
        return true.response()
    }


}