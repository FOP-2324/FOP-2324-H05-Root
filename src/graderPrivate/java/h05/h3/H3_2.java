package h05.h3;

import h05.ComponentRater;
import h05.H5Links;
import h05.H5Utils;
import h05.model.CPU;
import h05.model.CPUImpl;
import h05.model.MainboardImpl;
import h05.model.Memory;
import h05.model.MemoryImpl;
import h05.model.Peripheral;
import h05.model.PeripheralImpl;
import h05.model.PeripheralType;
import h05.model.Socket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@TestForSubmission
public class H3_2 {

    @Test
    public void testConfigurationInterface() {
        TypeLink configuration = H5Links.CONFIGURATION_LINK.get();
        MethodLink rateBy = H5Links.CONFIGURATION_RATE_BY_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(configuration, Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.INTERFACE);

        H5Utils.assertMethodCorrect(
            rateBy,
            BasicTypeLink.of(void.class),
            new TypeLink[]{H5Links.COMPONENT_RATER_LINK.get()},
            Modifier.PUBLIC, Modifier.ABSTRACT
        );
    }

    @Test
    public void testMainboardImplAdjustment() {
        TypeLink mainboard = H5Links.MAINBOARD_LINK.get();
        MethodLink rateBy = H5Links.MAINBOARD_IMPL_RATE_BY_METHOD_LINK.get();

        H5Utils.assertCorrectInterfaces(mainboard, H5Links.COMPONENT_LINK.get(), H5Links.CONFIGURATION_LINK.get());

        H5Utils.assertMethodCorrect(
            rateBy,
            BasicTypeLink.of(void.class),
            new TypeLink[]{H5Links.COMPONENT_RATER_LINK.get()},
            Modifier.PUBLIC
        );
    }

    @ParameterizedTest
    @MethodSource("generateMainboardImplSetup")
    public void testMainboardImplRateBy(CPU cpu, Memory[] memories, Peripheral[] peripherals) {
        H5Links.COMPONENT_RATER_LINK.get(); // Exists check
        H5Links.SOCKET_LINK.get(); // Exists check
        H5Links.MAINBOARD_IMPL_RATE_BY_METHOD_LINK.get(); // Exists check

        Context context = Assertions2.contextBuilder()
            .add("cpu", cpu)
            .add("memories", memories.length)
            .add("peripherals", peripherals.length)
            .build();

        MainboardImpl mainboard = Assertions2.callObject(
            () -> new MainboardImpl(Socket.LGA1700, memories.length + 3, peripherals.length + 8, 55D),
            context,
            (r) -> "Could not instantiate class `MainboardImpl`."
        );

        Assertions2.call(() -> {
                mainboard.addCPU(cpu);
                for (Memory memory : memories)
                    mainboard.addMemory(memory);
                for (Peripheral peripheral : peripherals)
                    mainboard.addPeripheral(peripheral);
            },
            context,
            (r) -> "Method `addCPU`, `addMemory` or `addPeripheral` in class `MainboardImpl` threw an exception."
        );

        ComponentRater rater = Mockito.mock(ComponentRater.class);
        Mockito.doNothing().when(rater).consumeMainboard(Mockito.any());
        Mockito.doNothing().when(rater).consumeCPU(Mockito.any());
        Mockito.doNothing().when(rater).consumeMemory(Mockito.any());
        Mockito.doNothing().when(rater).consumePeripheral(Mockito.any());

        Assertions2.call(
            () -> mainboard.rateBy(rater),
            context,
            (r) -> "Method `rateBy` in class `MainboardImpl` threw an exception."
        );

        Mockito.verify(rater).consumeMainboard(Mockito.same(mainboard));

        if (cpu != null)
            Mockito.verify(rater).consumeCPU(Mockito.same(cpu));
        Mockito.verify(rater, Mockito.never()).consumeCPU(null);

        for (Memory memory : memories) {
            if (memory != null)
                Mockito.verify(rater).consumeMemory(Mockito.same(memory));
        }

        for (Peripheral peripheral : peripherals) {
            if (peripheral != null)
                Mockito.verify(rater).consumePeripheral(Mockito.same(peripheral));
        }

        Mockito.verifyNoMoreInteractions(rater);
    }

    public static List<Arguments> generateMainboardImplSetup() {
        List<Arguments> args = new ArrayList<>();
        for (CPU cpu : Arrays.asList(null, new CPUImpl(Socket.LGA1700, 1, 5.6d, 2.5D))) {
            for (int memoryCount = 0; memoryCount < 4; memoryCount++) {
                for (int peripheralCount = 0; peripheralCount < 4; peripheralCount++) {
                    args.add(Arguments.of(
                        cpu,
                        IntStream.range(0, memoryCount)
                            .mapToObj(i -> new MemoryImpl('b', 1))
                            .toArray(Memory[]::new),
                        IntStream.range(0, peripheralCount)
                            .mapToObj(i -> new PeripheralImpl(PeripheralType.TPU, 1))
                            .toArray(Peripheral[]::new)
                    ));
                }
            }
        }

        return args;
    }
}
