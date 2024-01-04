package movie_diary;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class MovieDiaryRunner
{
    private static Library library;
    private static Diary diary;
    private static Scanner scan;
    
    public static void logFilm()
    {
	System.out.print("What's the name of the film? ");
	String filmName = scan.nextLine();
	System.out.print("What year did it release? ");
	int year = Integer.parseInt(scan.nextLine());
	Film toLog = library.getFilm(filmName, year);
	if(toLog == null) 
	{
	    System.out.println(filmName + " (" + year + ") could not be found in the library.");
	    return;
	}
	
	System.out.print("What rating do you give it? (0-5 stars, can be half stars; leave it blank if you'd prefer no rating): ");
	String ratingStr = scan.nextLine();
	System.out.print("When did you watch the film? (leave this blank if you want to log it for today): ");
	String dateStr = scan.nextLine();
	
	if(ratingStr == null || ratingStr.length() < 1)
	{
	    if(dateStr == null || dateStr.length() < 1)
	    {
		diary.logFilm(toLog);
	    }
	    else
	    {
		diary.logFilm(toLog, LocalDate.parse(dateStr));
	    }
	}
	else if(dateStr == null || dateStr.length() < 1)
	{
	    diary.logFilm(toLog, Double.parseDouble(ratingStr));
	}
	else
	{
	    diary.logFilm(toLog, Double.parseDouble(ratingStr), LocalDate.parse(dateStr));
	}
    }
    
    public static void viewLogs()
    {
	//Grab the 5 most recent logs and show them to the user
	String[] recentLogs = diary.getRecentLogs();
	String logs = "";
	System.out.println("Recent Logs:");
	for(String log : recentLogs)
	{
	    System.out.println("\t" + log);
	}
	
	String[] options = {"Logs By Year", "Most Logged Directors", "Average Rating Per Actor", "Year End Favorites", "Go Back"};
	System.out.println("\nWhat would you like to check? (choose a number):");
	for(int i = 0; i < options.length; i++) System.out.println(i + ": " + options[i]);
        int n = Integer.parseInt(scan.nextLine());
        
        if(n == 0)
        {
            // Logs by Year
            System.out.print("What year? ");
            int year = Integer.parseInt(scan.nextLine());
            String films = "";
            for(DiaryEntry log : diary.getLogsByYear(year)) {films += log.toString() + "\n";}
            System.out.println(films);
        }
        else if(n == 1)
        {
          //Most Logged Directors
            String directors = "";
            for(String s : diary.getMostLoggedDirectors()) {directors += s + "\n";}
            System.out.println("Most Logged Directors are:\n" + directors);
        }
        else if(n == 2)
        {
            // Average Rating Per Actor
            System.out.print("Which actor? ");
            System.out.println("Average Rating: " + diary.getAverageRating(scan.nextLine()));
        }
        else if(n == 3)
        {
            // Year End Favorites
            System.out.print("What year? ");
            int year = Integer.parseInt(scan.nextLine());
            //String[] favorites = diary.getYearEndFavorites(year);
           // System.out.println("Favorite Actor: " + favorites[0]);
           // System.out.println("Favorite Director: " + favorites[1]);
        }
        
    }
    
    public static boolean mainPrompt() 
    {
	String[] options = {"Log a film", "View Logs", "Quit"};
	System.out.println("What would you like to do? (Choose a number)");
	for(int i = 0; i < options.length; i++) System.out.println(i + ": " + options[i]);
        int n = Integer.parseInt(scan.nextLine());
        
        if(n == 0)
        {
            logFilm();
            return true;
        }
        else if(n ==1)
        {
            viewLogs();
            return true;
        }
        
        return n != 2;
    }

    public static void main(String[] args)
    {
  	Library l = new Library("library.csv");
    	Diary d = new Diary("Diary.csv",l);
    	System.out.println(d.logs.size());
//     
//    	Film f = new Film("Mad Max: Fury Road",2015,121);
//    	DiaryEntry DE = new DiaryEntry(f,5,LocalDate.now());
//    	System.out.println(d.getYearEndFavorites(2021)[0]);
//    	System.out.println(d.getYearEndFavorites(2020)[1]);
//    	Film tester =l.getFilm("Finally Got the News",1970);
//    	System.out.println(tester.actors.length);
//    	for(String s : tester.actors)
//    	{
//    		System.out.println(s);
//    	}
    	//System.out.println(DE);
    	
//		scan = new Scanner(System.in);
//		
//		System.out.print("What's the file name of your library: ");
//		String library_fn = scan.nextLine();
//		System.out.print("What's the file name of your diary: ");
//		String diary_fn = scan.nextLine();
//		
//		//Instantiate our library by reading in the library file
//		library = new Library(library_fn);
//		//Instantiate our diary by reading in the diary file
//		diary = new Diary(diary_fn, library);
//		
//		boolean continuing = true;
//		while(continuing)
//		{
//		    continuing = mainPrompt();
//		}
    }

}
