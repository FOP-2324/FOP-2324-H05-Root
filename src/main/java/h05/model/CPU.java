package h05.model;

public interface CPU extends Component{

    /**
     *
     * @return the socket of the processor
     */
    Socket getSocket();

    /**
     *
     * @return the number of cores inside the processor
     */
    int getCores();

    /**
     *
     * @return the frequency of the cpu in Hertz
     */
    double getFrequency();
}
