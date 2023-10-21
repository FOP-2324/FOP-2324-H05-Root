package h05.h1;

import h05.H5Links;
import h05.H5Utils;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

@TestForSubmission
public class H1_2 {

    @Test
    public void testSocketEnum() {
        TypeLink socket = H5Links.SOCKET_LINK.get();

        H5Utils.assertCorrectModifiers(socket, Modifier.PUBLIC, Modifier.FINAL, Modifier.ENUM);
        Assertions2.assertEquals(
            0,
            getEnumMethodCount(socket),
            Assertions2.emptyContext(),
            (r) -> "Interface `Socket` should have no methods."
        );

        Assertions3.assertHasEnumConstant(socket, BasicStringMatchers.identical("AM4"));
        Assertions3.assertHasEnumConstant(socket, BasicStringMatchers.identical("LGA1700"));
    }

    @Test
    public void testPeripheralTypeEnum() {
        TypeLink peripheralType = H5Links.PERIPHERAL_TYPE_LINK.get();

        H5Utils.assertCorrectModifiers(peripheralType, Modifier.PUBLIC, Modifier.FINAL, Modifier.ENUM);
        Assertions2.assertEquals(
            0,
            getEnumMethodCount(peripheralType),
            Assertions2.emptyContext(),
            (r) -> "Interface `PeripheralType` should have no methods."
        );

        Assertions3.assertHasEnumConstant(peripheralType, BasicStringMatchers.identical("GPU"));
        Assertions3.assertHasEnumConstant(peripheralType, BasicStringMatchers.identical("ETHERNET"));
        Assertions3.assertHasEnumConstant(peripheralType, BasicStringMatchers.identical("TPU"));
    }

    private static int getEnumMethodCount(TypeLink type) {
        return (int) type.getMethods().stream().filter(m -> !m.name().equals("values") && !m.name().equals("valueOf") && !m.name().equals("$values")).count();
    }
}
