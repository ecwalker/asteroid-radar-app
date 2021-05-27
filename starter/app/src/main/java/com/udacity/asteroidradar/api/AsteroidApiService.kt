package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


//Retrofit Object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

//@GET method to get asteroids
interface AsteroidApiService {
    @GET("neo/rest/v1/feed?api_key=${API_KEY}")
    suspend fun getAsteroids():
            String
}

//NASA API object to expose retrofit to rest of system
object AsteroidApi {
    val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}

