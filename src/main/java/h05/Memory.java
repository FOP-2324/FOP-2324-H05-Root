package h05;

/**
 * Representing Random Access Memory of a system
 */
public class Memory extends ComponentImpl{

    private final char capacity;

    /**
     * Constructs a new memory with given parameters
     * @param capacity Capacity of the memory in Gigabyte
     * @param price Price of the memory
     */
    public Memory(char capacity, double price) {
        super(price);
        this.capacity = capacity;
    }

    /**
     *
     * @return the capacity in gigabyte
     */
    public char getCapacity() {
        return capacity;
    }
}
