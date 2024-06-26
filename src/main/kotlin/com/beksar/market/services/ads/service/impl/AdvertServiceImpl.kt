package com.beksar.market.services.ads.service.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.direction
import com.beksar.market.core.extentions.paging
import com.beksar.market.core.extentions.toPageable
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.SearchPagingParams
import com.beksar.market.services.ads.mapper.toResponse
import com.beksar.market.services.ads.models.dto.AddOrUpdateAdvertRequest
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.entity.AdvertEntity
import com.beksar.market.services.ads.models.entity.AdvertStatus
import com.beksar.market.services.ads.models.entity.relations.AdvertPhotoEntity
import com.beksar.market.services.ads.repository.AdvertPhotoRepository
import com.beksar.market.services.ads.repository.AdvertRepository
import com.beksar.market.services.ads.service.AdvertService
import com.ibm.icu.text.Transliterator
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class AdvertServiceImpl(
    private val repository: AdvertRepository,
    private val advertPhotoRepository: AdvertPhotoRepository,
) : AdvertService {

    override fun delete(advertId: String) {
        repository.deleteById(advertId)
    }

    override fun add(request: AddOrUpdateAdvertRequest) {
        repository.save(
            AdvertEntity(
                description = request.description.orEmpty(),
            )
        )
    }

    override fun update(advertId: String, request: AddOrUpdateAdvertRequest) {
        val entity = repository.findById(advertId).checkNotNull()
        repository.save(
            entity.copy(
                description = request.description.orEmpty(),
            )
        )
    }

    override fun advert(advertId: String): AdvertResponse {
        val photos = photos(listOfNotNull(advertId)).filter { it.advertId == advertId }
            .map { it.photo }
        return repository.findById(advertId)
            .checkNotNull()
            .toResponse(photos)
    }

    override fun adverts(searchParams: SearchPagingParams): BasePageResponse<AdvertResponse> {
        val sortDirection = searchParams.sortDirection?.direction()
        val sort = if (sortDirection == null) {
            null
        } else {
            Sort.by(sortDirection, searchParams.sortField)
        }
        val adverts = if (searchParams.search.isBlank()) {
            repository.findAll(searchParams.toPageable(sort))
        } else {
            val latin = Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(searchParams.search)
            val cyrillic = Transliterator.getInstance(LATIN_TO_CYRILLIC).transliterate(searchParams.search)

            repository.findAllByDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                name = searchParams.search,
                nameEN = latin,
                nameRU = cyrillic,
                pageable = searchParams.toPageable(sort)
            )
        }
        val photos = photos(adverts.content.map { it.id })
        return adverts
            .paging { p ->
                p.toResponse(
                    photos = photos.filter { it.advertId == p.id }.map { it.photo },
                )
            }
    }

    override fun changeStatus(id: String, status: AdvertStatus) {
        val entity = repository.findById(id).checkNotNull()
        repository.save(
            entity.copy(
                status = status,
            )
        )
    }

    private fun photos(advertId: List<String>): List<AdvertPhotoEntity> {
        return advertPhotoRepository.findAllByAdvertIdIn(advertId)
    }
}