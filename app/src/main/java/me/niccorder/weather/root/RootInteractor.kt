package me.niccorder.weather.root

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.Presenter
import com.uber.rib.core.RibInteractor
import me.niccorder.weather.welcome.WelcomeInteractor
import timber.log.Timber
import javax.inject.Inject

/**
 * Coordinates Business Logic for [RootScope].
 */
@RibInteractor
open class RootInteractor : Interactor<RootInteractor.RootPresenter, RootRouter>() {

    @Inject public lateinit var rootPresenter: RootPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachWelcome()
    }


    /**
     * RootPresenter interface implemented by this RIB's view.
     */
    interface RootPresenter

    inner class OnBeginListener : WelcomeInteractor.Listener {
        override fun onBegin() {
            router.detachWelcome().subscribe {
                Timber.i("detach welcome success. Now attaching login.")
                router.attachLogin()
            }
        }
    }
}