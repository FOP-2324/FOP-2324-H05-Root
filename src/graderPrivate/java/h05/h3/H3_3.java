package h05.h3;

import h05.H5Links;
import h05.H5Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static h05.Global.peripheralTypeMapping;

@TestForSubmission
public class H3_3 {

    @ParameterizedTest
    @CsvSource(value = {
        "142.12, 250, 78.4, 12.123",
        "54, 1, 1551, 1",
        "0, 0, 0, 0",
        "1, 1, 1, 1",
        "510.12, 12666.6, 65.32, 12.2"
    })
    public void testTotalCostRater(double mainboardPrice, double cpuPrice, double memoryPrice, double peripheralPrice) throws Throwable {
        TypeLink totalCostRater = H5Links.TOTAL_COST_RATER_LINK.get();
        TypeLink componentRater = H5Links.COMPONENT_RATER_LINK.get();
        MethodLink getTotalPrice = H5Links.TOTAL_COST_RATER_GET_TOTAL_PRICE_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(totalCostRater, Modifier.PUBLIC);
        H5Utils.assertCorrectInterfaces(totalCostRater, componentRater);

        H5Utils.assertMethodCorrect(
            getTotalPrice,
            BasicTypeLink.of(double.class),
            Modifier.PUBLIC
        );

        Context context = Assertions2.contextBuilder()
            .add("mainboardPrice", mainboardPrice)
            .add("cpuPrice", cpuPrice)
            .add("memoryPrice", memoryPrice)
            .add("peripheralPrice", peripheralPrice)
            .build();

        Object rater = H5Links.TOTAL_COST_RATER_LINK.get()
            .getConstructor(Matcher.of(constructorLink -> constructorLink.typeList().isEmpty()))
            .invoke();

        double totalPrice = 0;
        H5Links.COMPONENT_RATER_CONSUME_MAINBOARD_METHOD_LINK.get()
            .invoke(rater, componentWithCost(H5Links.MAINBOARD_LINK.get().reflection(), mainboardPrice));
        totalPrice += mainboardPrice;
        H5Links.COMPONENT_RATER_CONSUME_CPU_METHOD_LINK.get()
            .invoke(rater, componentWithCost(H5Links.CPU_LINK.get().reflection(), cpuPrice));
        totalPrice += cpuPrice;
        for (int i = 0; i < 5; i++) {
            H5Links.COMPONENT_RATER_CONSUME_MEMORY_METHOD_LINK.get()
                .invoke(rater, componentWithCost(H5Links.MEMORY_LINK.get().reflection(), i * memoryPrice));
            totalPrice += i * memoryPrice;
        }
        for (int i = 0; i < 5; i++) {
            H5Links.COMPONENT_RATER_CONSUME_PERIPHERAL_METHOD_LINK.get()
                .invoke(rater, componentWithCost(H5Links.PERIPHERAL_LINK.get().reflection(), i * peripheralPrice));
            totalPrice += i * peripheralPrice;
        }

        Assertions2.assertEquals(
            totalPrice,
            Assertions2.callObject(
                () -> getTotalPrice.invoke(rater),
                context,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getTotalPrice` threw an exception.";
                }
            ),
            context,
            (r) -> "TotalCostRater did not calculate the total price correctly."
        );
    }

    @Test
    public void testMachineLearningRater() {
        TypeLink machineLearningRater = H5Links.MACHINE_LEARNING_RATER_LINK.get();

        H5Utils.assertCorrectModifiers(machineLearningRater, Modifier.PUBLIC);
        H5Utils.assertCorrectInterfaces(machineLearningRater, H5Links.COMPONENT_RATER_LINK.get());
    }

    @ParameterizedTest
    @MethodSource("generateMachineLearningRaterArgs")
    public void testMachineLearningRaterCheckModel(int modelSize, List<Character> memorySizes, int tpuCount, int nonTpuCount) throws Throwable {
        MethodLink checkModel = H5Links.MACHINE_LEARNING_RATER_CHECK_MODEL_METHOD_LINK.get();

        H5Utils.assertMethodCorrect(
            checkModel,
            BasicTypeLink.of(double.class),
            new TypeLink[]{BasicTypeLink.of(int.class)},
            Modifier.PUBLIC
        );

        Context context = Assertions2.contextBuilder()
            .add("modelSize", modelSize)
            .add("memorySizes", memorySizes)
            .add("tpuCount", tpuCount)
            .add("nonTpuCount", nonTpuCount)
            .build();

        Object rater = H5Links.MACHINE_LEARNING_RATER_LINK.get()
            .getConstructor(Matcher.of(constructorLink -> constructorLink.typeList().isEmpty()))
            .invoke();

        Assertions2.call(() -> {
                H5Links.COMPONENT_RATER_CONSUME_CPU_METHOD_LINK.get()
                    .invoke(rater, componentWithCost(H5Links.CPU_LINK.get().reflection(), 5));
                H5Links.COMPONENT_RATER_CONSUME_MAINBOARD_METHOD_LINK.get()
                    .invoke(rater, componentWithCost(H5Links.MAINBOARD_LINK.get().reflection(), 5));
                H5Links.COMPONENT_RATER_CONSUME_PERIPHERAL_METHOD_LINK.get()
                    .invoke(rater, componentWithCost(H5Links.PERIPHERAL_LINK.get().reflection(), 5));
            },
            context,
            (r) -> {
                r.cause().printStackTrace();
                return "Method `consumeCPU`, `consumeMainboard` or `consumePeripheral` in class `MachineLearningRater` threw an exception.";
            }
        );

        for (int i = 0; i < tpuCount; i++) {
            Object peripheral = Mockito.mock(H5Links.PERIPHERAL_LINK.get().reflection(), (Answer<?>) invocationOnMock -> {
                if (invocationOnMock.getMethod().equals(H5Links.PERIPHERAL_GET_PERIPHERAL_TYPE_METHOD_LINK.get().reflection())) {
                    return peripheralTypeMapping("TPU");
                } else {
                    return Mockito.RETURNS_DEFAULTS.answer(invocationOnMock);
                }
            });

            Assertions2.call(
                () -> H5Links.COMPONENT_RATER_CONSUME_PERIPHERAL_METHOD_LINK.get().invoke(rater, peripheral),
                context,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `consumePeripheral` in class `MachineLearningRater` threw an exception.";
                }
            );
        }

        for (int i = 0; i < nonTpuCount; i++) {
            Object peripheral = Mockito.mock(H5Links.PERIPHERAL_LINK.get().reflection(), (Answer<?>) invocationOnMock -> {
                if (invocationOnMock.getMethod().equals(H5Links.PERIPHERAL_GET_PERIPHERAL_TYPE_METHOD_LINK.get().reflection())) {
                    return peripheralTypeMapping("GPU");
                } else {
                    return Mockito.RETURNS_DEFAULTS.answer(invocationOnMock);
                }
            });

            Assertions2.call(
                () -> H5Links.COMPONENT_RATER_CONSUME_PERIPHERAL_METHOD_LINK.get().invoke(rater, peripheral),
                context,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `consumePeripheral` in class `MachineLearningRater` threw an exception.";
                }
            );
        }

        for (Character c : memorySizes) {
            Object memory = Mockito.mock(H5Links.MEMORY_LINK.get().reflection(), (Answer<?>) invocationOnMock -> {
                if (invocationOnMock.getMethod().equals(H5Links.MEMORY_GET_CAPACITY_METHOD_LINK.get().reflection())) {
                    return c;
                } else {
                    return Mockito.RETURNS_DEFAULTS.answer(invocationOnMock);
                }
            });

            Assertions2.call(
                () -> H5Links.COMPONENT_RATER_CONSUME_MEMORY_METHOD_LINK.get().invoke(rater, memory),
                context,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `consumeMemory` in class `MachineLearningRater` threw an exception.";
                }
            );
        }

        Double result = Assertions2.callObject(
            () -> H5Links.MACHINE_LEARNING_RATER_CHECK_MODEL_METHOD_LINK.get().invoke(rater, modelSize),
            context,
            (r) -> {
                r.cause().printStackTrace();
                return "Method `checkModel` in class `MachineLearningRater` threw an exception.";
            }
        );

        double totalMemorySize = memorySizes.stream().mapToInt(i -> i).sum();
        Set<Double> expectedValues = DoubleStream.of(tpuCount, -tpuCount)
            .flatMap(d -> DoubleStream.of(
                totalMemorySize / modelSize * (100 - (100 - 1) * Math.pow(1.02, d)),
                totalMemorySize / modelSize * (100 * (100 - 1) * Math.pow(1.02, d))))
            .boxed()
            .collect(Collectors.toSet());
        if (expectedValues.contains(result))
            return;

        Assertions2.fail(
            context,
            (r) -> "Method `checkModel` in class `MachineLearningRater` did not calculate the score correctly."
        );
    }

    private static <T> T componentWithCost(Class<T> clazz, double price) {
        return Mockito.mock(clazz, (Answer<?>) invocationOnMock -> {
            if (invocationOnMock.getMethod().equals(H5Links.COMPONENT_GET_PRICE_METHOD_LINK.get().reflection())) {
                return price;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocationOnMock);
            }
        });
    }

    public static List<Arguments> generateMachineLearningRaterArgs() {
        List<Arguments> args = new ArrayList<>();
        for (int tpuCount = 0; tpuCount < 5; tpuCount++) {
            for (int nonTpuCount = 0; nonTpuCount < 5; nonTpuCount++) {
                for (int memoryCount = 0; memoryCount < 5; memoryCount++) {
                    for (int modelSize : List.of(59, 123)) {
                        int finalMemoryCount = memoryCount;
                        int finalTpuCount = tpuCount;
                        args.add(Arguments.of(
                            modelSize,
                            IntStream.range(0, memoryCount).mapToObj(i -> (char) (65 + finalMemoryCount + finalTpuCount)).toList(),
                            tpuCount,
                            nonTpuCount
                        ));
                    }
                }
            }
        }
        return args;
    }
}
