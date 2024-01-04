package transit_manager;

public class Bus 
{
	private String busIdentifier;
	private String driverName;
	private int capacity=50;
	private int passengers;
	private double speed;
	private BusRoute route;
	public BusStop currentStop;
	public Bus(String bI,String dN  ,double s, BusRoute r)
	{
		busIdentifier= bI;
		driverName = dN;
		
		if(s>0)
		{
			speed = s;
		}
		else
		{
			speed = 0;
		}
		route = r;
		currentStop =route.getFirstStop();
	}
	public Bus(String bI,String dN, int c,  double s, BusRoute r)
	{
		busIdentifier= bI;
		driverName = dN;
		if (c>=0)
		{
			capacity = c;
		}
		else
		{
			capacity = 0;
		}
		
		
		if(s>0)
		{
			speed = s;
		}
		else
		{
			speed = 0;
		}
		route = r;
		currentStop =route.getFirstStop();
	}
	public String getBusIdentifier()
	{
		return busIdentifier;
	}
	public String getDriverName()
	{
		return driverName;
	}
	public int getCapacity()
	{
		return capacity;
	}
	public int getPassengers()
	{
		return passengers;
	}
	public double getSpeed()
	{
		return speed;
	}
	public BusRoute getRoute()
	{
		return route;
	}
	public BusStop getCurrentStop()
	{
		return currentStop;
	}
	
	public void setBusIdentifier(String s)
	{
		busIdentifier = s;
	}
	
	
	public void setDriverName(String n)
	{
		driverName = n;
	}
	public void setCapacity(int c)
	{
		if (c>=0)
		{
			capacity = c;
		}
	}
	public void setPassengers(int p)
	{
		if (p>=0)
		{
			passengers = p;
		}
	}
	public void setSpeed(double s)
	{
		if (s>=0)
		{
			speed = s;
		}
	}
	public void setRoute(BusRoute b)
	{
		route = b;
	}
	public void setCurrentStop(BusStop b)
	{
		currentStop = b;
	}
	
	public void thankTheDriver()
	{
		System.out.println("Thank you Driver "+driverName+" for your hard work!");
	}
	public int letPassengersOff()
	{
		int x = passengers;
		passengers = 0;
		return x;
	}
	public int letPassengersOn()
	{
		//int x = capacity;
		int y = 0;
		if(currentStop.getPassengersWaiting()>capacity)
		{
			y=capacity;
			currentStop.setPassengersWaiting(currentStop.getPassengersWaiting()-capacity);
			passengers+=currentStop.getPassengersWaiting()-capacity;
			//System.out.println(currentStop.getPassengersWaiting()+"left");
			capacity =0;
			return y;
		}
		else
		{
			y = currentStop.getPassengersWaiting();
			passengers+=y;
			currentStop.setPassengersWaiting(0);
		}
		
		
		
		return y;
	}
	public double moveToNextStop()
	{
		double miles = route.calculateDistance();
		double time = miles/speed*60;
		if(getCurrentStop()==route.getLastStop())
		{
			setCurrentStop(route.getFirstStop());
		}
		else
		{
			setCurrentStop(route.getLastStop());
		}
		
		return time;
	}
	public String toString()
	{
		String result ="Bus Information: \n"+
				"Bus Identifier: "+busIdentifier+"\n"+
				"Driver Name: "+driverName+"\n"+
				"Capacity: "+capacity+"\n"+
				"passenger: "+passengers+"\n"+
				"Speed: "+speed+"\n"+
				"Route: "+route+"\n"+
				"Current Stop: "+currentStop;
		return result;
	}
}
