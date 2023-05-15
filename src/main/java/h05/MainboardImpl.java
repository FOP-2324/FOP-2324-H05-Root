package h05;

public class MainboardImpl extends ComponentImpl implements Mainboard{

    private final Socket socket;
    private Cpu cpu = null;
    private final Memory[] memories;
    private int nextMemorySlot = 0;
    private final Peripheral[] peripherals;
    private int nextPeripheralSlot = 0;

    public MainboardImpl(Socket socket, int memorySlots, int peripheralSlots, double price){
        super(price);
        this.socket = socket;
        this.memories = new Memory[memorySlots];
        this.peripherals = new Peripheral[peripheralSlots];
    }

    public boolean addCpu(Cpu cpu) {
        if(cpu != null && this.cpu == null && cpu.getSocket() == socket){
            this.cpu = cpu;
            return true;
        }
        return false;
    }


    public boolean addMemory(Memory memory) {
        if(memory != null && nextMemorySlot < memories.length){
            memories[nextMemorySlot] = memory;
            nextMemorySlot++;
            return true;
        }
        return false;
    }

    public boolean addPeripheral(Peripheral peripheral) {
        if(peripheral != null && nextPeripheralSlot < peripherals.length){
            peripherals[nextPeripheralSlot] = peripheral;
            nextPeripheralSlot++;
            return true;
        }
        return false;
    }

    @Override
    public void rateBy(ComponentRater rater) {
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
