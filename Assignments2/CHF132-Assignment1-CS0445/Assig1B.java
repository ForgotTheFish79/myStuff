// CS 0445 Spring 2024
// Assig1B driver program.  This program must work as is with your
// MyBuffer class and Product class hierarchy.  Look at the code 
// carefully to help you to determine the correct implementation of
// your classes.

import java.util.*;

public class Assig1B
{
	public static void main(String [] args)
	{
		MyBuffer<Product> theBuff = new MyBuffer<Product>(8);

		initData(theBuff);

		System.out.println("Buffer Initialized");
		System.out.println(theBuff.toString());

		theBuff.saveToFile("A1B.out");
		System.out.println("Buffer Saved to File");

		theBuff.clear();
		System.out.println("Buffer Cleared");
		System.out.println(theBuff.toString());

		theBuff.restoreFromFile("A1B.out");
		System.out.println("Buffer restored");
		System.out.println();

        // Move the data into an ArrayList so that we can sort it
		ArrayList<Product> A = new ArrayList<Product>();
		while (!(theBuff.isEmpty()))
		{
			A.add(theBuff.dequeue());
		}

		// Note the output (in file A1B-out.txt) to see the
		// formatting required for the toString() method.  Note
		// that polymorphism is used in this method call.		
		System.out.println("Here is the data:");
		for (int i = 0; i < A.size(); i++)
		{
			System.out.println(A.get(i).toString());
		}
		System.out.println();

		// The Collections.sort() method in Java uses the Comparable
		// interface (and compareTo() method) to sort the data in the
		// ArrayList.  As long as you have properly defined the cost()
		// method in your classes, this should sort the data properly.
		// See also Product.java for the compareTo() method.
		System.out.println("Sorting the data");
		Collections.sort(A);
		System.out.println();

		System.out.println("Here is the data:");
		for (int i = 0; i < A.size(); i++)
		{
			System.out.println(A.get(i));
		}
		System.out.println();
	}

	// Note carefully the arguments to the constructors for each
	// different type.
	public static void initData(QueueInterface<Product> buff)
	{
		Variable v = new ByVolume("Bleach", 0.01, 128);
		buff.enqueue(v);
		v = new ByVolume("Milk", 0.03, 128);
		buff.enqueue(v);
		Product p = new Fixed("TV", 1200.0);
		buff.enqueue(p);
		p = new Fixed("Phone", 1000.0);
		buff.enqueue(p);
		v = new ByWeight("Sugar", 0.005, 80);
		buff.enqueue(v);
		v = new ByWeight("Salt", 0.01, 400);
		buff.enqueue(v);
		p = new Fixed("Candy", 1.75);
		buff.enqueue(p);
	}

}
