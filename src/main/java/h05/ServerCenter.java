package h05;

import h05.model.MainboardImpl;
import h05.model.Configuration;

import java.util.ArrayList;

/**
 * Representing a system which consists of multiple subsystems
 */
public class ServerCenter implements Configuration {

    ArrayList<MainboardImpl> mainboards = new ArrayList<>();

    /**
     * Adds a {@link MainboardImpl} to the server center
     * @param mainboard specific {@link MainboardImpl} which gets included
     */
    void addMainboard(MainboardImpl mainboard){
        mainboards.add(mainboard);
    }

    @Override
    public void rateBy(ComponentRater rater) {
        for(MainboardImpl mainboard : mainboards){
            mainboard.rateBy(rater);
        }
    }
}
