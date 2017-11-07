package me.niccorder.weather.location

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.RibBuilder
import com.uber.rib.core.ViewBuilder
import dagger.BindsInstance
import me.niccorder.weather.R
import me.niccorder.weather.internal.infra.SharedPreferencesStore
import me.niccorder.weather.root.*
import me.niccorder.weather.welcome.WelcomeBuilder
import javax.inject.Qualifier
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.BINARY)
annotation class LocationScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationInternal

@LocationScope
@dagger.Component(
        modules = arrayOf(LocationModule::class),
        dependencies = arrayOf(LocationBuilder.ParentComponent::class)
)
interface LocationComponent : InteractorBaseComponent<LocationInteractor>,
        LocationBuilder.BuilderComponent {

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance fun interactor(interactor: LocationInteractor): Builder
        @BindsInstance fun view(view: LocationView): Builder

        fun parentComponent(parentComponent: LocationBuilder.ParentComponent): Builder

        fun build(): LocationComponent
    }
}


/**
 * Builder for the [LocationScope].
 *
 * TODO describe this scope's responsibility as a whole.
 */
@RibBuilder
public class LocationBuilder(
        dependency: ParentComponent
) : ViewBuilder<LocationView, LocationRouter, LocationBuilder.ParentComponent>(
        dependency
) {



    /**
     * Builds a new [LocationRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [LocationRouter].
     */
    fun build(parentViewGroup: ViewGroup): LocationRouter {
        val component = DaggerLocationComponent.builder()
                .parentComponent(dependency as ParentComponent)
                .view(createView(parentViewGroup))
                .interactor(LocationInteractor())
                .build()

        return component.locationRouter()
    }

    override fun inflateView(
            inflater: LayoutInflater,
            parentViewGroup: ViewGroup
    ): LocationView? = inflater.inflate(
            R.layout.layout_location,
            parentViewGroup,
            false
    ) as LocationView?

    interface ParentComponent {
        fun userPreferences(): SharedPreferencesStore
    }

    interface BuilderComponent {
        fun locationRouter(): LocationRouter
    }
}
