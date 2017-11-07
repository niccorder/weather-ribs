package me.niccorder.weather.root

import android.os.Build
import android.view.animation.AccelerateInterpolator
import com.uber.rib.core.ViewRouter
import io.reactivex.Observable
import me.niccorder.weather.location.LocationBuilder
import me.niccorder.weather.location.LocationRouter
import me.niccorder.weather.welcome.WelcomeBuilder
import me.niccorder.weather.welcome.WelcomeRouter

class RootRouter constructor(
        view: RootView,
        interactor: RootInteractor,
        rootComponent: RootComponent,
        private val welcomeBuilder: WelcomeBuilder,
        private val locationBuilder: LocationBuilder
) : ViewRouter<RootView, RootInteractor, RootComponent>(
        view,
        interactor,
        rootComponent
) {

    private var welcomeRouter: WelcomeRouter? = null
    private var locationRouter: LocationRouter? = null

    fun attachWelcome() {
        welcomeRouter = welcomeBuilder.build(view)
        attachChild(welcomeRouter)

        view.addView(welcomeRouter!!.view)
    }

    fun detachWelcome(): Observable<Unit> {
        if (welcomeRouter != null) {
            detachChild(welcomeRouter)

            val welcomeView = welcomeRouter!!.view
            welcomeRouter = null

            return Observable.create {
                welcomeView.animate()
                        ?.alpha(0f)
                        ?.translationY(.85f)
                        ?.setDuration(150L)
                        ?.setInterpolator(AccelerateInterpolator())
                        ?.let {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                it.translationZBy(.5f)
                            }
                            it
                        }
                        ?.withEndAction {
                            view.removeView(welcomeView)
                            it.onNext(Unit)
                            it.onComplete()
                        }
                        ?.start()
            }
        }
        return Observable.empty()
    }

    fun attachLogin() {
        locationRouter = locationBuilder.build(view)
        attachChild(locationRouter)

        view.addView(locationRouter!!.view)
    }
}