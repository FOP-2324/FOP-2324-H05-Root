package h05.h4;

import com.google.common.util.concurrent.AtomicDouble;
import h05.H5Links;
import h05.Unchecked;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.util.reflection.LenientCopyTool;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.student.test.StudentTestResult;
import org.tudalgo.algoutils.student.test.StudentTestState;
import org.tudalgo.algoutils.student.test.StudentTestUtils;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static h05.Global.peripheralTypeMapping;
import static h05.Global.socketMapping;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class H4_1 {

    @Test
    public void testConfigurationCorrect() throws Throwable {
        MethodLink main = H5Links.MAIN_MAIN_METHOD_LINK.get();

        Map<String, Boolean> calledMethod = new HashMap<>() {{
            put(H5Links.MAINBOARD_IMPL_ADD_CPU_METHOD_LINK.get().name(), false);
            put(H5Links.MAINBOARD_IMPL_ADD_MEMORY_METHOD_LINK.get().name(), false);
            put(H5Links.MAINBOARD_IMPL_ADD_PERIPHERAL_METHOD_LINK.get().name(), false);
        }};
        try (var mainboardMock = Mockito.mockConstruction(H5Links.MAINBOARD_IMPL_LINK.get().reflection(),
            Mockito.withSettings().defaultAnswer(invocationOnMock -> {
                if (invocationOnMock.getMethod().equals(H5Links.MAINBOARD_IMPL_ADD_CPU_METHOD_LINK.get().reflection()) ||
                    invocationOnMock.getMethod().equals(H5Links.MAINBOARD_IMPL_ADD_MEMORY_METHOD_LINK.get().reflection()) ||
                    invocationOnMock.getMethod().equals(H5Links.MAINBOARD_IMPL_ADD_PERIPHERAL_METHOD_LINK.get().reflection())) {
                    calledMethod.computeIfPresent(invocationOnMock.getMethod().getName(), (key, value) -> true);
                }
                return Mockito.CALLS_REAL_METHODS.answer(invocationOnMock);
            }),
            (mock, context) -> {
                var referenceObject = callObject(
                    Unchecked.uncheckedObjectCallable(() -> H5Links.MAINBOARD_IMPL_CONSTRUCTOR_LINK.get().invoke(context.arguments().toArray())),
                    emptyContext(),
                    (r) -> {
                        r.cause().printStackTrace();
                        return "`MainboardImpl` constructor threw an exception.";
                    }
                );
                new LenientCopyTool().copyToMock(referenceObject, mock);

                if (context.getCount() > 1) {
                    fail(
                        emptyContext(),
                        (r) -> "`MainboardImpl` should not be instantiated more than once."
                    );
                }

                if (context.arguments().size() != 4) {
                    fail(
                        emptyContext(),
                        (r) -> "`MainboardImpl` should be instantiated with 4 arguments."
                    );
                }

                assertEquals(
                    socketMapping("AM4"),
                    context.arguments().get(0),
                    emptyContext(),
                    (r) -> "`MainboardImpl` should be instantiated with `Socket.AM4` as the first argument."
                );

                assertEquals(
                    2,
                    context.arguments().get(1),
                    emptyContext(),
                    (r) -> "`MainboardImpl` should be instantiated with `2` as the second argument."
                );

                assertEquals(
                    2,
                    context.arguments().get(2),
                    emptyContext(),
                    (r) -> "`MainboardImpl` should be instantiated with `2` as the third argument."
                );

                assertEquals(
                    100.0,
                    context.arguments().get(3),
                    emptyContext(),
                    (r) -> "`MainboardImpl` should be instantiated with `100.0` as the fourth argument."
                );
            });
            var cpuMock = Mockito.mockConstruction(H5Links.CPU_IMPL_LINK.get().reflection(),
                Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
                (mock, context) -> {
                    var referenceObject = callObject(
                        Unchecked.uncheckedObjectCallable(() -> H5Links.CPU_IMPL_CONSTRUCTOR_LINK.get().invoke(context.arguments().toArray())),
                        emptyContext(),
                        (r) -> {
                            r.cause().printStackTrace();
                            return "`CPUImpl` constructor threw an exception.";
                        }
                    );
                    new LenientCopyTool().copyToMock(referenceObject, mock);

                    if (context.getCount() > 1) {
                        fail(
                            emptyContext(),
                            (r) -> "`CPUImpl` should not be instantiated more than once."
                        );
                    }

                    if (context.arguments().size() != 4) {
                        fail(
                            emptyContext(),
                            (r) -> "`CPUImpl` should be instantiated with 4 arguments."
                        );
                    }

                    assertEquals(
                        socketMapping("AM4"),
                        context.arguments().get(0),
                        emptyContext(),
                        (r) -> "`CPUImpl` should be instantiated with `Socket.AM4` as the first argument."
                    );

                    assertEquals(
                        10,
                        context.arguments().get(1),
                        emptyContext(),
                        (r) -> "`CPUImpl` should be instantiated with `10` as the second argument."
                    );

                    double arg3 = (double) context.arguments().get(2);
                    if (arg3 != 3.3e9 && arg3 != 3.3e6 && arg3 != 3.3e3 && arg3 != 3.3) {
                        fail(
                            emptyContext(),
                            (r) -> "`CPUImpl` should be instantiated with `3.3e9`, `3.3e6`, `3.3e3` or `3.3` as the third argument."
                        );
                    }

                    assertEquals(
                        300.0,
                        context.arguments().get(3),
                        emptyContext(),
                        (r) -> "`CPUImpl` should be instantiated with `300.0` as the fourth argument."
                    );
                });
            var memoryMock = Mockito.mockConstruction(H5Links.MEMORY_IMPL_LINK.get().reflection(),
                Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
                (mock, context) -> {
                    var referenceObject = callObject(
                        Unchecked.uncheckedObjectCallable(() -> H5Links.MEMORY_IMPL_CONSTRUCTOR_LINK.get().invoke(context.arguments().toArray())),
                        emptyContext(),
                        (r) -> {
                            r.cause().printStackTrace();
                            return "`MemoryImpl` constructor threw an exception.";
                        }
                    );
                    new LenientCopyTool().copyToMock(referenceObject, mock);

                    if (context.getCount() > 1) {
                        fail(
                            emptyContext(),
                            (r) -> "`MemoryImpl` should not be instantiated more than once."
                        );
                    }

                    if (context.arguments().size() != 2) {
                        fail(
                            emptyContext(),
                            (r) -> "`MemoryImpl` should be instantiated with 2 arguments."
                        );
                    }

                    assertEquals(
                        (char) 8,
                        context.arguments().get(0),
                        emptyContext(),
                        (r) -> "`MemoryImpl` should be instantiated with `(char) 8` as the first argument."
                    );

                    assertEquals(
                        60.0,
                        context.arguments().get(1),
                        emptyContext(),
                        (r) -> "`MemoryImpl` should be instantiated with `60.0` as the second argument."
                    );
                });
            var peripheralMock = Mockito.mockConstruction(H5Links.PERIPHERAL_IMPL_LINK.get().reflection(),
                Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS),
                (mock, context) -> {
                    var referenceObject = callObject(
                        Unchecked.uncheckedObjectCallable(() -> H5Links.PERIPHERAL_IMPL_CONSTRUCTOR_LINK.get().invoke(context.arguments().toArray())),
                        emptyContext(),
                        (r) -> {
                            r.cause().printStackTrace();
                            return "`PeripheralImpl` constructor threw an exception.";
                        }
                    );
                    new LenientCopyTool().copyToMock(referenceObject, mock);

                    if (context.getCount() > 1) {
                        fail(
                            emptyContext(),
                            (r) -> "`PeripheralImpl` should not be instantiated more than once."
                        );
                    }

                    if (context.arguments().size() != 2) {
                        fail(
                            emptyContext(),
                            (r) -> "`PeripheralImpl` should be instantiated with 2 arguments."
                        );
                    }

                    assertEquals(
                        peripheralTypeMapping("GPU"),
                        context.arguments().get(0),
                        emptyContext(),
                        (r) -> "`PeripheralImpl` should be instantiated with `PeripheralType.GPU` as the first argument."
                    );

                    assertEquals(
                        300.0,
                        context.arguments().get(1),
                        emptyContext(),
                        (r) -> "`PeripheralImpl` should be instantiated with `300.0` as the second argument."
                    );
                })) {
            try {
                call(
                    () -> main.invokeStatic(new Object[]{new String[0]}),
                    emptyContext(),
                    (r) -> {
                        r.cause().printStackTrace();
                        return "Method `main` threw an exception.";
                    }
                );
            } catch (AssertionFailedError e) {
                if (e.getCause() instanceof MockitoException) {
                    throw e.getCause().getCause();
                } else {
                    throw e;
                }
            }

            assertEquals(
                1,
                mainboardMock.constructed().size(),
                emptyContext(),
                (r) -> "Exactly one instance of `MainboardImpl` should be instantiated."
            );

            assertEquals(
                1,
                cpuMock.constructed().size(),
                emptyContext(),
                (r) -> "Exactly one instance of `CPUImpl` should be instantiated."
            );

            assertEquals(
                1,
                memoryMock.constructed().size(),
                emptyContext(),
                (r) -> "Exactly one instance of `MemoryImpl` should be instantiated."
            );

            assertEquals(
                1,
                peripheralMock.constructed().size(),
                emptyContext(),
                (r) -> "Exactly one instance of `PeripheralImpl` should be instantiated."
            );

            assertTrue(calledMethod.get(H5Links.MAINBOARD_IMPL_ADD_CPU_METHOD_LINK.get().name()), emptyContext(),
                result -> "`addCPU(CPU)` was not called at least once");
            assertTrue(calledMethod.get(H5Links.MAINBOARD_IMPL_ADD_MEMORY_METHOD_LINK.get().name()), emptyContext(),
                result -> "`addMemory(Memory)` was not called at least once");
            assertTrue(calledMethod.get(H5Links.MAINBOARD_IMPL_ADD_PERIPHERAL_METHOD_LINK.get().name()), emptyContext(),
                result -> "`addPeripheral(Peripheral)` was not called at least once");
        }
    }

    @Test
    public void testTestMethodsUsed() {
        MethodLink main = H5Links.MAIN_MAIN_METHOD_LINK.get();

        var getTotalCostInvocations = new AtomicInteger();
        var checkModelInvocations = new AtomicInteger();
        var checkModelResult = new AtomicDouble();
        try (var totalCostRaterMock = Mockito.mockConstruction(H5Links.TOTAL_COST_RATER_LINK.get().reflection(),
            Mockito.withSettings().defaultAnswer(invocationOnMock -> {
                if (invocationOnMock.getMethod().equals(H5Links.TOTAL_COST_RATER_GET_TOTAL_PRICE_METHOD_LINK.get().reflection())) {
                    getTotalCostInvocations.incrementAndGet();
                }
                return Mockito.CALLS_REAL_METHODS.answer(invocationOnMock);
            }),
            (mock, context) -> {});
            var machineLearningRaterMock = Mockito.mockConstruction(H5Links.MACHINE_LEARNING_RATER_LINK.get().reflection(),
                Mockito.withSettings().defaultAnswer(invocationOnMock -> {
                    if (invocationOnMock.getMethod().equals(H5Links.MACHINE_LEARNING_RATER_CHECK_MODEL_METHOD_LINK.get().reflection())) {
                        checkModelInvocations.incrementAndGet();
                        checkModelResult.set((Double) invocationOnMock.callRealMethod());
                        return checkModelResult.get();
                    } else {
                        return Mockito.CALLS_REAL_METHODS.answer(invocationOnMock);
                    }
                }),
                (mock, context) -> {});
            var staticMock = Mockito.mockStatic(StudentTestUtils.class, Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS))) {

            call(
                Unchecked.uncheckedCallable(() -> main.invokeStatic(new Object[]{new String[0]})),
                emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `main` threw an exception.";
                }
            );

            assertEquals(
                1,
                totalCostRaterMock.constructed().size(),
                emptyContext(),
                (r) -> "Exactly one instance of `TotalCostRater` should be instantiated."
            );

            assertEquals(
                1,
                machineLearningRaterMock.constructed().size(),
                emptyContext(),
                (r) -> "Exactly one instance of `MachineLearningRater` should be instantiated."
            );

            assertTrue(getTotalCostInvocations.get() > 0, emptyContext(),
                result -> "`TotalCostRater.getTotalPrice()` was not called at least once");
            assertTrue(checkModelInvocations.get() > 0, emptyContext(),
                result -> "`MachineLearningRater.checkModel(int)` was not called at least once");

            staticMock.verify(() -> StudentTestUtils.testWithinRange(Mockito.anyDouble(), Mockito.anyDouble(), ArgumentMatchers.doubleThat(d -> 759 <= d && d <= 761)));
            staticMock.verify(() -> StudentTestUtils.testWithinRange(Mockito.anyDouble(), Mockito.anyDouble(), ArgumentMatchers.doubleThat(d -> checkModelResult.get() - 1 <= d && d <= checkModelResult.get() + 1)));

            assertEquals(
                2,
                StudentTestUtils.testResults.size(),
                emptyContext(),
                (r) -> "Exactly two student tests should be executed."
            );

            for (StudentTestResult<?> testResult : StudentTestUtils.testResults) {
                assertEquals(
                    StudentTestState.PASSED,
                    testResult.state(),
                    contextBuilder()
                        .add("testResult", testResult)
                        .build(),
                    (r) -> "Student tests should pass."
                );
            }
        }
    }
}
