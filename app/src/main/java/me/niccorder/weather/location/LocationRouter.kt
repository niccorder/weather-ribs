package me.niccorder.weather.location

import com.uber.rib.core.ViewRouter

class LocationRouter(
        view: LocationView,
        interactor: LocationInteractor,
        locationComponent: LocationComponent
) : ViewRouter<LocationView, LocationInteractor, LocationComponent>(
        view,
        interactor,
        locationComponent
) {

}