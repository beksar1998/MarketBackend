package com.beksar.market.services.storage.errors

import com.beksar.market.core.errors.HttpError


object StorageErrors {
    object FileInvalid : HttpError("FileInvalid", mapOf())
    object FileNotFound : HttpError("FileNotFound", mapOf())
    object FileUploadError : HttpError("FileUploadError", mapOf())
}