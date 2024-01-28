package h05;

import com.google.common.base.Suppliers;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static h05.Global.similarityMatcher;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertNotNull;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions3.*;

@SuppressWarnings("unchecked")
public class H5Links {

    // Make the classloader load package h05.model (or misspellings) if used in class
    private static final Object JANK_DO_NOT_TOUCH = new Main();

    public static final Supplier<PackageLink> PACKAGES_LINK = Suppliers.memoize(() ->
        new CombinedPackageLink(Stream.concat(
                Stream.of("h05", "h05.model"),
                Arrays.stream(Package.getPackages())
                    .map(Package::getName)
                    .filter(s -> s.startsWith("h05") && !s.matches("h05\\.h[1-4]")))
            .distinct()
            .map(BasicPackageLink::of)
            .toList()));

    private static Supplier<BasicTypeLink> typeLinkSup(String name) {
        return Suppliers.memoize(() -> (BasicTypeLink) assertTypeExists(
            PACKAGES_LINK.get(),
            similarityMatcher(name)
        ));
    }

    private static Supplier<BasicMethodLink> methodLinkSup(Supplier<? extends TypeLink> tl, String name) {
        return Suppliers.memoize(() -> (BasicMethodLink) assertMethodExists(
            tl.get(),
            similarityMatcher(name)
        ));
    }

    private static Supplier<BasicFieldLink> fieldLinkSup(Supplier<? extends TypeLink> tl, Matcher<FieldLink> matcher) {
        return Suppliers.memoize(() -> (BasicFieldLink) assertFieldExists(
            tl.get(),
            matcher
        ));
    }

    private static Supplier<BasicFieldLink> fieldLinkSup(Supplier<? extends TypeLink> tl, String name) {
        return fieldLinkSup(tl, similarityMatcher(name));
    }

    public static Supplier<BasicConstructorLink> constructorLinkSup(Supplier<? extends TypeLink> tl, Supplier<? extends TypeLink>... args) {
        return Suppliers.memoize(() -> (BasicConstructorLink) assertConstructorExists(
            tl.get(),
            sameTypes(args)
        ));
    }

    public static <T extends WithTypeList> Matcher<T> sameTypes(Supplier<? extends TypeLink>... types) {
        try {
            List<? extends TypeLink> parameterList = Arrays.stream(types).map(Supplier::get).toList();
            return Matcher.of((l) -> l.typeList().equals(parameterList),
                String.format("Same parameter types: %s", parameterList.stream().map(TypeLink::name).toList()));
        } catch (Throwable t) {
            return Matcher.never();
        }
    }

    // Interface Component
    public static final Supplier<BasicTypeLink> COMPONENT_LINK = typeLinkSup("Component");
    public static final Supplier<BasicMethodLink> COMPONENT_GET_PRICE_METHOD_LINK = methodLinkSup(COMPONENT_LINK, "getPrice");

    // Enum Socket
    public static final Supplier<BasicTypeLink> SOCKET_LINK = typeLinkSup("Socket");

    // Enum PeripheralType
    public static final Supplier<BasicTypeLink> PERIPHERAL_TYPE_LINK = typeLinkSup("PeripheralType");

