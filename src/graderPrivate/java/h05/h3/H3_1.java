package h05.h3;

import h05.H5Links;
import h05.H5Utils;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

@TestForSubmission
public class H3_1 {

    @Test
    public void testComponentRaterInterface() {
        TypeLink componentRater = H5Links.COMPONENT_RATER_LINK.get();
        MethodLink consumeMainboard = H5Links.COMPONENT_RATER_CONSUME_MAINBOARD_METHOD_LINK.get();
        MethodLink consumeCPU = H5Links.COMPONENT_RATER_CONSUME_CPU_METHOD_LINK.get();
        MethodLink consumeMemory = H5Links.COMPONENT_RATER_CONSUME_MEMORY_METHOD_LINK.get();
        MethodLink consumePeripheral = H5Links.COMPONENT_RATER_CONSUME_PERIPHERAL_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(componentRater, Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.INTERFACE);
        H5Utils.assertCorrectInterfaces(componentRater);

        H5Utils.assertMethodCorrect(
            consumeMainboard,
            BasicTypeLink.of(void.class),
            new TypeLink[]{H5Links.MAINBOARD_LINK.get()},
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
        H5Utils.assertMethodCorrect(
            consumeCPU,
            BasicTypeLink.of(void.class),
            new TypeLink[]{H5Links.CPU_LINK.get()},
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
        H5Utils.assertMethodCorrect(
            consumeMemory,
            BasicTypeLink.of(void.class),
            new TypeLink[]{H5Links.MEMORY_LINK.get()},
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
        H5Utils.assertMethodCorrect(
            consumePeripheral,
            BasicTypeLink.of(void.class),
            new TypeLink[]{H5Links.PERIPHERAL_LINK.get()},
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
    }
}
