package com.beksar.market.services.banner.models.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator


@Entity
data class BannerEntity(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String = "",
    @Enumerated(EnumType.STRING)
    val type: BannerType = BannerType.MAIN,
    val title: String = "",
    val photo: String = ""

)