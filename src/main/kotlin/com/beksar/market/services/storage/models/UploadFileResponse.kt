package com.beksar.market.services.storage.models

class UploadFileResponse(
    val name: String,
    val uri: String,
    val contentType: String,
    val size: Long,
)