package transit.bus;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public class Bus extends transit.core.Vehicle
{
	private int capacity=35;
	public Bus(String driver, double sp, BusRoute rt, BusStop stop, boolean isInbound)
	{
		super(driver,sp,rt,stop,isInbound);
	}
	public Bus(String driver, double sp, BusRoute rt, BusStop stop)
	{
		super(driver,sp,rt,stop);
	}
	public Bus(String driver, double sp, BusRoute rt)
	{
		super(driver,sp,rt);
	}
	public int getCapacity()
	{
		return capacity;
	}
	public void setCapacity(int c)
	{
		capacity = c;
	}
	
	@Override
	public double move(int minutes) 
	{
		double totalDis = minutes*speed/60;
	
		
	   
	
		if(currentDestination.nextStop == null&&isInBound)
			 isInBound=  false;		 
		else if(currentDestination.prevStop == null&&!isInBound)
			 isInBound = true;
		if(isStopped&&isInBound)
		{
			this.letPassengersOn();
			currentDestination = currentDestination.nextStop;
			isStopped = false;
		}
		else if(isStopped&&!isInBound)
		{
			this.letPassengersOn();
			currentDestination = currentDestination.prevStop;
			isStopped = false;
		}
		double x0 = xCoordinate;
		double y0 = yCoordinate;
		//System.out.println(x0+","+y0);
		double x1= currentDestination.getxCoordinate();
		double y1= currentDestination.getyCoordinate();
	    double disX = Math.abs(x1-x0);
	   // System.out.println(disX);
		double disY = Math.abs(y1-y0);
		//System.out.println(disY);
		//System.out.println(totalDis);
		//System.out.println(y1>yCoordinate);
		//System.out.println(y0);
		if (totalDis >= (disX + disY)) 
		{
	       //System.out.println("made it");
	        this.letPassengersOff();
	        xCoordinate = x1;
	        yCoordinate = y1;
	        isStopped = true;

	        return (disX + disY);
	    }
		else if ((totalDis>= disX))
		{
			//System.out.println("need y");
			xCoordinate = x1;
			totalDis -= disX;		
			//System.out.println(totalDis);
			if(totalDis>0)
			{
				
				if(y1>yCoordinate)
	            yCoordinate = yCoordinate + totalDis;
				else
				yCoordinate = yCoordinate - totalDis;
	           
			}
			return totalDis+disX;
		}
		else
		{
			//System.out.println("failed");
	        xCoordinate = xCoordinate + totalDis;
	        isStopped = false;
	        return totalDis;
		}
	}
	@Override
	public String toString()
	{
		String code = "inbound";
		String code2 = "is stopped";
		Stop choiceStop = currentDestination;//.prevStop;
//		if(currentDestination.nextStop!=null)
//			choiceStop = currentDestination.nextStop;
		if(!isInBound)
			code = "outbound";
		else
			code = "inbound";
		if(!isStopped)
			code2 = "is not stopped";
		return identifier+"("+driverName+") traveling "+code+" on route #"+route.getRouteNumber()+"\n moving towards "+choiceStop.getStopName()+" at location "+xCoordinate+","+yCoordinate
				+"\n"+Passengers.size()+" seats taken out of "+getCapacity()+" This bus "+code2;
	}
	
}
