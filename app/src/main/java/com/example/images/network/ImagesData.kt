package com.example.images.network

import com.squareup.moshi.Json

data class ImagesData(
    val id: String,
    val author: String
    , @Json(name = "url") val url:String)
