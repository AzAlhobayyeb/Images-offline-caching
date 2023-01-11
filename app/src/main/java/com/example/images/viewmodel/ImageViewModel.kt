package com.example.images.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.images.database.getDatabase
import com.example.images.repository.ImagesRepository
import kotlinx.coroutines.launch
import java.io.IOException


class ImagesViewModel(application: Application): AndroidViewModel(application) {

    private val _imagesRepository = ImagesRepository(getDatabase(application))
    val imagesRepository get() = _imagesRepository

    val playlist = _imagesRepository.images

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() = viewModelScope.launch {
        try {
            _imagesRepository.refreshImages()
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        } catch (networkError: IOException) {
            if (playlist.value.isNullOrEmpty())
                _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ImagesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ImagesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
