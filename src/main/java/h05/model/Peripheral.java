package h05.model;

public interface Peripheral extends Component {

    /**
     * @return the actual type of the peripheral module
     */
    PeripheralType getPeripheralType();
}
