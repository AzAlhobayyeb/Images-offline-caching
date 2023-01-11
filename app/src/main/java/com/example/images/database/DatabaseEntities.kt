package com.example.images.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.images.domain.ImagesModels


@Entity
data class DatabaseImages constructor(
    @PrimaryKey
    val id: String,
    val author: String,
    val download_url: String
//    val width: String,
//    val height: String,
//    val url: String
    )

fun List<DatabaseImages>.asDomainModel(): List<ImagesModels> {
    return map{
        ImagesModels(
            id = it.id,
            author = it.author,
            download_url = it.download_url
//            width = it.width,
//            height = it.height,
//            url = it.url
        )

    }
}