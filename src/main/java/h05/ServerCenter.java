package h05;

import h05.model.Mainboard;
import h05.model.Configuration;

import java.util.ArrayList;

/**
 * Representing a system which consists of multiple subsystems
 */
public class ServerCenter implements Configuration {

    private int nextFreeSlot = 0;
    private final Mainboard[] mainboards = new Mainboard[10000];

    /**
     * Adds a {@link Mainboard} to the server center
     * @param mainboard specific {@link Mainboard} which gets included
     */
    public void addMainboard(Mainboard mainboard){
        mainboards[nextFreeSlot++] = mainboard;
    }

    @Override
    public void rateBy(ComponentRater rater) {
        for(int i = 0; i < nextFreeSlot; i++){
            Mainboard mainboard = mainboards[i];
            mainboard.rateBy(rater);
        }
    }
}
