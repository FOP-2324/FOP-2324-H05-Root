package h05;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.match.BasicReflectionMatchers;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.ConstructorLink;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.PackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.function.Supplier;

import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

public class H5Links {

    public static final Supplier<PackageLink> MODEL_PACKAGE_LINK = () -> Assertions2.assertNotNull(
        BasicPackageLink.of("h05.model"),
        Assertions2.emptyContext(),
        (r) -> "Package `h05.model` does not exist."
    );

    // Interface Component
    public static final Supplier<TypeLink> COMPONENT_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("Component")),
        Assertions2.emptyContext(),
        (r) -> "Interface `Component` does not exist."
    );
    public static final Supplier<MethodLink> COMPONENT_GET_PRICE_METHOD_LINK = () -> Assertions2.assertNotNull(
        COMPONENT_LINK.get().getMethod(identical("getPrice")),
        Assertions2.emptyContext(),
        (r) -> "Method `getPrice` not found in interface `Component`."
    );

    // Enum Socket
    public static final Supplier<TypeLink> SOCKET_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("Socket")),
        Assertions2.emptyContext(),
        (r) -> "Enum `Socket` does not exist."
    );

    // Enum PeripheralType
    public static final Supplier<TypeLink> PERIPHERAL_TYPE_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("PeripheralType")),
        Assertions2.emptyContext(),
        (r) -> "Enum `PeripheralType` does not exist."
    );

    // Interface CPU
    public static final Supplier<TypeLink> CPU_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("CPU")),
        Assertions2.emptyContext(),
        (r) -> "Interface `CPU` does not exist."
    );
    public static final Supplier<MethodLink> CPU_GET_SOCKET_METHOD_LINK = () -> Assertions2.assertNotNull(
        CPU_LINK.get().getMethod(identical("getSocket")),
        Assertions2.emptyContext(),
        (r) -> "Method `getSocket` not found in interface `CPU`."
    );
    public static final Supplier<MethodLink> CPU_GET_CORES_METHOD_LINK = () -> Assertions2.assertNotNull(
        CPU_LINK.get().getMethod(identical("getCores")),
        Assertions2.emptyContext(),
        (r) -> "Method `getCores` not found in interface `CPU`."
    );
    public static final Supplier<MethodLink> CPU_GET_FREQUENCY_METHOD_LINK = () -> Assertions2.assertNotNull(
        CPU_LINK.get().getMethod(identical("getFrequency")),
        Assertions2.emptyContext(),
        (r) -> "Method `getFrequency` not found in interface `CPU`."
    );

    // Class CPUImpl
    public static final Supplier<TypeLink> CPU_IMPL_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("CPUImpl")),
        Assertions2.emptyContext(),
        (r) -> "Class `CPUImpl` does not exist."
    );
    public static final Supplier<ConstructorLink> CPU_IMPL_CONSTRUCTOR_LINK = () -> Assertions2.assertNotNull(
        CPU_IMPL_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            SOCKET_LINK.get(),
            BasicTypeLink.of(int.class),
            BasicTypeLink.of(double.class),
            BasicTypeLink.of(double.class)
        )),
        Assertions2.emptyContext(),
        (r) -> "Constructor `CPUImpl(Socket, int, double, double)` not found in class `CPUImpl`."
    );
    public static final Supplier<FieldLink> CPU_IMPL_SOCKET_FIELD_LINK = () -> Assertions2.assertNotNull(
        CPU_IMPL_LINK.get().getField(identical("socket")),
        Assertions2.emptyContext(),
        (r) -> "Field `socket` not found in class `CPUImpl`."
    );
    public static final Supplier<FieldLink> CPU_IMPL_NUM_OF_CORES_FIELD_LINK = () -> Assertions2.assertNotNull(
        CPU_IMPL_LINK.get().getField(identical("numOfCores")),
        Assertions2.emptyContext(),
        (r) -> "Field `cores` not found in class `CPUImpl`."
    );
    public static final Supplier<FieldLink> CPU_IMPL_FREQUENCY_FIELD_LINK = () -> Assertions2.assertNotNull(
        CPU_IMPL_LINK.get().getField(identical("frequency")),
        Assertions2.emptyContext(),
        (r) -> "Field `frequency` not found in class `CPUImpl`."
    );

    // Interface Memory
    public static final Supplier<TypeLink> MEMORY_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("Memory")),
        Assertions2.emptyContext(),
        (r) -> "Interface `Memory` does not exist."
    );
    public static final Supplier<MethodLink> MEMORY_GET_CAPACITY_METHOD_LINK = () -> Assertions2.assertNotNull(
        MEMORY_LINK.get().getMethod(identical("getCapacity")),
        Assertions2.emptyContext(),
        (r) -> "Method `getCapacity` not found in interface `Memory`."
    );

    // Class MemoryImpl
    public static final Supplier<TypeLink> MEMORY_IMPL_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("MemoryImpl")),
        Assertions2.emptyContext(),
        (r) -> "Class `MemoryImpl` does not exist."
    );
    public static final Supplier<ConstructorLink> MEMORY_IMPL_CONSTRUCTOR_LINK = () -> Assertions2.assertNotNull(
        MEMORY_IMPL_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            BasicTypeLink.of(char.class),
            BasicTypeLink.of(double.class)
        )),
        Assertions2.emptyContext(),
        (r) -> "Constructor `MemoryImpl(char, double)` not found in class `MemoryImpl`."
    );
    public static final Supplier<FieldLink> MEMORY_IMPL_CAPACITY_FIELD_LINK = () -> Assertions2.assertNotNull(
        MEMORY_IMPL_LINK.get().getField(identical("capacity")),
        Assertions2.emptyContext(),
        (r) -> "Field `capacity` not found in class `MemoryImpl`."
    );

    // Interface Peripheral
    public static final Supplier<TypeLink> PERIPHERAL_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("Peripheral")),
        Assertions2.emptyContext(),
        (r) -> "Interface `Peripheral` does not exist."
    );
    public static final Supplier<MethodLink> PERIPHERAL_GET_PERIPHERAL_TYPE_METHOD_LINK = () -> Assertions2.assertNotNull(
        PERIPHERAL_LINK.get().getMethod(identical("getPeripheralType")),
        Assertions2.emptyContext(),
        (r) -> "Method `getPeripheralType` not found in interface `Peripheral`."
    );

    // Class PeripheralImpl
    public static final Supplier<TypeLink> PERIPHERAL_IMPL_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("PeripheralImpl")),
        Assertions2.emptyContext(),
        (r) -> "Class `PeripheralImpl` does not exist."
    );
    public static final Supplier<ConstructorLink> PERIPHERAL_IMPL_CONSTRUCTOR_LINK = () -> Assertions2.assertNotNull(
        PERIPHERAL_IMPL_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            PERIPHERAL_TYPE_LINK.get(),
            BasicTypeLink.of(double.class)
        )),
        Assertions2.emptyContext(),
        (r) -> "Constructor `PeripheralImpl(PeripheralType, double)` not found in class `PeripheralImpl`."
    );
    public static final Supplier<FieldLink> PERIPHERAL_IMPL_PERIPHERAL_TYPE_FIELD_LINK = () -> Assertions2.assertNotNull(
        PERIPHERAL_IMPL_LINK.get().getField(identical("peripheralType")),
        Assertions2.emptyContext(),
        (r) -> "Field `peripheralType` not found in class `PeripheralImpl`."
    );

    // Class PurchasedComponent
    public static final Supplier<TypeLink> PURCHASED_COMPONENT_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("PurchasedComponent")),
        Assertions2.emptyContext(),
        (r) -> "Class `PurchasedComponent` does not exist."
    );
    public static final Supplier<ConstructorLink> PURCHASED_COMPONENT_CONSTRUCTOR_LINK = () -> Assertions2.assertNotNull(
        PURCHASED_COMPONENT_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            BasicTypeLink.of(double.class)
        )),
        Assertions2.emptyContext(),
        (r) -> "Constructor `PurchasedComponent(double)` not found in class `PurchasedComponent`."
    );
    public static final Supplier<FieldLink> PURCHASED_COMPONENT_PRICE_FIELD_LINK = () -> Assertions2.assertNotNull(
        PURCHASED_COMPONENT_LINK.get().getField(identical("price")),
        Assertions2.emptyContext(),
        (r) -> "Field `price` not found in class `PurchasedComponent`."
    );
    public static final Supplier<MethodLink> PURCHASED_COMPONENT_GET_PRICE_METHOD_LINK = () -> Assertions2.assertNotNull(
        PURCHASED_COMPONENT_LINK.get().getMethod(identical("getPrice")),
        Assertions2.emptyContext(),
        (r) -> "Method `getPrice` not found in class `PurchasedComponent`."
    );

    // Interface Mainboard
    public static final Supplier<TypeLink> MAINBOARD_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("Mainboard")),
        Assertions2.emptyContext(),
        (r) -> "Interface `Mainboard` does not exist."
    );

    // Class MainboardImpl
    public static final Supplier<TypeLink> MAINBOARD_IMPL_LINK = () -> Assertions2.assertNotNull(
        MODEL_PACKAGE_LINK.get().getType(identical("MainboardImpl")),
        Assertions2.emptyContext(),
        (r) -> "Class `MainboardImpl` does not exist."
    );
    public static final Supplier<ConstructorLink> MAINBOARD_IMPL_CONSTRUCTOR_LINK = () -> Assertions2.assertNotNull(
        MAINBOARD_IMPL_LINK.get().getConstructor(BasicReflectionMatchers.sameTypes(
            SOCKET_LINK.get(),
            BasicTypeLink.of(int.class),
            BasicTypeLink.of(int.class),
            BasicTypeLink.of(double.class)
        )),
        Assertions2.emptyContext(),
        (r) -> "Constructor `MainboardImpl(Socket, int, int, double)` not found in class `MainboardImpl`."
    );
    public static final Supplier<FieldLink> MAINBOARD_IMPL_CPU_FIELD_LINK = () -> Assertions2.assertNotNull(
        MAINBOARD_IMPL_LINK.get().getField(identical("cpu")),
        Assertions2.emptyContext(),
        (r) -> "Field `cpu` not found in class `MainboardImpl`."
    );
    public static final Supplier<FieldLink> MAINBOARD_IMPL_MEMORIES_FIELD_LINK = () -> Assertions2.assertNotNull(
        MAINBOARD_IMPL_LINK.get().getField(identical("memories")),
        Assertions2.emptyContext(),
        (r) -> "Field `memories` not found in class `MainboardImpl`."
    );
    public static final Supplier<FieldLink> MAINBOARD_IMPL_PERIPHERALS_FIELD_LINK = () -> Assertions2.assertNotNull(
        MAINBOARD_IMPL_LINK.get().getField(identical("peripherals")),
        Assertions2.emptyContext(),
        (r) -> "Field `peripherals` not found in class `MainboardImpl`."
    );
    public static final Supplier<MethodLink> MAINBOARD_IMPL_ADD_CPU_METHOD_LINK = () -> Assertions2.assertNotNull(
        MAINBOARD_IMPL_LINK.get().getMethod(identical("addCpu")),
        Assertions2.emptyContext(),
        (r) -> "Method `addCpu` not found in class `MainboardImpl`."
    );
    public static final Supplier<MethodLink> MAINBOARD_IMPL_ADD_MEMORY_METHOD_LINK = () -> Assertions2.assertNotNull(
        MAINBOARD_IMPL_LINK.get().getMethod(identical("addMemory")),
        Assertions2.emptyContext(),
        (r) -> "Method `addMemory` not found in class `MainboardImpl`."
    );
    public static final Supplier<MethodLink> MAINBOARD_IMPL_ADD_PERIPHERAL_METHOD_LINK = () -> Assertions2.assertNotNull(
        MAINBOARD_IMPL_LINK.get().getMethod(identical("addPeripheral")),
        Assertions2.emptyContext(),
        (r) -> "Method `addPeripheral` not found in class `MainboardImpl`."
    );
}
