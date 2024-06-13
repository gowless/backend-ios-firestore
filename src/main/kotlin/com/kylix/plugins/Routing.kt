package com.kylix.plugins

import com.google.firebase.cloud.StorageClient
import com.kylix.FirebaseStorageUrl
import com.kylix.FirebaseStorageUrl.getDownloadUrl
import com.kylix.FirebaseStorageUrl.reference
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

fun Application.configureRouting() {

    val bucket = StorageClient.getInstance().bucket()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/add-image") {
            //upload image from multipart
            val multipart = call.receiveMultipart()
            var urlPath = ""

            try {
                multipart.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        val (fileName, fileBytes) = part.convert()
                        bucket.create(fileName, fileBytes, "image/png")
                        urlPath = FirebaseStorageUrl
                            .basePath getDownloadUrl(fileName)
                    }
                }
                call.respondText(urlPath)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, "Error while uploading image")
            }
        }

        post("/add-image-with-path") {

            //upload image from multipart
            val multipart = call.receiveMultipart()
            var urlPath = ""

            try {
                val folderName = generateRandomString(10) // Change the length as needed
                multipart.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        val (fileName, fileBytes) = part.convert()
                        bucket.create("avatar_url/$folderName/cleopatrasfallingtreasures.info", fileBytes, "image/png")
                        urlPath = FirebaseStorageUrl
                            .basePath reference "avatar_url" reference "images" getDownloadUrl fileName
                    }
                }
                call.respondText("avatar_url/$folderName/cleopatrasfallingtreasures.info")
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, "Error while uploading image")
            }
        }
    }
}

fun generateRandomString(length: Int): String {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun PartData.FileItem.convert() = run {
    val fileBytes = streamProvider().readBytes()
    val fileExtension = originalFileName?.takeLastWhile { it != '.' }
    val fileName = UUID.randomUUID().toString() + "." + fileExtension
    Pair(fileName, fileBytes)
}
