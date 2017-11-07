package me.niccorder.weather.root;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import me.niccorder.weather.location.LocationBuilder;
import me.niccorder.weather.root.RootInteractor.RootPresenter;
import me.niccorder.weather.welcome.WelcomeBuilder;
import me.niccorder.weather.welcome.WelcomeInteractor;

@Module
abstract class RootModule {

  @RootScope
  @Binds
  abstract RootPresenter presenter(RootView view);

  @RootScope
  @Provides
  static WelcomeInteractor.Listener welcomeInteractorListener(RootInteractor interactor) {
    return interactor.new OnBeginListener();
  }

  @RootScope
  @Provides
  static RootRouter router(
      RootComponent rootComponent,
      RootView view,
      RootInteractor interactor
  ) {
    return new RootRouter(
        view,
        interactor,
        rootComponent,
        new WelcomeBuilder(rootComponent),
        new LocationBuilder(rootComponent)
    );
  }
}
