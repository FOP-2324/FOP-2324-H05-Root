package h05.model;

/**
 * Representing Random Access Memory of a system
 */
public class MemoryImpl extends ComponentImpl implements Memory{

    private final char capacity;

    /**
     * Constructs a new memory with given parameters
     * @param capacity Capacity of the memory in Gigabyte
     * @param price Price of the memory
     */
    public MemoryImpl(char capacity, double price) {
        super(price);
        this.capacity = capacity;
    }

    @Override
    public char getCapacity() {
        return capacity;
    }
}
