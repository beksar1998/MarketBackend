package com.beksar.market.services.ads.service.impl

import com.beksar.market.core.errors.MessageError
import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.httpError
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.services.ads.models.entity.relations.AdvertCategoryEntity
import com.beksar.market.services.ads.repository.AdvertCategoryRepository
import com.beksar.market.services.ads.service.AdvertCategoryService
import com.beksar.market.services.category.models.dto.CategoryResponse
import com.beksar.market.services.category.service.CategoryService
import org.springframework.stereotype.Service


@Service
class AdvertCategoryServiceImpl(
    private val repository: AdvertCategoryRepository,
    private val categoryService: CategoryService
) : AdvertCategoryService {

    override fun bind(advertId: String, categoryId: String) {
        val category = categoryService.category(categoryId, false)
        if (category.parentId == null) {
            httpError(MessageError("Нужно выбрать категорию 2 уровня"))
        }
        repository.saveWithException(
            AdvertCategoryEntity(
                advertId = advertId,
                categoryId = categoryId
            )
        )
    }

    override fun unBind(advertId: String, categoryId: String) {
        val relation = repository.findByAdvertIdAndCategoryId(advertId, categoryId).checkNotNull()
        repository.deleteById(relation.id)
//        repository.deleteByadvertIdAndCategoryId(advertId, categoryId)
    }

    override fun categories(advertId: String): List<CategoryResponse> {
        val relations = repository.findAllByAdvertId(advertId)
        val categoryIds = relations.map { it.categoryId }
        val child = categoryService.categoriesById(categoryIds)
        val parentIds = child.mapNotNull { it.parentId }
        val parent = categoryService.categoriesById(parentIds)
        return parent.map { p ->
            p.copy(
                child = child.filter { it.parentId == p.id }
            )
        }

    }
}