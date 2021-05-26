package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
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

    //List of Asteroid data classes encapsulated
    private val _asteroidList = MutableLiveData<ArrayList<Asteroid>>()

    //List of asteroids from database
    val asteroids = database.getAllAsteroids()

    //Get NASA data on ViewModel initialisation
    init {
        Timber.i("MainViewModel init block run")
        getNasaData()
        getNasaAsteroids()
        //updateDatabase()
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
                Timber.i("Response (Parsed): ${_asteroidList.value}")
                _asteroidList.value?.let {
                    //TODO: Does this need to check whether asteroid exists before inserting?
                    for (i in it) {
                        insert(i)
                    }
                }
            } catch (e: Exception) {
                Timber.i("getNasaAsteroids call failed: ${e.message}")
            }
        }
    }

    /**
     * Interactions with database
     */
    //TODO: Add worker to schedule API checks
    private fun updateDatabase() {
        Timber.i("updateDatabase called")
        viewModelScope.launch {
            try {
                _asteroidList.value?.let {
                    Timber.i("Inserting asteroids into database...")
                    for (i in it) {
                        insert(i)
//                        val inDatabase = checkExists(i).toString()
//                        if (inDatabase == "0") {
//                            insert(i)
//                        }
                    }
                    Timber.i("Asteroids inserted into database")
                } ?: Timber.i("Asteroid list is null")
            } catch (e: Exception) {
                Timber.i("Database update failed: ${e.message}")
            }
        }
    }

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

    /**
     * Database functions
     */

    //Insert
    private suspend fun insert(asteroid: Asteroid) {
        database.insert(asteroid)
    }

    //Check if asteroid record already exists
    private suspend fun checkExists(asteroid: Asteroid) {
        //database.checkExists(asteroid.id)
    }

}