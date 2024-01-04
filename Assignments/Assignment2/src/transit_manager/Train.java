package transit_manager;

public class Train 
{
	private String trainIdentifier;
	private String conductorName;
	private int cars=10;
	private int passengers;
	private double speed;
	private MetroRoute route;
	public MetroStation currentStation;
	public Train(String tI,String cN, double s,int c,  MetroRoute r)
	{
		trainIdentifier= tI;
		conductorName = cN;
		if (c>=0)
		{
			cars = c;
		}
		else
		{
			cars = 1;
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
		currentStation = r.getFirstStop();
	}
	public Train(String tI,String cN, int c, int p, double s, MetroRoute r,MetroStation cS)
	{
		trainIdentifier= tI;
		conductorName = cN;
		if (c>=0)
		{
			cars = c;
		}
		else
		{
			cars = 1;
		}
		
		if(p>0)
		{
		passengers = p;
		}
		else
		{
			passengers = 0;
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
		currentStation = cS;
	}
	public String getTrainIdentifier()
	{
		return trainIdentifier;
	}
	public String getConductorName()
	{
		return conductorName;
	}
	public int getCars()
	{
		return cars;
	}
	public int getPassengers()
	{
		return passengers;
	}
	public double getSpeed()
	{
		return speed;
	}
	public MetroRoute getRoute()
	{
		return route;
	}
	public MetroStation getCurrentStop()
	{
		return currentStation;
	}
	
	public void setTrainIdentifier(String s)
	{
		trainIdentifier = s;
	}
	
	
	public void setDriverName(String n)
	{
		conductorName = n;
	}
	public void setCar(int c)
	{
		if (c>=0)
		{
			cars = c;
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
	public void setRoute(MetroRoute m)
	{
		route = m;
	}
	public void setCurrentStop(MetroStation m)
	{
		currentStation = m;
	}
	
	public void thankTheConductor()
	{
		System.out.println("Thank you Conductor "+conductorName+" for your hard work!");
	}
	public int letPassengersOff()
	{
		int x = passengers;
		passengers = 0;
		return x;
	}
	public int calculateCapacity()
	{
		return cars*120;
	}
	public int letPassengersOn()
	{
		//int x = capacity;
		int y = calculateCapacity();
		//System.out.println(y);
		if(currentStation.getPassengersWaiting()>calculateCapacity())
		{
			
			currentStation.setPassengersWaiting(currentStation.getPassengersWaiting()-y);
			passengers+=currentStation.getPassengersWaiting()-y;
			setPassengers(y);
			//System.out.println(currentStop.getPassengersWaiting()+"left");
			 
			return y;
		}
		else
		{
			setPassengers( currentStation.getPassengersWaiting());
			
			passengers = currentStation.getPassengersWaiting();
			currentStation.setPassengersWaiting(0);
			
		}
				
				return y;	
	}
	public double moveToNextStation()
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
				"Bus Identifier: "+trainIdentifier+"\n"+
				"Driver Name: "+conductorName+"\n"+
				"Number of Cars: "+cars+"\n"+
				"passenger: "+passengers+"\n"+
				"Speed: "+speed+"\n"+
				"Route: "+route+"\n"+
				"Current Stop: "+currentStation;
		return result;
	}
}
