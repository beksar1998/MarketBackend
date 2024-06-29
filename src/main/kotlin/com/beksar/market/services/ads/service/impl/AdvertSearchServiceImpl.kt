package com.beksar.market.services.ads.service.impl

import com.beksar.market.core.extentions.paging
import com.beksar.market.core.extentions.toPageable
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.services.ads.mapper.toSearchResponse
import com.beksar.market.services.ads.models.dto.AdvertSearchResponse
import com.beksar.market.services.ads.models.dto.SearchFilterParams
import com.beksar.market.services.ads.models.entity.AdvertStatus
import com.beksar.market.services.ads.models.entity.relations.AdvertPhotoEntity
import com.beksar.market.services.ads.repository.AdvertCategoryRepository
import com.beksar.market.services.ads.repository.AdvertPhotoRepository
import com.beksar.market.services.ads.repository.AdvertRepository
import com.beksar.market.services.ads.service.AdvertSearchService
import com.beksar.market.services.favourite.models.entity.FavouriteEntity
import com.beksar.market.services.favourite.repository.FavouriteRepository
import com.beksar.market.services.sso.core.jwt.JWTService
import com.ibm.icu.text.Transliterator
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


val CYRILLIC_TO_LATIN = "Cyrillic-Latin"
val LATIN_TO_CYRILLIC = "Latin-Cyrillic"

@Service
class AdvertSearchServiceImpl(
    private val advertRepository: AdvertRepository,
    private val advertCategoryRepository: AdvertCategoryRepository,
    private val advertPhotoRepository: AdvertPhotoRepository,
    private val favouriteRepository: FavouriteRepository,
    private val jwtService: JWTService
) : AdvertSearchService {


    override fun search(params: SearchFilterParams): BasePageResponse<AdvertSearchResponse> {
        return if (params.categoryId != null) {

            //товары в этой категории
            val categoryCategory = advertCategoryRepository
                .findAllByCategoryId(params.categoryId)

            val advertIds = categoryCategory.map { it.advertId }

            val adverts = advertRepository.findAllByIdIn(advertIds, params.toPageable())
            val favourites = favourites()

            return adverts.paging { advert ->
                advert.toSearchResponse(
                    photos = photos(advertIds).filter { it.advertId == advert.id }.map { it.photo },
                    isFavourite = favourites.contains(advert.id)
                )
            }

        } else if (!params.search.isNullOrBlank()) {

            val latin = Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(params.search)
            val cyrillic = Transliterator.getInstance(LATIN_TO_CYRILLIC).transliterate(params.search)

            val adverts =
                advertRepository.findAllByDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndStatus(
                    name = params.search,
                    nameEN = latin,
                    nameRU = cyrillic,
                    status = AdvertStatus.ACTIVE,
                    pageable = params.toPageable()
                )

            val advertIds = adverts.content.map { it.id }

            val favourites = favourites()

            return adverts.paging { advert ->
                advert.toSearchResponse(
                    photos = photos(advertIds).filter { it.advertId == advert.id }.map { it.photo },
                    isFavourite = favourites.contains(advert.id)
                )
            }

        } else {
            val adverts = advertRepository.findAll(params.toPageable(sort = Sort.by(Sort.Direction.DESC, "date")))
            val advertIds = adverts.content.map { it.id }
            val favourites = favourites()

            adverts.paging { advert ->
                advert.toSearchResponse(
                    photos = photos(advertIds).filter { it.advertId == advert.id }.map { it.photo },
                    isFavourite = favourites.contains(advert.id)
                )
            }
        }
    }

    private fun photos(advertIds: List<String>): List<AdvertPhotoEntity> {
        return advertPhotoRepository.findAllByAdvertIdIn(advertIds)
    }

    private fun favourites(): List<String> {
        val userId = jwtService.userIdOrNull ?: return emptyList()
        val favourites = favouriteRepository.findAllByUserId(userId)
        return favourites.map { it.advertId }
    }
}