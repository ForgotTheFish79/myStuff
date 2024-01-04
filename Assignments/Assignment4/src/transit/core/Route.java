package transit.core;

import java.util.ArrayList;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public abstract class Route 
{
	protected int routeNumber;
	protected String routeDescription;
	public Stop firstStop, lastStop;
	protected ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	public Route(int rN, String rD, Stop first)
	{
		routeNumber = rN;
		routeDescription=rD;
		firstStop = first;
		lastStop = first;
	}
	public int getRouteNumber() 
	{
		return routeNumber;
	}
	public void setRouteNumber(int routeNumber) 
	{
		this.routeNumber = routeNumber;
	}
	public String getRouteDescription() 
	{
		return routeDescription;
	}
	public void setRouteDescription(String routeDescription) 
	{
		this.routeDescription = routeDescription;
	}
	public ArrayList<Vehicle> getVehicles() 
	{
		return vehicles;
	}
	public void setVehicles(ArrayList<Vehicle> vehicles) 
	{
		this.vehicles = vehicles;
	}
	public void gainPassengersAll()
	{
		Stop currentStop = firstStop;
		while(currentStop!= null)
		{
			currentStop.gainPassengers();
			currentStop = currentStop.nextStop;
		}
		
	}
	public void moveAll(int m)
	{
		for(Vehicle v :vehicles)
		{
			v.move(m);
		}
	}
	
	public abstract void addDriver(String n,double s);
	public abstract void addStop(String sN, double x, double y);	
	public String toString()
	{
		return "Route #"+routeNumber+": "+routeDescription+"\n"+firstStop; 
	}
}
