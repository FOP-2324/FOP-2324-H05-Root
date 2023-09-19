package h05.model;

import h05.Rater;

/**
 * Interface to allow rating a system
 */
public interface Configuration {

    /**
     * Rates the system given a specific {@link Rater}
     * @param rater specific {@link  Rater}
     */
    void rateBy(Rater rater);
}
