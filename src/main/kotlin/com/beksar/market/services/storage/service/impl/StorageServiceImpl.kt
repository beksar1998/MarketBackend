package com.beksar.market.services.storage.service.impl


import com.beksar.market.core.extentions.httpError
import com.beksar.market.services.storage.errors.StorageErrors
import com.beksar.market.services.storage.service.StorageService
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import javax.imageio.ImageIO
import kotlin.random.Random


@Service
class StorageServiceImpl : StorageService {

    //TODO FIX FILES
    private val path =
        Paths.get(System.getProperty("user.dir") + "/file").toAbsolutePath().normalize()

    override fun upload(file: MultipartFile): String {
        // Normalize file name
        val fileName: String =
            "${Date().time}${Random.nextInt()}." + file.originalFilename?.substringAfterLast('.')

        return try {
            if (fileName.contains("..")) {
                httpError(StorageErrors.FileInvalid)

            }
            checkFolder()
            val targetLocation: Path = this.path.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

            addTextWatermark(
                "Market",
                file.originalFilename?.substringAfterLast('.') ?: "png",
                targetLocation.toFile(),
                targetLocation.toFile()
            )

            fileName
        } catch (ex: IOException) {
            httpError(StorageErrors.FileUploadError)
        }
    }

    //TODO FIX viewer
    //TODO FIX PATH
    override fun loadFileAsResource(fileName: String): Resource {
        return try {
            val filePath: Path = this.path.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                resource
            } else {
                httpError(StorageErrors.FileNotFound)
            }
        } catch (ex: MalformedURLException) {
            httpError(StorageErrors.FileNotFound)
        }
    }

    private fun checkFolder() {
        val newDirectory = File(path.toFile().path)
        if (!newDirectory.exists()) {
            newDirectory.mkdir()
        }
    }

    @Throws(IOException::class)
    fun resizeImage(originalImage: BufferedImage?, targetWidth: Int, targetHeight: Int): BufferedImage {
        val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB)
        val graphics2D = resizedImage.createGraphics()
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null)
        graphics2D.dispose()
        return resizedImage
    }

    @Throws(IOException::class)
    private fun addTextWatermark(text: String, type: String, source: File, destination: File) {
        val image = ImageIO.read(source)

        // determine image type and handle correct transparency
        val imageType =
            if ("png".equals(type, ignoreCase = true)) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB
        val watermarked = BufferedImage(image.width, image.height, imageType)

        // initializes necessary graphic properties
        val w = watermarked.graphics as Graphics2D
        w.drawImage(image, 0, 0, null)
        val alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f)
        w.composite = alphaChannel
        w.color = Color.GRAY
        w.font = Font(Font.SANS_SERIF, Font.BOLD, 30)
        val fontMetrics = w.fontMetrics
        val rect = fontMetrics.getStringBounds(text, w)

        // calculate center of the image
        val centerX = (image.width - rect.width.toInt()) / 2
        val centerY = image.height / 2

        // add text overlay to the image
//        w.drawString(text, centerX, centerY)
        val logo = ImageIO.read(File("file/pro_mobile_service_mark.png"))


        w.drawImage(logo, centerX - logo.width/2, centerY - logo.height/2, null)
        ImageIO.write(watermarked, type, destination)
        w.dispose()
    }

}