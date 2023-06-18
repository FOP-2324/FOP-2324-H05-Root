package h05;

public class Cpu extends ComponentImpl{

    private final Socket socket;
    private final int numOfCores;
    private final double frequency;

    public Cpu(Socket socket, int numOfCores, double frequency, double price) {
        super(price);
        this.socket = socket;
        this.numOfCores = numOfCores;
        this.frequency = frequency;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getCores() {
        return numOfCores;
    }

    public double getFrequency() {
        return frequency;
    }
}
