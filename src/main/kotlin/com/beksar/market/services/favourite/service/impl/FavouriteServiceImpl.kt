package com.beksar.market.services.favourite.service.impl

import com.beksar.market.core.extentions.httpError
import com.beksar.market.core.extentions.paging
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.core.extentions.toPageable
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.ads.mapper.toResponse
import com.beksar.market.services.ads.mapper.toSearchResponse
import com.beksar.market.services.ads.models.dto.AdvertResponse
import com.beksar.market.services.ads.models.dto.AdvertSearchResponse
import com.beksar.market.services.ads.models.entity.relations.AdvertPhotoEntity
import com.beksar.market.services.ads.models.entity.relations.AdvertViewEntity
import com.beksar.market.services.ads.repository.AdvertPhotoRepository
import com.beksar.market.services.ads.repository.AdvertRepository
import com.beksar.market.services.ads.repository.AdvertViewRepository
import com.beksar.market.services.favourite.models.entity.FavouriteEntity
import com.beksar.market.services.favourite.repository.FavouriteRepository
import com.beksar.market.services.favourite.service.FavouriteService
import org.springframework.stereotype.Service


@Service
class FavouriteServiceImpl(
    private val repository: FavouriteRepository,
    private val advertRepository: AdvertRepository,
    private val advertPhotoRepository: AdvertPhotoRepository,
    private val advertViewRepository : AdvertViewRepository
) : FavouriteService {

    override fun favourites(
        userId: String,
        pagingParams: PagingParams
    ): BasePageResponse<AdvertSearchResponse> {
        val favourites = repository.findAllByUserId(userId, pageable = pagingParams.toPageable())
        val advertIds = favourites.content.map { it.advertId }
        val adverts = advertRepository.findAllByIdIn(advertIds)
        return favourites.paging {
            adverts.find { a -> a.id == it.advertId }?.toSearchResponse(
                isFavourite = true,
                photos = photos(advertIds).filter { a -> a.id == it.advertId }.map { it.photo },
                viewed = viewed(advertIds).count {  a -> a.id == it.advertId },
            ) ?: httpError()
        }

    }

    override fun add(userId: String, advertId: String) {
        repository.saveWithException(
            FavouriteEntity(
                userId = userId,
                advertId = advertId
            )
        )
    }

    override fun delete(userId: String, advertId: String) {
        repository.deleteByUserIdAndAdvertId(userId, advertId)
    }

    private fun photos(advertIds: List<String>): List<AdvertPhotoEntity> {
        return advertPhotoRepository.findAllByAdvertIdIn(advertIds)
    }

    private fun viewed(advertIds: List<String>): List<AdvertViewEntity> {
        return advertViewRepository.findAllByAdvertIdIn(advertIds)
    }
}