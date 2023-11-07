package h05.h2;

import h05.H5Links;
import h05.H5Utils;
import h05.Unchecked;
import h05.model.CPU;
import h05.model.Memory;
import h05.model.Peripheral;
import h05.model.PeripheralType;
import h05.model.Socket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
            toStringifiable(BasicTypeLink.of(Memory[].class)),
            toStringifiable(memoriesField.type()),
            Assertions2.emptyContext(),
            (r) -> "Field `memories` in class `MainboardImpl` should be of type `Memory[]`."
        );

        Assertions2.assertEquals(
            toStringifiable(BasicTypeLink.of(Peripheral[].class)),
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
            () -> constructor.invoke(Socket.AM4, 2, 4, 5.3D),
            Assertions2.emptyContext(),
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` threw an exception."
        );

        Assertions2.assertEquals(
            5.3D,
            H5Links.PURCHASED_COMPONENT_PRICE_FIELD_LINK.get().get(instance),
            Assertions2.emptyContext(),
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `price` field correctly."
        );

        var memoriesArray = (Memory[]) memoriesField.get(instance);
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

        var peripheralsArray = (Peripheral[]) peripheralsField.get(instance);
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
    public void testAddCpu(boolean cpuFieldNull, boolean cpuArgumentNull, Socket mainboardSocket, Socket cpuArgSocket) {
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

        var instance = Assertions2.callObject(
            () -> constructor.invoke(mainboardSocket, 2, 4, 5.3D),
            context,
            (r) -> "Could not instantiate class `MainboardImpl`."
        );

        TestCpu cpuFieldValue = !cpuFieldNull ? new TestCpu(mainboardSocket) : null;
        if (!cpuFieldNull) {
            cpuField.set(instance, cpuFieldValue);
        }

        var argumentCpu = cpuArgumentNull ? null : new TestCpu(cpuArgSocket);

        var result = Assertions2.callObject(
            Unchecked.uncheckedObjectCallable(() -> addCPU.invoke(instance, argumentCpu)),
            context,
            (r) -> "Method `addCPU(CPU)` in class `MainboardImpl` threw an exception."
        );

        boolean expectedResult = !cpuArgumentNull && cpuFieldNull && mainboardSocket == cpuArgSocket;
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
        List<Arguments> arguments = new ArrayList<>();
        for (int firstBool = 0; firstBool < 2; firstBool++) {
            for (int secondBool = 0; secondBool < 2; secondBool++) {
                for (int firstSocket = 0; firstSocket < Socket.values().length; firstSocket++) {
                    for (int secondSocket = 0; secondSocket < Socket.values().length; secondSocket++) {
                        arguments.add(Arguments.of(
                            firstBool == 0,
                            secondBool == 0,
                            Socket.values()[firstSocket],
                            Socket.values()[secondSocket]
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
    public void testAddMemory(int memorySlots, List<Memory> memoryInstances) {
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
            () -> constructor.invoke(Socket.AM4, memorySlots, 4, 5.3D),
            context,
            (r) -> "Could not instantiate class `MainboardImpl`."
        );

        var memoriesArray = (Memory[]) memoriesField.get(instance);
        Assertions2.assertNotNull(
            memoriesArray,
            context,
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `memories` field correctly."
        );

        int nonNullCount = 0;
        for (Memory memory : memoryInstances) {
            var tempContext = Assertions2.contextBuilder()
                    .add(context)
                    .add("memory", memory)
                    .add("index", nonNullCount)
                    .build();

            var result = Assertions2.callObject(
                    Unchecked.uncheckedObjectCallable(() -> addMemory.invoke(instance, memory)),
                    tempContext,
                    (r) -> "Method `addMemory(Memory)` in class `MainboardImpl` threw an exception."
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
                        Stream.of((Memory) null),
                        IntStream.range(0, memoryInstance).mapToObj(i -> new TestMemory())
                    ).toList()
                ));
            }
        }

        Collections.shuffle(arguments);
        return arguments;
    }

    @ParameterizedTest
    @MethodSource("generateAddPeripheralArguments")
    public void testAddPeripheral(int peripheralSlots, List<Peripheral> peripheralInstances) {
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
            () -> constructor.invoke(Socket.AM4, 2, peripheralSlots, 5.3D),
            context,
            (r) -> "Could not instantiate class `MainboardImpl`."
        );

        var peripheralsArray = (Peripheral[]) peripheralsField.get(instance);
        Assertions2.assertNotNull(
            peripheralsArray,
            context,
            (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` did not set the `peripherals` field correctly."
        );

        int nonNullCount = 0;
        for (Peripheral peripheral : peripheralInstances) {
            var tempContext = Assertions2.contextBuilder()
                    .add(context)
                    .add("peripheral", peripheral)
                    .add("index", nonNullCount)
                    .build();

            var result = Assertions2.callObject(
                    Unchecked.uncheckedObjectCallable(() -> addPeripheral.invoke(instance, peripheral)),
                    tempContext,
                    (r) -> "Method `addPeripheral(Peripheral)` in class `MainboardImpl` threw an exception."
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
                        Stream.of((Peripheral) null),
                        IntStream.range(0, peripheralInstance).mapToObj(i -> new TestPeripheral())
                    ).toList()
                ));
            }
        }

        Collections.shuffle(arguments);
        return arguments;
    }

    public static class TestCpu implements CPU {

        private final Socket socket;

        public TestCpu(Socket socket) {
            this.socket = socket;
        }

        @Override
        public Socket getSocket() {
            return socket;
        }

        @Override
        public int getCores() {
            return 0;
        }

        @Override
        public double getFrequency() {
            return 0;
        }

        @Override
        public double getPrice() {
            return 0;
        }

        @Override
        public String toString() {
            return "TestCpu{" + "socket=" + socket + "'}'";
        }
    }

    public static class TestMemory implements Memory {

        @Override
        public double getPrice() {
            return 0;
        }

        @Override
        public char getCapacity() {
            return 0;
        }
    }

    public static class TestPeripheral implements Peripheral {

        @Override
        public double getPrice() {
            return 0;
        }

        @Override
        public PeripheralType getPeripheralType() {
            return null;
        }
    }
}
