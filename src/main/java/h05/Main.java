package h05;

import h05.model.CPUImpl;
import h05.model.MainboardImpl;
import h05.model.MemoryImpl;
import h05.model.PeripheralImpl;
import h05.model.PeripheralType;
import h05.model.Socket;
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
        desktop.addPeripheral(new PeripheralImpl(PeripheralType.GPU, 300));


        TotalCostRater totalCostRater = new TotalCostRater();
        desktop.rateBy(totalCostRater);
        System.out.println("Price: " + totalCostRater.getTotalPrice());
        StudentTestUtils.testWithinRange(760.0 - EPSILON, 760 + EPSILON, totalCostRater.getTotalPrice());


        MachineLearningRater machineLearningRater = new MachineLearningRater();
        desktop.rateBy(machineLearningRater);
        System.out.println("Model speed: " + machineLearningRater.checkModel(3));
        StudentTestUtils.testWithinRange(2.65, 2.67, machineLearningRater.checkModel(3));
    }
}
