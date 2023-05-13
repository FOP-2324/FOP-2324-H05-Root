package h05;

public class PeripheralImpl extends ComponentImpl implements Peripheral{ // TODO: ggf in Gpu und Ethernet Klassen einzeln

    private final PeripheralType peripheralType;

    public PeripheralImpl(PeripheralType peripheralType, double price) {
        super(price);
        this.peripheralType = peripheralType;
    }

    @Override
    public PeripheralType getPeripheralType() {
        return peripheralType;
    }
}
