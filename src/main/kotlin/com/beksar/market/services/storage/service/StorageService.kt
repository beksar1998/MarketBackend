package com.beksar.market.services.storage.service

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface StorageService {

    fun upload(file: MultipartFile): String
    fun loadFileAsResource(fileName: String): Resource
}