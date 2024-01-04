package transit_manager;

public class BusRoute
{
	private int routeNumber;
	private String routeDescription;
	public BusStop firstStop;
	public BusStop lastStop;
	public BusRoute(int rN,String rD,BusStop f, BusStop l)
	{
		routeNumber = rN;
		routeDescription = rD;
		firstStop = f;
		lastStop = l;
	}
	public int getRouteNumber()
	{
		return routeNumber;
	}
	public String getRouteDescription()
	{
		return routeDescription;
	}
	public BusStop getFirstStop()
	{
		return firstStop;
	}
	public BusStop getLastStop()
	{
		return lastStop;
	}
	public void setRouteNumber(int rN)
	{
		if(rN>0)
		{
			routeNumber = rN;
		}
	}
	public void setRouteDescription(String s)
	{
		routeDescription = s;
	}
	public void setFirstStop(BusStop b)
	{
		firstStop = b;
	}
	public void setLastStop(BusStop b)
	{
		lastStop = b;
	}
	
	public double calculateDistance()
	{
		double x = 0;
		x=firstStop.distance(lastStop);
		return x;
	}
	public String toString()
	{
		String result ="Bus Route Information: \n"+
						"Route Number: "+routeNumber+"\n"+
						"Route Description: "+routeDescription+"\n"+
						"First Stop: \n"+firstStop+"\n"+
						"Last Stop: \n"+lastStop		;
		return result;
	}
}
