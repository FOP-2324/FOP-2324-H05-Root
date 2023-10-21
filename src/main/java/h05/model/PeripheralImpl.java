package h05.model;

/**
 * Representing a peripheral of a system
 */
public class PeripheralImpl extends PurchasedComponent implements Peripheral {

    private final PeripheralType peripheralType;

    /**
     * Constructs a new peripheral with specific parameters
     *
     * @param peripheralType actual type of peripheral
     * @param price          the price of the peripheral module
     */
    public PeripheralImpl(PeripheralType peripheralType, double price) {
        super(price);
        this.peripheralType = peripheralType;
    }

    @Override
    public PeripheralType getPeripheralType() {
        return peripheralType;
    }
}
