package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel : ViewModel() {

    //Encapsulated status LiveData
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    //Picture of the day data class encapsulated
    private val _pictureOfDay = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfDay

    //List of Asteroid data classes encapsulated
    private val _asteroid = MutableLiveData<String>()
    val asteroid: LiveData<String>
        get() = _asteroid

    //Get NASA data on ViewModel initialisation
    init {
        getNasaData()
    }

    //Define GetNasaData as coroutine
    private fun getNasaData() {
        viewModelScope.launch {
            try {
                var imageResult = NasaApi.retrofitService.getImage()
                _status.value = "Success"
                if (imageResult.mediaType == "image") {
                    _pictureOfDay.value = imageResult
                } else {
                    _pictureOfDay.value = null
                }
                //TODO add title text to talkback in xml
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}