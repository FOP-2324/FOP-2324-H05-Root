package h05;

import h05.h1.H1_1;
import h05.h1.H1_2;
import h05.h1.H1_3;
import h05.h1.H1_4;
import h05.h1.H1_5;
import h05.h1.H1_6;
import h05.h2.H2;
import h05.h3.H3_1;
import h05.h3.H3_2;
import h05.h3.H3_3;
import h05.h4.H4_1;
import h05.h4.H4_2;
import h05.h4.H4_3;
import h05.model.CPU;
import h05.model.Memory;
import h05.model.Peripheral;
import h05.model.Socket;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.GradeResult;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import java.util.List;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;
import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.defaultCriterionBuilder;

public class H05_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H05")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H1 | Komponenten Modellierung")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H1.1 | Interface `Component`")
                        .addChildCriteria(
                            criterion("Das Interface `Component` ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_1.class.getDeclaredMethod("testInterface")))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H1.2 | Enumerationen")
                        .addChildCriteria(
                            criterion("Enum `Socket` ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_2.class.getDeclaredMethod("testSocketEnum"))),
                            criterion("Enum `PeripheralType` ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_2.class.getDeclaredMethod("testPeripheralTypeEnum")))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H1.3 | `CPU` modellieren")
                        .addChildCriteria(
                            criterion("Interface `CPU` ist korrekt und wird in `CPUImpl` verwendet.",
                                JUnitTestRef.ofMethod(() -> H1_3.class.getDeclaredMethod("testCPUInterface"))),
                            criterion("Konstruktor von `CPUImpl` ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_3.class.getDeclaredMethod("testCPUImplConstructor"))),
                            criterion("Objektkonstanten von `CPUImpl` sind korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_3.class.getDeclaredMethod("testCPUImplFields"))),
                            criterion("Methoden von `CPU` sind korrekt in `CPUImpl` implementiert.",
                                JUnitTestRef.ofMethod(() -> H1_3.class.getDeclaredMethod("testGetMethods")))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H1.4 | `Memory` modellieren")
                        .addChildCriteria(
                            criterion("Interface `Memory` und Klasse `MemoryImpl` sind korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_4.class.getDeclaredMethod("testMemoryInterface")),
                                JUnitTestRef.ofMethod(() -> H1_4.class.getDeclaredMethod("testMemoryImplConstructor")),
                                JUnitTestRef.ofMethod(() -> H1_4.class.getDeclaredMethod("testMemoryImplFields")),
                                JUnitTestRef.ofMethod(() -> H1_4.class.getDeclaredMethod("testGetCapacity"))
                            )
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H1.5 | `Peripheral` modellieren")
                        .addChildCriteria(
                            criterion("Interface `Peripheral` und Klasse `PeripheralImpl` sind korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_5.class.getDeclaredMethod("testPeripheralInterface")),
                                JUnitTestRef.ofMethod(() -> H1_5.class.getDeclaredMethod("testPeripheralImplConstructor")),
                                JUnitTestRef.ofMethod(() -> H1_5.class.getDeclaredMethod("testPeripheralImplFields")),
                                JUnitTestRef.ofMethod(() -> H1_5.class.getDeclaredMethod("testGetPeripheralType"))
                            )
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H1.6 | `PurchasedComponent` implementieren")
                        .addChildCriteria(
                            criterion("Klasse `PurchasedComponent` korrekt definiert und implementiert `Component`.",
                                JUnitTestRef.ofMethod(() -> H1_6.class.getDeclaredMethod("testClassCorrect"))),
                            criterion("Konstruktor ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_6.class.getDeclaredMethod("testConstructor"))),
                            criterion("Objektkonstante `price` und Methode `getPrice` sind korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_6.class.getDeclaredMethod("testFieldAndGetter"))),
                            criterion("`CPUImpl`, `MemoryImpl` und `PeripheralImpl` erben von `PurchasedComponent` und `getPrice` sowie der Konstruktor wurden angepasst.",
                                JUnitTestRef.ofMethod(() -> H1_6.class.getDeclaredMethod("testOthersWereAdjustedCorrectly")))
                        ).build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H2 | System Modellierung")
                .addChildCriteria(
                    criterion("Interface `Mainboard` und Klasse `MainboardImpl` korrekt definiert.",
                        JUnitTestRef.ofMethod(() -> H2.class.getDeclaredMethod("testInterfaceAndClassCorrect"))),
                    criterion("Konstruktor ist korrekt.",
                        JUnitTestRef.ofMethod(() -> H2.class.getDeclaredMethod("testConstructor"))),
                    criterion("Objektattribute `peripherals` und `memories` sind korrekt.",
                        JUnitTestRef.ofMethod(() -> H2.class.getDeclaredMethod("testMemoryAndPeripheralFields"))),
                    criterion("Methode `addCPU` funktioniert korrekt.",
                        JUnitTestRef.ofMethod(() -> H2.class.getDeclaredMethod("testAddCpu", boolean.class, boolean.class, Socket.class, Socket.class))),
                    criterion("Methode `addMemory` und `addPeripheral` funktionieren korrekt.",
                        JUnitTestRef.and(
                            JUnitTestRef.ofMethod(() -> H2.class.getDeclaredMethod("testAddMemory", int.class, List.class)),
                            JUnitTestRef.ofMethod(() -> H2.class.getDeclaredMethod("testAddPeripheral", int.class, List.class))
                        )
                    )
                ).build(),
            Criterion.builder()
                .shortDescription("H3 | Bewertung")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H3.1 | Interface `ComponentRater`")
                        .addChildCriteria(
                            criterion("Interface `ComponentRater` korrekt definiert.",
                                JUnitTestRef.ofMethod(() -> H3_1.class.getDeclaredMethod("testComponentRaterInterface")))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H3.2 | Interface `Configuration`")
                        .addChildCriteria(
                            criterion("Interface `Configuration` korrekt defininiert.",
                                JUnitTestRef.ofMethod(() -> H3_2.class.getDeclaredMethod("testConfigurationInterface"))),
                            criterion("Klasse `MainboardImpl` verwendet das Interface `Configuration` korrekt.",
                                JUnitTestRef.ofMethod(() -> H3_2.class.getDeclaredMethod("testMainboardImplAdjustment"))),
                            criterion("Methode `rateBy` korrekt implementiert.",
                                JUnitTestRef.ofMethod(() -> H3_2.class.getDeclaredMethod("testMainboardImplRateBy", CPU.class, Memory[].class, Peripheral[].class)))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H3.3 | Verschiedene Implementierungen")
                        .addChildCriteria(
                            criterion("Klasse `TotalCostRater` ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H3_3.class.getDeclaredMethod("testTotalCostRater", double.class, double.class, double.class, double.class))),
                            criterion("Klasse `MachineLearningRater` ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H3_3.class.getDeclaredMethod("testMachineLearningRater"))),
                            criterion("Methode `checkModel` ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H3_3.class.getDeclaredMethod("testMachineLearningRaterCheckModel", int.class, List.class, int.class, int.class)))
                        ).build()
                ).build(),
            Criterion.builder()
                .shortDescription("H4 | Testen")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H4.1 | Explizite Konfiguration testen")
                        .addChildCriteria(
                            criterion("Die Konfiguration wurde wie gefordert korrekt erstellt",
                                JUnitTestRef.ofMethod(() -> H4_1.class.getDeclaredMethod("testConfigurationCorrect"))),
                            criterion("Die Konfiguration wird mit `testWithinRange` getestet",
                                JUnitTestRef.ofMethod(() -> H4_1.class.getDeclaredMethod("testTestMethodsUsed")))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H4.2 | Klasse `VirtualMemory`")
                        .addChildCriteria(
                            criterion("VirtualMemory implementiert Memory, aber ist nicht von MemoryImpl abgeleitet.",
                                JUnitTestRef.ofMethod(() -> H4_2.class.getDeclaredMethod("testVirtualMemoryClassCorrect"))),
                            criterion("Konstruktor, Konstante, `getCapacity` und `getPrice` korrekt.",
                                JUnitTestRef.ofMethod(() -> H4_2.class.getDeclaredMethod("testVirtualMemoryMethodsAndConstructor", double.class, int.class)))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H4.3 | Klasse `ServerCenter`")
                        .addChildCriteria(
                            criterion("Klasse `ServerCenter` korrekt definiert.",
                                JUnitTestRef.ofMethod(() -> H4_3.class.getDeclaredMethod("testServerCenterClassCorrect"))),
                            criterion("Methoden `addMainboard` und `rateBy` korrekt.",
                                JUnitTestRef.ofMethod(() -> H4_3.class.getDeclaredMethod("testAddMainboardAndRateBy")),
                                2)
                        ).build()
                ).build()
        ).build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
