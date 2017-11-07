package me.niccorder.weather.internal

import com.uber.rib.core.RibActivity
import me.niccorder.weather.WeatherApplication

abstract class DaggerRibActivity : RibActivity() {

    protected fun getWeatherApplication(): WeatherApplication = (application as WeatherApplication)
}