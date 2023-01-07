package com.example.images.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.images.network.ImageApi
import com.example.images.network.ImagesData
import kotlinx.coroutines.launch

enum class ImageApiStatus { LOADING, ERROR, DONE }

class ImageViewModel : ViewModel() {

    private val _status = MutableLiveData<ImageApiStatus>()
    val status: LiveData<ImageApiStatus> = _status

    private val _imagesModel = MutableLiveData<List<ImagesData>>()
    val imagesModel: LiveData<List<ImagesData>> = _imagesModel

    private val _imagePicked = MutableLiveData<ImagesData>()
    val imagePicked: LiveData<ImagesData> = _imagePicked

    init {
        getImages()
    }

    private fun getImages() {

        viewModelScope.launch {
            _status.value = ImageApiStatus.LOADING
            try {
                _imagesModel.value = ImageApi.retrofitService.getImages()
                _status.value = ImageApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ImageApiStatus.ERROR
                _imagesModel.value = listOf()
            }
        }
    }

    fun onImageClicked(imagesData:ImagesData){
        _imagePicked.value = imagesData
        Log.v("tete","${imagePicked.value?.url}?grayscale")
    }

}