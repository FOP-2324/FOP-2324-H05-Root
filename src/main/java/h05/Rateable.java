package h05;

/**
 * Interface to allow rating a system
 */
public interface Rateable {

    /**
     * Rates the system given a specific {@link ComponentRater}
     * @param rater specific {@link  ComponentRater}
     */
    void rateBy(ComponentRater rater);
}
