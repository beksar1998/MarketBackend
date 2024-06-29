package com.beksar.market.services.ads.models.entity.relations

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator


@Entity
class AdvertViewEntity(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String = "",
    val advertId: String = "",
    val userId: String? = null,
)