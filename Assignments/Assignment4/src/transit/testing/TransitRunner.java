package transit.testing;

import transit.train.*;

import java.util.concurrent.TimeUnit;

import transit.bus.*;
import transit.people.Passenger;

public class TransitRunner
{
    
    public static MetroRoute instantiateMetroSystem()
    {
	//Create one metro route
	MetroRoute mr = new MetroRoute(1, "A short route that serves downtown going, roughtly, north to south.", new MetroStation("Central Station", 0, 0));
	
	// Add a bunch of stops
	mr.addStop("North Hills Station", -1, 4.2);
	mr.addStop("Allegheny Station", -1.4, 1.2);
	mr.addStop("Central Station", 0, 0);
	mr.addStop("South Side Station", 1.5, -1.2);
	mr.addStop("Homestead Station", 4.5, -2.8);
	
	//Add one train to the route
	mr.addDriver("Big Jim", 55);
	//mr.addDriver("Small Jim", 52);
	
	return mr;
    }
    
    public static BusRoute instantiateBusSystem()
    {
	//Create a single bus route
	BusRoute br = new BusRoute(61, "An east-west bus route connecting downtown to the eastern suburbs.", new BusStop("Downtown", 0, 0));
	
	//Add a bunch of stops
	br.addStop("Duquesne", 0.6, -0.1);
	br.addStop("West Oakland", 1.6, 0);
	br.addStop("Central Oakland", 2.3, 0.3);
	br.addStop("Bellefield", 2.8, 0.5);
	br.addStop("CMU", 3.1, 0.5);
	br.addStop("Squirrel Hill", 4.1, 0);
	br.addStop("Regent Square", 5.5, 0.1);
	br.addStop("Swissvale", 6.0, -1.1);
	
	// Add one bus
	br.addDriver("Little Ted", 30);
	
	return br;
    }

    public static void main(String[] args) throws InterruptedException
    {
    	
	MetroRoute mr = instantiateMetroSystem();
	BusRoute br = instantiateBusSystem();
//	mr.gainPassengersAll();
//	System.out.println(mr);
//	mr.moveAll(20);
	//System.out.println(mr.getVehicles());
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	br.gainPassengersAll();
//	System.out.println(br);
//	System.out.println(br.firstStop.losePassengers(1));
//	//br.getVehicles().get(0).letPassengersOn();
//	System.out.println(br);

	//System.out.println(mr);
	
	int ticks = 0;
	while(ticks < 0)
	{
	    if(ticks < 10)
		System.out.println("12:0" + ticks + " pm");
	    else
		System.out.println("12:" + ticks + " pm");
	    System.out.println("=======================================");
		
	    if(ticks % 10 == 0)
	    {
		mr.gainPassengersAll();
	    }
	    mr.moveAll(1);
	    System.out.println(mr);
	    System.out.println("=======================================");
	   // TimeUnit.SECONDS.sleep(1);
	    ticks++;
	}
	
	System.out.println("**************************************************************");
	System.out.println("STARTING BUS SIMULATION! *************************************");
	System.out.println("**************************************************************");
	
	ticks = 0;
	while(ticks < 20)
	{
	    if(ticks < 10)
		System.out.println("1:0" + ticks + " pm");
	    else
		System.out.println("1:" + ticks + " pm");
	    System.out.println("=======================================");
	    
	    if(ticks % 10 == 0)
	    {
		br.gainPassengersAll();
	    }
	    br.moveAll(1);
	    System.out.println(br.getVehicles());
	    System.out.println("=======================================");
	    
	    ticks++;
	}
    }

}
