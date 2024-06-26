package com.beksar.market.services.ads.models.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.Date

@Entity
data class AdvertEntity(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String = "",
    @Column(nullable = false)
    val description: String = "",
    val date: Date = Date(),
    @Enumerated(EnumType.STRING)
    val status: AdvertStatus = AdvertStatus.MODERATING
)