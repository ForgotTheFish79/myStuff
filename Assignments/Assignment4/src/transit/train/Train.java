package transit.train;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public class Train extends Vehicle
{
	private int cars;
	public Train(String driver, double sp, Route rt) 
	{
		super(driver, sp, rt);
		// TODO Auto-generated constructor stub
	}
	
	public Train(String driver, double sp, int cars, MetroRoute rt, MetroStation stop, boolean isInbound)
	{
		super(driver, sp,  rt, stop,  isInbound);
		this.cars = cars;
	}
	public Train(String driver, double sp, int cars, MetroRoute rt, MetroStation stop)
	{
		super(driver, sp,  rt, stop);
		this.cars = cars;
	}
	public Train(String driver, double sp, int cars, MetroRoute rt)
	{
		super(driver, sp,  rt);
		this.cars = cars;
	}
	
	public int getCars() 
	{
		return cars;
	}

	public void setCars(int cars) 
	{
		this.cars = cars;
	}
	@Override
	public void thankTheDriver()
	{
		System.out.println("Thank you Conductor "+driverName);
	}
	@Override
	public double move(int minutes) 
	{
		 double travelled = minutes*speed/60;
		
		 if(currentDestination.nextStop == null&&isInBound)
			 isInBound=  false;		 
		 else if(currentDestination.prevStop ==null&&!isInBound)
			 isInBound = true;
		 if(isInBound)
		 {
			 
			 if(isStopped)
			 {
				 this.letPassengersOn();
				//s this.letPassengersOff();//+" got off");
				 isStopped = false;
				 currentDestination = currentDestination.nextStop;
				 
			 }
			 double x0 = xCoordinate;
			 double y0 = yCoordinate;
			 double x1= currentDestination.getxCoordinate();
			 double y1= currentDestination.getyCoordinate();
			 double distance = Math.sqrt(Math.pow((x1-x0), 2)+Math.pow((y1-y0), 2));
			 double ratio = travelled/distance;
			 if(travelled>=distance)
			 {
				// currentDestination = currentDestination.nextStop;
				 xCoordinate = x1;
				 yCoordinate = y1;
				 isStopped = true;
				
				 this.letPassengersOff();
				 return distance;
			 }
			 else 
			 {
					 xCoordinate = ((1-ratio)*x0+ratio*x1);
					 yCoordinate = ((1-ratio)*y0+ratio*y1);
				 
			 }
			 return travelled;
			
		 }
		 else
		 {
			 
			 if(isStopped)
			 {
				 this.letPassengersOn();
				 
				 isStopped = false;
				 
				 //currentDestination = currentDestination.prevStop;
			 }
			 double x0 = xCoordinate;
			 double y0 = yCoordinate;
			 double x1= currentDestination.getxCoordinate();
			 double y1= currentDestination.getyCoordinate();
			 double distance = Math.sqrt(Math.pow((x0-x1), 2)+Math.pow((y0-y1), 2));
			 double ratio = travelled/distance;
			 if(travelled>=distance)
			 {
				// currentDestination = currentDestination.nextStop;
				 xCoordinate = x1;//currentDestination.getxCoordinate();
				 yCoordinate = y1;//currentDestination.getyCoordinate();
				 this.letPassengersOff();
				 isStopped = true;
				 currentDestination = currentDestination.prevStop;
				 return distance;
			 }
			 else
			 {
 
					 xCoordinate = (1-ratio)*x0+ratio*x1;
					 yCoordinate = (1-ratio)*y0+ratio*y1;
			 }
			 return travelled;
			
		 }
		 
		
	

	}
	@Override
	public int getCapacity() 
	{
		// TODO Auto-generated method stub
		return cars*120-Passengers.size();
	}
	//1547912110 (Big Jim) traveling outbound/inbound on route #1
		//	Moving towards North Hills Station
		//	at location (-0.21231881669720307, 0.891739030128253)
		//	5 seats taken out of 360.
		//	PASSENGER SUMMARY:
		//	1 person on their way to Central Station
		//	1 person on their way to Allegheny Station
		//	2 people on their way to Homestead Station
		//	1 person on their way to South Side Station
	@Override
	public String toString()
	{
		String code = "inbound";
		String code2 = "is Stopped";
		Stop choiceStop = currentDestination;//.prevStop;
		Stop curStop = null;
//		if(currentDestination.nextStop!=null)
//			choiceStop = currentDestination.nextStop;
		if(!isInBound)
			code = "outbound";
		else
			code = "inbound";
		if(!isStopped)
			code = "is not stopped";
		
		return identifier+"("+driverName+") traveling "+code+" on route #"+route.getRouteNumber()+"\n moving towards "+choiceStop.getStopName()+" at location "+xCoordinate+","+yCoordinate
				+"\n"+Passengers.size()+" seats taken out of "+getCars()*120+ ". The train is currently "+code2;
	}
}
