package me.niccorder.weather.location

import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import me.niccorder.weather.data.remote.model.Location
import javax.inject.Inject

@RibInteractor
class LocationInteractor : Interactor<LocationInteractor.Presenter, LocationRouter>() {

    @Inject lateinit var locationPresenter: Presenter

    interface Presenter {
        fun setCurrentLocation(location: Location)
        fun setLocations(locations: List<Location>): Observable<Int>
        fun locationClicks(): Observable<Location>
    }
}