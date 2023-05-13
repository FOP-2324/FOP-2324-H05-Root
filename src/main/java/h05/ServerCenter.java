package h05;

import java.util.ArrayList;

public class ServerCenter implements Rateable {

    ArrayList<Mainboard> mainboards = new ArrayList<>();

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
