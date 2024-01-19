package h05.h1;

import h05.H5Links;
import h05.H5Utils;
import h05.Unchecked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.ConstructorLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Arrays;

import static h05.Global.peripheralTypeMapping;
import static h05.Global.socketMapping;
import static h05.H5Utils.toStringifiable;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.fail;

@TestForSubmission
public class H1_6 {

    @Test
    public void testClassCorrect() {
        TypeLink purchasedComponent = H5Links.PURCHASED_COMPONENT_LINK.get();
        TypeLink component = H5Links.COMPONENT_LINK.get();

        H5Utils.assertCorrectModifiers(purchasedComponent, Modifier.PUBLIC, Modifier.ABSTRACT);
        H5Utils.assertCorrectInterfaces(purchasedComponent, component);
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    @ExtendWith(JagrExecutionCondition.class)
    public void testConstructor(TestCycle testCycle) throws Throwable {
        var clazz = testCycle.getClassLoader().loadClass(H5Links.PURCHASED_COMPONENT_LINK.get().reflection().getName(),
            new PurchasedComponentTransformer());
        var constructor = Arrays.stream(clazz.getDeclaredConstructors())
            .filter(c -> c.getParameterCount() == 1 && c.getParameterTypes()[0] == double.class)
            .peek(c -> {
                if ((c.getModifiers() & java.lang.reflect.Modifier.PUBLIC) == 0) {
                    fail(emptyContext(), result -> "The constructor should have modifier PUBLIC.");
                }
            })
            .findFirst()
            .orElseThrow(() -> fail(emptyContext(), result -> "There is no constructor with one parameter of type `double`"));

        var instance = Assertions2.callObject(
            () -> constructor.newInstance(3.1D),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `PurchasedComponent(double)` threw an exception.";
            }
        );

        var field = clazz.getDeclaredField(H5Links.PURCHASED_COMPONENT_PRICE_FIELD_LINK.get().name());
        field.trySetAccessible();
        Assertions2.assertEquals(
            3.1D,
            field.get(instance),
            emptyContext(),
            (r) -> "Constructor `PurchasedComponent(double)` did not set the `price` field correctly."
        );
    }

    @Test
    public void testFieldAndGetter() {
        var price = H5Links.PURCHASED_COMPONENT_PRICE_FIELD_LINK.get();
        var getPrice = H5Links.PURCHASED_COMPONENT_GET_PRICE_METHOD_LINK.get();

        H5Utils.assertCorrectModifiers(price, Modifier.PRIVATE, Modifier.FINAL);

        H5Utils.assertMethodCorrect(
            getPrice,
            BasicTypeLink.of(double.class),
            Modifier.PUBLIC
        );

        Assertions2.assertEquals(
            toStringifiable(BasicTypeLink.of(double.class)),
            toStringifiable(price.type()),
            emptyContext(),
            (r) -> "Field `price` in class `PurchasedComponent` should be of type `double`."
        );

        var instance = Assertions2.callObject(
            () -> Mockito.mock(H5Links.PURCHASED_COMPONENT_LINK.get().reflection(), Mockito.CALLS_REAL_METHODS),
            emptyContext(),
            (r) -> "Could not instantiate class `PurchasedComponent`."
        );

        price.set(instance, 3.2D);

        Assertions2.assertEquals(
            3.2D,
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getPrice.invoke(instance)),
                emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getPrice()` in class `PurchasedComponent` threw an exception.";
                }
            ),
            emptyContext(),
            (r) -> "Method `getPrice()` in class `PurchasedComponent` did not return the correct value."
        );
    }

    @Test
    public void testOthersWereAdjustedCorrectly() {
        TypeLink cpuImpl = H5Links.CPU_IMPL_LINK.get();
        TypeLink memoryImpl = H5Links.MEMORY_IMPL_LINK.get();
        TypeLink peripheralImpl = H5Links.PERIPHERAL_IMPL_LINK.get();
        ConstructorLink cpuImplConstructor = H5Links.CPU_IMPL_CONSTRUCTOR_LINK.get();
        ConstructorLink memoryImplConstructor = H5Links.MEMORY_IMPL_CONSTRUCTOR_LINK.get();
        ConstructorLink peripheralImplConstructor = H5Links.PERIPHERAL_IMPL_CONSTRUCTOR_LINK.get();

        H5Utils.assertCorrectSuperclass(cpuImpl, H5Links.PURCHASED_COMPONENT_LINK.get());
        H5Utils.assertCorrectSuperclass(memoryImpl, H5Links.PURCHASED_COMPONENT_LINK.get());
        H5Utils.assertCorrectSuperclass(peripheralImpl, H5Links.PURCHASED_COMPONENT_LINK.get());

        // Price methods removed
        Assertions2.assertNull(
            cpuImpl.getMethod(BasicStringMatchers.identical("getPrice")),
            emptyContext(),
            (r) -> "Class `CPUImpl` should not have a method `getPrice`."
        );

        Assertions2.assertNull(
            memoryImpl.getMethod(BasicStringMatchers.identical("getPrice")),
            emptyContext(),
            (r) -> "Class `MemoryImpl` should not have a method `getPrice`."
        );

        Assertions2.assertNull(
            peripheralImpl.getMethod(BasicStringMatchers.identical("getPrice")),
            emptyContext(),
            (r) -> "Class `PeripheralImpl` should not have a method `getPrice`."
        );

        // Super call correct
        var cpuInstance = Assertions2.callObject(
            () -> cpuImplConstructor.invoke(socketMapping("AM4"), 1, 351D, 2.5D),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `CPUImpl(Socket, int, double, double)` threw an exception.";
            }
        );

        Assertions2.assertEquals(
            2.5D,
            H5Links.PURCHASED_COMPONENT_PRICE_FIELD_LINK.get().get(cpuInstance),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `CPUImpl(Socket, int, double, double)` did not set the `price` field correctly.";
            }
        );

        var memoryInstance = Assertions2.callObject(
            () -> memoryImplConstructor.invoke('a', 3.1D),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `MemoryImpl(char, double)` threw an exception.";
            }
        );

        Assertions2.assertEquals(
            3.1D,
            H5Links.PURCHASED_COMPONENT_PRICE_FIELD_LINK.get().get(memoryInstance),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `MemoryImpl(char, double)` did not set the `price` field correctly.";
            }
        );

        var peripheralInstance = Assertions2.callObject(
            () -> peripheralImplConstructor.invoke(peripheralTypeMapping("ETHERNET"), 4.1D),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `PeripheralImpl(char, double)` threw an exception.";
            }
        );

        Assertions2.assertEquals(
            4.1D,
            H5Links.PURCHASED_COMPONENT_PRICE_FIELD_LINK.get().get(peripheralInstance),
            emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `PeripheralImpl(char, double)` did not set the `price` field correctly.";
            }
        );
    }

    private static class PurchasedComponentTransformer implements ClassTransformer {
        @Override
        public String getName() {
            return "PurchasedComponentTransformer";
        }

        @Override
        public void transform(ClassReader reader, ClassWriter writer) {
            reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    super.visit(version, access & ~Opcodes.ACC_ABSTRACT, name, signature, superName, interfaces);
                }
            }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }
}
