package me.niccorder.weather.data.remote.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import me.niccorder.weather.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A [retrofit2.Retrofit] rest API definition for our weather services.
 */
interface WeatherApi {

    /**
     * Retrieves the forecast for the given latitude and longitude.
     */
    @GET("/forecast/${BuildConfig.WEATHER_API_KEY}/{latitude},{longitude}")
    fun getForcast(
            @Path("latitude") latitude: Double,
            @Path("longitude") longitude: Double
    ): Observable<JsonObject>
}