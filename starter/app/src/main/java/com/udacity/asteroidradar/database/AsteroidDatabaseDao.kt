package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(vararg asteroids: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: Asteroid)

    //TODO: Only fetch asteroids from today onwards
    @Query("SELECT * FROM asteroid_details_table WHERE close_approach_date >= :today " +
            "ORDER BY close_approach_date")
    fun getAllAsteroids(today: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_details_table WHERE Id = :key")
    suspend fun get(key: Long): Asteroid?

}