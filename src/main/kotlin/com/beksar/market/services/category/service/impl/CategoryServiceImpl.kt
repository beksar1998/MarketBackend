package com.beksar.market.services.category.service.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.paging
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.core.extentions.toPageable
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.category.mapper.toResponse
import com.beksar.market.services.category.models.dto.AddOrUpdateCategoryRequest
import com.beksar.market.services.category.models.dto.CategoryResponse
import com.beksar.market.services.category.models.entity.CategoryEntity
import com.beksar.market.services.category.repository.CategoryRepository
import com.beksar.market.services.category.service.CategoryService
import org.springframework.stereotype.Service


@Service
class CategoryServiceImpl(private val repository: CategoryRepository) : CategoryService {

    override fun categories(
        parentId: String?,
        recursive: Boolean,
        pagingParams: PagingParams
    ): BasePageResponse<CategoryResponse> {
        return when {
            parentId.isNullOrBlank() && !recursive -> {
                repository.findAllByParentIdIsNull(pagingParams.toPageable()).paging { it.toResponse() }
            }

            !parentId.isNullOrBlank() && !recursive -> {
                repository.findAllByParentId(parentId, pagingParams.toPageable()).paging { it.toResponse() }
            }

            parentId.isNullOrBlank() && recursive -> {
                val all = repository.findAllByParentIdIsNull(pagingParams.toPageable())
                val ids = all.mapNotNull { it.id }
                val child = repository.findAllByParentIdIn(ids)
                all.paging { p ->
                    p.toResponse(child.filter { p.id == it.parentId }.map { it.toResponse() })
                }
            }

            !parentId.isNullOrBlank() && recursive -> {
                val parent = repository.findAllByParentId(parentId, pagingParams.toPageable())
                val all = parent.content
                val ids = all.mapNotNull { it.id }
                val child = repository.findAllByParentIdIn(ids)
                parent.paging { p -> p.toResponse(child.filter { p.id == it.parentId }.map { it.toResponse() }) }
            }

            else -> {
                throw RuntimeException()
            }
        }
    }

    override fun category(id: String, recursive: Boolean): CategoryResponse {
        return if (recursive) {
            val child = repository.findAllByParentId(id).checkNotNull().map { it.toResponse() }
            return repository.findById(id).checkNotNull().toResponse(child)
        } else {
            repository.findById(id).checkNotNull().toResponse()
        }

    }

    override fun delete(id: String) {
        repository.deleteById(id)
    }

    override fun update(id: String, request: AddOrUpdateCategoryRequest) {
        val category = repository.findById(id).checkNotNull()
        repository.saveWithException(
            category.copy(
                title = request.title,
                photo = request.photo
            )
        )
    }

    override fun add(request: AddOrUpdateCategoryRequest) {
        repository.saveWithException(
            CategoryEntity(
                title = request.title,
                parentId = request.parentId,
                photo = request.photo
            )
        )
    }

    override fun categoriesById(ids: List<String>): List<CategoryResponse> {
        return repository.findAllByIdIn(ids).checkNotNull().map { it.toResponse() }
    }
}