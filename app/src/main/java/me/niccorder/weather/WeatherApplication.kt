package me.niccorder.weather

import android.app.Application
import me.niccorder.weather.root.RootBuilder
import timber.log.Timber

class WeatherApplication : Application() {

    private val appComponent = DaggerAppComponent.builder().application(this).build()

    fun getRootParentComponent(): RootBuilder.ParentComponent = appComponent

    override fun onCreate() {
        super.onCreate()

        appComponent.injectMembers(this)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}