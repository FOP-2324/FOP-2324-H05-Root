package h05.h4;

import h05.H5Links;
import h05.H5Utils;
import h05.Unchecked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.ConstructorLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

@TestForSubmission
public class H4_2 {

    @Test
    public void testVirtualMemoryClassCorrect() {
        TypeLink virtualMemory = H5Links.VIRTUAL_MEMORY_LINK.get();

        H5Utils.assertCorrectModifiers(virtualMemory, Modifier.PUBLIC);
        H5Utils.assertCorrectInterfaces(virtualMemory, H5Links.MEMORY_LINK.get());
        H5Utils.assertHasNoSuperclass(virtualMemory);
    }

    @ParameterizedTest
    @CsvSource({
        "5.13, 1",
        "0.0, 2",
        "1.0, 3",
        "15.56, 4",
        "1661.0, 52",
    })
    public void testVirtualMemoryMethodsAndConstructor(double costPerGigaByte, int capacity) {
        ConstructorLink constructor = H5Links.VIRTUAL_MEMORY_CONSTRUCTOR_LINK.get();
        MethodLink getPrice = H5Links.COMPONENT_GET_PRICE_METHOD_LINK.get();
        MethodLink getCapacity = H5Links.MEMORY_GET_CAPACITY_METHOD_LINK.get();
        MethodLink setCapacity = H5Links.VIRTUAL_MEMORY_SET_CAPACITY_METHOD_LINK.get();

        H5Utils.assertConstructorCorrect(constructor, new TypeLink[]{BasicTypeLink.of(double.class)}, Modifier.PUBLIC);
        H5Utils.assertMethodCorrect(
            setCapacity,
            BasicTypeLink.of(void.class),
            new TypeLink[]{BasicTypeLink.of(char.class)},
            Modifier.PUBLIC
        );

        var context = Assertions2.contextBuilder()
            .add("costPerGigaByte", costPerGigaByte)
            .add("capacity", capacity)
            .build();

        var instance = Assertions2.callObject(
            () -> constructor.invoke(costPerGigaByte),
            context,
            (r) -> "Constructor `VirtualMemory(double)` threw an exception."
        );

        Assertions2.call(
            Unchecked.uncheckedCallable(() -> setCapacity.invoke(instance, (char) capacity)),
            context,
            (r) -> "Method `setCapacity(char)` threw an exception."
        );

        Assertions2.assertEquals(
            (char) capacity,
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getCapacity.invoke(instance)),
                context,
                (r) -> "Method `getCapacity()` threw an exception."
            ),
            context,
            (r) -> "Method `setCapacity(char)` did not save the capacity correctly."
        );

        Assertions2.assertEquals(
            costPerGigaByte * capacity,
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getPrice.invoke(instance)),
                context,
                (r) -> "Method `getPrice()` threw an exception."
            ),
            context,
            (r) -> "Constructor `VirtualMemory(double)` did not set the `price` field correctly."
        );
    }
}
