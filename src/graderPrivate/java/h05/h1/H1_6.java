package h05.h1;

import h05.H5Links;
import h05.H5Utils;
import h05.Unchecked;
import h05.model.PurchasedComponent;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import static h05.H5Utils.toStringifiable;

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
    public void testConstructor() {
        var constructor = H5Links.PURCHASED_COMPONENT_CONSTRUCTOR_LINK.get();

        H5Utils.assertConstructorCorrect(constructor, new TypeLink[]{BasicTypeLink.of(double.class)}, Modifier.PUBLIC);

        var instance = Assertions2.callObject(
            () -> new TestComponent(3.1D),
            Assertions2.emptyContext(),
            (r) -> {
                r.cause().printStackTrace();
                return "Constructor `PurchasedComponent(double)` threw an exception.";
            }
        );

        Assertions2.assertEquals(
            3.1D,
            H5Links.PURCHASED_COMPONENT_PRICE_FIELD_LINK.get().get(instance),
            Assertions2.emptyContext(),
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
            Assertions2.emptyContext(),
            (r) -> "Field `price` in class `PurchasedComponent` should be of type `double`."
        );

        var instance = Assertions2.callObject(
            () -> new TestComponent(3.2D),
            Assertions2.emptyContext(),
            (r) -> "Could not instantiate class `PurchasedComponent`."
        );

        price.set(instance, 3.2D);

        Assertions2.assertEquals(
            3.2D,
            Assertions2.callObject(
                Unchecked.uncheckedObjectCallable(() -> getPrice.invoke(instance)),
                Assertions2.emptyContext(),
                (r) -> {
                    r.cause().printStackTrace();
                    return "Method `getPrice()` in class `PurchasedComponent` threw an exception.";
                }
            ),
            Assertions2.emptyContext(),
            (r) -> "Method `getPrice()` in class `PurchasedComponent` did not return the correct value."
        );
    }

    @Test
    public void testOthersExtendPurchasedComponent() {
        TypeLink cpuImpl = H5Links.CPU_IMPL_LINK.get();
        TypeLink memoryImpl = H5Links.MEMORY_IMPL_LINK.get();
        TypeLink peripheralImpl = H5Links.PERIPHERAL_IMPL_LINK.get();

        H5Utils.assertCorrectSuperclass(cpuImpl, H5Links.PURCHASED_COMPONENT_LINK.get());
        H5Utils.assertCorrectSuperclass(memoryImpl, H5Links.PURCHASED_COMPONENT_LINK.get());
        H5Utils.assertCorrectSuperclass(peripheralImpl, H5Links.PURCHASED_COMPONENT_LINK.get());

        // TODO: Check that these don't have a getPrice method?
    }

    private static class TestComponent extends PurchasedComponent {

        public TestComponent(double price) {
            super(price);
        }
    }
}
