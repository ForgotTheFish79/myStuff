package transit_manager;

public class MetroRoute 
{
	private int routeNumber;
	private String routeDescription;
	public MetroStation firstStop;
	public MetroStation lastStop;
	
	
	public MetroRoute(int rN,String rD,MetroStation f, MetroStation l)
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
	public MetroStation getFirstStop()
	{
		return firstStop;
	}
	public MetroStation getLastStop()
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
	public void setFirstStop(MetroStation b)
	{
		firstStop = b;
	}
	public void setLastStop(MetroStation b)
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
