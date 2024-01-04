package transit_manager;

public class MetroStation 
{
	private String stationName;
	private int stationNumber;
	private double xCoordinate;
	private double yCoordinate;
	private int passengersWaiting= 0;
	
	public MetroStation(String n, int sN,double x, double y)
	{
		stationName = n;
		stationNumber = sN;
		xCoordinate = x;
		yCoordinate = y;
		
	}
	public String getStationName()
	{
		return stationName;
	}
	public int getStationNumber()
	{
		return stationNumber;
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
	
	public void setStationName(String s)
	{
		stationName = s;
	}
	public void setStationNumber(int n)
	{
		stationNumber = n;
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
		int nP = 20 + (int)(Math.random() * 181);
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
	public double distance(MetroStation x)
	{
		double dX = this.getXCoord()-x.getXCoord();
		double dY =this.getYCoord()-x.getYCoord();
		return Math.sqrt(Math.pow(dX,2)+Math.pow(dY, 2));
	}
	public String toString()
	{
		String result ="Station Name: "+stationName+"\n"+
						"Station Number: "+stationNumber+"\n"+
						"X Coordinate: "+xCoordinate+"\n"+
						"Y Coordinate: "+yCoordinate+"\n"+
						"Number of Passengers Waiting: "+passengersWaiting		;
		return result;
	}
}
