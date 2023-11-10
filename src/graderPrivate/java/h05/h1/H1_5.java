package h05.h1;

import h05.H5Links;
import h05.H5Utils;
import h05.Unchecked;
import h05.model.PeripheralType;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.ConstructorLink;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import static h05.H5Utils.toStringifiable;

@TestForSubmission
public class H1_5 {

    @Test
    public void testPeripheralInterface() {
        TypeLink peripheral = H5Links.PERIPHERAL_LINK.get();
        TypeLink peripheralImpl = H5Links.PERIPHERAL_IMPL_LINK.get();
        MethodLink getPeripheralType = H5Links.PERIPHERAL_GET_PERIPHERAL_TYPE_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(peripheralImpl, Modifier.PUBLIC);
        H5Utils.assertCorrectInterfaces(peripheralImpl, peripheral);

        H5Utils.assertCorrectModifiers(peripheral, Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.INTERFACE);

        H5Utils.assertMethodCorrect(
            getPeripheralType,
            H5Links.PERIPHERAL_TYPE_LINK.get(),
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
    }

    @Test
    public void testPeripheralImplConstructor() {
        ConstructorLink constructor = H5Links.PERIPHERAL_IMPL_CONSTRUCTOR_LINK.get();

        H5Utils.assertConstructorCorrect(
            constructor,
            new TypeLink[]{
                H5Links.PERIPHERAL_TYPE_LINK.get(),
                BasicTypeLink.of(double.class)
            },
            Modifier.PUBLIC
        );

        var instance = Assertions2.callObject(
            () -> constructor.invoke(PeripheralType.ETHERNET, 3.1D),
            Assertions2.emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `PeripheralImpl(char, double)` threw an exception.";
            }
        );

        Assertions2.assertEquals(
            PeripheralType.ETHERNET,
            H5Links.PERIPHERAL_IMPL_PERIPHERAL_TYPE_FIELD_LINK.get().get(instance),
            Assertions2.emptyContext(),
            (r) -> "Constructor `PeripheralImpl(char, double)` did not set the `peripheralType` field correctly."
        );
    }

    @Test
    public void testPeripheralImplFields() {
        FieldLink peripheralType = H5Links.PERIPHERAL_IMPL_PERIPHERAL_TYPE_FIELD_LINK.get();

        H5Utils.assertCorrectModifiers(peripheralType, Modifier.PRIVATE, Modifier.FINAL);

        Assertions2.assertEquals(
            toStringifiable(H5Links.PERIPHERAL_TYPE_LINK.get()),
            toStringifiable(peripheralType.type()),
            Assertions2.emptyContext(),
            (r) -> "Field `peripheralType` in class `PeripheralImpl` should be of type `PeripheralType`."
        );
    }

    @Test
    public void testGetPeripheralType() {
        MethodLink getPeripheralType = H5Links.PERIPHERAL_GET_PERIPHERAL_TYPE_METHOD_LINK.get();

        var instance = Assertions2.callObject(
            () -> H5Utils.createUnsafeInstance(H5Links.PERIPHERAL_IMPL_LINK.get().reflection()),
            Assertions2.emptyContext(),
            (r) -> "Could not instantiate class `PeripheralImpl`."
        );

        H5Links.PERIPHERAL_IMPL_PERIPHERAL_TYPE_FIELD_LINK.get().set(instance, PeripheralType.ETHERNET);

        Assertions2.assertEquals(
            PeripheralType.ETHERNET,
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getPeripheralType.invoke(instance)),
                Assertions2.emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getPeripheralType` in class `PeripheralImpl` threw an exception.";
                }
            ),
            Assertions2.emptyContext(),
            (r) -> "Method `getPeripheralType` in class `PeripheralImpl` did not return the correct value."
        );
    }
}
