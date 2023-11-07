package h05.h4;

import h05.ComponentRater;
import h05.H5Links;
import h05.H5Utils;
import h05.ServerCenter;
import h05.Unchecked;
import h05.model.Mainboard;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.stream.IntStream;

@TestForSubmission
public class H4_3 {

    @Test
    public void testServerCenterClassCorrect() {
        TypeLink serverCenter = H5Links.SERVER_CENTER_LINK.get();

        H5Utils.assertCorrectModifiers(serverCenter, Modifier.PUBLIC);
        H5Utils.assertHasNoSuperclass(serverCenter);
        H5Utils.assertCorrectInterfaces(serverCenter, H5Links.CONFIGURATION_LINK.get());
    }

    @Test
    public void testAddMainboardAndRateBy() {
        MethodLink addMainboard = H5Links.SERVER_CENTER_ADD_MAINBOARD_METHOD_LINK.get();
        MethodLink rateBy = H5Links.CONFIGURATION_RATE_BY_METHOD_LINK.get();

        H5Utils.assertMethodCorrect(
            addMainboard,
            BasicTypeLink.of(void.class),
            new TypeLink[]{H5Links.MAINBOARD_LINK.get()},
            Modifier.PUBLIC
        );

        var mainboards = IntStream.range(0, 10500)
            .mapToObj(i -> {
                Mainboard mainboard = Mockito.mock(Mainboard.class);
                Mockito.doNothing().when(mainboard).rateBy(Mockito.any());
                return mainboard;})
            .toList();

        var raterInstance = Mockito.mock(ComponentRater.class);
        var instance = new ServerCenter();

        Assertions2.call(
            Unchecked.uncheckedCallable(() -> {
                for (Mainboard mainboard : mainboards) {
                    addMainboard.invoke(instance, mainboard);
                }
            }),
            Assertions2.emptyContext(),
            (r) -> "Method `addMainboard(Mainboard)` threw an exception."
        );

        Assertions2.call(
            Unchecked.uncheckedCallable(() -> rateBy.invoke(instance, raterInstance)),
            Assertions2.emptyContext(),
            (r) -> "Method `rateBy(ComponentRater)` threw an exception."
        );

        for (Mainboard mainboard : mainboards) {
            Mockito.verify(mainboard, Mockito.times(1)).rateBy(raterInstance);
            Mockito.verifyNoMoreInteractions(mainboard);
        }
    }
}
