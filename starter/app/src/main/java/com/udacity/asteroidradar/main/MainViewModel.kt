package com.udacity.asteroidradar.main

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    //Picture of the day data class encapsulated
    private val _pictureOfDay = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfDay

    //Use repository instead of ViewModel directly
    private val asteroidRepository = AsteroidRepository(database)
    val asteroids = asteroidRepository.asteroids

    //Get NASA data on ViewModel initialisation
    init {
        Timber.i("MainViewModel init block run")
        getNasaData()
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    /**
     * Access NASA APIs to get image of the day and asteroid list
     */
    //Define GetNasaData as coroutine
    private fun getNasaData() {
        viewModelScope.launch {
            try {
                var imageResult = NasaApi.retrofitService.getImage()
                Timber.i("NASA image of the day media type: ${imageResult.mediaType}")
                if (imageResult.mediaType == "image") {
                    _pictureOfDay.value = imageResult
                } else {
                    _pictureOfDay.value = null
                }
            } catch (e: Exception) {
                Timber.i("getNasaData call failed: ${e.message}")
            }
        }
    }

    /**
     * Interactions with database (Moved to repository)
     */


    /**
     * Navigation to asteroid detail fragment
     */
    private val _navigateToAsteroidDetail = MutableLiveData<Long>()
    val navigateToAsteroidDetail: LiveData<Long>
        get() = _navigateToAsteroidDetail

    fun onAsteroidClicked(id: Long) {
        _navigateToAsteroidDetail.value = id
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

}