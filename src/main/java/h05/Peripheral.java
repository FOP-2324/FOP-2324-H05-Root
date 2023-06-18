package h05;

public class Peripheral extends ComponentImpl{ // TODO: ggf in Gpu und Ethernet Klassen einzeln

    private final PeripheralType peripheralType;

    public Peripheral(PeripheralType peripheralType, double price) {
        super(price);
        this.peripheralType = peripheralType;
    }

    public PeripheralType getPeripheralType() {
        return peripheralType;
    }
}
