package com.example.images.network


import com.example.images.database.DatabaseImages
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class NetworkImage(
    @Json(name = "id")val id: String,
    @Json(name = "author")val author:String,
    @Json(name = "download_url")val download_url: String,
    )

fun List<NetworkImage>.asDatabaseModel(): List<DatabaseImages>{
    return map {
        DatabaseImages(
            id = it.id,
            author = it.author,
            download_url = it.download_url,
        )
    }
}



