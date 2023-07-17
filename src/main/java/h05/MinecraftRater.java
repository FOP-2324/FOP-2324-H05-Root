package h05;

/**
 * Classifies the system supports playing Minecraft
 */
public class MinecraftRater implements ComponentRater{

    private boolean hasCpu = false;
    private boolean hasGpu = false;
    private char memorySize = 0;

    @Override
    public void consumeMainboard(Mainboard mainboard) {

    }

    @Override
    public void consumeCpu(Cpu cpu) {
        hasCpu = true;
    }

    @Override
    public void consumeMemory(Memory memory) {
        memorySize += memory.getCapacity();
    }

    @Override
    public void consumePeripheral(Peripheral peripheral) {
        if(peripheral.getPeripheralType() == PeripheralType.GPU){
            hasGpu = true;
        }
    }

    /**
     *
     * @return true if Minecraft is playable, otherwise false
     */
    public boolean isPlayable(){
        return  hasCpu & hasGpu & memorySize > 4;
    }
}
