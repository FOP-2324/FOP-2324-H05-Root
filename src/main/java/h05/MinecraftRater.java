package h05;

public class MinecraftRater implements ComponentRater{

    private boolean hasGpu = false;
    private char memorySize = 0;

    @Override
    public void consumeMainboard(Mainboard mainboard) {

    }

    @Override
    public void consumeCpu(Cpu cpu) {

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

    public boolean isPlayable(){
        return  hasGpu & memorySize > 4;
    }
}
