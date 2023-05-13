package h05;

public abstract class ComponentImpl implements Component{

    private final double price;

    public ComponentImpl(double price){
        this.price = price;
    }


    @Override
    public double getPrice() {
        return price;
    }
}