    // Interface CPU
    public static final Supplier<BasicTypeLink> CPU_LINK = typeLinkSup("CPU");
    public static final Supplier<BasicMethodLink> CPU_GET_SOCKET_METHOD_LINK = methodLinkSup(CPU_LINK, "getSocket");
    public static final Supplier<MethodLink> CPU_GET_CORES_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        CPU_LINK.get().getMethod(similarityMatcher("getCores").or(similarityMatcher("getNumberOfCores"))),
        emptyContext(),
        (r) -> "Method `getCores/getNumberOfCores` not found in interface `CPU`."
    ));
    public static final Supplier<BasicMethodLink> CPU_GET_FREQUENCY_METHOD_LINK = methodLinkSup(CPU_LINK, "getFrequency");

    // Class CPUImpl
    public static final Supplier<BasicTypeLink> CPU_IMPL_LINK = typeLinkSup("CPUImpl");
    public static final Supplier<BasicConstructorLink> CPU_IMPL_CONSTRUCTOR_LINK = constructorLinkSup(CPU_IMPL_LINK,
        SOCKET_LINK,
        () -> BasicTypeLink.of(int.class),
        () -> BasicTypeLink.of(double.class),
        () -> BasicTypeLink.of(double.class)
    );
    public static final Supplier<BasicFieldLink> CPU_IMPL_SOCKET_FIELD_LINK = fieldLinkSup(CPU_IMPL_LINK, "socket");
    public static final Supplier<BasicFieldLink> CPU_IMPL_NUM_OF_CORES_FIELD_LINK = fieldLinkSup(CPU_IMPL_LINK, "numOfCores");
    public static final Supplier<BasicFieldLink> CPU_IMPL_FREQUENCY_FIELD_LINK = fieldLinkSup(CPU_IMPL_LINK, "frequency");

    // Interface Memory
    public static final Supplier<BasicTypeLink> MEMORY_LINK = typeLinkSup("Memory");
    public static final Supplier<BasicMethodLink> MEMORY_GET_CAPACITY_METHOD_LINK = methodLinkSup(MEMORY_LINK, "getCapacity");

    // Class MemoryImpl
    public static final Supplier<BasicTypeLink> MEMORY_IMPL_LINK = typeLinkSup("MemoryImpl");
    public static final Supplier<BasicConstructorLink> MEMORY_IMPL_CONSTRUCTOR_LINK = constructorLinkSup(MEMORY_IMPL_LINK,
        () -> BasicTypeLink.of(char.class),
        () -> BasicTypeLink.of(double.class)
    );
    public static final Supplier<BasicFieldLink> MEMORY_IMPL_CAPACITY_FIELD_LINK = fieldLinkSup(MEMORY_IMPL_LINK, "capacity");

    // Interface Peripheral
    public static final Supplier<BasicTypeLink> PERIPHERAL_LINK = typeLinkSup("Peripheral");
    public static final Supplier<BasicMethodLink> PERIPHERAL_GET_PERIPHERAL_TYPE_METHOD_LINK = methodLinkSup(PERIPHERAL_LINK, "getPeripheralType");

    // Class PeripheralImpl
    public static final Supplier<BasicTypeLink> PERIPHERAL_IMPL_LINK = typeLinkSup("PeripheralImpl");
    public static final Supplier<BasicConstructorLink> PERIPHERAL_IMPL_CONSTRUCTOR_LINK = constructorLinkSup(PERIPHERAL_IMPL_LINK,
        PERIPHERAL_TYPE_LINK,
        () -> BasicTypeLink.of(double.class)
    );
    public static final Supplier<BasicFieldLink> PERIPHERAL_IMPL_PERIPHERAL_TYPE_FIELD_LINK = fieldLinkSup(PERIPHERAL_IMPL_LINK, "peripheralType");

    // Class PurchasedComponent
    public static final Supplier<BasicTypeLink> PURCHASED_COMPONENT_LINK = typeLinkSup("PurchasedComponent");
    public static final Supplier<BasicConstructorLink> PURCHASED_COMPONENT_CONSTRUCTOR_LINK = constructorLinkSup(PURCHASED_COMPONENT_LINK,
        () -> BasicTypeLink.of(double.class)
    );
    public static final Supplier<BasicFieldLink> PURCHASED_COMPONENT_PRICE_FIELD_LINK = fieldLinkSup(PURCHASED_COMPONENT_LINK, "price");
    public static final Supplier<BasicMethodLink> PURCHASED_COMPONENT_GET_PRICE_METHOD_LINK = methodLinkSup(PURCHASED_COMPONENT_LINK, "getPrice");

    // Interface Mainboard
    public static final Supplier<BasicTypeLink> MAINBOARD_LINK = typeLinkSup("Mainboard");

    // Class MainboardImpl
    public static final Supplier<BasicTypeLink> MAINBOARD_IMPL_LINK = typeLinkSup("MainboardImpl");
    public static final Supplier<BasicConstructorLink> MAINBOARD_IMPL_CONSTRUCTOR_LINK = constructorLinkSup(MAINBOARD_IMPL_LINK,
        SOCKET_LINK,
        () -> BasicTypeLink.of(int.class),
        () -> BasicTypeLink.of(int.class),
        () -> BasicTypeLink.of(double.class)
    );
    public static final Supplier<BasicFieldLink> MAINBOARD_IMPL_CPU_FIELD_LINK = fieldLinkSup(MAINBOARD_IMPL_LINK, "cpu");
    public static final Supplier<BasicFieldLink> MAINBOARD_IMPL_MEMORIES_FIELD_LINK = fieldLinkSup(MAINBOARD_IMPL_LINK, "memories");
    public static final Supplier<BasicFieldLink> MAINBOARD_IMPL_PERIPHERALS_FIELD_LINK = fieldLinkSup(MAINBOARD_IMPL_LINK, "peripherals");
    public static final Supplier<BasicMethodLink> MAINBOARD_IMPL_ADD_CPU_METHOD_LINK = methodLinkSup(MAINBOARD_IMPL_LINK, "addCPU");
    public static final Supplier<BasicMethodLink> MAINBOARD_IMPL_ADD_MEMORY_METHOD_LINK = methodLinkSup(MAINBOARD_IMPL_LINK, "addMemory");
    public static final Supplier<BasicMethodLink> MAINBOARD_IMPL_ADD_PERIPHERAL_METHOD_LINK = methodLinkSup(MAINBOARD_IMPL_LINK, "addPeripheral");
    public static final Supplier<BasicMethodLink> MAINBOARD_IMPL_RATE_BY_METHOD_LINK = methodLinkSup(MAINBOARD_IMPL_LINK, "rateBy");

    // Interface ComponentRater
    public static final Supplier<BasicTypeLink> COMPONENT_RATER_LINK = typeLinkSup("ComponentRater");
    public static final Supplier<BasicMethodLink> COMPONENT_RATER_CONSUME_MAINBOARD_METHOD_LINK = methodLinkSup(COMPONENT_RATER_LINK, "consumeMainboard");
    public static final Supplier<BasicMethodLink> COMPONENT_RATER_CONSUME_CPU_METHOD_LINK = methodLinkSup(COMPONENT_RATER_LINK, "consumeCPU");
    public static final Supplier<BasicMethodLink> COMPONENT_RATER_CONSUME_MEMORY_METHOD_LINK = methodLinkSup(COMPONENT_RATER_LINK, "consumeMemory");
    public static final Supplier<BasicMethodLink> COMPONENT_RATER_CONSUME_PERIPHERAL_METHOD_LINK = methodLinkSup(COMPONENT_RATER_LINK, "consumePeripheral");

    // Interface Configuration
    public static final Supplier<BasicTypeLink> CONFIGURATION_LINK = typeLinkSup("Configuration");
    public static final Supplier<BasicMethodLink> CONFIGURATION_RATE_BY_METHOD_LINK = methodLinkSup(CONFIGURATION_LINK, "rateBy");

    // Class TotalCostRater
    public static final Supplier<BasicTypeLink> TOTAL_COST_RATER_LINK = typeLinkSup("TotalCostRater");
    public static final Supplier<BasicMethodLink> TOTAL_COST_RATER_GET_TOTAL_PRICE_METHOD_LINK = methodLinkSup(TOTAL_COST_RATER_LINK, "getTotalPrice");

    // Class MachineLearningRater
    public static final Supplier<BasicTypeLink> MACHINE_LEARNING_RATER_LINK = typeLinkSup("MachineLearningRater");
    public static final Supplier<BasicMethodLink> MACHINE_LEARNING_RATER_CHECK_MODEL_METHOD_LINK = methodLinkSup(MACHINE_LEARNING_RATER_LINK, "checkModel");

    // Class VirtualMemory
    public static final Supplier<BasicTypeLink> VIRTUAL_MEMORY_LINK = typeLinkSup("VirtualMemory");
    public static final Supplier<BasicConstructorLink> VIRTUAL_MEMORY_CONSTRUCTOR_LINK = constructorLinkSup(VIRTUAL_MEMORY_LINK,
        () -> BasicTypeLink.of(double.class)
    );
    public static final Supplier<BasicMethodLink> VIRTUAL_MEMORY_SET_CAPACITY_METHOD_LINK = methodLinkSup(VIRTUAL_MEMORY_LINK, "setCapacity");

    // Class ServerCenter
    public static final Supplier<BasicTypeLink> SERVER_CENTER_LINK = typeLinkSup("ServerCenter");
    public static final Supplier<BasicMethodLink> SERVER_CENTER_ADD_MAINBOARD_METHOD_LINK = methodLinkSup(SERVER_CENTER_LINK, "addMainboard");

    // Class Main
    public static final Supplier<BasicTypeLink> MAIN_LINK = typeLinkSup("Main");
    public static final Supplier<BasicMethodLink> MAIN_MAIN_METHOD_LINK = methodLinkSup(MAIN_LINK, "main");
}
