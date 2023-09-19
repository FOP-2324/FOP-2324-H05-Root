package h05.model;

import h05.Rater;

/**
 * Represents a mainboard of a system, which combines all other components
 */
public class MainboardImpl extends ComponentImpl implements Mainboard {

    private final Socket socket;
    private CPU cpu = null;
    private final Memory[] memories;
    private int nextMemorySlot = 0;
    private final Peripheral[] peripherals;
    private int nextPeripheralSlot = 0;

    /**
     * Constructs a new Mainboard with given parameters
     * @param socket actual cpu socket
     * @param numberOfMemorySlots max number of allowed memories
     * @param numberOfPeripheralSlots max number of allowed peripheral
     * @param price price of mainboard
     */
    public MainboardImpl(Socket socket, int numberOfMemorySlots, int numberOfPeripheralSlots, double price){
        super(price);
        this.socket = socket;
        this.memories = new Memory[numberOfMemorySlots];
        this.peripherals = new Peripheral[numberOfPeripheralSlots];
    }

    /**
     * Adds a Cpu to the mainboard
     * @param cpu cpu, which gets added in the system
     * @return true if operation was successful, otherwise false
     */
    public boolean addCpu(CPU cpu) {
        if(cpu != null && this.cpu == null && cpu.getSocket() == socket){
            this.cpu = cpu;
            return true;
        }
        return false;
    }

    /**
     * Adds a memory to the mainboard
     * @param memory memory which gets added to the system
     * @return true if operation was successful, otherwise false
     */
    public boolean addMemory(Memory memory) {
        if(memory != null && nextMemorySlot < memories.length){
            memories[nextMemorySlot] = memory;
            nextMemorySlot++;
            return true;
        }
        return false;
    }

    /**
     * Adds a peripheral to the mainboard
     * @param peripheral peripheral which gets added to the system
     * @return true if operation was successful, otherwise false
     */
    public boolean addPeripheral(Peripheral peripheral) {
        if(peripheral != null && nextPeripheralSlot < peripherals.length){
            peripherals[nextPeripheralSlot] = peripheral;
            nextPeripheralSlot++;
            return true;
        }
        return false;
    }

    @Override
    public void rateBy(Rater rater) {
        rater.consumeMainboard(this);

        if(cpu != null) {
            rater.consumeCpu(cpu);
        }

        for(int i = 0; i < nextMemorySlot; i++){
            rater.consumeMemory(memories[i]);
        }

        for(int i = 0; i < nextPeripheralSlot; i++){
            rater.consumePeripheral(peripherals[i]);
        }
    }
}
