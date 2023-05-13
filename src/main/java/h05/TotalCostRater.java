package h05;

public class TotalCostRater implements ComponentRater{

    private double cost = 0.0f;

    @Override
    public void consumeMainboard(Mainboard mainboard) {
        cost += mainboard.getPrice();
    }

    @Override
    public void consumeCpu(Cpu cpu) {
        cost += cpu.getPrice();
    }

    @Override
    public void consumeMemory(Memory memory) {
        cost += memory.getPrice();
    }

    @Override
    public void consumePeripheral(Peripheral peripheral) {
        cost += peripheral.getPrice();
    }

    public double getTotalCost(){
        return cost;
    }
}
