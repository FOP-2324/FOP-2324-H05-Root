package h05;

import h05.model.Mainboard;

/**
 * Representing a system which consists of multiple subsystems
 */
public class ServerCenter implements Configuration {

    private int nextFreeSlot = 0;
    private Mainboard[] mainboards = new Mainboard[1];

    /**
     * Adds a {@link Mainboard} to the server center
     *
     * @param mainboard specific {@link Mainboard} which gets included
     */
    public void addMainboard(Mainboard mainboard) {
        if (nextFreeSlot >= mainboards.length) { // currently full -> resize (double size)
            Mainboard[] resizedMainboards = new Mainboard[mainboards.length * 2];
            System.arraycopy(mainboards, 0, resizedMainboards, 0, nextFreeSlot);
            mainboards = resizedMainboards;
        }

        mainboards[nextFreeSlot++] = mainboard;
    }

    @Override
    public void rateBy(ComponentRater rater) {
        for (int i = 0; i < nextFreeSlot; i++) {
            Mainboard mainboard = mainboards[i];
            mainboard.rateBy(rater);
        }
    }
}
