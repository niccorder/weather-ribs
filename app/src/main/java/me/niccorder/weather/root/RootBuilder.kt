package me.niccorder.weather.root

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
import me.niccorder.weather.location.LocationBuilder
import me.niccorder.weather.welcome.WelcomeBuilder
import javax.inject.Qualifier
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class RootScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RootInternal

@RootScope
@dagger.Component(
        modules = arrayOf(RootModule::class),
        dependencies = arrayOf(RootBuilder.ParentComponent::class)
)
interface RootComponent : InteractorBaseComponent<RootInteractor>,
        WelcomeBuilder.ParentComponent,
        LocationBuilder.ParentComponent,
        RootBuilder.BuilderComponent {

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance fun interactor(interactor: RootInteractor): Builder
        @BindsInstance fun view(view: RootView): Builder
        fun parentComponent(parentComponent: RootBuilder.ParentComponent): Builder
        fun build(): RootComponent
    }
}


/**
 * Builder for the [RootScope].
 *
 * TODO describe this scope's responsibility as a whole.
 */
public class RootBuilder(
        dependency: ParentComponent
) : ViewBuilder<RootView, RootRouter, RootBuilder.ParentComponent>(
        dependency
) {

    /**
     * Builds a new [RootRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [RootRouter].
     */
    fun build(parentViewGroup: ViewGroup): RootRouter {
        val component = DaggerRootComponent.builder()
                .parentComponent(dependency as ParentComponent)
                .view(createView(parentViewGroup) as RootView)
                .interactor(RootInteractor())
                .build()

        return component.rootRouter()
    }

    override fun inflateView(
            inflater: LayoutInflater,
            parentViewGroup: ViewGroup
    ): RootView? = inflater.inflate(
            R.layout.layout_root,
            parentViewGroup,
            false
    ) as RootView

    interface ParentComponent {
        fun userPreferences(): SharedPreferencesStore
    }

    interface BuilderComponent {
        fun rootRouter(): RootRouter
    }
}
