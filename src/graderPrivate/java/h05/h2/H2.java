package h05.h2;

import h05.H5Links;
import h05.H5Utils;
import h05.Unchecked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static h05.Global.socketMapping;
import static h05.H5Utils.toStringifiable;

@TestForSubmission
public class H2 {

    @Test
    public void testInterfaceAndClassCorrect() {
        TypeLink mainboard = H5Links.MAINBOARD_LINK.get();
        TypeLink mainboardImpl = H5Links.MAINBOARD_IMPL_LINK.get();

        H5Utils.assertCorrectModifiers(mainboard, Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.INTERFACE);
        H5Utils.assertCorrectModifiers(mainboardImpl, Modifier.PUBLIC);

        H5Utils.assertCorrectSuperclass(mainboardImpl, H5Links.PURCHASED_COMPONENT_LINK.get());
        H5Utils.assertCorrectInterfaces(mainboardImpl, mainboard);
        if (mainboard.interfaces().stream().noneMatch(i -> i.equals(H5Links.COMPONENT_LINK.get()))) {
            Assertions2.fail(
                Assertions2.emptyContext(),
                (r) -> "`Mainboard` should implement interface `Component`."
            );
        }
    }

    @Test
    public void testMemoryAndPeripheralFields() {
        var memoriesField = H5Links.MAINBOARD_IMPL_MEMORIES_FIELD_LINK.get();
        var peripheralsField = H5Links.MAINBOARD_IMPL_PERIPHERALS_FIELD_LINK.get();

        H5Utils.assertCorrectModifiers(memoriesField, Modifier.PRIVATE, Modifier.FINAL);
        H5Utils.assertCorrectModifiers(peripheralsField, Modifier.PRIVATE, Modifier.FINAL);

        Assertions2.assertEquals(
            toStringifiable(BasicTypeLink.of(H5Links.MEMORY_LINK.get().reflection().arrayType())),
            toStringifiable(memoriesField.type()),
            Assertions2.emptyContext(),
            (r) -> "Field `memories` in class `MainboardImpl` should be of type `Memory[]`."
        );

        Assertions2.assertEquals(
            toStringifiable(BasicTypeLink.of(H5Links.PERIPHERAL_LINK.get().reflection().arrayType())),
            toStringifiable(peripheralsField.type()),
            Assertions2.emptyContext(),
            (r) -> "Field `peripherals` in class `MainboardImpl` should be of type `Peripheral[]`."
        );
    }

    @Test
    public void testConstructor() {
        var constructor = H5Links.MAINBOARD_IMPL_CONSTRUCTOR_LINK.get();
        var memoriesField = H5Links.MAINBOARD_IMPL_MEMORIES_FIELD_LINK.get();
        var peripheralsField = H5Links.MAINBOARD_IMPL_PERIPHERALS_FIELD_LINK.get();

        H5Utils.assertConstructorCorrect(
            constructor,
            new TypeLink[]{
                H5Links.SOCKET_LINK.get(),
                BasicTypeLink.of(int.class),
                BasicTypeLink.of(int.class),
                BasicTypeLink.of(double.class)
            },
            Modifier.PUBLIC
        );

        var instance = Assertions2.callObject(
            () -> constructor.invoke(socketMapping("AM4"), 2, 4, 5.3D),
            Assertions2.emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `MainboardImpl(Socket, int, int, double)` threw an exception.";
            }
        );

        Assertions2.assertEquals(
            5.3D,
            H5Links.PURCHASED_COMPONENT_PRICE_FIELD_LINK.get().get(instance),
            Assertions2.emptyContext(),
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `price` field correctly."
        );

        var memoriesArray = (Object[]) memoriesField.get(instance);
        Assertions2.assertNotNull(
            memoriesArray,
            Assertions2.emptyContext(),
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `memories` field correctly."
        );
        Assertions2.assertEquals(
            2,
            memoriesArray.length,
            Assertions2.emptyContext(),
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `memories` field correctly."
        );

