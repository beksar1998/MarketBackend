package com.beksar.market.services.ads.service.impl

import com.beksar.market.core.extentions.paging
import com.beksar.market.core.extentions.toPageable
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.SearchFilterParams
import com.beksar.market.services.ads.models.entity.relations.AdvertPhotoEntity
import com.beksar.market.services.ads.repository.AdvertCategoryRepository
import com.beksar.market.services.ads.repository.AdvertPhotoRepository
import com.beksar.market.services.ads.repository.AdvertRepository
import com.beksar.market.services.ads.service.AdvertSearchService
import com.ibm.icu.text.Transliterator
import org.springframework.stereotype.Service


val CYRILLIC_TO_LATIN = "Cyrillic-Latin"
val LATIN_TO_CYRILLIC = "Latin-Cyrillic"

@Service
class AdvertSearchServiceImpl(
    private val advertRepository: AdvertRepository,
    private val advertCategoryRepository: AdvertCategoryRepository,
    private val advertPhotoRepository: AdvertPhotoRepository
) : AdvertSearchService {



    override fun search(params: SearchFilterParams): BasePageResponse<AdvertResponse> {
        return if (params.categoryId != null) {

            //товары в этой категории
            val categoryCategory = advertCategoryRepository
                .findAllByCategoryId(params.categoryId)

            val advertIds = categoryCategory.map { it.advertId }

            val adverts = advertRepository.findAllByIdIn(advertIds, params.toPageable())

            return adverts.paging { advert ->
                AdvertResponse(
                    id = advert.id,
                    description = advert.description,
                    photos = photos(advertIds).filter { it.advertId == advert.id }.map { it.photo }
                )
            }

        } else if (!params.search.isNullOrBlank()) {

            val latin = Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(params.search)
            val cyrillic = Transliterator.getInstance(LATIN_TO_CYRILLIC).transliterate(params.search)

            val adverts =
                advertRepository.findAllByDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    name = params.search,
                    nameEN = latin,
                    nameRU = cyrillic,
                    pageable = params.toPageable()
                )

            val advertIds = adverts.content.map { it.id }

            return adverts.paging { advert ->
                AdvertResponse(
                    id = advert.id,
                    description = advert.description,
                    photos = photos(advertIds).filter { it.advertId == advert.id }.map { it.photo }
                )
            }

        } else {
            BasePageResponse(emptyList(), 0, 0, 0)
        }
    }

    private fun photos(advertIds: List<String>): List<AdvertPhotoEntity> {
        return advertPhotoRepository.findAllByAdvertIdIn(advertIds)

    }
}