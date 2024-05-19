// CS 0445 Spring 2024
// LinkedListPlus<T> class partial implementation

// See the commented methods below.  You must complete this class by
// filling in the method bodies for the remaining methods.  Note that you
// may NOT add any new instance variables, but you may use method variables
// as needed.

public class LinkedListPlus2<T> extends A3LList<T>
{
	// Default constructor simply calls super()
	public LinkedListPlus2()
	{
		super();
	}
	
	// Copy constructor.  This is a "deepish" copy so it will make new
	// Node objects for all of the nodes in the old list.  However, it
	// is not totally deep since it does NOT make copies of the objects
	// within the Nodes -- rather it just copies the references.  The
	// idea of this method is as follows:  If oldList has at least one
	// Node in it, create the first Node for the new list, then iterate
	// through the old list, appending a new Node in the new list for each
	// Node in the old List.  At the end, link the Nodes around to make sure
	// that the list is circular.
	
	// Note: This method implementation is the topic of Recitation Exercise
	// 4 on Friday, February 9, 2024.  If you cannot get it to work, see the
	// posted solution after the recitation is complete.
	public LinkedListPlus2(LinkedListPlus2<T> oldList)
	{
		super();
		if (oldList.getLength() > 0)
		{
			// Special case for first Node
			this.add(oldList.firstNode.data);
			copyConstructorHelper(oldList.firstNode.next, oldList);
		}
	}
	private void copyConstructorHelper(Node curr,LinkedListPlus2<T> oldList)
	{
		if(curr.equals( oldList.firstNode) )//base case: if we are back at first node, stop
		{
			return;
		}
		else
		{//else copy node and put in linked list
			this.add(curr.data);
			copyConstructorHelper( curr.next,oldList);
		}
	}




	public String toString()
	{//new string builder
		StringBuilder str = new StringBuilder();
		Node curr = firstNode;


		if (curr != null)//if this is not empty
		{
			str.append(curr.getData());
			str.append(" ");
			return toStringHelper(curr.next, str);
		}
		else return str.toString();
	}
	private String toStringHelper(Node curr, StringBuilder B)
	{

		if (curr != firstNode)//if there is more than one node
		{
			B.append(curr.getData());
			B.append(" ");
			return toStringHelper(curr.getNextNode(), B);
		}
		else
			return B.toString();
	}





	public void leftShift(int num)// Remove num items from the front of the list
	{
		if(num==0)//when we reach zero, stop removing things
		{
			return;

		}
		else
		{
			remove(1);
			 leftShift((num-1));
		}
	}


	public void rightShift(int num)	// Remove num items from the end of the list
	{
		if(num==0)// when we reach 0, stop removing nodes
		{
			return;

		}
		else
		{
			remove(numberOfEntries);
			rightShift((num-1));
		}
	}

	// Rotate to the left num locations in the list.  No Nodes
	// should be created or destroyed.
	public void leftRotate(int num)
	{
		if(num<0)///less than zero, then go other way
		{
			rightRotate(num*-1);
		}
		else if(num==0)//reach zero, stop rotating
		{
			return;
		}
		else// not zero, continue rotating
		{
			firstNode=firstNode.next;
			leftRotate(num-1);
		}
	}

	// Rotate to the right num locations in the list.  No Nodes
	// should be created or destroyed.
	public void rightRotate(int num)
	{
		if(num<0)///less than zero, then go other way
		{
			leftRotate(num*-1);
		}
		else if(num==0)//reach zero, stop rotating
		{
			return;
		}
		else// not zero, continue rotating
		{
			firstNode=firstNode.prev;
			rightRotate(num-1);
		}
	}	
}
