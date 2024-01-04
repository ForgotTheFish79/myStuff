package movie_diary;
import java.util.*;
import java.io.*;

import java.time.*;
import java.time.format.DateTimeFormatter;


public class DiaryEntry 
{
	private Film film;
	private double rating;
	private LocalDate date;
	public DiaryEntry(Film F)
	{
		film = F;
		rating = -1;
		date = LocalDate.now();
	}
	public DiaryEntry(Film F,double r)
	{
		date = LocalDate.now();
		film =F;
		if((r>5||r<0)&&r!=-1)
		{
			throw new IllegalStateException();
		}
		else if(r*10%5!=0)
		{
			throw new IllegalStateException();
		}
		else
		{
			rating = r;
		}
	
	}
	public DiaryEntry(Film F,LocalDate d)
	{
		date = d;
		film = F;
		rating =-1;
	}
	public DiaryEntry(Film F,double r,LocalDate d)
	{
		film =F;
		date = d;
		if((r>5||r<0)&&r!=-1)
		{
			throw new IllegalStateException();
		}
		else if(r*10%5!=0)
		{
			throw new IllegalStateException();
		}
		else
		{
			rating = r;
		}
	
	}
	
	public Film getFilm()
	{
		return film;
	}
	public double getRating()
	{
		return rating;
	}
	public LocalDate getDate()
	{
		return date;
	}
	public void setFilm(Film f)
	{
		film = f;
	}
	public void setRating(double r)
	{
		if((r>5||r<0)&&r!=-1)
		{
			throw new IllegalStateException();
		}
		else if(r*10%5!=0)
		{
			throw new IllegalStateException();
		}
		else
		{
			rating = r;
		}
	}
	
	public String toString()
	{
		String result = "";
		if(rating!=-1)	
		{
			result =film+"; rating: "+rating+", date of diary entry: "+date;
		}
		else 
		{
			result =film+", date of diary entry: "+date;
		}
		return result;
	}
	public String toCSV()
	{
		//filmName,year,rating,MM/dd/yyyy\ n
		String result;
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/uuuu");
		String time = date.format(formatters);
		if(rating!=-1)	
		{
			result =film.getfilmName()+","+film.getYear()+","+rating+","+time+"\n";
		}
		else 
		{
			result =film.getfilmName()+","+film.getYear()+","+","+time+"\n";
		}
		return result;
	}
	public boolean equals(DiaryEntry other)
	{
		if(other.getDate()==date&&other.getFilm().equals(film))
		{
			return true;
		}
		return false;
	}
	
	
	
	
}
