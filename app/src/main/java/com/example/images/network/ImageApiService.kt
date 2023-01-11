package com.example.images.network


import com.example.images.database.DatabaseImages
import com.example.images.domain.ImagesModels
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkImageContainer(val images:List<NetworkImage>)

@JsonClass(generateAdapter = true)
data class NetworkImage(
    val id: String,
    val author:String,
    @Json(name = "download_url")val download_url: String,
    // val width: String,
    // val height: String,
    // val url: String
    )


fun NetworkImageContainer.asDomainModel(): List<ImagesModels> {
    return images.map {
        ImagesModels(
            id = it.id,
            author = it.author,
            download_url = it.download_url,
       //     width = it.width,
         //   height = it.height,
           // url = it.url
    )
    }
}

fun NetworkImageContainer.asDatabaseModel(): List<DatabaseImages>{
    return images.map {
        DatabaseImages(
            id = it.id,
            author = it.author,
            download_url = it.download_url,
//            width = it.width,
//            height = it.height,
//            url = it.url
        )

    }
}



