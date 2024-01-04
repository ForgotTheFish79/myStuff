package transit.bus;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public class BusRoute extends transit.core.Route
{

	public BusRoute(int rN, String rD, BusStop first) 
	{
		super(rN, rD, first);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addDriver(String n, double s) 
	{
		Bus newBus = new Bus(n,s,this);
		vehicles.add(newBus);
	}

	@Override
	public void addStop(String sN, double x, double y) 
	{
		// TODO Auto-generated method stub
		BusStop newStop = new BusStop(sN,x,y);
		Stop temp = lastStop;
		lastStop = newStop;
		newStop.prevStop = temp;
		temp.nextStop = newStop;
		
		
	}
	public String toString()
	{
		String result = "Bus "+super.toString();
		for(Vehicle v : vehicles)
		{
			result+=v+"\n";
		}
		return result;
	}

}
