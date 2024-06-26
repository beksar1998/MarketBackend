package com.beksar.market.core.extentions

import com.beksar.market.core.errors.SaveDBError
import org.springframework.data.jpa.repository.JpaRepository

fun <T : Any, ID> JpaRepository<T, ID>.saveWithException(entity: T): T {
    try {
        return save(entity)
    } catch (e: Exception) {
        httpError(SaveDBError(e))
    }
}

fun <T : Any, ID> JpaRepository<T, ID>.saveWithException(entity: List<T>): List<T> {
    try {
        return saveAll(entity)
    } catch (e: Exception) {
        httpError(SaveDBError(e))
    }
}