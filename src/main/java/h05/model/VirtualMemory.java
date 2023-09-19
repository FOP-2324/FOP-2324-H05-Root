package h05.model;

public class VirtualMemory implements Memory{

    final double costPerGigaByte;
    char capacity = 0;

    public VirtualMemory(double costPerGigaByte){
        this.costPerGigaByte = costPerGigaByte;
    }

    @Override
    public double getPrice() {
        return capacity * costPerGigaByte;
    }

    @Override
    public char getCapacity() {
        return capacity;
    }

    public void setCapacity(char capacity){
        this.capacity = capacity;
    }
}
