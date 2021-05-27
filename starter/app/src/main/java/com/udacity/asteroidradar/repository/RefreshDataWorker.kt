package com.udacity.asteroidradar.repository

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import retrofit2.HttpException

class RefreshDataWorker(private val dataSource: AsteroidDatabaseDao,
                        appContext: Context,
                        params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        //val database = getDatabase(applicationContext)
        val database = dataSource
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
}