package h05;

public interface Mainboard extends Component, Rateable {
    boolean addCpu(Cpu cpu);
    boolean addMemory(Memory memory);
    boolean addPeripheral(Peripheral peripheral);
}
