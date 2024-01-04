package transit_manager;

public class BusStop 
{
	private String stopName;
	private int stopNumber;
	private double xCoordinate;
	private double yCoordinate;
	private int passengersWaiting=0;
	
	public BusStop(String n, int sN,double x, double y)
	{
		stopName = n;
		stopNumber = sN;
		xCoordinate = x;
		yCoordinate = y;
	
	}
	public String getStopName()
	{
		return stopName;
	}
	public int getStopNumber()
	{
		return stopNumber;
	}
	public double getXCoord()
	{
		return xCoordinate;
	}
	public double getYCoord()
	{
		return yCoordinate;
	}
	public int getPassengersWaiting()
	{
		return passengersWaiting;
	}
	
	public void setStopName(String s)
	{
		stopName = s;
	}
	public void setStopNumber(int n)
	{
		stopNumber = n;
	}
	public void setXCoord(int x)
	{
		xCoordinate = x;
	}
	public void setYCoord(int y)
	{
		yCoordinate = y;
	}
	public void setPassengersWaiting(int p)
	{
		if(p>=0)
		{
			passengersWaiting = p;
		}
	}
	
	public void gainPassengers()
	{
		int nP = 5 + (int)(Math.random() * 26);
		passengersWaiting+=nP;
		
	}
	
	public boolean losePassengers(int nP)
	{
		if (passengersWaiting-nP<0)
		{
			return false;
		}
		else 
		{
			passengersWaiting-=nP;
			return true;
		}
	}
	public double distance(BusStop x)
	{
		double dX = this.getXCoord()-x.getXCoord();
		double dY =this.getYCoord()-x.getYCoord();
		return Math.sqrt(Math.pow(dX,2)+Math.pow(dY, 2));
	}
	public String toString()
	{
		String result ="Stop Name: "+stopName+"\n"+
						"Stop Number: "+stopNumber+"\n"+
						"X Coordinate: "+xCoordinate+"\n"+
						"Y Coordinate: "+yCoordinate+"\n"+
						"Number of Passengers Waiting: "+passengersWaiting		;
		return result;
	}
}
