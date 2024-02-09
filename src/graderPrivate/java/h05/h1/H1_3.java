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

import static h05.Global.socketMapping;
import static h05.H5Utils.toStringifiable;

@TestForSubmission
public class H1_3 {

    @Test
    public void testCPUInterface() {
        TypeLink cpu = H5Links.CPU_LINK.get();
        TypeLink cpuImpl = H5Links.CPU_IMPL_LINK.get();
        MethodLink getSocket = H5Links.CPU_GET_SOCKET_METHOD_LINK.get();
        MethodLink getCores = H5Links.CPU_GET_CORES_METHOD_LINK.get();
        MethodLink getFrequency = H5Links.CPU_GET_FREQUENCY_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(cpuImpl, Modifier.PUBLIC);
        H5Utils.assertCorrectInterfaces(cpuImpl, cpu);

        H5Utils.assertCorrectModifiers(cpu, Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.INTERFACE);

        H5Utils.assertMethodCorrect(
            getSocket,
            H5Links.SOCKET_LINK.get(),
            Modifier.PUBLIC, Modifier.ABSTRACT
        );

        H5Utils.assertMethodCorrect(
            getCores,
            BasicTypeLink.of(int.class),
            Modifier.PUBLIC, Modifier.ABSTRACT
        );

        H5Utils.assertMethodCorrect(
            getFrequency,
            BasicTypeLink.of(double.class),
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
    }

    @Test
    public void testCPUImplConstructor() {
        ConstructorLink constructor = H5Links.CPU_IMPL_CONSTRUCTOR_LINK.get();

        H5Utils.assertConstructorCorrect(
            constructor,
            new TypeLink[]{
                H5Links.SOCKET_LINK.get(),
                BasicTypeLink.of(int.class),
                BasicTypeLink.of(double.class),
                BasicTypeLink.of(double.class)
            },
            Modifier.PUBLIC
        );

        var instance = Assertions2.callObject(
            () -> constructor.invoke(socketMapping("AM4"), 1, 2.5D, 3.5D),
            Assertions2.emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `CPUImpl(Socket, int, double, double)` threw an exception.";
            }
        );

        Assertions2.assertEquals(
            socketMapping("AM4"),
            H5Links.CPU_IMPL_SOCKET_FIELD_LINK.get().get(instance),
            Assertions2.emptyContext(),
            (r) -> "Constructor `CPUImpl(Socket, int, double, double)` did not set the `socket` field correctly."
        );

        Assertions2.assertEquals(
            1,
            H5Links.CPU_IMPL_NUM_OF_CORES_FIELD_LINK.get().get(instance),
            Assertions2.emptyContext(),
            (r) -> "Constructor `CPUImpl(Socket, int, double, double)` did not set the `numOfCores` field correctly."
        );

        Assertions2.assertEquals(
            2.5D,
            H5Links.CPU_IMPL_FREQUENCY_FIELD_LINK.get().get(instance),
            Assertions2.emptyContext(),
            (r) -> "Constructor `CPUImpl(Socket, int, double, double)` did not set the `frequency` field correctly."
        );
    }

    @Test
    public void testCPUImplFields() {
        FieldLink socket = H5Links.CPU_IMPL_SOCKET_FIELD_LINK.get();
        FieldLink numOfCores = H5Links.CPU_IMPL_NUM_OF_CORES_FIELD_LINK.get();
        FieldLink frequency = H5Links.CPU_IMPL_FREQUENCY_FIELD_LINK.get();

        H5Utils.assertCorrectModifiers(socket, Modifier.PRIVATE, Modifier.FINAL);
        H5Utils.assertCorrectModifiers(numOfCores, Modifier.PRIVATE, Modifier.FINAL);
        H5Utils.assertCorrectModifiers(frequency, Modifier.PRIVATE, Modifier.FINAL);

        Assertions2.assertEquals(
            toStringifiable(H5Links.SOCKET_LINK.get()),
            toStringifiable(socket.type()),
            Assertions2.emptyContext(),
            (r) -> "Field `socket` in class `CPUImpl` should be of type `Socket`."
        );

        Assertions2.assertEquals(
            toStringifiable(BasicTypeLink.of(int.class)),
            toStringifiable(numOfCores.type()),
            Assertions2.emptyContext(),
            (r) -> "Field `numOfCores` in class `CPUImpl` should be of type `int`."
        );

        Assertions2.assertEquals(
            toStringifiable(BasicTypeLink.of(double.class)),
            toStringifiable(frequency.type()),
            Assertions2.emptyContext(),
            (r) -> "Field `frequency` in class `CPUImpl` should be of type `double`."
        );
    }

    @Test
    public void testGetMethods() {
        MethodLink getSocket = H5Links.CPU_GET_SOCKET_METHOD_LINK.get();
        MethodLink getCores = H5Links.CPU_GET_CORES_METHOD_LINK.get();
        MethodLink getFrequency = H5Links.CPU_GET_FREQUENCY_METHOD_LINK.get();

        var instance = Assertions2.callObject(
            () -> H5Utils.createUnsafeInstance(H5Links.CPU_IMPL_LINK.get().reflection()),
            Assertions2.emptyContext(),
            (r) -> "Could not instantiate class `CPUImpl`."
        );

        H5Links.CPU_IMPL_SOCKET_FIELD_LINK.get().set(instance, socketMapping("AM4"));
        H5Links.CPU_IMPL_NUM_OF_CORES_FIELD_LINK.get().set(instance, 1);
        H5Links.CPU_IMPL_FREQUENCY_FIELD_LINK.get().set(instance, 2.5D);

        Assertions2.assertEquals(
            socketMapping("AM4"),
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getSocket.invoke(instance)),
                Assertions2.emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getSocket` in class `CPUImpl` threw an exception.";
                }
            ),
            Assertions2.emptyContext(),
            (r) -> "Method `getSocket` in class `CPUImpl` did not return the correct value."
        );

        Assertions2.assertEquals(
            1,
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getCores.invoke(instance)),
                Assertions2.emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getCores/getNumberOfCores` in class `CPUImpl` threw an exception.";
                }
            ),
            Assertions2.emptyContext(),
            (r) -> "Method `getCores/getNumberOfCores` in class `CPUImpl` did not return the correct value."
        );

        Assertions2.assertEquals(
            2.5D,
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getFrequency.invoke(instance)),
                Assertions2.emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getFrequency` in class `CPUImpl` threw an exception.";
                }
            ),
            Assertions2.emptyContext(),
            (r) -> "Method `getFrequency` in class `CPUImpl` did not return the correct value."
        );
    }
}
