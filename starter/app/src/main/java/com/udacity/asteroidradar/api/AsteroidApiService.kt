package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.util.*


//Retrofit Object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

//@GET method to get asteroids
interface AsteroidApiService {
    //TODO make start and end date dynamic (lamda fun?)
    //@GET("neo/rest/v1/feed?start_date=2021-05-24&end_date=2021-05-27&api_key=${API_KEY}")
    @GET("neo/rest/v1/feed?start_date=2021-05-24&api_key=${API_KEY}")
    suspend fun getAsteroids():
            //ArrayList<Asteroid>
            String
}

//NASA API object to expose retrofit to rest of system
object AsteroidApi {
    val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}

private fun getToday(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime)
}