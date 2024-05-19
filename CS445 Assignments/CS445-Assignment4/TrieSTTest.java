// CS 0445 Spring 2024
// Test program for TrieSTNew class. Use this program to see how the class
// is used and see the output to see the results. You must use the TrieSTNew
// class in your Assignment 4 program in order to reduce the number of required
// recursive calls when finding your anagrams. See the Assignment 4 sheet for
// details. If you want to confirm the output of this program, you can open the
// dictionary.txt file in an editor and look at the words - just be careful not
// to change anything in that file.

import java.io.*;
import java.util.*;
public class TrieSTTest
{
	public static void main(String [] args) throws IOException
	{
		Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"));
		String st;
		StringBuilder sb;
		TrieSTNew D = new TrieSTNew();  // Make a new object
		TrieST2 F = new TrieST2();
		while (fileScan.hasNext())
		{
			st = fileScan.nextLine();  // get next word from the file
			//D.put(st, st);  // use the word as both the key and the value
			F.put(new StringBuilder(st),new StringBuilder(st));
		}

		String [] tests = {"A","abc", "abe", "abet", "abx", "ace", "acid", "hives",
						   "iodi", "iodine", "iodiner", "inval", "zoo", "zool", "zoologist", 
						   "zoologists", "zurich"};
		for (int i = 0; i < tests.length; i++)
		{
			int ans = F.searchPrefix(new StringBuilder(tests[i]));
			System.out.print(tests[i] + " is ");
			switch (ans)
			{
				case 0: System.out.println("neither a word nor a prefix ");
					break;
				case 1: System.out.println("a prefix only");
					break;
				case 2: System.out.println("a word only");
					break;
				case 3: System.out.println("both a word and prefix");
			}
		}
	}
}

