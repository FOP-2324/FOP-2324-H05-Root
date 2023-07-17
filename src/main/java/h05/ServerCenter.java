package h05;

import java.util.ArrayList;

/**
 * Representing a system which consists of multiple subsystems
 */
public class ServerCenter implements Rateable {

    ArrayList<Mainboard> mainboards = new ArrayList<>();

    /**
     * Adds a {@link Mainboard} to the server center
     * @param mainboard specific {@link Mainboard} which gets included
     */
    void addMainboard(Mainboard mainboard){
        mainboards.add(mainboard);
    }

    @Override
    public void rateBy(ComponentRater rater) {
        for(Mainboard mainboard : mainboards){
            mainboard.rateBy(rater);
        }
    }
}
