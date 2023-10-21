package h05.h1;

import h05.H5Links;
import h05.H5Utils;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

@TestForSubmission
public class H1_1 {

    @Test
    public void testInterface() {
        TypeLink component = H5Links.COMPONENT_LINK.get();
        MethodLink getPrice = H5Links.COMPONENT_GET_PRICE_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(component, Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.INTERFACE);

        H5Utils.assertMethodCorrect(
            getPrice,
            BasicTypeLink.of(double.class),
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
    }
}
