package h05.h3;

import h05.H5Links;
import h05.H5Utils;
import kotlin.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static h05.Global.peripheralTypeMapping;
import static h05.Global.socketMapping;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

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
    public void testMainboardImplRateBy(Object cpu, Object[] memories, Object[] peripherals) {
        H5Links.COMPONENT_RATER_LINK.get(); // Exists check
        H5Links.SOCKET_LINK.get(); // Exists check
        H5Links.MAINBOARD_IMPL_RATE_BY_METHOD_LINK.get(); // Exists check

        Context baseContext = contextBuilder()
            .add("cpu", cpu)
            .add("memories", memories.length)
            .add("peripherals", peripherals.length)
            .build();

        Object mainboard = callObject(
            () -> H5Links.MAINBOARD_IMPL_CONSTRUCTOR_LINK.get()
                .invoke(socketMapping("LGA1700"), memories.length + 3, peripherals.length + 8, 55D),
            baseContext,
            (r) -> "Could not instantiate class `MainboardImpl`."
        );

        call(() -> {
                H5Links.MAINBOARD_IMPL_ADD_CPU_METHOD_LINK.get().invoke(mainboard, cpu);
                for (Object memory : memories)
                    H5Links.MAINBOARD_IMPL_ADD_MEMORY_METHOD_LINK.get().invoke(mainboard, memory);
                for (Object peripheral : peripherals)
                    H5Links.MAINBOARD_IMPL_ADD_PERIPHERAL_METHOD_LINK.get().invoke(mainboard, peripheral);
            },
            baseContext,
            (r) -> {
                r.cause().printStackTrace();
                return "Method `addCPU`, `addMemory` or `addPeripheral` in class `MainboardImpl` threw an exception.";
            }
        );

        var consumeMainboardLink = H5Links.COMPONENT_RATER_CONSUME_MAINBOARD_METHOD_LINK.get();
        var consumeCPULink = H5Links.COMPONENT_RATER_CONSUME_CPU_METHOD_LINK.get();
        var consumeMemoryLink = H5Links.COMPONENT_RATER_CONSUME_MEMORY_METHOD_LINK.get();
        var consumePeripheralLink = H5Links.COMPONENT_RATER_CONSUME_PERIPHERAL_METHOD_LINK.get();

        Map<String, Pair<AtomicInteger, List<Object[]>>> invocationsOnMock = new HashMap<>() {{
            put(consumeMainboardLink.name(), new Pair<>(new AtomicInteger(), new ArrayList<>()));
            put(consumeCPULink.name(), new Pair<>(new AtomicInteger(), new ArrayList<>()));
            put(consumeMemoryLink.name(), new Pair<>(new AtomicInteger(), new ArrayList<>()));
            put(consumePeripheralLink.name(), new Pair<>(new AtomicInteger(), new ArrayList<>()));
        }};
        Object rater = Mockito.mock(H5Links.COMPONENT_RATER_LINK.get().reflection(), (Answer<?>) invocationOnMock -> {
            invocationsOnMock.compute(invocationOnMock.getMethod().getName(), (key, value) -> {
                if (value != null) {
                    value.getFirst().incrementAndGet();
                    value.getSecond().add(invocationOnMock.getRawArguments());
                }
                return value;
            });
            return Mockito.RETURNS_DEFAULTS.answer(invocationOnMock);
        });

        call(
            () -> H5Links.MAINBOARD_IMPL_RATE_BY_METHOD_LINK.get().invoke(mainboard, rater),
            baseContext,
            (r) -> {
                r.cause().printStackTrace();
                return "Method `rateBy` in class `MainboardImpl` threw an exception.";
            }
        );

//        Mockito.verify(rater).consumeMainboard(Mockito.same(mainboard));
        assertTrue(invocationsOnMock.get(consumeMainboardLink.name()).getFirst().get() > 0, baseContext,
            result -> "`consumeMainboard(Mainboard)` was not called in `MainboardImpl.rateBy`");
        assertTrue(invocationsOnMock.get(consumeMainboardLink.name())
            .getSecond()
            .stream()
            .flatMap(Arrays::stream)
            .allMatch(o -> o == mainboard), baseContext, result -> "`consumeMainboard(Mainboard)` was not called with the expected argument");

        if (cpu != null) {
//            Mockito.verify(rater).consumeCPU(Mockito.same(cpu));
            assertTrue(invocationsOnMock.get(consumeCPULink.name()).getFirst().get() > 0, baseContext,
                result -> "`consumeCPU(CPU)` was not called in `MainboardImpl.rateBy`");
            assertTrue(invocationsOnMock.get(consumeCPULink.name())
                .getSecond()
                .stream()
                .flatMap(Arrays::stream)
                .allMatch(o -> o == cpu), baseContext, result -> "`consumeCPU(CPU)` was not called with the expected argument");
        }
//        Mockito.verify(rater, Mockito.never()).consumeCPU(null);
        assertTrue(invocationsOnMock.get(consumeCPULink.name())
            .getSecond()
            .stream()
            .flatMap(Arrays::stream)
            .allMatch(Objects::nonNull), baseContext, result -> "`consumeCPU(CPU)` was called with `null` as argument");

        for (Object memory : memories) {
            if (memory != null) {
                Context context = contextBuilder()
                    .add(baseContext)
                    .add("memory", memory)
                    .build();
//                Mockito.verify(rater).consumeMemory(Mockito.same(memory));
                assertTrue(invocationsOnMock.get(consumeMemoryLink.name()).getFirst().get() > 0, context,
                    result -> "`consumeMemory(Memory)` was not called in `MainboardImpl.rateBy`");
                assertTrue(invocationsOnMock.get(consumeMemoryLink.name())
                    .getSecond()
                    .stream()
                    .flatMap(Arrays::stream)
                    .anyMatch(o -> o == memory), context, result -> "`consumeMemory(Memory)` was not called with the expected argument");
            }
        }

        for (Object peripheral : peripherals) {
            if (peripheral != null) {
                Context context = contextBuilder()
                    .add(baseContext)
                    .add("peripheral", peripheral)
                    .build();
//                Mockito.verify(rater).consumePeripheral(Mockito.same(peripheral));
                assertTrue(invocationsOnMock.get(consumePeripheralLink.name()).getFirst().get() > 0, context,
                    result -> "`consumePeripheral(Peripheral)` was not called in `MainboardImpl.rateBy`");
                assertTrue(invocationsOnMock.get(consumePeripheralLink.name())
                    .getSecond()
                    .stream()
                    .flatMap(Arrays::stream)
                    .anyMatch(o -> o == peripheral), context, result -> "`consumePeripheral(Peripheral)` was not called with the expected argument");
            }
        }
    }

    public static List<Arguments> generateMainboardImplSetup() {
        List<Arguments> args = new ArrayList<>();
        for (Object cpu : Arrays.asList(null, Mockito.mock(H5Links.CPU_LINK.get().reflection(), CPU_ANSWER))) {
            for (int memoryCount = 0; memoryCount < 4; memoryCount++) {
                for (int peripheralCount = 0; peripheralCount < 4; peripheralCount++) {
                    args.add(Arguments.of(
                        cpu,
                        IntStream.range(0, memoryCount)
                            .mapToObj(i -> Mockito.mock(H5Links.MEMORY_LINK.get().reflection(), MEMORY_ANSWER))
                            .toArray(Object[]::new),
                        IntStream.range(0, peripheralCount)
                            .mapToObj(i -> Mockito.mock(H5Links.PERIPHERAL_LINK.get().reflection(), PERIPHERAL_ANSWER))
                            .toArray(Object[]::new)
                    ));
                }
            }
        }

        return args;
    }

    private static final Answer<?> CPU_ANSWER = invocationOnMock -> {
        if (invocationOnMock.getMethod().equals(H5Links.CPU_GET_SOCKET_METHOD_LINK.get().reflection())) {
            return socketMapping("LGA1700");
        } else if (invocationOnMock.getMethod().equals(H5Links.CPU_GET_CORES_METHOD_LINK.get().reflection())) {
            return 1;
        } else if (invocationOnMock.getMethod().equals(H5Links.CPU_GET_FREQUENCY_METHOD_LINK.get().reflection())) {
            return 5.6d;
        } else if (invocationOnMock.getMethod().equals(H5Links.COMPONENT_GET_PRICE_METHOD_LINK.get().reflection())) {
            return 2.5d;
        } else {
            return invocationOnMock.callRealMethod();
        }
    };

    private static final Answer<?> MEMORY_ANSWER = invocationOnMock -> {
        if (invocationOnMock.getMethod().equals(H5Links.MEMORY_GET_CAPACITY_METHOD_LINK.get().reflection())) {
            return 'b';
        } else if (invocationOnMock.getMethod().equals(H5Links.COMPONENT_GET_PRICE_METHOD_LINK.get().reflection())) {
            return 1;
        } else {
            return invocationOnMock.callRealMethod();
        }
    };

    private static final Object TPU = peripheralTypeMapping("TPU");

    private static final Answer<?> PERIPHERAL_ANSWER = invocationOnMock -> {
        if (invocationOnMock.getMethod().equals(H5Links.PERIPHERAL_GET_PERIPHERAL_TYPE_METHOD_LINK.get().reflection())) {
            return TPU;
        } else if (invocationOnMock.getMethod().equals(H5Links.COMPONENT_GET_PRICE_METHOD_LINK.get().reflection())) {
            return 1;
        } else {
            return invocationOnMock.callRealMethod();
        }
    };
}
