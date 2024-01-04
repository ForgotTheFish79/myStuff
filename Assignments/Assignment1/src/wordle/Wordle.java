package wordle;
import java.util.Scanner;
public class Wordle 
{
	public static void main(String [] args)
	{
		//System.out.println(playRound("SALSA", new Scanner(System.in)));
		Scanner kb = new Scanner(System.in);
		
		playGame("SWORD",  kb);
		//System.out.println("Welcome to Wordle! You have 6 guesses.");
		
	}
	public static boolean containsChar(String s, char c )
	{
		String temp = ""+c;
		if (s.contains(temp))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public static boolean isValidInput(String s)
	{
		for(int i=0;i<s.length();i++)
		{
			char a = s.charAt(i);
			if(!(a >= 'a' && a <= 'z') && !(a >= 'A' && a <= 'Z'))
			{
				return false;
			}
		}
		if(s.length()!=5)
		{
			return false;
		}
		else
		{
			
			return true;
		}
	}
	
	public static boolean isCharCorrect(String s,char c, int i)
	{
		if(s.toLowerCase().charAt(i)==c|| s.toUpperCase().charAt(i)==c)
		{
			return true;
		}
		return false;
	}

	public static String removeWhitespace(String s) 
	{
		
		//if(s.contains("\t")||s.contains(" ")||s.contains("\n"))
		//{
			s=s.replaceAll(" ", "");
			s=s.replaceAll("\t","");
			s=s.replaceAll("\n","");
			//System.out.println(s);
		//}
		return s;
	}

	public static String generateRoundFeedback(String t, String s)
	{
		s = s.toUpperCase();
		String result = "";
		if(!isValidInput(s))
		{
			return null;
		}
		for(int i = 0; i<5;i++)
		{
			if(isCharCorrect(s,t.charAt(i),i))
			{
				result+=t.charAt(i);
			}
			else if (containsChar(t,s.charAt(i)))
			{
				result+="*";
			}
			else
			{
				result+="_";
			}

		}
		return result;
	}
	
	public static boolean playRound(String t, Scanner kb)
	{
		
		System.out.println("Enter your guess");
		String guess = kb.nextLine();
		guess = removeWhitespace(guess);
		guess = guess.toUpperCase();
		System.out.println(generateRoundFeedback(t,guess));
		return guess.equals(t);
	}

	public static void playGame(String t, Scanner kb)
	{
		System.out.println("Welceome to Wordle! \n you have 6 tries to guess the word");
		int tries = 5;
		while(tries>=0) 
		{
			if(playRound(t,kb))
			{
				System.out.println("YOU WIN!");
				break;
			}
			else
			{
			
			System.out.println("you have "+tries+" guesses left");
			tries--;
			}
		}
		if(tries<0)
		{
			System.out.println("YOU LOSE");
		}
		
	}





}