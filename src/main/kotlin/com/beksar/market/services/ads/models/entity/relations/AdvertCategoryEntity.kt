package com.beksar.market.services.ads.models.entity.relations

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.GenericGenerator


@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["advertId", "categoryId"])])
class AdvertCategoryEntity(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String = "",
    @Column(nullable = false)
    val advertId: String = "",
    @Column(nullable = false)
    val categoryId: String = "",
)