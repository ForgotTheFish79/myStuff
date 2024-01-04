package transit.people;
import java.util.ArrayList;
import transit.core.*;
import transit.bus.*;
import transit.train.*;
import transit.people.Passenger;
public class Passenger 
{
	private String name;
	private Stop destination;
	public Passenger(String n,Stop currentStop)
	{
		//set destination to some random route;
		Stop banned = currentStop;
		ArrayList<Stop> allStop = new ArrayList<Stop>();
		
		
		while(currentStop.nextStop != null)
		{
			currentStop = currentStop.nextStop;
		}
		while(currentStop!= null)
		{
			allStop.add(currentStop);
			currentStop = currentStop.prevStop;
			
		}
		//allStop.add(currentStop);
		
		allStop.remove(banned);
		int rand = ((int)(Math.random()*allStop.size()));
		if(allStop.size()>0)
		destination = allStop.get(rand);
		name =n;
	}
	public Passenger(Stop currentStop)
	{
		String [] firstName = {"Bob","John","Ryan","Chris","Billy","Joe","Jim","Brenden","Luke","Eric","Cam","Daniel","Leo","Venkatesh","Max","Sadie","Ashley","Abbie","Chloe","Olivia"};
		String [] lastName = {"Henry","Swift","Addison","Dell","Hill","Aubrey","LaPorta","Spears","Palmer","Johnson","Lee","Robertson","Hurts","Nwosu","Williams","Burrow","Allen","Kelce","St.Brown","Akers","Lamb","Smith","Murray","Andrews"};
		int f = (int)(Math.random()*firstName.length);
		int l = (int)(Math.random()*firstName.length);
		name = firstName[f]+" "+lastName[l]; 
		
		Stop banned = currentStop;
		ArrayList<Stop> allStop = new ArrayList<Stop>();
		
		
		while(currentStop.nextStop != null)
		{
			currentStop = currentStop.nextStop;
		}
		while(currentStop!= null)
		{
			allStop.add(currentStop);
			currentStop = currentStop.prevStop;
		}
		//allStop.add(currentStop);
		
		allStop.remove(banned);
		int rand = ((int)(Math.random()*allStop.size()));
		if(allStop.size()>0)
		destination = allStop.get(rand);
		
		//destination = currentStop;
		
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public Stop getDestination() 
	{
		return destination;
	}
	public void setDestination(transit.core.Stop destination) 
	{
		this.destination = destination;
	}
	public String toString()
	{
		if(getDestination()!=null)
			return name+" is going to "+getDestination().getStopName()+"\n";
		else
			return name;
	}

}