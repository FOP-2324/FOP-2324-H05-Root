package h05;

public interface Cpu extends Component{
    Socket getSocket();
    int getCores();
    double getFrequency();
}
