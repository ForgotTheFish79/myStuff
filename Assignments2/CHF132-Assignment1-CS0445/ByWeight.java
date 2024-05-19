public class ByWeight extends Variable
{
    private double weight;
    private double price;

    public ByWeight(String name,  double p,double w)
    {
        super(name);
        weight = w;
        price = p;
    }

    @Override
    public double cost()
    {
        return weight*price;
    }

    public String toString()
    {
        return (super.toString()+" Weight: " +weight+". Price: "+ cost()+" ");
    }
}
