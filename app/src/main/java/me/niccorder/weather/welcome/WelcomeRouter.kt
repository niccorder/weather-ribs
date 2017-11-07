package me.niccorder.weather.welcome

import com.uber.rib.core.ViewRouter


/**
 * Adds and removes children of WelcomeScope.
 */
open class WelcomeRouter(
        view: WelcomeView,
        interactor: WelcomeInteractor,
        welcomeComponent: WelcomeComponent
) : ViewRouter<WelcomeView, WelcomeInteractor, WelcomeComponent>(
        view,
        interactor,
        welcomeComponent
)