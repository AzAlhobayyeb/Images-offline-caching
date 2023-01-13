package com.example.images.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.images.database.ImagesDatabase
import com.example.images.database.asDomainModel
import com.example.images.domain.ImagesModels
import com.example.images.network.ImageApiNet
import com.example.images.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImagesRepository(private val database: ImagesDatabase) {

    val images: LiveData<List<ImagesModels>> = Transformations.map(database.imagesDao.getImages()){
        it.asDomainModel()
    }
    suspend fun refreshImages() {
        withContext(Dispatchers.IO){
            val playlist = ImageApiNet.retrofitService.getImages()
            database.imagesDao.insertAll(playlist.asDatabaseModel())
        }
    }
}