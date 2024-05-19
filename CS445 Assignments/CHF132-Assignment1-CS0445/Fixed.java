public class Fixed extends Product
{
    private double price;

    public Fixed(String n, double p)
    {
        super(n);
        price = p;
    }

    @Override
    public double cost() {
        return price;
    }
    public String toString()
    {
        return (super.toString()+". Price: " +price+" ");
    }
}
