package h05;

/**
 * Representing a processor
 */
public class Cpu extends ComponentImpl{

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
    public Cpu(Socket socket, int numOfCores, double frequency, double price) {
        super(price);
        this.socket = socket;
        this.numOfCores = numOfCores;
        this.frequency = frequency;
    }

    /**
     *
     * @return the socket of the processor
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     *
     * @return the number of cores inside the processor
     */
    public int getCores() {
        return numOfCores;
    }

    /**
     *
     * @return the frequency of the cpu in Hertz
     */
    public double getFrequency() {
        return frequency;
    }
}
