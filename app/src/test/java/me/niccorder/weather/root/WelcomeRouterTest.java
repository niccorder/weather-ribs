package me.niccorder.weather.root;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import me.niccorder.weather.welcome.WelcomeInteractor;
import me.niccorder.weather.welcome.WelcomeRouter;
import me.niccorder.weather.welcome.WelcomeView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WelcomeRouterTest extends RibTestBasePlaceholder {

    @Mock me.niccorder.weather.welcome.WelcomeBuilder.Component component;
    @Mock WelcomeInteractor interactor;
    @Mock WelcomeView view;

    private WelcomeRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new WelcomeRouter(view, interactor, component);
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    public void anExampleTest_withSomeConditions_shouldPass() {
        // Use RouterHelper to drive your router's lifecycle.
        RouterHelper.attach(router);
        RouterHelper.detach(router);

        throw new RuntimeException("Remove this test and add real tests.");
    }

}
