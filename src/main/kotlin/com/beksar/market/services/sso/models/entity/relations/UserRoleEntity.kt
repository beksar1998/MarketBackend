package com.beksar.market.services.sso.models.entity.relations

import com.beksar.market.services.sso.core.roles.Role
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@Entity(name = "user_role")
class UserRoleEntity(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String? = null,
    val userId: String? = null,
    @Enumerated(EnumType.STRING)
    val role: Role? = null,
)