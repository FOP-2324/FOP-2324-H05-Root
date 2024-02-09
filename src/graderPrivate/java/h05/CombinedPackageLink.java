package h05;

import org.tudalgo.algoutils.tutor.general.reflections.PackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Collection;
import java.util.List;

/**
 * A {@link PackageLink} that combines multiple {@link PackageLink}s into one.
 */
public class CombinedPackageLink implements PackageLink {

    /**
     * The {@link TypeLink}s of the combined {@link PackageLink}s.
     */
    private final List<? extends TypeLink> typeLinks;

    /**
     * Constructs a {@link CombinedPackageLink} from the given {@link PackageLink}s.
     *
     * @param packageLinks the {@link PackageLink}s to combine
     */
    public CombinedPackageLink(List<? extends PackageLink> packageLinks) {
        typeLinks = packageLinks.stream().flatMap(pl -> pl.getTypes().stream()).toList();
    }


    @Override
    public Collection<? extends TypeLink> getTypes() {
        return typeLinks;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Object reflection() {
        return null;
    }
}
