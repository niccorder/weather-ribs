package me.niccorder.weather.welcome;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WelcomeRibRouterTest extends RibTestBasePlaceholder {

    @Mock WelcomeBuilder.Component component;
    @Mock WelcomeRibInteractor interactor;
    @Mock WelcomeRibView view;

    private WelcomeRibRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new WelcomeRibRouter(view, interactor, component);
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
