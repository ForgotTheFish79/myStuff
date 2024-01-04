package transit.core;

import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public abstract class Stop 
{
	
	protected String stopName;
	protected int stopNumber;
	protected double xCoordinate,yCoordinate;
	protected ArrayList<Passenger> passengersWaiting= new ArrayList<Passenger>();
	protected ArrayList<Passenger> passengersArrived = new ArrayList<Passenger>();
	public Stop nextStop,prevStop = null;
	public Stop(String n,double x,double y)
	{
		stopName = n;
		stopNumber = n.hashCode();
		xCoordinate = x;
		yCoordinate = y;
	}
	public String getStopName() 
	{
		return stopName;
	}
	public void setStopName(String stopName) 
	{
		this.stopName = stopName;
	}
	public int getStopNumber() 
	{
		return stopNumber;
	}
	public void setStopNumber(int stopNumber) 
	{
		this.stopNumber = stopNumber;
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
	public ArrayList<transit.people.Passenger> getPassengersWaiting() 
	{
		return passengersWaiting;
	}
	public void setPassengersWaiting(ArrayList<transit.people.Passenger> passengersWaiting) 
	{
		this.passengersWaiting = passengersWaiting;
	}
	public ArrayList<transit.people.Passenger> getPassengersArrived() 
	{
		return passengersArrived;
	}
	public void setPassengersArrived(ArrayList<transit.people.Passenger> passengersArrived) 
	{
		this.passengersArrived = passengersArrived;
	}
	public ArrayList<Passenger> losePassengers(int n)
	{
		ArrayList<Passenger> left = new ArrayList<Passenger>();
		if(getPassengersWaiting().size()>=n)
		{
			for(int i =0;i<n;i++)
			{
				left.add(getPassengersWaiting().remove(0));
			}
			//System.out.println(left);
			return left;
		}
		return null;
	}
	public Stop getRandomStop()
	{
		return null;
	}
	public String toString()
	{
		String result = "Stop " + stopNumber + ": " + stopName + "\n" + passengersWaiting.size() + " Passengers Waiting\n";

	    if (nextStop != null) 
	    {
	        result += nextStop.toString(); 
	    }
		return result;
	}
	public abstract void gainPassengers();
	
		
	
}
