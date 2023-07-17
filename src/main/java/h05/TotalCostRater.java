package h05;

/**
 * {@link ComponentRater} which rates the system in respect to the price
 */
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

    /**
     *
     * @return the total cost of the system
     */
    public double getTotalCost(){
        return cost;
    }
}
