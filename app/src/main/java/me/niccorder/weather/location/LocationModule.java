package me.niccorder.weather.location;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class LocationModule {

  @LocationScope
  @Binds
  abstract LocationInteractor.Presenter locationPresenter(LocationView locationView);

  @LocationScope
  @Provides
  static LocationRouter router(
      LocationView view,
      LocationInteractor interactor,
      LocationComponent component
  ) {
    return new LocationRouter(view, interactor, component);
  }
}
