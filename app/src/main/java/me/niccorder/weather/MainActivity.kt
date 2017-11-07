package me.niccorder.weather

import android.view.ViewGroup
import com.uber.rib.core.ViewRouter
import me.niccorder.weather.internal.DaggerRibActivity
import me.niccorder.weather.root.RootBuilder

class MainActivity : DaggerRibActivity() {

    override fun createRouter(
            parentViewGroup: ViewGroup?
    ): ViewRouter<*, *, *> = RootBuilder(
            getWeatherApplication().getRootParentComponent()
    ).build(parentViewGroup!!)
}
