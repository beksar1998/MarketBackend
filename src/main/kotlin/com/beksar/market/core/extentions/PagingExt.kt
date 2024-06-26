package com.beksar.market.core.extentions

import com.beksar.market.core.errors.PageSizeError
import com.beksar.market.core.models.paging.PagingParams

fun PagingParams.validate() {
    if (this.page < 0) httpError(PageSizeError)
}