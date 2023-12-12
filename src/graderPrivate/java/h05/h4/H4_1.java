package h05.h4;

import h05.H5Links;
import h05.MachineLearningRater;
import h05.TotalCostRater;
import h05.Unchecked;
import h05.model.CPUImpl;
import h05.model.MainboardImpl;
import h05.model.MemoryImpl;
import h05.model.PeripheralImpl;
import h05.model.PeripheralType;
import h05.model.Socket;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.LenientCopyTool;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.student.test.StudentTestResult;
import org.tudalgo.algoutils.student.test.StudentTestState;
import org.tudalgo.algoutils.student.test.StudentTestUtils;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;

@TestForSubmission
public class H4_1 {

    @Test
    public void testConfigurationCorrect() {
        MethodLink main = H5Links.MAIN_MAIN_METHOD_LINK.get();

        try (var mainboardMock = Mockito.mockConstruction((Class<MainboardImpl>) H5Links.MAINBOARD_IMPL_LINK.get().reflection(),
            Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
            (mock, context) -> {
                var referenceObject = Assertions2.callObject(
                    Unchecked.uncheckedObjectCallable(() -> H5Links.MAINBOARD_IMPL_CONSTRUCTOR_LINK.get().invoke(context.arguments().toArray())),
                    Assertions2.emptyContext(),
                    (r) -> {
                        r.cause().printStackTrace();
                        return "`MainboardImpl` constructor threw an exception.";
                    }
                );
                new LenientCopyTool().copyToMock(referenceObject, mock);

                if (context.getCount() > 1) {
                    Assertions2.fail(
                        Assertions2.emptyContext(),
                        (r) -> "`MainboardImpl` should not be instantiated more than once."
                    );
                }

                if (context.arguments().size() != 4) {
                    Assertions2.fail(
                        Assertions2.emptyContext(),
                        (r) -> "`MainboardImpl` should be instantiated with 4 arguments."
                    );
                }

                Assertions2.assertEquals(
                    Socket.AM4,
                    context.arguments().get(0),
                    Assertions2.emptyContext(),
                    (r) -> "`MainboardImpl` should be instantiated with `Socket.AM4` as the first argument."
                );

                Assertions2.assertEquals(
                    2,
                    context.arguments().get(1),
                    Assertions2.emptyContext(),
                    (r) -> "`MainboardImpl` should be instantiated with `2` as the second argument."
                );

                Assertions2.assertEquals(
                    2,
                    context.arguments().get(2),
                    Assertions2.emptyContext(),
                    (r) -> "`MainboardImpl` should be instantiated with `2` as the third argument."
                );

                Assertions2.assertEquals(
                    100.0,
                    context.arguments().get(3),
                    Assertions2.emptyContext(),
                    (r) -> "`MainboardImpl` should be instantiated with `100.0` as the fourth argument."
                );
            });
             var cpuMock = Mockito.mockConstruction((Class<CPUImpl>) H5Links.CPU_IMPL_LINK.get().reflection(),
                 Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
                 (mock, context) -> {
                     var referenceObject = Assertions2.callObject(
                         Unchecked.uncheckedObjectCallable(() -> H5Links.CPU_IMPL_CONSTRUCTOR_LINK.get().invoke(context.arguments().toArray())),
                         Assertions2.emptyContext(),
                         (r) -> {
                             r.cause().printStackTrace();
                             return "`CPUImpl` constructor threw an exception.";
                         }
                     );
                     new LenientCopyTool().copyToMock(referenceObject, mock);

                     if (context.getCount() > 1) {
                         Assertions2.fail(
                             Assertions2.emptyContext(),
                             (r) -> "`CPUImpl` should not be instantiated more than once."
                         );
                     }

                     if (context.arguments().size() != 4) {
                         Assertions2.fail(
                             Assertions2.emptyContext(),
                             (r) -> "`CPUImpl` should be instantiated with 4 arguments."
                         );
                     }

                     Assertions2.assertEquals(
                         Socket.AM4,
                         context.arguments().get(0),
                         Assertions2.emptyContext(),
                         (r) -> "`CPUImpl` should be instantiated with `Socket.AM4` as the first argument."
                     );

                     Assertions2.assertEquals(
                         10,
                         context.arguments().get(1),
                         Assertions2.emptyContext(),
                         (r) -> "`CPUImpl` should be instantiated with `10` as the second argument."
                     );

                     double arg3 = (double) context.arguments().get(2);
                     if (arg3 != 3.3e9 && arg3 != 3.3e6) {
                         Assertions2.fail(
                             Assertions2.emptyContext(),
                             (r) -> "`CPUImpl` should be instantiated with `3.3e9` or `3.3e6` as the third argument."
                         );
                     }

                     Assertions2.assertEquals(
                         300.0,
                         context.arguments().get(3),
                         Assertions2.emptyContext(),
                         (r) -> "`CPUImpl` should be instantiated with `300.0` as the fourth argument."
                     );
                 });
             var memoryMock = Mockito.mockConstruction((Class<MemoryImpl>) H5Links.MEMORY_IMPL_LINK.get().reflection(),
                 Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
                 (mock, context) -> {
                     var referenceObject = Assertions2.callObject(
                         Unchecked.uncheckedObjectCallable(() -> H5Links.MEMORY_IMPL_CONSTRUCTOR_LINK.get().invoke(context.arguments().toArray())),
                         Assertions2.emptyContext(),
                         (r) -> {
                             r.cause().printStackTrace();
                             return "`MemoryImpl` constructor threw an exception.";
                         }
                     );
                     new LenientCopyTool().copyToMock(referenceObject, mock);

                     if (context.getCount() > 1) {
                         Assertions2.fail(
                             Assertions2.emptyContext(),
                             (r) -> "`MemoryImpl` should not be instantiated more than once."
                         );
                     }

                     if (context.arguments().size() != 2) {
                         Assertions2.fail(
                             Assertions2.emptyContext(),
                             (r) -> "`MemoryImpl` should be instantiated with 2 arguments."
                         );
                     }

                     Assertions2.assertEquals(
                         (char) 8,
                         context.arguments().get(0),
                         Assertions2.emptyContext(),
                         (r) -> "`MemoryImpl` should be instantiated with `(char) 8` as the first argument."
                     );

                     Assertions2.assertEquals(
                         60.0,
                         context.arguments().get(1),
                         Assertions2.emptyContext(),
                         (r) -> "`MemoryImpl` should be instantiated with `60.0` as the second argument."
                     );
                 });
             var peripheralMock = Mockito.mockConstruction((Class<PeripheralImpl>) H5Links.PERIPHERAL_IMPL_LINK.get().reflection(),
                 Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
                 (mock, context) -> {
                     var referenceObject = Assertions2.callObject(
                         Unchecked.uncheckedObjectCallable(() -> H5Links.PERIPHERAL_IMPL_CONSTRUCTOR_LINK.get().invoke(context.arguments().toArray())),
                         Assertions2.emptyContext(),
                         (r) -> {
                             r.cause().printStackTrace();
                             return "`PeripheralImpl` constructor threw an exception.";
                         }
                     );
                     new LenientCopyTool().copyToMock(referenceObject, mock);

                     if (context.getCount() > 1) {
                         Assertions2.fail(
                             Assertions2.emptyContext(),
                             (r) -> "`PeripheralImpl` should not be instantiated more than once."
                         );
                     }

                     if (context.arguments().size() != 2) {
                         Assertions2.fail(
                             Assertions2.emptyContext(),
                             (r) -> "`PeripheralImpl` should be instantiated with 2 arguments."
                         );
                     }

                     Assertions2.assertEquals(
                         PeripheralType.GPU,
                         context.arguments().get(0),
                         Assertions2.emptyContext(),
                         (r) -> "`PeripheralImpl` should be instantiated with `PeripheralType.GPU` as the first argument."
                     );

                     Assertions2.assertEquals(
                         300.0,
                         context.arguments().get(1),
                         Assertions2.emptyContext(),
                         (r) -> "`PeripheralImpl` should be instantiated with `300.0` as the second argument."
                     );
                 })) {
            Assertions2.call(
                Unchecked.uncheckedCallable(() -> main.invokeStatic(new Object[]{new String[0]})),
                Assertions2.emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `main` threw an exception.";
                }
            );

            Assertions2.assertEquals(
                1,
                mainboardMock.constructed().size(),
                Assertions2.emptyContext(),
                (r) -> "Exactly one instance of `MainboardImpl` should be instantiated."
            );

            Assertions2.assertEquals(
                1,
                cpuMock.constructed().size(),
                Assertions2.emptyContext(),
                (r) -> "Exactly one instance of `CPUImpl` should be instantiated."
            );

            Assertions2.assertEquals(
                1,
                memoryMock.constructed().size(),
                Assertions2.emptyContext(),
                (r) -> "Exactly one instance of `MemoryImpl` should be instantiated."
            );

            Assertions2.assertEquals(
                1,
                peripheralMock.constructed().size(),
                Assertions2.emptyContext(),
                (r) -> "Exactly one instance of `PeripheralImpl` should be instantiated."
            );

            Mockito.verify(mainboardMock.constructed().get(0)).addCPU(cpuMock.constructed().get(0));
            Mockito.verify(mainboardMock.constructed().get(0)).addMemory(memoryMock.constructed().get(0));
            Mockito.verify(mainboardMock.constructed().get(0)).addPeripheral(peripheralMock.constructed().get(0));
        }
    }

    @Test
    public void testTestMethodsUsed() {
        MethodLink main = H5Links.MAIN_MAIN_METHOD_LINK.get();

        var checkModelResult = new double[1];
        try (var totalCostRaterMock = Mockito.mockConstruction((Class<TotalCostRater>) H5Links.TOTAL_COST_RATER_LINK.get().reflection(),
            Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
            (mock, context) -> {});
             var machineLearningRaterMock = Mockito.mockConstruction((Class<MachineLearningRater>) H5Links.MACHINE_LEARNING_RATER_LINK.get().reflection(),
                 Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
                 (mock, context) -> {
                     Mockito.doAnswer((invocation) -> {
                         checkModelResult[0] = (double) invocation.callRealMethod();
                         return checkModelResult[0];
                     }).when(mock).checkModel(Mockito.anyInt());
                 });
             var staticMock = Mockito.mockStatic(StudentTestUtils.class, Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS))) {

            Assertions2.call(
                Unchecked.uncheckedCallable(() -> main.invokeStatic(new Object[]{new String[0]})),
                Assertions2.emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `main` threw an exception.";
                }
            );

            Assertions2.assertEquals(
                1,
                totalCostRaterMock.constructed().size(),
                Assertions2.emptyContext(),
                (r) -> "Exactly one instance of `TotalCostRater` should be instantiated."
            );

            Assertions2.assertEquals(
                1,
                machineLearningRaterMock.constructed().size(),
                Assertions2.emptyContext(),
                (r) -> "Exactly one instance of `MachineLearningRater` should be instantiated."
            );

            Mockito.verify(totalCostRaterMock.constructed().get(0), Mockito.atLeast(1)).getTotalPrice();
            Mockito.verify(machineLearningRaterMock.constructed().get(0), Mockito.atLeast(1)).checkModel(Mockito.anyInt());

            staticMock.verify(() -> StudentTestUtils.testWithinRange(Mockito.anyDouble(), Mockito.anyDouble(), ArgumentMatchers.doubleThat(d -> 759 <= d && d <= 761)));
            staticMock.verify(() -> StudentTestUtils.testWithinRange(Mockito.anyDouble(), Mockito.anyDouble(), ArgumentMatchers.doubleThat(d -> checkModelResult[0] - 1 <= d && d <= checkModelResult[0] + 1)));

            Assertions2.assertEquals(
                2,
                StudentTestUtils.testResults.size(),
                Assertions2.emptyContext(),
                (r) -> "Exactly two student tests should be executed."
            );

            for (StudentTestResult<?> testResult : StudentTestUtils.testResults) {
                Assertions2.assertEquals(
                    StudentTestState.PASSED,
                    testResult.state(),
                    Assertions2.contextBuilder()
                        .add("testResult", testResult)
                        .build(),
                    (r) -> "Student tests should pass."
                );
            }
        }
    }
}
