package h05;

public class CpuImpl extends ComponentImpl implements Cpu{

    private final Socket socket;
    private final int numOfCores;
    private final double frequency;

    public CpuImpl(Socket socket, int numOfCores, double frequency, double price) {
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
