package h05;

public class MemoryImpl extends ComponentImpl implements Memory{

    private final char capacity;

    public MemoryImpl(char capacity, double price) {
        super(price);
        this.capacity = capacity;
    }

    @Override
    public char getCapacity() {
        return capacity;
    }
}
