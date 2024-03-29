package h05;

import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

public class H05_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H05")
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
