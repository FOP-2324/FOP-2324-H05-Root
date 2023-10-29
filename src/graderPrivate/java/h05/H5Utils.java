package h05;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.basic.BasicEnvironment;
import org.tudalgo.algoutils.tutor.general.match.Stringifiable;
import org.tudalgo.algoutils.tutor.general.reflections.ConstructorLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.WithModifiers;
import org.tudalgo.algoutils.tutor.general.reflections.WithTypeList;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class H5Utils {

    private static final Modifier[] ACTUAL_MODIFIERS = new Modifier[]{
        Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE, Modifier.STATIC,
        Modifier.FINAL, Modifier.INTERFACE, Modifier.ABSTRACT, Modifier.ENUM
    };

    public static void assertCorrectSuperclass(
        TypeLink type,
        TypeLink superclass
    ) {
        var typeString = BasicEnvironment.getInstance().getStringifier().stringify(type);
        var superclassString = BasicEnvironment.getInstance().getStringifier().stringify(superclass);

        Assertions2.assertEquals(
            toStringifiable(superclass),
            toStringifiable(type.superType()),
            Assertions2.emptyContext(),
            (r) -> "`%s` should extend `%s`.".formatted(typeString, superclassString)
        );
    }

    public static void assertCorrectInterfaces(
        TypeLink type,
        TypeLink... interfaces
    ) {
        var exactInterfaces = Arrays.stream(interfaces).map(H5Utils::toStringifiable).collect(Collectors.toSet());
        var actualInterfaces = type.interfaces().stream().map(H5Utils::toStringifiable).collect(Collectors.toSet());

        var typeString = BasicEnvironment.getInstance().getStringifier().stringify(type);

        var missingInterfaces = new HashSet<>(exactInterfaces);
        missingInterfaces.removeAll(actualInterfaces);

        if (!missingInterfaces.isEmpty()) {
            Assertions2.fail(
                Assertions2.emptyContext(),
                (r) -> "`%s` should implement interfaces %s, but is missing %s.".formatted(
                    typeString, exactInterfaces, missingInterfaces
                )
            );
        }

        var extraInterfaces = new HashSet<>(actualInterfaces);
        extraInterfaces.removeAll(exactInterfaces);

        if (!extraInterfaces.isEmpty()) {
            Assertions2.fail(
                Assertions2.emptyContext(),
                (r) -> "`%s` should implement interfaces %s, but has extra %s.".formatted(
                    typeString, exactInterfaces, extraInterfaces
                )
            );
        }
    }

    public static void assertConstructorCorrect(
        ConstructorLink constructor,
        TypeLink[] parameterTypes,
        Modifier... modifiers
    ) {
        assertCorrectModifiers(constructor, modifiers);
        assertCorrectParameters(constructor, parameterTypes);
    }

    public static void assertMethodCorrect(
        MethodLink method,
        TypeLink returnType,
        Modifier... modifiers
    ) {
        assertCorrectModifiers(method, modifiers);
        assertCorrectReturnType(method, returnType);
        assertHasNoParameters(method);
    }

    public static void assertMethodCorrect(
        MethodLink method,
        TypeLink returnType,
        TypeLink[] parameterTypes,
        Modifier... modifiers
    ) {
        assertCorrectModifiers(method, modifiers);
        assertCorrectReturnType(method, returnType);
        assertCorrectParameters(method, parameterTypes);
    }

    private static <T extends WithTypeList> void assertHasNoParameters(T method) {
        var methodString = BasicEnvironment.getInstance().getStringifier().stringify(method);

        Assertions2.assertEquals(
            0,
            method.typeList().size(),
            Assertions2.emptyContext(),
            (r) -> "Method `%s` should have no parameters.".formatted(methodString)
        );
    }

    private static <T extends WithTypeList> void assertCorrectParameters(T method, TypeLink... parameterTypes) {
        if (parameterTypes.length == 0) {
            assertHasNoParameters(method);
            return;
        }

        var exactParameters = Arrays.stream(parameterTypes).map(H5Utils::toStringifiable).collect(Collectors.toSet());
        var actualParameters = method.typeList().stream().map(H5Utils::toStringifiable).collect(Collectors.toSet());

        var methodString = BasicEnvironment.getInstance().getStringifier().stringify(method);

        var missingParameters = new HashSet<>(exactParameters);
        missingParameters.removeAll(actualParameters);

        if (!missingParameters.isEmpty()) {
            Assertions2.fail(
                Assertions2.emptyContext(),
                (r) -> "Method `%s` should have parameters %s, but is missing %s.".formatted(
                    methodString, exactParameters, missingParameters
                )
            );
        }

        var extraParameters = new HashSet<>(actualParameters);
        extraParameters.removeAll(exactParameters);

        if (!extraParameters.isEmpty()) {
            Assertions2.fail(
                Assertions2.emptyContext(),
                (r) -> "Method `%s` should have parameters %s, but has extra %s.".formatted(
                    methodString, exactParameters, extraParameters
                )
            );
        }
    }

    public static void assertCorrectReturnType(
        MethodLink method,
        TypeLink returnType
    ) {
        var methodString = BasicEnvironment.getInstance().getStringifier().stringify(method);
        var returnTypeString = BasicEnvironment.getInstance().getStringifier().stringify(returnType);

        Assertions2.assertEquals(
            toStringifiable(returnType),
            toStringifiable(method.returnType()),
            Assertions2.emptyContext(),
            (r) -> "Method `%s` should return `%s`.".formatted(methodString, returnTypeString)
        );
    }

    public static <T extends WithModifiers> void assertCorrectModifiers(T t, Modifier... modifiers) {
        var exactModifiers = new HashSet<>(List.of(modifiers));
        var actualModifiers = Arrays.stream(ACTUAL_MODIFIERS).filter(m -> m.is(t.modifiers())).collect(Collectors.toSet());

        var missingModifiers = new HashSet<>(exactModifiers);
        missingModifiers.removeAll(actualModifiers);

        if (!missingModifiers.isEmpty()) {
            Assertions2.fail(
                Assertions2.emptyContext(),
                (r) -> "%s should have modifiers %s, but is missing %s.".formatted(
                    BasicEnvironment.getInstance().getStringifier().stringify(t), exactModifiers, missingModifiers
                )
            );
        }

        var extraModifiers = new HashSet<>(actualModifiers);
        extraModifiers.removeAll(exactModifiers);

        if (!extraModifiers.isEmpty()) {
            Assertions2.fail(
                Assertions2.emptyContext(),
                (r) -> "`%s` should have modifiers %s, but has extra %s.".formatted(
                    BasicEnvironment.getInstance().getStringifier().stringify(t), exactModifiers, extraModifiers
                )
            );
        }
    }

    public static <T> T createUnsafeInstance(Class<?> c) {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (T) ((Unsafe) theUnsafe.get(null)).allocateInstance(c);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Stringifiable toStringifiable(Object o) {
        return new StringifiableWrapper(o);
    }

    private static class StringifiableWrapper implements Stringifiable {

        private final Object o;

        public StringifiableWrapper(Object o) {
            this.o = o;
        }

        @Override
        public String string() {
            return toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StringifiableWrapper s)
                return o.equals(s.o);
            return o.equals(obj);
        }

        @Override
        public int hashCode() {
            return o.hashCode();
        }

        @Override
        public String toString() {
            return BasicEnvironment.getInstance().getStringifier().stringify(o);
        }
    }
}
