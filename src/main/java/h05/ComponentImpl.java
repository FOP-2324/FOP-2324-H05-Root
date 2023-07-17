package h05;

/**
 * Abstract Implementation of a Component
 */
public abstract class ComponentImpl implements Component{

    private final double price;

    /**
     * Construct a new component with given price
     * @param price actual price of component
     */
    public ComponentImpl(double price){
        this.price = price;
    }


    @Override
    public double getPrice() {
        return price;
    }
}
