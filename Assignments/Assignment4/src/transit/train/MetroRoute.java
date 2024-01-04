package transit.train;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public class MetroRoute extends Route
{

	 public MetroRoute(int rN, String rD, MetroStation first)
	{
		super(rN, rD, first);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addDriver(String n, double s) 
	{
		Train newTrain = new Train(n,s,this);
		newTrain.setCars(3);
		vehicles.add(newTrain);
		
	}

	@Override
	public void addStop(String sN, double x, double y) 
	{
		MetroStation newStop = new MetroStation(sN,x,y);
		
		Stop temp = lastStop;
		lastStop = newStop;
		newStop.prevStop = temp;
		temp.nextStop = newStop;
	}
	public String toString()
	{
		String result="Metro "+super.toString()+"\nTrains: ";
		for(Vehicle v : vehicles)
		{
			result+=v+"\n";
		}
		return result;
	}
}
