package h05.h3;

import h05.H5Links;
import h05.H5Utils;
import h05.MachineLearningRater;
import h05.TotalCostRater;
import h05.model.CPU;
import h05.model.Component;
import h05.model.Mainboard;
import h05.model.Memory;
import h05.model.Peripheral;
import h05.model.PeripheralType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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
import java.util.List;
import java.util.stream.IntStream;

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
    public void testTotalCostRater(double mainboardPrice, double cpuPrice, double memoryPrice, double peripheralPrice) {
        TypeLink totalCostRater = H5Links.TOTAL_COST_RATER_LINK.get();
        TypeLink componentRater = H5Links.COMPONENT_RATER_LINK.get();
        MethodLink getTotalCost = H5Links.TOTAL_COST_RATER_GET_TOTAL_COST_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(totalCostRater, Modifier.PUBLIC);
        H5Utils.assertCorrectInterfaces(totalCostRater, componentRater);

        H5Utils.assertMethodCorrect(
            getTotalCost,
            BasicTypeLink.of(double.class),
            Modifier.PUBLIC
        );

        Context context = Assertions2.contextBuilder()
            .add("mainboardPrice", mainboardPrice)
            .add("cpuPrice", cpuPrice)
            .add("memoryPrice", memoryPrice)
            .add("peripheralPrice", peripheralPrice)
            .build();

        TotalCostRater rater = new TotalCostRater();

        double totalPrice = 0;
        rater.consumeMainboard(componentWithCost(Mainboard.class, mainboardPrice));
        totalPrice += mainboardPrice;
        rater.consumeCPU(componentWithCost(CPU.class, cpuPrice));
        totalPrice += cpuPrice;
        for (int i = 0; i < 5; i++) {
            rater.consumeMemory(componentWithCost(Memory.class, i * memoryPrice));
            totalPrice += i * memoryPrice;
        }
        for (int i = 0; i < 5; i++) {
            rater.consumePeripheral(componentWithCost(Peripheral.class, i * peripheralPrice));
            totalPrice += i * peripheralPrice;
        }

        Assertions2.assertEquals(
            totalPrice,
            Assertions2.callObject(
                rater::getTotalCost,
                context,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getTotalCost` threw an exception.";
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
    public void testMachineLearningRaterCheckModel(int modelSize, List<Character> memorySizes, int tpuCount, int nonTpuCount) {
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

        MachineLearningRater rater = new MachineLearningRater();

        Assertions2.call(() -> {
                rater.consumeCPU(componentWithCost(CPU.class, 5));
                rater.consumeMainboard(componentWithCost(Mainboard.class, 5));
                rater.consumePeripheral(componentWithCost(Peripheral.class, 5));
            },
            context,
            (r) -> {
                r.cause().printStackTrace();
                return "Method `consumeCPU`, `consumeMainboard` or `consumePeripheral` in class `MachineLearningRater` threw an exception.";
            }
        );

        for (int i = 0; i < tpuCount; i++) {
            Peripheral peripheral = Mockito.mock(Peripheral.class);
            Mockito.when(peripheral.getPeripheralType()).thenReturn(PeripheralType.TPU);

            Assertions2.call(
                () -> rater.consumePeripheral(peripheral),
                context,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `consumePeripheral` in class `MachineLearningRater` threw an exception.";
                }
            );
        }

        for (int i = 0; i < nonTpuCount; i++) {
            Peripheral peripheral = Mockito.mock(Peripheral.class);
            Mockito.when(peripheral.getPeripheralType()).thenReturn(PeripheralType.GPU);

            Assertions2.call(
                () -> rater.consumePeripheral(peripheral),
                context,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `consumePeripheral` in class `MachineLearningRater` threw an exception.";
                }
            );
        }

        for (Character c : memorySizes) {
            Memory memory = Mockito.mock(Memory.class);
            Mockito.when(memory.getCapacity()).thenReturn(c);

            Assertions2.call(
                () -> rater.consumeMemory(memory),
                context,
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `consumeMemory` in class `MachineLearningRater` threw an exception.";
                }
            );
        }

        var result = Assertions2.callObject(
            () -> rater.checkModel(modelSize),
            context,
            (r) -> {
                r.cause().printStackTrace();
                return "Method `checkModel` in class `MachineLearningRater` threw an exception.";
            }
        );

        double totalMemorySize = memorySizes.stream().mapToInt(i -> i).sum();
        double expected1 = totalMemorySize / modelSize * (100 - (100 - 1) * Math.pow(1.02, tpuCount));
        double expected2 = totalMemorySize / modelSize * (100 - (100 - 1) * Math.pow(1.02, -tpuCount));
        if (result == expected1 || result == expected2)
            return;

        Assertions2.fail(
            context,
            (r) -> "Method `checkModel` in class `MachineLearningRater` did not calculate the score correctly."
        );
    }

    private static <T extends Component> T componentWithCost(Class<T> clazz, double price) {
        T component = Mockito.mock(clazz);
        Mockito.when(component.getPrice()).thenReturn(price);
        return component;
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
