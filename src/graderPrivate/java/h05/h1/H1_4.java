package h05.h1;

import h05.H5Links;
import h05.H5Utils;
import h05.Unchecked;
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
public class H1_4 {

    @Test
    public void testMemoryInterface() {
        TypeLink memory = H5Links.MEMORY_LINK.get();
        TypeLink memoryImpl = H5Links.MEMORY_IMPL_LINK.get();
        MethodLink getCapacity = H5Links.MEMORY_GET_CAPACITY_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(memoryImpl, Modifier.PUBLIC);
        H5Utils.assertCorrectInterfaces(memoryImpl, memory);

        H5Utils.assertCorrectModifiers(memory, Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.INTERFACE);

        H5Utils.assertMethodCorrect(
            getCapacity,
            BasicTypeLink.of(char.class),
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
    }

    @Test
    public void testMemoryImplConstructor() {
        ConstructorLink constructor = H5Links.MEMORY_IMPL_CONSTRUCTOR_LINK.get();

        H5Utils.assertConstructorCorrect(
            constructor,
            new TypeLink[]{
                BasicTypeLink.of(char.class),
                BasicTypeLink.of(double.class)
            },
            Modifier.PUBLIC
        );

        var instance = Assertions2.callObject(
            () -> constructor.invoke('a', 3.1D),
            Assertions2.emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `MemoryImpl(char, double)` threw an exception.";
            }
        );

        Assertions2.assertEquals(
            'a',
            H5Links.MEMORY_IMPL_CAPACITY_FIELD_LINK.get().get(instance),
            Assertions2.emptyContext(),
            (r) -> "Constructor `MemoryImpl(char, double)` did not set the `capacity` field correctly."
        );

        // TODO: Price field
    }

    @Test
    public void testMemoryImplFields() {
        FieldLink capacity = H5Links.MEMORY_IMPL_CAPACITY_FIELD_LINK.get();

        H5Utils.assertCorrectModifiers(capacity, Modifier.PRIVATE, Modifier.FINAL);

        Assertions2.assertEquals(
            toStringifiable(BasicTypeLink.of(char.class)),
            toStringifiable(capacity.type()),
            Assertions2.emptyContext(),
            (r) -> "Field `capacity` in class `MemoryImpl` should be of type `char`."
        );
    }

    @Test
    public void testGetCapacity() {
        MethodLink getCapacity = H5Links.MEMORY_GET_CAPACITY_METHOD_LINK.get();

        var instance = Assertions2.callObject(
            () -> H5Utils.createUnsafeInstance(H5Links.MEMORY_IMPL_LINK.get().reflection()),
            Assertions2.emptyContext(),
            (r) -> "Could not instantiate class `MemoryImpl`."
        );

        H5Links.MEMORY_IMPL_CAPACITY_FIELD_LINK.get().set(instance, 'a');

        Assertions2.assertEquals(
            'a',
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getCapacity.invoke(instance)),
                Assertions2.emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getCapacity` in class `MemoryImpl` threw an exception.";
                }
            ),
            Assertions2.emptyContext(),
            (r) -> "Method `getCapacity` in class `MemoryImpl` did not return the correct value."
        );
    }
}
