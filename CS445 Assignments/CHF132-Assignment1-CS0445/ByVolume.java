public class ByVolume extends Variable
{
    private double volume;
    private double price;

    public ByVolume(String name, double p, double v)
    {
        super(name);
        volume = v;
        price = p;
    }

    @Override
    public double cost()
    {
        return volume*price;
    }
    public String toString()
    {
        return (super.toString()+" Volume: "+volume+". Price: "+ cost()+" ");
    }
}
