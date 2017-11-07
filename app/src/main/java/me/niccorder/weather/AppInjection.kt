package me.niccorder.weather

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.*
import me.niccorder.weather.data.DataModule
import me.niccorder.weather.internal.AppScope
import me.niccorder.weather.root.RootBuilder

@Module
class AppModule {

    @Provides
    @AppScope
    fun appContext(app: Application): Context = app

    @Provides
    @AppScope
    fun activityManager(
            context: Context
    ): ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    @Provides
    @AppScope
    fun userPreferences(
            context: Context
    ): SharedPreferences = context.getSharedPreferences(
            "weather-app",
            Context.MODE_PRIVATE
    )
}

@AppScope
@Component(modules = arrayOf(
        AppModule::class,
        DataModule::class
))
interface AppComponent : RootBuilder.ParentComponent, MembersInjector<WeatherApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance fun application(app: Application): Builder
        fun build(): AppComponent
    }
}