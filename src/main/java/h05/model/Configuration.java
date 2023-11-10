package h05.model;

import h05.ComponentRater;

/**
 * Interface to allow rating a system
 */
public interface Configuration {

    /**
     * Rates the system given a specific {@link ComponentRater}
     * @param rater specific {@link  ComponentRater}
     */
    void rateBy(ComponentRater rater);
}
