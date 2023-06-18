package h05;

public class Memory extends ComponentImpl{

    private final char capacity;

    public Memory(char capacity, double price) {
        super(price);
        this.capacity = capacity;
    }

    public char getCapacity() {
        return capacity;
    }
}
