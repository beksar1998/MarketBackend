package com.beksar.market.services.sso.models.entity

import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.core.user.UserStatus
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String = "",

    val phone: String? = null,
    val email: String? = null,

    val name: String? = null,
    val surname: String? = null,

    val password: String? = null,

    val isEmailConfirmed: Boolean = false,
    val isPhoneConfirmed: Boolean = false,

    val emailConfirmCode: Int? = null,
    val phoneConfirmCode: Int? = null,

    val emailConfirmCodeDate: Date? = null,
    val phoneConfirmCodeDate: Date? = null,

    val smsAuthCode: Int? = null,
    val smsAuthCodeDate: Date? = null,

    val fcmToken: String? = null,


    val photo: String? = null,

    @Enumerated(EnumType.STRING)
    val status: UserStatus = UserStatus.ACTIVE,
)