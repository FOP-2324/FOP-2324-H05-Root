package h05;

import h05.h1.H1_1;
import h05.h1.H1_2;
import h05.h1.H1_3;
import h05.h1.H1_4;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H05_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H05")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H1 | Komponenten Modellierung")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H1.1 | Interface Component")
                        .addChildCriteria(
                            criterion("Das Interface ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_1.class.getDeclaredMethod("testInterface")))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H1.2 | Enumerationen")
                        .addChildCriteria(
                            criterion("Enum Socket ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_2.class.getDeclaredMethod("testSocketEnum"))),
                            criterion("Enum PeripheralType ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_2.class.getDeclaredMethod("testPeripheralTypeEnum")))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H1.3 | CPU modellieren")
                        .addChildCriteria(
                            criterion("Interface CPU ist korrekt und wird in CPUImpl verwendet.",
                                JUnitTestRef.ofMethod(() -> H1_3.class.getDeclaredMethod("testCPUInterface"))),
                            criterion("Konstruktor von CPUImpl ist korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_3.class.getDeclaredMethod("testCPUImplConstructor"))),
                            criterion("Objektkonstanten von CPUImpl sind korrekt.",
                                JUnitTestRef.ofMethod(() -> H1_3.class.getDeclaredMethod("testCPUImplFields"))),
                            criterion("Methoden von CPU sind korrekt in CPUImpl implementiert.",
                                JUnitTestRef.ofMethod(() -> H1_3.class.getDeclaredMethod("testGetMethods")))
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H1.3 | Memory modellieren")
                        .addChildCriteria(
                            criterion("Interface Memory ist korrekt und wird in MemoryImpl verwendet.",
                                JUnitTestRef.ofMethod(() -> H1_4.class.getDeclaredMethod("testMemoryInterface"))),
                            criterion("Konstruktor und Objektkonstanten von MemoryImpl sind korrekt und methoden von Memory sind korrekt in MemoryImpl implementiert.",
                                JUnitTestRef.and(
                                    JUnitTestRef.ofMethod(() -> H1_4.class.getDeclaredMethod("testMemoryImplConstructor")),
                                    JUnitTestRef.ofMethod(() -> H1_4.class.getDeclaredMethod("testMemoryImplFields")),
                                    JUnitTestRef.ofMethod(() -> H1_4.class.getDeclaredMethod("testGetCapacity"))
                                )
                            )
                        ).build()
                )
                .build()
        )
        .build();

    /**
     * H1.1:
     * - Interface ist korrekt
     * <p>
     * H1.2:
     * - Enum Socket ist korrekt
     * - Enum PeripheralType ist korrekt
     * <p>
     * H1.3:
     * - Interface korrekt definiert und CpuImpl implementiert Interface
     * - Konstuktor richtig
     * - alle Objektkonstanten
     * - alle Getter
     * <p>
     * H1.4:
     * - Interface korrekt definiert und MemoryImpl implementiert Interface
     * - Konstante, Konstuktor & Getter
     * <p>
     * H1.5:
     * - Interface definiert und Peripheral implementiert Interface
     * - Konstante, Konstuktor & Getter
     * <p>
     * <p>
     * H1.6:
     * - Klasse PurchasedComponent korrekt definiert und implementiert Component
     * - Konstukor richtig, aber trotzdem abstrakt
     * - Objektkonstante price und Getter
     * - CPUImpl, MemoryImpl und PeripehralImpl erben von PurchasedComponent (und optional: schauen, dass diese Implementierung von getPrice genutzt wird)
     * <p>
     * <p>
     * H2:
     * - Klasse definiert und Interface korrekt implementiert und von PurchasedComponent abgeleitet
     * - Slots für Memory und Peripheral
     * - Konstruktor korrekt
     * - addCpu
     * - addMemory, addPeripheral
     * <p>
     * H3.1:
     * - alle Methoden korrekt definiert
     * <p>
     * H3.2:
     * - Ratable korrekt mir korrekter Methode
     * - consume* Aufruf korrekt
     * - Prüfung auf ungleich null
     * <p>
     * H3.3:
     * - TotalCostRater korrekt
     * - Machine LearningRater korrekt (bis auf checkModel)
     * - checkModel korrekt
     * <p>
     * H4.1:
     * - Konfiguration erstellt (wie testen??)
     * - Wird getestet und checkExpect genutzt
     * <p>
     * H4.2:
     * - VirtualMemory implementiert Memory, aber ist nicht von MemoryImpl abgeleitet
     * - Konstruktor, Konstanze und get{Capacity,Price} korrekt
     * <p>
     * H4.3:
     * - Klasse korrekt definiert und Interface implementiert
     * - addMainboard und Größe passend
     * - rateBy korrekt
     */

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
