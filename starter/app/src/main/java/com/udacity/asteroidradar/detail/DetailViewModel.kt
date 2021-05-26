package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(private val asteroidKey: Long = 0L,
                      dataSource: AsteroidDatabaseDao): ViewModel() {

    val database = dataSource

    //Selected asteroid LiveData
    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid = MediatorLiveData<Asteroid>()
    //fun getAsteroid() = selectedAsteroid

    //Initialise selected asteroid
    init {
        getAsteroidFromDatabase(asteroidKey)
        selectedAsteroid.addSource(_selectedAsteroid, selectedAsteroid::setValue)
    }


    /**
     * Interactions with database
     */
    private fun getAsteroidFromDatabase(id: Long) {
        viewModelScope.launch {
            try {
                _selectedAsteroid.value = database.get(id)
            } catch (e: Exception) {
                Timber.i("Failed to get selected asteroid from database: ${e.message}")
            }
        }
    }

}