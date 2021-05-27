package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.getToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AsteroidRepository(private val database: AsteroidDatabaseDao) {

    val asteroids = database.getAllAsteroids(getToday())

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val stringResult = AsteroidApi.retrofitService.getAsteroids()
            val jsonResult = parseAsteroidsJsonResult(JSONObject(stringResult))
            Timber.i("${jsonResult.size} asteroids found")
            Timber.i("Response (Parsed): ${jsonResult}")
            jsonResult?.let {
                for (i in it) {
                    database.insert(i)
                }
            }
        }
    }
}