package com.example.images.network

import com.squareup.moshi.Json

data class ImagesData(
    val id: String,
     @Json(name = "download_url") val url: String
)
