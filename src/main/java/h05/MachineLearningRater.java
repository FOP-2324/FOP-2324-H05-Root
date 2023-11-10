package h05;

import h05.model.*;

/**
 * Classifies the system supports playing Minecraft
 */
public class MachineLearningRater implements ComponentRater {

    private int tpuCount = 0;
    private int memorySize = 0;

    @Override
    public void consumeMainboard(Mainboard mainboard) {

    }

    @Override
    public void consumeCPU(CPU cpu) {

    }

    @Override
    public void consumeMemory(Memory memory) {
        memorySize += memory.getCapacity();
    }

    @Override
    public void consumePeripheral(Peripheral peripheral) {
        if(peripheral.getPeripheralType() == PeripheralType.TPU){
            tpuCount++;
        }
    }

    /**
     * Calculate scorr of system for specific model
     * @param modelSize Size of Machine Learning Model
     * @return score of System
     */
    public double checkModel(int modelSize){
        double tpu_fac = 100 - (100 - 1) * Math.pow(1.02, tpuCount);
        double memory = (double) memorySize / modelSize;

        return memory * tpu_fac;
    }
}
