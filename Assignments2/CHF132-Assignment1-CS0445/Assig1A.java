// CS 0445 Spring 2024
// Assig1A driver program.  This program must work as is with your
// MyBuffer class.  Look carefully at all of the method calls so that
// you create your MyBuffer methods correctly.  For example, note the
// constructor calls and the toString() method call.
public class Assig1A
{
	public static void main(String [] args)
	{
		// Testing constructor
		QueueInterface<Integer> theBuff = new MyBuffer<Integer>(10);

		// Testing enqueue and resizing. Note that the original size of the MyBuffer
		// is 3 but we have enqueued 8 items.  Thus the underlying array needed to
		// be resized twice (from 3 to 6 and then from 6 to 12)
		for (int i = 0; i < 8; i++)
		{
			Integer newItem = Integer.valueOf(2 * i);
			theBuff.enqueue(newItem);
			System.out.println(newItem + " added to queue");
		}

		// Testing the SaveRestore interface.
		// Note: If you cannot get the SaveRestore interface to work,
		// comment out the code from line 28 to line 38 (inclusive).  You
		// will not get credit for SaveRestore but the rest of the program
		// should execute correctly.
		System.out.println("\nSaving the queue to a file");
		((SaveRestore)theBuff).saveToFile("A1A.out");
		theBuff.clear();

		boolean empty = theBuff.isEmpty();
		if (empty)
			System.out.println("The queue is empty");

		((SaveRestore)theBuff).restoreFromFile("A1A.out");

		System.out.println("Buffer restored\n");

		// Testing dequeue
		while (!(theBuff.isEmpty()))
		{
			Integer oldItem = theBuff.dequeue();
			System.out.println(oldItem + " retrieved from queue");
		}
		// Special case of dequeue from empty queue
		try
		{
			Integer noItem = theBuff.dequeue();
			System.out.println("Item is " + noItem);
		}
		catch (EmptyQueueException e)
		{
			System.out.println("Cannot dequeue from an empty queue");
		}
		System.out.println();

		// Testing array management.  The array here should never have
		// to be resized since the logical queue size stays small
		QueueInterface<String> theBuff2 = new MyBuffer<String>(6);
		int count = 1;
		String theItem = new String("Item" + count);
		System.out.println("Enqueuing " + theItem);
		theBuff2.enqueue(theItem);
		for (int i = 0; i < 8; i++)
		{
			count++;
			theItem = new String("Item" + count);
			System.out.println("Enqueuing " + theItem);
			theBuff2.enqueue(theItem);
			theItem = theBuff2.dequeue();
			System.out.println("Dequeuing " + theItem);
		}

		// Note: Even though toString() is not explicitly defined in
		// QueueInterface<T>, it can still be called because Java
		// interfaces by default contain abstract methods corresponding
		// to all of the public methods in class Object.
		System.out.println("Here is the MyBuffer:");
		System.out.println(theBuff2.toString());
		System.out.println();

		// Now we again test the management and resizing
		for (int i = 0; i < 10; i++)
		{
			count++;
			theItem = new String("Item" + count);
			System.out.println("Enqueuing " + theItem);
			theBuff2.enqueue(theItem);
		}

		System.out.println("Here is the MyBuffer:");
		System.out.println(theBuff2.toString());
		System.out.println();

		// Empty and refill to check some special cases
		while (!theBuff2.isEmpty())
		{
			theItem = theBuff2.dequeue();
			System.out.println("Dequeueing " + theItem);
		}

		System.out.println("Here is the MyBuffer:");
		System.out.println(theBuff2.toString());

		// Now add a few more items
		for (int i = 0; i < 5; i++)
		{
			count++;
			theItem = new String("Item" + count);
			System.out.println("Enqueuing " + theItem);
			theBuff2.enqueue(theItem);
		}

		System.out.println("Here is the MyBuffer:");
		System.out.println(theBuff2.toString());
		System.out.println();

		// This code will test Reverser interface.
		MyBuffer<Integer> newBuff = new MyBuffer<Integer>(10);
		Reverser R = (Reverser) newBuff;
		System.out.println("Adding some Integers...");
		for (int i = 0; i < 9; i++)
		{
			newBuff.enqueue(Integer.valueOf(i));
		}
		System.out.println(newBuff.toString());
		System.out.println("Reversing the data...");
		R.reverse();
		System.out.println(newBuff.toString());

		System.out.println("Dequeue 3 items, enqueue 2 items...");
		Integer bogus = newBuff.dequeue();
		bogus = newBuff.dequeue();
		bogus = newBuff.dequeue();
		newBuff.enqueue(Integer.valueOf(9));
		newBuff.enqueue(Integer.valueOf(10));
		System.out.println(newBuff.toString());
		System.out.println("Reversing again...");
		R.reverse();
		System.out.println(newBuff.toString());
	}
}
