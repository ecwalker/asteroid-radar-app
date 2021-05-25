package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class DetailViewModel(private val asteroid: Asteroid,
                      private val application: Application): ViewModel() {

    //Selected asteroid LiveData
    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid

    //Initialise selected asteroid
    init {
        _selectedAsteroid.value = asteroid
    }
}