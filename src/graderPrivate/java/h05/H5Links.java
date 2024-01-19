package h05;

import com.google.common.base.Suppliers;
import org.tudalgo.algoutils.tutor.general.match.BasicReflectionMatchers;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.util.function.Supplier;

import static h05.Global.similarityMatcher;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertNotNull;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

public class H5Links {

    public static final Supplier<PackageLink> H05_PACKAGE_LINK = new PackageLinkSupplier("h05");
    public static final Supplier<PackageLink> MODEL_PACKAGE_LINK = new PackageLinkSupplier("h05.model");

    // Interface Component
    public static final Supplier<TypeLink> COMPONENT_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("Component")),
        emptyContext(),
        (r) -> "Interface `Component` does not exist."
    ));
    public static final Supplier<MethodLink> COMPONENT_GET_PRICE_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        COMPONENT_LINK.get().getMethod(similarityMatcher("getPrice")),
        emptyContext(),
        (r) -> "Method `getPrice` not found in interface `Component`."
    ));

    // Enum Socket
    public static final Supplier<TypeLink> SOCKET_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("Socket")),
        emptyContext(),
        (r) -> "Enum `Socket` does not exist."
    ));

    // Enum PeripheralType
    public static final Supplier<TypeLink> PERIPHERAL_TYPE_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("PeripheralType")),
        emptyContext(),
        (r) -> "Enum `PeripheralType` does not exist."
    ));

    // Interface CPU
    public static final Supplier<TypeLink> CPU_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("CPU")),
        emptyContext(),
        (r) -> "Interface `CPU` does not exist."
    ));
    public static final Supplier<MethodLink> CPU_GET_SOCKET_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        CPU_LINK.get().getMethod(similarityMatcher("getSocket")),
        emptyContext(),
        (r) -> "Method `getSocket` not found in interface `CPU`."
    ));
    public static final Supplier<MethodLink> CPU_GET_CORES_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        CPU_LINK.get().getMethod(similarityMatcher("getCores").or(similarityMatcher("getNumberOfCores"))),
        emptyContext(),
        (r) -> "Method `getCores/getNumberOfCores` not found in interface `CPU`."
    ));
    public static final Supplier<MethodLink> CPU_GET_FREQUENCY_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        CPU_LINK.get().getMethod(similarityMatcher("getFrequency")),
        emptyContext(),
        (r) -> "Method `getFrequency` not found in interface `CPU`."
    ));

    // Class CPUImpl
    public static final Supplier<TypeLink> CPU_IMPL_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("CPUImpl")),
        emptyContext(),
        (r) -> "Class `CPUImpl` does not exist."
    ));
    public static final Supplier<ConstructorLink> CPU_IMPL_CONSTRUCTOR_LINK = Suppliers.memoize(() -> assertNotNull(
        CPU_IMPL_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            SOCKET_LINK.get(),
            BasicTypeLink.of(int.class),
            BasicTypeLink.of(double.class),
            BasicTypeLink.of(double.class)
        )),
        emptyContext(),
        (r) -> "Constructor `CPUImpl(Socket, int, double, double)` not found in class `CPUImpl`."
    ));
    public static final Supplier<FieldLink> CPU_IMPL_SOCKET_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        CPU_IMPL_LINK.get().getField(similarityMatcher("socket")),
        emptyContext(),
        (r) -> "Field `socket` not found in class `CPUImpl`."
    ));
    public static final Supplier<FieldLink> CPU_IMPL_NUM_OF_CORES_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        CPU_IMPL_LINK.get().getField(similarityMatcher("numOfCores")),
        emptyContext(),
        (r) -> "Field `cores` not found in class `CPUImpl`."
    ));
    public static final Supplier<FieldLink> CPU_IMPL_FREQUENCY_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        CPU_IMPL_LINK.get().getField(similarityMatcher("frequency")),
        emptyContext(),
        (r) -> "Field `frequency` not found in class `CPUImpl`."
    ));

    // Interface Memory
    public static final Supplier<TypeLink> MEMORY_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("Memory")),
        emptyContext(),
        (r) -> "Interface `Memory` does not exist."
    ));
    public static final Supplier<MethodLink> MEMORY_GET_CAPACITY_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        MEMORY_LINK.get().getMethod(similarityMatcher("getCapacity")),
        emptyContext(),
        (r) -> "Method `getCapacity` not found in interface `Memory`."
    ));

    // Class MemoryImpl
    public static final Supplier<TypeLink> MEMORY_IMPL_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("MemoryImpl")),
        emptyContext(),
        (r) -> "Class `MemoryImpl` does not exist."
    ));
    public static final Supplier<ConstructorLink> MEMORY_IMPL_CONSTRUCTOR_LINK = Suppliers.memoize(() -> assertNotNull(
        MEMORY_IMPL_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            BasicTypeLink.of(char.class),
            BasicTypeLink.of(double.class)
        )),
        emptyContext(),
        (r) -> "Constructor `MemoryImpl(char, double)` not found in class `MemoryImpl`."
    ));
    public static final Supplier<FieldLink> MEMORY_IMPL_CAPACITY_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        MEMORY_IMPL_LINK.get().getField(similarityMatcher("capacity")),
        emptyContext(),
        (r) -> "Field `capacity` not found in class `MemoryImpl`."
    ));

    // Interface Peripheral
    public static final Supplier<TypeLink> PERIPHERAL_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("Peripheral")),
        emptyContext(),
        (r) -> "Interface `Peripheral` does not exist."
    ));
    public static final Supplier<MethodLink> PERIPHERAL_GET_PERIPHERAL_TYPE_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        PERIPHERAL_LINK.get().getMethod(similarityMatcher("getPeripheralType")),
        emptyContext(),
        (r) -> "Method `getPeripheralType` not found in interface `Peripheral`."
    ));

    // Class PeripheralImpl
    public static final Supplier<TypeLink> PERIPHERAL_IMPL_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("PeripheralImpl")),
        emptyContext(),
        (r) -> "Class `PeripheralImpl` does not exist."
    ));
    public static final Supplier<ConstructorLink> PERIPHERAL_IMPL_CONSTRUCTOR_LINK = Suppliers.memoize(() -> assertNotNull(
        PERIPHERAL_IMPL_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            PERIPHERAL_TYPE_LINK.get(),
            BasicTypeLink.of(double.class)
        )),
        emptyContext(),
        (r) -> "Constructor `PeripheralImpl(PeripheralType, double)` not found in class `PeripheralImpl`."
    ));
    public static final Supplier<FieldLink> PERIPHERAL_IMPL_PERIPHERAL_TYPE_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        PERIPHERAL_IMPL_LINK.get().getField(similarityMatcher("peripheralType")),
        emptyContext(),
        (r) -> "Field `peripheralType` not found in class `PeripheralImpl`."
    ));

    // Class PurchasedComponent
    public static final Supplier<TypeLink> PURCHASED_COMPONENT_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("PurchasedComponent")),
        emptyContext(),
        (r) -> "Class `PurchasedComponent` does not exist."
    ));
    public static final Supplier<ConstructorLink> PURCHASED_COMPONENT_CONSTRUCTOR_LINK = Suppliers.memoize(() -> assertNotNull(
        PURCHASED_COMPONENT_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            BasicTypeLink.of(double.class)
        )),
        emptyContext(),
        (r) -> "Constructor `PurchasedComponent(double)` not found in class `PurchasedComponent`."
    ));
    public static final Supplier<FieldLink> PURCHASED_COMPONENT_PRICE_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        PURCHASED_COMPONENT_LINK.get().getField(similarityMatcher("price")),
        emptyContext(),
        (r) -> "Field `price` not found in class `PurchasedComponent`."
    ));
    public static final Supplier<MethodLink> PURCHASED_COMPONENT_GET_PRICE_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        PURCHASED_COMPONENT_LINK.get().getMethod(similarityMatcher("getPrice")),
        emptyContext(),
        (r) -> "Method `getPrice` not found in class `PurchasedComponent`."
    ));

    // Interface Mainboard
    public static final Supplier<TypeLink> MAINBOARD_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("Mainboard")),
        emptyContext(),
        (r) -> "Interface `Mainboard` does not exist."
    ));

    // Class MainboardImpl
    public static final Supplier<TypeLink> MAINBOARD_IMPL_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("MainboardImpl")),
        emptyContext(),
        (r) -> "Class `MainboardImpl` does not exist."
    ));
    public static final Supplier<ConstructorLink> MAINBOARD_IMPL_CONSTRUCTOR_LINK = Suppliers.memoize(() -> assertNotNull(
        MAINBOARD_IMPL_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            SOCKET_LINK.get(),
            BasicTypeLink.of(int.class),
            BasicTypeLink.of(int.class),
            BasicTypeLink.of(double.class)
        )),
        emptyContext(),
        (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` not found in class `MainboardImpl`."
    ));
    public static final Supplier<FieldLink> MAINBOARD_IMPL_CPU_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        MAINBOARD_IMPL_LINK.get().getField(similarityMatcher("cpu")),
        emptyContext(),
        (r) -> "Field `cpu` not found in class `MainboardImpl`."
    ));
    public static final Supplier<FieldLink> MAINBOARD_IMPL_MEMORIES_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        MAINBOARD_IMPL_LINK.get().getField(similarityMatcher("memories")),
        emptyContext(),
        (r) -> "Field `memories` not found in class `MainboardImpl`."
    ));
    public static final Supplier<FieldLink> MAINBOARD_IMPL_PERIPHERALS_FIELD_LINK = Suppliers.memoize(() -> assertNotNull(
        MAINBOARD_IMPL_LINK.get().getField(similarityMatcher("peripherals")),
        emptyContext(),
        (r) -> "Field `peripherals` not found in class `MainboardImpl`."
    ));
    public static final Supplier<MethodLink> MAINBOARD_IMPL_ADD_CPU_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        MAINBOARD_IMPL_LINK.get().getMethod(similarityMatcher("addCPU")),
        emptyContext(),
        (r) -> "Method `addCPU` not found in class `MainboardImpl`."
    ));
    public static final Supplier<MethodLink> MAINBOARD_IMPL_ADD_MEMORY_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        MAINBOARD_IMPL_LINK.get().getMethod(similarityMatcher("addMemory")),
        emptyContext(),
        (r) -> "Method `addMemory` not found in class `MainboardImpl`."
    ));
    public static final Supplier<MethodLink> MAINBOARD_IMPL_ADD_PERIPHERAL_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        MAINBOARD_IMPL_LINK.get().getMethod(similarityMatcher("addPeripheral")),
        emptyContext(),
        (r) -> "Method `addPeripheral` not found in class `MainboardImpl`."
    ));
    public static final Supplier<MethodLink> MAINBOARD_IMPL_RATE_BY_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        MAINBOARD_IMPL_LINK.get().getMethod(similarityMatcher("rateBy")),
        emptyContext(),
        (r) -> "Method `rateBy` not found in class `MainboardImpl`."
    ));

    // Interface ComponentRater
    public static final Supplier<TypeLink> COMPONENT_RATER_LINK = Suppliers.memoize(() -> assertNotNull(
        H05_PACKAGE_LINK.get().getType(similarityMatcher("ComponentRater")),
        emptyContext(),
        (r) -> "Interface `ComponentRater` does not exist."
    ));
    public static final Supplier<MethodLink> COMPONENT_RATER_CONSUME_MAINBOARD_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        COMPONENT_RATER_LINK.get().getMethod(similarityMatcher("consumeMainboard")),
        emptyContext(),
        (r) -> "Method `consumeMainboard` not found in interface `ComponentRater`."
    ));
    public static final Supplier<MethodLink> COMPONENT_RATER_CONSUME_CPU_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        COMPONENT_RATER_LINK.get().getMethod(similarityMatcher("consumeCPU")),
        emptyContext(),
        (r) -> "Method `consumeCPU` not found in interface `ComponentRater`."
    ));
    public static final Supplier<MethodLink> COMPONENT_RATER_CONSUME_MEMORY_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        COMPONENT_RATER_LINK.get().getMethod(similarityMatcher("consumeMemory")),
        emptyContext(),
        (r) -> "Method `consumeMemory` not found in interface `ComponentRater`."
    ));
    public static final Supplier<MethodLink> COMPONENT_RATER_CONSUME_PERIPHERAL_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        COMPONENT_RATER_LINK.get().getMethod(similarityMatcher("consumePeripheral")),
        emptyContext(),
        (r) -> "Method `consumePeripheral` not found in interface `ComponentRater`."
    ));

    // Interface Configuration
    public static final Supplier<TypeLink> CONFIGURATION_LINK = Suppliers.memoize(() -> assertNotNull(
        H05_PACKAGE_LINK.get().getType(similarityMatcher("Configuration")),
        emptyContext(),
        (r) -> "Interface `Configuration` does not exist."
    ));
    public static final Supplier<MethodLink> CONFIGURATION_RATE_BY_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        CONFIGURATION_LINK.get().getMethod(similarityMatcher("rateBy")),
        emptyContext(),
        (r) -> "Method `rateBy` not found in interface `Configuration`."
    ));

    // Class TotalCostRater
    public static final Supplier<TypeLink> TOTAL_COST_RATER_LINK = Suppliers.memoize(() -> assertNotNull(
        H05_PACKAGE_LINK.get().getType(similarityMatcher("TotalCostRater")),
        emptyContext(),
        (r) -> "Class `TotalCostRater` does not exist."
    ));
    public static final Supplier<MethodLink> TOTAL_COST_RATER_GET_TOTAL_PRICE_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        TOTAL_COST_RATER_LINK.get().getMethod(similarityMatcher("getTotalPrice")),
        emptyContext(),
        (r) -> "Method `getTotalPrice` not found in class `TotalCostRater`."
    ));

    // Class MachineLearningRater
    public static final Supplier<TypeLink> MACHINE_LEARNING_RATER_LINK = Suppliers.memoize(() -> assertNotNull(
        H05_PACKAGE_LINK.get().getType(similarityMatcher("MachineLearningRater")),
        emptyContext(),
        (r) -> "Class `MachineLearningRater` does not exist."
    ));
    public static final Supplier<MethodLink> MACHINE_LEARNING_RATER_CHECK_MODEL_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        MACHINE_LEARNING_RATER_LINK.get().getMethod(similarityMatcher("checkModel")),
        emptyContext(),
        (r) -> "Method `checkModel` not found in class `MachineLearningRater`."
    ));

    // Class VirtualMemory
    public static final Supplier<TypeLink> VIRTUAL_MEMORY_LINK = Suppliers.memoize(() -> assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(similarityMatcher("VirtualMemory")),
        emptyContext(),
        (r) -> "Class `VirtualMemory` does not exist."
    ));
    public static final Supplier<ConstructorLink> VIRTUAL_MEMORY_CONSTRUCTOR_LINK = Suppliers.memoize(() -> assertNotNull(
        VIRTUAL_MEMORY_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            BasicTypeLink.of(double.class)
        )),
        emptyContext(),
        (r) -> "Constructor `VirtualMemory(double)` not found in class `VirtualMemory`."
    ));
    public static final Supplier<MethodLink> VIRTUAL_MEMORY_SET_CAPACITY_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        VIRTUAL_MEMORY_LINK.get().getMethod(similarityMatcher("setCapacity")),
        emptyContext(),
        (r) -> "Method `setCapacity` not found in class `VirtualMemory`."
    ));

    // Class ServerCenter
    public static final Supplier<TypeLink> SERVER_CENTER_LINK = Suppliers.memoize(() -> assertNotNull(
        H05_PACKAGE_LINK.get().getType(similarityMatcher("ServerCenter")),
        emptyContext(),
        (r) -> "Class `ServerCenter` does not exist."
    ));
    public static final Supplier<MethodLink> SERVER_CENTER_ADD_MAINBOARD_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        SERVER_CENTER_LINK.get().getMethod(similarityMatcher("addMainboard")),
        emptyContext(),
        (r) -> "Method `addMainboard` not found in class `ServerCenter`."
    ));

    // Class Main
    public static final Supplier<TypeLink> MAIN_LINK = Suppliers.memoize(() -> assertNotNull(
        H05_PACKAGE_LINK.get().getType(similarityMatcher("Main")),
        emptyContext(),
        (r) -> "Class `Main` does not exist."
    ));
    public static final Supplier<MethodLink> MAIN_MAIN_METHOD_LINK = Suppliers.memoize(() -> assertNotNull(
        MAIN_LINK.get().getMethod(similarityMatcher("main")),
        emptyContext(),
        (r) -> "Method `main` not found in class `Main`."
    ));

    private static class PackageLinkSupplier implements Supplier<PackageLink> {

        private final String name;

        private PackageLink value = null;
        private boolean initialized = false;

        public PackageLinkSupplier(String name) {
            this.name = name;
        }

        @Override
        public PackageLink get() {
            if (!initialized) {
                value = init();
                initialized = true;
            }

            return value;
        }

        private PackageLink init() {
            return BasicPackageLink.of(name);
        }
    }

    // Interface ComponentRater
}
