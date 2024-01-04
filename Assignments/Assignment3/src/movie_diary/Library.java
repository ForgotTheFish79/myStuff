package movie_diary;
import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Library 
{
	
	private ArrayList<Film> films = new ArrayList<Film>();
	public ArrayList<Film> getFilms()
	{
		return films;
	}
	
	public Library(String fileName)
	{
	
		try
		{
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			try
			{
			
				String line =br.readLine();
				while ((line = br.readLine()) != null)
				{
	
					String[] text = line.split(","); 
					
					String name = text[0];
					int year = Integer.parseInt(text[1]);
					String director = text[2];
					int runtime = Integer.parseInt(text[3]);
					String actorsAll="";//= ;
					
					for(int i = 4;i<text.length;i++)
					{
						actorsAll= actorsAll+text[i]+",";
					}
					
					actorsAll = actorsAll.replaceAll("\\[","");
					actorsAll = actorsAll.replaceAll("\\]","");
					actorsAll = actorsAll.replaceAll("\"", "");
					actorsAll = actorsAll.replaceAll("\'", "");
				
					
					String[] actorList2 = actorsAll.split(",");
			
	
					try
					{ 
						
						Film temp = new Film(name,year,runtime,director);
						temp.actors=actorList2;
						
						if(temp.getRuntime()>=0&& temp.getYear()>1880)
						{
							films.add(temp);
						}
					}
					catch(IllegalStateException e)
					{
						System.out.println("film has at least one error");
					}
					
				}
				br.close();
				fr.close();
				}
			catch(IOException e)
			{
				System.out.println("Line doesnt not exist");
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		
	}
	public Film getFilm(String name,int year)
	{
		for(Film current:films)
		{
		
			if(current.getfilmName().equals(name) &&current.getYear()==year)
			{
				return current;
			}
		}
		
		return null;
	}
}
