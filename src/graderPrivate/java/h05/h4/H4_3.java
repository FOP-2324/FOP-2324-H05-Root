package h05.h4;

import h05.H5Links;
import h05.H5Utils;
import h05.Unchecked;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

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
    public void testAddMainboardAndRateBy() throws Throwable {
        MethodLink addMainboard = H5Links.SERVER_CENTER_ADD_MAINBOARD_METHOD_LINK.get();
        MethodLink rateBy = H5Links.CONFIGURATION_RATE_BY_METHOD_LINK.get();

        H5Utils.assertMethodCorrect(
            addMainboard,
            BasicTypeLink.of(void.class),
            new TypeLink[]{H5Links.MAINBOARD_LINK.get()},
            Modifier.PUBLIC
        );

        Map<Object, Integer> rateByInvocations = new HashMap<>();
        var mainboards = IntStream.range(0, 10000)
            .mapToObj(i -> Mockito.mock(H5Links.MAINBOARD_LINK.get().reflection(), (Answer<?>) invocationOnMock -> {
                if (invocationOnMock.getMethod().equals(H5Links.CONFIGURATION_RATE_BY_METHOD_LINK.get().reflection())) {
                    rateByInvocations.compute(invocationOnMock.getMock(), (key, value) -> value != null ? value + 1 : 1);
                }
                return Mockito.RETURNS_DEFAULTS.answer(invocationOnMock);
            }))
            .toList();

        var raterInstance = Mockito.mock(H5Links.COMPONENT_RATER_LINK.get().reflection());
        var instance = H5Links.SERVER_CENTER_LINK.get()
            .getConstructor(Matcher.of(constructorLink -> constructorLink.typeList().isEmpty()))
            .invoke();

        call(
            Unchecked.uncheckedCallable(() -> {
                for (Object mainboard : mainboards) {
                    addMainboard.invoke(instance, mainboard);
                }
            }),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Method `addMainboard(Mainboard)` threw an exception.";
            }
        );

        call(
            Unchecked.uncheckedCallable(() -> rateBy.invoke(instance, raterInstance)),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Method `rateBy(ComponentRater)` threw an exception.";
            }
        );

        for (Object mainboard : mainboards) {
            assertEquals(1, rateByInvocations.get(mainboard), emptyContext(),
                result -> "`Mainboard.rateBy(int)` was not invoked exactly once");
        }
    }
}
