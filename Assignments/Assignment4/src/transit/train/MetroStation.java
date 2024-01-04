package transit.train;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public class MetroStation extends transit.core.Stop
{
	public MetroStation(String n, double x, double y)
	{
		super(n,x,y);
	}
	public void gainPassengers()
	{
		int rand = (int)(Math.random()*10)+1;
		for(int i = 0;i<rand;i++)
		super.getPassengersWaiting().add(new Passenger(this));
	}

}
