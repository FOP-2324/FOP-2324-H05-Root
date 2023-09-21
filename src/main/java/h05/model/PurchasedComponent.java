package h05.model;

/**
 * Abstract Implementation of a Component
 */
public abstract class PurchasedComponent implements Component{

    private final double price;

    /**
     * Construct a new component with given price
     * @param price actual price of component
     */
    public PurchasedComponent(double price){
        this.price = price;
    }


    @Override
    public double getPrice() {
        return price;
    }
}
