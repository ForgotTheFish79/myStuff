package movie_diary;
import java.util.*;
import java.io.*;
import java.time.LocalDate;


public class Film 
{
	private String filmName;
	private int year;
	private int runtime;
	private String director;
	public String[] actors = new String[0];
	
	public Film(String n,int y,int t)
	{
		
		filmName = n;
		
		
		
		if(y<1880)
		{
			throw new IllegalStateException("Year must be after 1880");
		}
		else
		{
			year = y;
		}
		if(t<0)
		{
			throw new IllegalStateException("Runtime cannot be negative");
		}
		else
		{
			runtime = t;
		}
		director = "Alan Smithee ";
		
	}
	public Film(String n,int y,int t,String d)

	{
		
		filmName = n;
		
		if(y<1880)
		{
			throw new IllegalStateException("Year must be after 1880");
		}
		else
		{
			year = y;
		}
		if(t<0)
		{
			throw new IllegalStateException("Runtime cannot be negative");
		}
		else
		{
			runtime = t;
		}
		director = d;
	}
	public void setfilmName(String s)
	{
		filmName = s;
	}
	public void setYear(int i)
	{
		if(i>=1880)
			year = i;
	}
	public void setRunTime(int i)
	{
		if(i>0)
			runtime= i;
	}
	public void setDirector(String d)
	{
		director =d;
	}
	public String getfilmName()
	{
		return filmName;
	}
	public int getYear()
	{
		return year;
	}
	public int getRuntime()
	{
		return runtime;
	}
	public String getDirector()
	{
		return director;
	}
	public String[] getActors()
	{
		return actors;
	}
	public void addActor(String n)
	{
		int i = 0;
		try
		{
			i = actors.length;
		}
		catch(NullPointerException e)
		{
			 i = 0;
		}
		
		String [] temp = new String[i+1];
		//System.out.println(temp.length);
		if(actors!=null)
		{
			for(int j = 0;j<actors.length;j++)
			{
				if(i!=0)
					temp[j]=actors[j];
			}
		}
		else
		{
			for(int j = 0;j<temp.length;j++)
			{
				if(i!=0)
					temp[j]=actors[j];
			}
		}
		
		temp[temp.length-1]=n;
		actors=temp;
 	}
	public String toString()
	{
		String r = "";
		
			 r = filmName+" ("+year+") directed by "+director+", "+runtime+" minutes";
		
		
		return r;
	}
	public boolean equals(Film other)
	{
		if(other.getYear()==year&&other.getfilmName()==filmName)
		{
			return true;
		}
		return false;
	}
		
}
