package com.beksar.market.services.ads.service.impl

import com.beksar.market.core.extentions.*
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
import com.beksar.market.services.favourite.repository.FavouriteRepository
import com.ibm.icu.text.Transliterator
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class AdvertServiceImpl(
    private val repository: AdvertRepository,
    private val advertPhotoRepository: AdvertPhotoRepository,
    private val favouriteRepository: FavouriteRepository
) : AdvertService {

    override fun delete(advertId: String) {
        repository.deleteById(advertId)
    }

    override fun add(request: AddOrUpdateAdvertRequest) {
        repository.saveWithException(
            AdvertEntity(
                description = request.description.orEmpty(),
            )
        )
    }

    override fun update(advertId: String, request: AddOrUpdateAdvertRequest) {
        val entity = repository.findById(advertId).checkNotNull()
        repository.saveWithException(
            entity.copy(
                description = request.description.orEmpty(),
            )
        )
    }

    override fun advert(advertId: String): AdvertResponse {
        return repository.findById(advertId)
            .checkNotNull()
            .toResponse()
    }

    override fun adverts(searchParams: SearchPagingParams): BasePageResponse<AdvertResponse> {
        val sortDirection = searchParams.sortDirection?.direction()
        val sort = if (sortDirection == null) {
            null
        } else {
            Sort.by(sortDirection, searchParams.sortField)
        }
        val adverts = if (searchParams.search.isBlank()) {
            if (searchParams.status == null) {
                repository.findAll(searchParams.toPageable(sort))
            } else {
                repository.findAllByStatus(searchParams.status, searchParams.toPageable(sort))
            }
        } else {
            val latin = Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(searchParams.search)
            val cyrillic = Transliterator.getInstance(LATIN_TO_CYRILLIC).transliterate(searchParams.search)

            if (searchParams.status == null) {
                repository.findAllByDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    name = searchParams.search,
                    nameEN = latin,
                    nameRU = cyrillic,
                    pageable = searchParams.toPageable(sort)
                )
            } else {
                repository.findAllByDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndStatus(
                    name = searchParams.search,
                    nameEN = latin,
                    nameRU = cyrillic,
                    status = searchParams.status,
                    pageable = searchParams.toPageable(sort)
                )
            }

        }
        return adverts
            .paging { p ->
                p.toResponse()
            }
    }

    override fun changeStatus(id: String, status: AdvertStatus) {
        val entity = repository.findById(id).checkNotNull()
        repository.saveWithException(
            entity.copy(
                status = status,
            )
        )
    }
}