package h05;

import h05.model.*;
import org.tudalgo.algoutils.student.test.;


/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {

        MainboardImpl desktop = new MainboardImpl(Socket.AM4, 2, 2, 100);
        desktop.addCPU(new CPUImpl(Socket.AM4, 10, 3.3e9, 300));
        desktop.addMemory(new MemoryImpl((char) 8, 60));

        TotalCostRater totalCostRater = new TotalCostRater();
        desktop.rateBy(totalCostRater);
        System.out.println("Price: " + totalCostRater.getTotalCost());
        //Price: 460.0
        //checkExpect


        MachineLearningRater machineLearningRater = new MachineLearningRater();
        desktop.rateBy(machineLearningRater);
        System.out.println("Model speed: " + machineLearningRater.checkModel(3));
        //Model speed: 2.6666666666666665
        //checkExpect
    }
}
