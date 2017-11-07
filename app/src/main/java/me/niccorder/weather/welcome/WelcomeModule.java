package me.niccorder.weather.welcome;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.niccorder.weather.welcome.WelcomeInteractor.WelcomePresenter;

@Module
public abstract class WelcomeModule {

  @WelcomeScope
  @Binds
  abstract WelcomePresenter presenter(WelcomeView welcomeView);

  @WelcomeScope
  @Provides
  static WelcomeRouter router(
      WelcomeComponent welcomeComponent,
      WelcomeView view,
      WelcomeInteractor interactor
  ) {
    return new WelcomeRouter(view, interactor, welcomeComponent);
  }

}
