// CS 0445 Spring 2024
// Simple program to show all of the permutations of letters of length K,
// where K is a command line argument. In this program, all letters can be
// used an arbitrary number of times. Using discrete math, we know that for
// M different values in each of K different positions, we can have a total
// of M^K permutations.

public class Perms
{
	public static void main(String[] args)
	{
		//int length = Integer.parseInt(args[0]);  // command line argument is the
						// length of the strings.  NOTE: In this case M = 26 and
						// M^K grows VERY QUICKLY. Keep length small to enable
						// this program to run in a reasonable amount of time.
		showPerms(3);
	}
	
	public static void showPerms(int length)
	{
		System.out.println("Here are the permutations of length " + length + ":");
		StringBuilder data = new StringBuilder("");
		
		// Initially we have used 0 out of length letters
		showPermsRec(0, length, data);
	}
	
	// Since all solutions have a length of K, our base case is when the current
	// count equals K, in which case we print out the string. Otherwise, we
	// iterate through all of the characters, recursing on each character in the
	// next position. Think about how differently this would be done if each
	// character could only be used one time within the String.
	public static void showPermsRec(int count, int K, StringBuilder data)
	{
		if (count == K)
			System.out.println(data.toString());
		else
		{
			for (char curr = 'A'; curr <= 'Z'; curr++)
			{
				data.append(curr);				// Try next char in set
				showPermsRec(count+1, K, data);	// Recurse
				data.deleteCharAt(data.length()-1);  // Remove char (note that
									// it will always be the last character)
			}
		}
	}
}
