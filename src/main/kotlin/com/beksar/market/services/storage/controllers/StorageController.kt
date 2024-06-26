package com.beksar.market.services.storage.controllers


import com.beksar.market.services.storage.models.UploadFileResponse
import com.beksar.market.services.storage.service.StorageService
import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException


@RestController
@RequestMapping("file")
class StorageController(private val service: StorageService) {


    @PostMapping(
        "/upload",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadFile(@RequestPart("file") file: MultipartFile): BaseResponse<UploadFileResponse> {
        val fileName: String = service.upload(file)

        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/file/")
            .path(fileName)
            .toUriString()

        return UploadFileResponse(
            fileName,
            fileDownloadUri,
            file.contentType.orEmpty(),
            file.size
        ).response()
    }

    @PostMapping(
        "/uploads",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadFiles(@RequestPart("files") files: List<MultipartFile>): BaseResponse<List<UploadFileResponse>> {
        return files.map { file ->
            val fileName = service.upload(file)
            val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/")
                .path(fileName)
                .toUriString()

            UploadFileResponse(
                fileName,
                fileDownloadUri,
                file.contentType.orEmpty(),
                file.size
            )
        }.response()

    }


    @GetMapping("/{fileName:.+}")
    fun file(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        // Load file as Resource
        val resource: Resource = service.loadFileAsResource(fileName)


        // Try to determine file's content type
        var contentType: String? = null
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (ex: IOException) {
            println()
        }

        // Fallback to the default content type if type could not be determined

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream"
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"")
            .body(resource)
    }

}