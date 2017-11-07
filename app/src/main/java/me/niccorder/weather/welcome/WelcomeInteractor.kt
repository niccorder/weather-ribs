package me.niccorder.weather.welcome

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Coordinates Business Logic for [WelcomeScope].
 */
@RibInteractor
open class WelcomeInteractor : Interactor<WelcomeInteractor.WelcomePresenter, WelcomeRouter>() {

    @Inject lateinit var presenter: WelcomePresenter
    @Inject lateinit var beginListener: Listener

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        disposables.add(presenter.beginClicks().subscribe({ beginListener.onBegin() }))
    }

    override fun willResignActive() {
        super.willResignActive()

        disposables.dispose()
    }

    /**
     * RootPresenter interface implemented by this RIB's view.
     */
    interface WelcomePresenter {
        fun beginClicks(): Observable<Unit>
    }

    interface Listener {
        fun onBegin()
    }
}