package h05;

/**
 * Representing a peripheral of a system
 */
public class Peripheral extends ComponentImpl{

    private final PeripheralType peripheralType;

    /**
     * Constructs a new peripheral with specific parameters
     * @param peripheralType actual type of peripheral
     * @param price the price of the peripheral module
     */
    public Peripheral(PeripheralType peripheralType, double price) {
        super(price);
        this.peripheralType = peripheralType;
    }

    /**
     *
     * @return the actual type of the peripheral module
     */
    public PeripheralType getPeripheralType() {
        return peripheralType;
    }
}
