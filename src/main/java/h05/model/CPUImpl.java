package h05.model;

/**
 * Representing a processor
 */
public class CPUImpl extends ComponentImpl implements CPU{

    private final Socket socket;
    private final int numOfCores;
    private final double frequency;

    /**
     * Constructs a processor with given parameters
     * @param socket Specific socket of processor
     * @param numOfCores the number of cores
     * @param frequency Clock frequency in Hertz
     * @param price price of the processor
     */
    public CPUImpl(Socket socket, int numOfCores, double frequency, double price) {
        super(price);
        this.socket = socket;
        this.numOfCores = numOfCores;
        this.frequency = frequency;
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public int getCores() {
        return numOfCores;
    }

    @Override
    public double getFrequency() {
        return frequency;
    }
}
