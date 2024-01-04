package transit.bus;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public class BusStop extends transit.core.Stop
{
	public BusStop(String n, double x, double y)
	{
		super(n,x,y);
	}
	public void gainPassengers()
	{
		int rand = (int)(Math.random()*5)+1;
		for(int i = 0;i<rand+5;i++)
		super.getPassengersWaiting().add(new Passenger(this));

	}
//	public String toString()
//	{
//		return "Bus Stop" +stopName +"is at "+xCoordinate+","+yCoordinate+" and has "+passengersWaiting.size()+"Passengers waiting";
//	}
}
