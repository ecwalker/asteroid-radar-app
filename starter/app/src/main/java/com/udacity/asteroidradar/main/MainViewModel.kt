package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel : ViewModel() {

    //Picture of the day data class encapsulated
    private val _pictureOfDay = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfDay

    //List of Asteroid data classes encapsulated
    private val _asteroidList = MutableLiveData<ArrayList<Asteroid>>()
    val asteroidList: LiveData<ArrayList<Asteroid>>
        get() = _asteroidList

    //Get NASA data on ViewModel initialisation
    init {
        Timber.i("MainViewModel init block run")
        getNasaData()
        getNasaAsteroids()
    }

    //Define GetNasaData as coroutine
    private fun getNasaData() {
        viewModelScope.launch {
            try {
                var imageResult = NasaApi.retrofitService.getImage()
                if (imageResult.mediaType == "image") {
                    _pictureOfDay.value = imageResult
                } else {
                    _pictureOfDay.value = null
                }
                //TODO add title text to talkback in xml
            } catch (e: Exception) {
                Timber.i("getNasaData call failed: ${e.message}")
            }
        }
    }

    private fun getNasaAsteroids() {
        Timber.i("getNasaAsteroids called")
        viewModelScope.launch {
            try {
                val stringResult = AsteroidApi.retrofitService.getAsteroids()
                val jsonResult = parseAsteroidsJsonResult(JSONObject(stringResult))
                _asteroidList.value = jsonResult
                Timber.i("${jsonResult.size} asteroids found")
                Timber.i("Response (Parsed): ${asteroidList.value}")
            } catch (e: Exception) {
                Timber.i("getNasaAsteroids call failed: ${e.message}")
            }
        }
    }

    //Navigation to asteroid detail fragment
    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail: LiveData<Asteroid>
        get() = _navigateToAsteroidDetail

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }
}