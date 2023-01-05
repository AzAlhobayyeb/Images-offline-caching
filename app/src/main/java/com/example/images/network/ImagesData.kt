package com.example.images.network

import com.squareup.moshi.Json

data class ImagesData(
    val id: Int,
    val author:String,
     @Json(name = "download_url") val url: String
)
