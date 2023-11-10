package h05;

import h05.model.*;
import org.tudalgo.algoutils.student.Student;
import org.tudalgo.algoutils.student.test.StudentTestUtils;


/**
 * Main entry point in executing the program.
 */
public class Main {
    private static double EPSILON = 10e-6;

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
        StudentTestUtils.testWithinRange(460.0 - EPSILON, 460.0 + EPSILON, totalCostRater.getTotalCost());


        MachineLearningRater machineLearningRater = new MachineLearningRater();
        desktop.rateBy(machineLearningRater);
        System.out.println("Model speed: " + machineLearningRater.checkModel(3));
        StudentTestUtils.testWithinRange(2.65, 2.67, machineLearningRater.checkModel(3));
    }
}
