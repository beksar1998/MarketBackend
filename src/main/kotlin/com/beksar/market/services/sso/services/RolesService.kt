package com.beksar.market.services.sso.services

import com.beksar.market.services.sso.core.roles.Role

interface RolesService {
    fun roles(): List<Role>

    fun userRoles(id: String): List<Role>
    fun addRole(userId: String, role: Role)
}