package h05;

public interface ComponentRater {
    void consumeMainboard(Mainboard mainboard);
    void consumeCpu(Cpu cpu);
    void consumeMemory(Memory memory);
    void consumePeripheral(Peripheral peripheral);
}
