package transit.core;

import java.util.ArrayList;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public abstract class Vehicle 
{
	protected String identifier,driverName;
	protected double speed,xCoordinate, yCoordinate;
	protected Route route;
	public Stop currentDestination;
	protected ArrayList<Passenger> Passengers =new ArrayList<Passenger>();
	protected boolean isStopped,isInBound;
	public Vehicle(String driver, double sp, Route rt, Stop stop, boolean isInbound)
	{
		driverName = driver;
		identifier = driver.hashCode()+"";
		speed = sp;
		route = rt;
		this.isInBound=isInbound;
		currentDestination = stop;
		isStopped = true;
		xCoordinate = currentDestination.xCoordinate;
		yCoordinate = currentDestination.yCoordinate;
		
	}
	public Vehicle(String driver, double sp, Route rt, Stop stop)
	{
		driverName = driver;
		identifier = driver.hashCode()+"";
		speed = sp;
		route = rt;
		currentDestination = stop;
		isInBound=true;
		isStopped = true;
		xCoordinate = currentDestination.xCoordinate;
		yCoordinate = currentDestination.yCoordinate;
	}
	public Vehicle(String driver, double sp, Route rt)
	{
		identifier = driver.hashCode()+"";
		driverName = driver;
		speed = sp;
		currentDestination = rt.firstStop;
		route = rt;
		isInBound=true;
		isStopped = true;
		xCoordinate = currentDestination.xCoordinate;
		yCoordinate = currentDestination.yCoordinate;
	}
	public String getIdentifier() 
	{
		return identifier;
	}
	public void setIdentifier(String identifier) 
	{
		this.identifier = identifier;
	}
	public String getDriverName() 
	{
		return driverName;
	}
	public void setDriverName(String driverName) 
	{
		this.driverName = driverName;
	}
	public double getSpeed() 
	{
		return speed;
	}
	public void setSpeed(double speed) 
	{
		this.speed = speed;
	}
	public double getxCoordinate() 
	{
		return xCoordinate;
	}
	public void setxCoordinate(double xCoordinate) 
	{
		this.xCoordinate = xCoordinate;
	}
	public double getyCoordinate() 
	{
		return yCoordinate;
	}
	public void setyCoordinate(double yCoordinate) 
	{
		this.yCoordinate = yCoordinate;
	}
	public Route getRoute() 
	{
		return route;
	}
	public void setRoute(Route route) 
	{
		this.route = route;
	}
	public ArrayList<transit.people.Passenger> getPassengers() 
	{
		return Passengers;
	}
	public void setPassengers(ArrayList<transit.people.Passenger> passengers) 
	{
		Passengers = passengers;
	}
	public boolean isStopped() 
	{
		return isStopped;
	}
	public void setStopped(boolean isStopped) 
	{
		this.isStopped = isStopped;
	}
	public boolean isInBound() 
	{
		return isInBound;
	}
	public void setInBound(boolean isInBound) 
	{
		this.isInBound = isInBound;
	}
	
	public abstract double move(int minutes);
	public abstract int getCapacity();
	
	public void thankTheDriver()
	{
		System.out.println("Thank you Driver "+driverName);
	}
	public int letPassengersOff()
	{
		ArrayList<Passenger> toBeRemoved = new ArrayList<Passenger>();
		if(Passengers.size()<=0)
		{
			return 0;
		}
		for(Passenger p :Passengers)
		{ 
			if(p.getDestination().equals(currentDestination))
			{
				toBeRemoved.add(p);
				currentDestination.getPassengersArrived().add(p);
			}
		
		}
		Passengers.removeAll(toBeRemoved);
		return toBeRemoved.size() ;
	}
	public int letPassengersOn()
	{
		int cap = getCapacity();
		int index =0;
		while(index<cap&&currentDestination.passengersWaiting.size()>0)
		{
			Passenger  temp = currentDestination.getPassengersWaiting().get(0);
			Passengers.add(temp);
			currentDestination.getPassengersWaiting().remove(temp);
			index++;
		}
		return index;
	}
	
}
