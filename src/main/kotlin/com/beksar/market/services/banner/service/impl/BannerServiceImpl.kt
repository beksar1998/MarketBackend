package com.beksar.market.services.banner.service.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.paging
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.core.extentions.toPageable
import com.beksar.market.core.models.base.BasePageResponse
import com.beksar.market.core.models.paging.PagingParams
import com.beksar.market.services.banner.mappers.toResponse
import com.beksar.market.services.banner.models.dto.AddBannerRequest
import com.beksar.market.services.banner.models.dto.BannerResponse
import com.beksar.market.services.banner.models.dto.UpdateBannerRequest
import com.beksar.market.services.banner.models.entity.BannerEntity
import com.beksar.market.services.banner.models.entity.BannerType
import com.beksar.market.services.banner.repository.BannerRepository
import com.beksar.market.services.banner.service.BannerService
import org.springframework.stereotype.Service

@Service
class BannerServiceImpl(private val repository: BannerRepository) : BannerService {


    override fun banners(type: BannerType): List<BannerResponse> {
        val banners = repository.findAllByType(type).shuffled().take(5)
        return banners.map { it.toResponse() }

    }

    override fun banners(type: BannerType, page: PagingParams): BasePageResponse<BannerResponse> {
        return repository.findAllByType(type, page.toPageable()).paging {
            it.toResponse()
        }
    }

    override fun banner(bannerId: String): BannerResponse {
        val banner = repository.findById(bannerId).checkNotNull()
        return banner.toResponse()
    }

    override fun add(banner: AddBannerRequest) {
        repository.saveWithException(
            BannerEntity(
                title = banner.title,
                photo = banner.photo,
                type = banner.type
            )
        )
    }

    override fun update(bannerId: String, banner: UpdateBannerRequest) {
        val entity = repository.findById(bannerId).checkNotNull()
        repository.saveWithException(
            entity.copy(
                title = banner.title,
                photo = banner.photo,
            )
        )
    }

    override fun delete(bannerId: String) {
        repository.deleteById(bannerId)
    }
}