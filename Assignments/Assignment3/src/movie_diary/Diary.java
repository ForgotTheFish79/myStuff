package movie_diary;

import java.util.*;
import java.io.*;
import java.time.*;

public class Diary 
{
	
	public ArrayList<DiaryEntry> logs =new ArrayList<DiaryEntry>();
	private String fileName;
	private Library lib;
	public Diary(String fN,Library l)
	{
		//System.out.println(fN);
		lib = l;
		fileName = fN;
		try
		{
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			try
			{
			
				String line = br.readLine();
				//System.out.println();
				while ((line = br.readLine()) != null)
				{
					//System.out.println(line);
					String[] d;
					String [] t = new String [4];
					 t = line.split(","); 
//					 for(String e :t)
//					 {
//						 System.out.println(e);
//					 }
//					
					try 
					{
						
						 d = t[3].split("/");
						 //System.out.println(d[1]);
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						d = null;
					}
					try
					{ 
						double rating = -1;
						try
						{	
							 rating=Double.parseDouble(t[2]);
						}
						catch(NumberFormatException e)
						{
							rating = -1;
						}
						
						DiaryEntry temp;
						Film tempFilm = new Film(t[0],Integer.parseInt(t[1]),0);
						
						//System.out.println(tempFilm);
						if(d==null)
						{
							 temp = new DiaryEntry(tempFilm,rating);
						}
						else
						{
							 temp = new DiaryEntry(tempFilm,rating,LocalDate.of(Integer.parseInt(d[2]),Integer.parseInt(d[0]),Integer.parseInt(d[1])));
						}
						try
						{
							boolean logTF = false;
							Film compFilm = l.getFilm(tempFilm.getfilmName(),tempFilm.getYear());
							if(compFilm!=null)
							{
								tempFilm.setDirector(compFilm.getDirector());
								tempFilm.setRunTime(compFilm.getRuntime());
								for(int i =0;i<compFilm.getActors().length;i++)
								{
									tempFilm.addActor(compFilm.getActors()[i]);
								}
								
								temp.setFilm(tempFilm);
								logs.add(temp);
								
							}
							else
							{
								//System.out.println(logTF);
								//throw new IllegalStateException();
							}
						}
						catch( IllegalStateException e)
						{
							System.out.println("Film not in library");
						}
						
					}
					catch(IllegalStateException e)
					{
						System.out.println("false");
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
	public void logFilm(Film f)
	{
		DiaryEntry d = new DiaryEntry(f);
		logs.add(d);
		try 
		{
			FileWriter fw = new FileWriter(fileName,true);			
			BufferedWriter bw = new BufferedWriter(fw);
			//System.out.println(lib.getFilm(f.getfilmName(),f.getYear())==null);
//			if(lib.getFilm(f.getfilmName(),f.getYear())==null)
//			{
//				bw.close();
//				fw.close();
//				throw new  IOException();
//			}
			bw.append(d.toCSV());
			bw.close();
			fw.close();
			
			} 
			catch (IOException e) 
			{
				System.out.println("film does not exist in the library");
			}
		
	}
	public void logFilm(Film f,double r, LocalDate l)
	{
		DiaryEntry d = new DiaryEntry(f,r,l);
		logs.add(d);
		try 
		{
			FileWriter fw = new FileWriter(fileName,true);
			BufferedWriter bw = new BufferedWriter(fw);
			
//			if(lib.getFilm(f.getfilmName(),f.getYear())==null)
//			{
//				bw.close();
//				fw.close();
//				throw new  IOException() ;
//			}
			bw.append(d.toCSV());
			bw.close();
			fw.close();
			
		} 
		catch (IOException e) 
			{
				System.out.println("File does not exist");
			}
		
	}
	public void logFilm(Film f, LocalDate l)
	{
		DiaryEntry d = new DiaryEntry(f,l);
		logs.add(d);
		try 
		{
			FileWriter fw = new FileWriter(fileName,true);	
			BufferedWriter bw = new BufferedWriter(fw);
//			if(lib.getFilm(f.getfilmName(),f.getYear())==null)
//			{
//				bw.close();
//				fw.close();
//				throw new  IOException() ;
//			}
			bw.append(d.toCSV());
			bw.close();
			fw.close();
			
		} 
		catch (IOException e) 
			{
				System.out.println("File does not exist");
			}
		
	}
	public void logFilm(Film f,double r)
	{
		DiaryEntry d = new DiaryEntry(f,r);
		logs.add(d);
		try 
		{
			FileWriter fw = new FileWriter(fileName,true);
			BufferedWriter bw = new BufferedWriter(fw);
//			if(lib.getFilm(f.getfilmName(),f.getYear())==null)
//			{
//				bw.close();
//				fw.close();
//				throw new  IOException() ;
//			}
			bw.append(d.toCSV());
			bw.close();
			fw.close();
			
		} 
			catch (IOException e) 
			{
				System.out.println("Film does not exist");
			}
		
	}
	public DiaryEntry[] getLogsByYear(int year)
	{
		ArrayList<DiaryEntry> cache = new ArrayList<DiaryEntry>();
		for(DiaryEntry d : logs)
		{
			int y =d.getDate().getYear();
			
			if(y==year)
			{
				
				cache.add(d);
			}
			
			
		}
		DiaryEntry[] result = new DiaryEntry[cache.size()];
		for(int i =0;i<cache.size();i++)
		{
			result[i]=cache.get(i);
		}
		return result;
	}
	public String[] getMostLoggedDirectors()
	{
		ArrayList<String> uniqueDirector = new ArrayList<String>();
		for(DiaryEntry d : logs)
		{
			if(uniqueDirector.contains(d.getFilm().getDirector()))
			{
				continue;
			}
			else
			{
				uniqueDirector.add(d.getFilm().getDirector());
			}
			
		}
		//DiaryTester.aPrint(uniqueDirector);
		int[] occurances = new int[uniqueDirector.size()];
		for(DiaryEntry d : logs)
		{
			int index =uniqueDirector.indexOf(d.getFilm().getDirector());
			occurances[index]++;
		}
//		
		String[] result;
//	
		if(uniqueDirector.size()>10)
		{
			result = new String[10];
			int i = 0;
			while(i<10)
			{
				int max = Integer.MIN_VALUE;
				int index = -1;
				for(int j=0;j<occurances.length;j++)
				{
					if(occurances[j]>max)
					{
						max = occurances[j];
						index = j;
					}
				}
				
				result[i]=uniqueDirector.get(index)+":"+occurances[index];
				occurances[index]=-1;
				i++;
			}
		}
		else
		{
			result = new String [uniqueDirector.size()];
			int i = 0;
			while(i<uniqueDirector.size())
			{
				int max = Integer.MIN_VALUE;
				int index = -1;
				for(int j=0;j<occurances.length;j++)
				{
					if(occurances[j]>max)
					{
						max = occurances[j];
						index = j;
					}
				}
				
				result[i]=uniqueDirector.get(index)+":"+occurances[index];
				occurances[index]=-1;
				i++;
			}
		}
		return result;
	}
	public String getMostLoggedDirector(DiaryEntry[] logYear)
	{
		ArrayList<String> uniqueDirector = new ArrayList<String>();
		for(DiaryEntry d : logYear)
		{
			if(uniqueDirector.contains(d.getFilm().getDirector()))
			{
				continue;
			}
			else
			{
				uniqueDirector.add(d.getFilm().getDirector());
			}
			
		}
		//DiaryTester.aPrint(uniqueDirector);
		int[] occurances = new int[uniqueDirector.size()];
		for(DiaryEntry d : logYear)
		{
			int index =uniqueDirector.indexOf(d.getFilm().getDirector());
			occurances[index]++;
		}
		
		String result="";
//		for(int i = 0;i<occurances.length;i++)
//		{
//			System.out.println(occurances[i]);
//		}
	
			
			int i = 0;
			
				int max = Integer.MIN_VALUE;
				int index = 0;
				for(int j=0;j<occurances.length;j++)
				{
					if(occurances[j]>max)
					{
						max = occurances[j];
						index = j;
					}
				}
				
				result=uniqueDirector.get(index);//+":"+occurances[index];
				occurances[index]=-1;
				i++;
			
		
		
		return result;
	}
	public double getAverageRating(String actorName)
	{
		
		double totalRating = 0;
		int amt = 0;
		for(int i = 0;i<logs.size();i++)
		{
			//iterate through logs WHICH IS IN Diary, each iteration has an element i.
			for(int j=0;j<logs.get(i).getFilm().actors.length;j++)
			{
				//iterate through the Actors[] in element i
//				for(int k =0;k<10;k++)
//				{
					//System.out.println(logs.get(i).getFilm().getActors()[j]);
				
				//System.out.print(logs.get(i).getFilm().getActors()[j].equals(actorName));
				
				
				if(logs.get(i).getFilm().actors[j].contains(actorName))
				{
					System.out.println(logs.get(i).getFilm().getfilmName());
					amt++;
					
					totalRating+=logs.get(i).getRating();
				}
			}
			
		}
		System.out.println(actorName+" "+amt);
		return totalRating/amt;
	}
	public String []getYearEndFavorites(int y)
	{
		DiaryEntry []logYear = getLogsByYear(y);
		HashMap<String,Integer>uniqueActor =  new HashMap<String,Integer>();
		//ArrayList<String> uniqueActor = new ArrayList();
	
		for(DiaryEntry d : logYear)
		{
			String [] aList= d.getFilm().actors;
			for(int i = 0;i<aList.length;i++)
			{
				if(uniqueActor.containsKey(aList[i]))
				{
					int newNum = uniqueActor.get(aList[i])+1;
					uniqueActor.put(aList[i], newNum);
				}
				else
				{
					uniqueActor.put(aList[i], 1);
				}
//				if(uniqueActors.contains(d.getFilm().actors[i]))
//				{
//					continue;
//				}
//				else
//				{
//					uniqueActors.add(d.getFilm().actors[i]);
//				}
			}
			
		}
		//System.out.println(uniqueActor.size());
		//System.out.println(uniqueActors.size());
		//DiaryTester.aPrint(uniqueDirector);
//		int[] occurances = new int[uniqueActor.size()];
//		for(DiaryEntry d : logYear)
//		{
//			for(int i = 0;i<d.getFilm().actors.length;i++)
//			{
//				int index =uniqueActor.indexOf(d.getFilm().actors[i]);
//				occurances[index]++;
//			}
//		}
		int max = Integer.MIN_VALUE;
		String mark="";
		for (String key : uniqueActor.keySet()) 
		{
			//System.out.println(key);
		    int freq = uniqueActor.get(key);
		  
		    //System.out.println(freq);
		    if (freq>max)
		    {
		    	
		    	max = freq;
		    	mark = key;
		    }
		}
		//System.out.println(max);
//		int index = -1;
//		for(int j=0;j<occurances.length;j++)
//		{
//			if(occurances[j]>max)
//			{
//				max = occurances[j];
//				index = j;
//			}
//		}
		
		//System.out.println(logYear.length);
		String mostActor = mark;
		String mostDirector=getMostLoggedDirector(logYear);
		return  new String[] {mostActor,mostDirector};
		
	}
	public String[] getRecentLogs()
	{
		int length = logs.size();
		String [] result = new String [5];
		for(int i = 0;i<result.length;i++)
		{
			result[4-i]=logs.get(length-5+i).toString();
		}
		return result;
	}
}
