package h05;

import org.tudalgo.algoutils.tutor.general.match.Match;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.match.MatchingUtils;
import org.tudalgo.algoutils.tutor.general.match.Stringifiable;
import org.tudalgo.algoutils.tutor.general.stringify.HTML;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

/**
 * Global constants and methods.
 */
@SuppressWarnings({"deprecated", "unused"})
public class Global {

    /**
     * The minimum similarity between two strings to be considered equal.
     */
    private static final double MINIMUM_SIMILARITY = .8;

    /**
     * The amount of misspelled names in the student implementation.
     */
    public static int MISSPELLING_COUNTER = 0;

    /**
     * The misspelled names in the student implementation.
     */
    public static final List<String> MISSPELLINGS = new ArrayList<>();

    /**
     * Returns a {@link Matcher} for the given string.
     *
     * @param string the string to match
     * @param <T>    the type of the object to match
     * @return a {@link Matcher} for the given string
     */
    public static <T extends Stringifiable> Matcher<T> similarityMatcher(final String string) {
        return new Matcher<>() {

            /**
             * The maximum similarity between the given string and a matched object.
             */
            double maxSimilarity = 0;

            @Override
            public String characteristic() {
                return String.format("at least %.0f%% similar to %s", Global.MINIMUM_SIMILARITY * 100, HTML.tt(string));
            }

            @Override
            public <ST extends T> Match<ST> match(final ST object) {

                return new Match<>() {

                    final double similarity = MatchingUtils.similarity(object.string(), string);

                    {
                        if (matched()) {
                            if (maxSimilarity == 0 && similarity != 1) {
                                MISSPELLING_COUNTER++;
                                MISSPELLINGS.add(string);
                            } else if (maxSimilarity != 0 && maxSimilarity != 1 && similarity == 1) {
                                MISSPELLING_COUNTER--;
                                MISSPELLINGS.remove(string);
                            }
                            maxSimilarity = max(maxSimilarity, similarity);
                        }
                    }

                    @Override
                    public boolean matched() {
                        return similarity >= MINIMUM_SIMILARITY;
                    }

                    @Override
                    public ST object() {
                        return object;
                    }

                    @Override
                    public int compareTo(final Match<ST> other) {
                        if (!other.matched()) {
                            return matched() ? 1 : 0;
                        } else if (!matched()) {
                            return -1;
                        }
                        final double otherSimilarity = MatchingUtils.similarity(other.object().string(), string);
                        return Double.compare(similarity, otherSimilarity);
                    }
                };
            }
        };
    }
}
