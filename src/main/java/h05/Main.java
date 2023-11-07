package h05;

import h05.model.*;

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
        VirtualMemory vmem = new VirtualMemory(0.5);
        desktop.addMemory(vmem);

        //vmem.setCapacity((char) 30);
        //desktop.addPeripheral(new PeripheralImpl(PeripheralType.GPU, 300));

        TotalCostRater totalCostRater = new TotalCostRater();
        desktop.rateBy(totalCostRater);
        System.out.println("Price: " + totalCostRater.getTotalCost());


        MachineLearningRater machineLearningRater = new MachineLearningRater();
        desktop.rateBy(machineLearningRater);
        System.out.println("Model speed: " + machineLearningRater.checkModel(3));

        ServerCenter serverCenter = new ServerCenter();
        serverCenter.addMainboard(desktop);
        serverCenter.addMainboard(desktop);

        TotalCostRater serverCenterRater = new TotalCostRater();
        serverCenter.rateBy(serverCenterRater);
        System.out.println("Server Center Price: " + serverCenterRater.getTotalCost());
    }
}