        var peripheralsArray = (Object[]) peripheralsField.get(instance);
        Assertions2.assertNotNull(
            peripheralsArray,
            Assertions2.emptyContext(),
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `peripherals` field correctly."
        );
        Assertions2.assertEquals(
            4,
            peripheralsArray.length,
            Assertions2.emptyContext(),
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `peripherals` field correctly."
        );
    }

    @ParameterizedTest
    @MethodSource("generateAddCpuArguments")
    public void testAddCpu(boolean cpuFieldNull, boolean cpuArgumentNull, String mainboardSocket, String cpuArgSocket) {
        var constructor = H5Links.MAINBOARD_IMPL_CONSTRUCTOR_LINK.get();
        var addCPU = H5Links.MAINBOARD_IMPL_ADD_CPU_METHOD_LINK.get();
        var cpuField = H5Links.MAINBOARD_IMPL_CPU_FIELD_LINK.get();

        H5Utils.assertMethodCorrect(
            addCPU,
            BasicTypeLink.of(boolean.class),
            new TypeLink[]{H5Links.CPU_LINK.get()},
            Modifier.PUBLIC
        );

        var context = Assertions2.contextBuilder()
            .add("cpuFieldNull", cpuFieldNull)
            .add("cpuArgumentNull", cpuArgumentNull)
            .add("mainboardSocket", mainboardSocket)
            .add("cpuArgSocket", cpuArgSocket)
            .build();
        List<Method> otherCPUMethods = Stream.of(
            H5Links.CPU_GET_CORES_METHOD_LINK,
            H5Links.CPU_GET_FREQUENCY_METHOD_LINK,
            H5Links.COMPONENT_GET_PRICE_METHOD_LINK
        )
            .map(methodLinkSupplier -> methodLinkSupplier.get().reflection())
            .toList();
        Function<String, Answer<?>> cpuAnswer = socket -> invocationOnMock -> {
            if (invocationOnMock.getMethod().equals(H5Links.CPU_GET_SOCKET_METHOD_LINK.get().reflection())) {
                return socketMapping(socket);
            } else if (otherCPUMethods.contains(invocationOnMock.getMethod())) {
                return 0;
            } else {
                return invocationOnMock.callRealMethod();
            }
        };
        var instance = Assertions2.callObject(
            () -> constructor.invoke(socketMapping(mainboardSocket), 2, 4, 5.3D),
            context,
            (r) -> "Could not instantiate class `MainboardImpl`."
        );

        Object cpuFieldValue = cpuFieldNull ? null : Mockito.mock(H5Links.CPU_LINK.get().reflection(), cpuAnswer.apply(mainboardSocket));
        if (!cpuFieldNull) {
            cpuField.set(instance, cpuFieldValue);
        }

        Object argumentCpu = cpuArgumentNull ? null : Mockito.mock(H5Links.CPU_LINK.get().reflection(), cpuAnswer.apply(cpuArgSocket));

        var result = Assertions2.callObject(
            Unchecked.uncheckedObjectCallable(() -> addCPU.invoke(instance, argumentCpu)),
            context,
            (r) -> {
                r.cause().printStackTrace();
                return "Method `addCPU(CPU)` in class `MainboardImpl` threw an exception.";
            }
        );

        boolean expectedResult = !cpuArgumentNull && cpuFieldNull && socketMapping(mainboardSocket) == socketMapping(cpuArgSocket);
        Assertions2.assertEquals(
            expectedResult,
            result,
            context,
            (r) -> "Method `addCPU(CPU)` in class `MainboardImpl` did not return the correct value."
        );

        Assertions2.assertEquals(
            expectedResult ? argumentCpu : cpuFieldValue,
            cpuField.get(instance),
            context,
            (r) -> "Method `addCPU(CPU)` in class `MainboardImpl` did not set the `cpu` field correctly."
        );
    }

    public static List<Arguments> generateAddCpuArguments() {
        String[] sockets = {"AM4", "LGA1700"};

        List<Arguments> arguments = new ArrayList<>();
        for (int firstBool = 0; firstBool < 2; firstBool++) {
            for (int secondBool = 0; secondBool < 2; secondBool++) {
                for (int firstSocket = 0; firstSocket < sockets.length; firstSocket++) {
                    for (int secondSocket = 0; secondSocket < sockets.length; secondSocket++) {
                        arguments.add(Arguments.of(
                            firstBool == 0,
                            secondBool == 0,
                            sockets[firstSocket],
                            sockets[secondSocket]
                        ));
                    }
                }
            }
        }

        Collections.shuffle(arguments);
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("generateAddMemoryArguments")
    public void testAddMemory(int memorySlots, List<Object> memoryInstances) {
        var constructor = H5Links.MAINBOARD_IMPL_CONSTRUCTOR_LINK.get();
        var addMemory = H5Links.MAINBOARD_IMPL_ADD_MEMORY_METHOD_LINK.get();
        var memoriesField = H5Links.MAINBOARD_IMPL_MEMORIES_FIELD_LINK.get();

        H5Utils.assertMethodCorrect(
            addMemory,
            BasicTypeLink.of(boolean.class),
            new TypeLink[]{H5Links.MEMORY_LINK.get()},
            Modifier.PUBLIC
        );

        var context = Assertions2.contextBuilder()
            .add("memorySlots", memorySlots)
            .add("memoryInstances", memoryInstances.size())
            .build();

        var instance = Assertions2.callObject(
            () -> constructor.invoke(socketMapping("AM4"), memorySlots, 4, 5.3D),
            context,
            (r) -> "Could not instantiate class `MainboardImpl`."
        );

        var memoriesArray = (Object[]) memoriesField.get(instance);
        Assertions2.assertNotNull(
            memoriesArray,
            context,
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `memories` field correctly."
        );

        int nonNullCount = 0;
        for (Object memory : memoryInstances) {
            var tempContext = Assertions2.contextBuilder()
                .add(context)
                .add("memory", memory)
                .add("index", nonNullCount)
                .build();

            var result = Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> addMemory.invoke(instance, memory)),
                tempContext,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `addMemory(Memory)` in class `MainboardImpl` threw an exception.";
                }
            );

            boolean expectedResult = memory != null && nonNullCount < memorySlots;
            Assertions2.assertEquals(
                expectedResult,
                result,
                tempContext,
                (r) -> "Method `addMemory(Memory)` in class `MainboardImpl` did not return the correct value."
            );

            if (memorySlots > 0 && nonNullCount < memorySlots) {
                Assertions2.assertSame(
                    memory,
                    memoriesArray[nonNullCount],
                    tempContext,
                    (r) -> "Method `addMemory(Memory)` in class `MainboardImpl` did not set the `memories` field correctly."
                );
            }

            if (memory != null) {
                nonNullCount++;
            }
        }
    }

    public static List<Arguments> generateAddMemoryArguments() {
        List<Arguments> arguments = new ArrayList<>();
        for (int memorySlots = 0; memorySlots < 3; memorySlots++) {
            for (int memoryInstance = 0; memoryInstance < memorySlots + 2; memoryInstance++) {
                arguments.add(Arguments.of(
                    memorySlots,
                    Stream.concat(
                        Stream.of((Object) null),
                        IntStream.range(0, memoryInstance).mapToObj(i -> Mockito.mock(H5Links.MEMORY_LINK.get().reflection()))
                    ).toList()
                ));
            }
        }

        Collections.shuffle(arguments);
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("generateAddPeripheralArguments")
    public void testAddPeripheral(int peripheralSlots, List<Object> peripheralInstances) {
        var constructor = H5Links.MAINBOARD_IMPL_CONSTRUCTOR_LINK.get();
        var addPeripheral = H5Links.MAINBOARD_IMPL_ADD_PERIPHERAL_METHOD_LINK.get();
        var peripheralsField = H5Links.MAINBOARD_IMPL_PERIPHERALS_FIELD_LINK.get();

        H5Utils.assertMethodCorrect(
            addPeripheral,
            BasicTypeLink.of(boolean.class),
            new TypeLink[]{H5Links.PERIPHERAL_LINK.get()},
            Modifier.PUBLIC
        );

        var context = Assertions2.contextBuilder()
            .add("peripheralSlots", peripheralSlots)
            .add("peripheralInstances", peripheralInstances.size())
            .build();

        var instance = Assertions2.callObject(
            () -> constructor.invoke(socketMapping("AM4"), 2, peripheralSlots, 5.3D),
            context,
            (r) -> "Could not instantiate class `MainboardImpl`."
        );

        var peripheralsArray = (Object[]) peripheralsField.get(instance);
        Assertions2.assertNotNull(
            peripheralsArray,
            context,
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `peripherals` field correctly."
        );

        int nonNullCount = 0;
        for (Object peripheral : peripheralInstances) {
            var tempContext = Assertions2.contextBuilder()
                .add(context)
                .add("peripheral", peripheral)
                .add("index", nonNullCount)
                .build();

            var result = Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> addPeripheral.invoke(instance, peripheral)),
                tempContext,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `addPeripheral(Peripheral)` in class `MainboardImpl` threw an exception.";
                }
            );

            boolean expectedResult = peripheral != null && nonNullCount < peripheralSlots;
            Assertions2.assertEquals(
                expectedResult,
                result,
                tempContext,
                (r) -> "Method `addPeripheral(Peripheral)` in class `MainboardImpl` did not return the correct value."
            );

            if (peripheralSlots > 0 && nonNullCount < peripheralSlots) {
                Assertions2.assertSame(
                    peripheral,
                    peripheralsArray[nonNullCount],
                    tempContext,
                    (r) -> "Method `addPeripheral(Peripheral)` in class `MainboardImpl` did not set the `peripherals` field correctly."
                );
            }

            if (peripheral != null) {
                nonNullCount++;
            }
        }
    }

    public static List<Arguments> generateAddPeripheralArguments() {
        List<Arguments> arguments = new ArrayList<>();
        for (int peripheralSlots = 0; peripheralSlots < 3; peripheralSlots++) {
            for (int peripheralInstance = 0; peripheralInstance < peripheralSlots + 2; peripheralInstance++) {
                arguments.add(Arguments.of(
                    peripheralSlots,
                    Stream.concat(
                        Stream.of((Object) null),
                        IntStream.range(0, peripheralInstance).mapToObj(i -> Mockito.mock(H5Links.PERIPHERAL_LINK.get().reflection()))
                    ).toList()
                ));
            }
        }

        Collections.shuffle(arguments);
        return arguments;
    }
}
