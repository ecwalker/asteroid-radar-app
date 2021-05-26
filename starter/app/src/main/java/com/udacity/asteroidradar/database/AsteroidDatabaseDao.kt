package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {

    @Insert
    suspend fun insert(asteroid: Asteroid)

//    @Query("SELECT COUNT(*) FROM asteroid_details_table WHERE Id = :key")
//    suspend fun checkExists(key: Long?): Asteroid?

    //TODO: Only fetch asteroids from today onwards
    @Query("SELECT * FROM asteroid_details_table ORDER BY close_approach_date")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_details_table WHERE Id = :key")
    suspend fun get(key: Long): Asteroid?

}