package h05;

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

        Mainboard desktop = new MainboardImpl(Socket.AM4, 2, 2, 100);
        desktop.addCpu(new CpuImpl(Socket.AM4, 10, 3.3e9, 300));
        desktop.addMemory(new MemoryImpl((char) 8, 60));
        //desktop.addPeripheral(new PeripheralImpl(PeripheralType.GPU, 300));

        TotalCostRater totalCostRater = new TotalCostRater();
        desktop.rateBy(totalCostRater);
        System.out.println("Price: " + totalCostRater.getTotalCost());


        MinecraftRater minecraftRater = new MinecraftRater();
        desktop.rateBy(minecraftRater);
        System.out.println("Minecraft supported: " + minecraftRater.isPlayable());

        ServerCenter serverCenter = new ServerCenter();
        serverCenter.addMainboard(desktop);
        serverCenter.addMainboard(desktop);

        TotalCostRater serverCenterRater = new TotalCostRater();
        serverCenter.rateBy(serverCenterRater);
        System.out.println("Server Center Price: " + serverCenterRater.getTotalCost());
    }
}
