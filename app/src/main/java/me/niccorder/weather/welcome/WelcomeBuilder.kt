package me.niccorder.weather.welcome

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import me.niccorder.weather.R
import me.niccorder.weather.internal.infra.SharedPreferencesStore
import javax.inject.Qualifier
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class WelcomeScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WelcomeInternal

@WelcomeScope
@dagger.Component(
        modules = arrayOf(WelcomeModule::class),
        dependencies = arrayOf(WelcomeBuilder.ParentComponent::class)
)
open interface WelcomeComponent : InteractorBaseComponent<WelcomeInteractor>, WelcomeBuilder.BuilderComponent {

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance
        fun interactor(interactor: WelcomeInteractor): Builder

        @BindsInstance
        fun view(view: WelcomeView): Builder

        fun parentComponent(component: WelcomeBuilder.ParentComponent): Builder
        fun build(): WelcomeComponent
    }
}


/**
 * Builder for the [WelcomeScope].
 *
 * TODO describe this scope's responsibility as a whole.
 */
open class WelcomeBuilder(
        dependency: WelcomeBuilder.ParentComponent
) : ViewBuilder<WelcomeView, WelcomeRouter, WelcomeBuilder.ParentComponent>(
        dependency
) {

    /**
     * Builds a new [WelcomeRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [WelcomeRouter].
     */
    fun build(parentViewGroup: ViewGroup): WelcomeRouter {
        val component = DaggerWelcomeComponent.builder()
                .parentComponent(dependency)
                .view(createView(parentViewGroup))
                .interactor(WelcomeInteractor())
                .build()

        return component.welcomeRouter()
    }

    override fun inflateView(
            inflater: LayoutInflater,
            parentViewGroup: ViewGroup
    ): WelcomeView? = inflater.inflate(
            R.layout.layout_welcome,
            parentViewGroup,
            false
    ) as WelcomeView?

    interface ParentComponent {
        fun userPreferences(): SharedPreferencesStore
        fun onBeginListener(): WelcomeInteractor.Listener
    }

    interface BuilderComponent {
        fun welcomeRouter(): WelcomeRouter
    }
}
