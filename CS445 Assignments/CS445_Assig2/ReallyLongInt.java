// CS 0445 Spring 2024
// This is a partial implementation of the ReallyLongInt class.  You need to
// complete the implementations of the remaining methods.  Also, for this class
// to work, you must complete the implementation of the LinkedListPlus class.
// See additional comments below.

public class ReallyLongInt 	extends LinkedListPlus<Integer>
							implements Comparable<ReallyLongInt>
{
	private ReallyLongInt()
	{
		super();
	}

	// Data is stored with the LEAST significant digit first in the list.  This is
	// done by adding all digits at the front of the list, which reverses the order
	// of the original string.  Note that because the list is doubly-linked and
	// circular, we could have just as easily put the most significant digit first.
	// You will find that for some operations you will want to access the number
	// from least significant to most significant, while in others you will want it
	// the other way around.  A doubly-linked list makes this access fairly
	// straightforward in either direction.
	public ReallyLongInt(String s)
	{
		super();
		char c;
		int digit = -1;
		// Iterate through the String, getting each character and converting it into
		// an int.  Then make an Integer and add at the front of the list.  Note that
		// the add() method (from A2LList) does not need to traverse the list since
		// it is adding in position 1.  Note also the the author's linked list
		// uses index 1 for the front of the list.
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				// Do not add leading 0s
				if (!(digit == 0 && this.getLength() == 0))
					this.add(1, Integer.valueOf(digit));
			}
			else throw new NumberFormatException("Illegal digit " + c);
		}
		// If number is all 0s, add a single 0 to represent it
		if (digit == 0 && this.getLength() == 0)
			this.add(1, Integer.valueOf(digit));
	}

	// Copy constructor can just call super()
	public ReallyLongInt(ReallyLongInt rightOp)
	{
		super(rightOp);
	}

	// Constructor with a long argument.  You MUST create the ReallyLongInt
	// digits by parsing the long argument directly -- you cannot convert to a String
	// and call the constructor above.  As a hint consider the / and % operators to
	// extract digits from the long value.
	public ReallyLongInt(long X)
	{
		if(X ==0)
		{//special case if X is set to 0;
			this.add(0);
			return;
		}
		while(X>0)//as long as X is greater than 0
		{
			this.add((int)(X%10));//adds the digit we need to add
			X/=10;//remove that digit from the long
		}

	}

	// Method to put digits of number into a String.  Note that toString()
	// has already been written for LinkedListPlus, but you need to
	// override it to show the numbers in the way they should appear.
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		if(firstNode==null)
		{
			return null;
		}
		if(firstNode.getPrevNode()==null)
		{
			b.append(firstNode.data.toString());
			return b.toString();
		}
		Node curr = firstNode.getPrevNode();
		int i = 0;
		while (i < this.getLength())
		{
			b.append(curr.data.toString());
			curr = curr.prev;
			i++;
		}
		return b.toString();
	}

	// See notes in the Assignment sheet for the methods below.  Be sure to
	// handle the (many) special cases.  Some of these are demonstrated in the
	// RLITest.java program.

	// Return new ReallyLongInt which is sum of current and argument
	public ReallyLongInt add(ReallyLongInt rightOp)
	{
		ReallyLongInt sumList = new ReallyLongInt();//create the list we are going to return;
		if(this.numberOfEntries<rightOp.numberOfEntries)//make sure that this is always the ReallyLongInt with more or the same digits
		{
			return rightOp.add(this);
		}
		if(rightOp.numberOfEntries==1&&rightOp.firstNode.getData()==0)//special case to save time
		{
			sumList= new ReallyLongInt(this);//copy constructor
			return sumList;
		}
		int index = rightOp.numberOfEntries;//we need to do as many operations as there are digits in the shorter number
		//System.out.println(index);
		Node currSmall = rightOp.firstNode;//labeling the iterator nodes
		Node currBig = firstNode;
		int sum = currSmall.getData()+currBig.getData();//create the sum int

		int remainder = 0;//initializing the remainder

		while(index>0)
		{

			sum = currSmall.getData()+currBig.getData()+remainder;//sum will be the sum of the two current node's data
			//System.out.println(sum);
			if(sum>9)
			{//affects next addition :(
				remainder=1;//
				sumList.add(sum-10);//biggest sum could be is 9+9=18; so this will never be a problem
			}
			else
			{
				remainder=0;//no remainder, straight forward
				sumList.add(sum);
			}
			currSmall=currSmall.getNextNode();//iterate;
			currBig= currBig.getNextNode();
			index--;
			//System.out.print(sum+" ");
		}
		int index2 = this.numberOfEntries-rightOp.numberOfEntries;//now we need to put in the rest of the digits in the long number
		//System.out.println(index2+ "="+this.numberOfEntries+"-"+rightOp.numberOfEntries);
		if(index2==0&&remainder==1)
		{//if there is a remainer like 999999 will change a lot
			sumList.add(remainder);
			return sumList;
		}
		while(index2>0)
		{
			if(remainder==0)
			{
				sumList.add(currBig.getData());//when we get here else if and else will never be triggered again
				//System.out.println(currBig.getData());
			}
			else if(remainder+currBig.getData()>9)//remaineder continues
			{
				//System.out.println(remainder+ currBig.getData());
				sumList.add(remainder+currBig.getData()-10);
				remainder =1;
				//System.out.println("Option2");
			}else {
				sumList.add(remainder+currBig.getData());// this sets the remainder to zero to get into the if part
				remainder=0;
				//System.out.println("Option3");
			}
			currBig=currBig.next;

			index2--;
		}
		//int index3 = this.numberOfEntries-rightOp.numberOfEntries;
		sumList.add(remainder);//adds the remaining zero if  the sum would be something like 10000000 from 9999999+1
		Node currNode = sumList.firstNode.prev;// for zero removal, we start at the one before the zero to get rid of leading zeroes
		while(true)
		{
			//System.out.println(currNode.data);
			if(currNode.getData()!=0)
			{
				return sumList;
			}

			sumList.remove(sumList.numberOfEntries);
			currNode=currNode.prev;


		}
		/*
		overall, we iterate through each RLI once. Removing leading zeroes does the rest

		 */
	}

	// Return new ReallyLongInt which is difference of current and argument
	public ReallyLongInt subtract(ReallyLongInt rightOp) throws ArithmeticException
	{


		ReallyLongInt minusList = new ReallyLongInt();//similar idea to add

		if(this.compareTo(rightOp)==-1)//right op is always smaller than left op
		{
			throw new ArithmeticException();
		}
		else if(this.compareTo(rightOp)==0)
		{
			minusList.add(0);

			return minusList;
		}
		else
		{
			int carryOver = 0;
			Node currSmall = rightOp.firstNode;//selecting the first node which is the last digit
			Node currBig = firstNode;
			int index1 = rightOp.numberOfEntries;
			int difference ;
			//System.out.println(index1);
			for(int i =0;i<index1;i++)//using a for loop rather than while loop like in add looks nicer
			{
				//System.out.println(i);
				difference = currBig.data-currSmall.data-carryOver;//453-435 =18 13-5=8,carryOver = 1,5-3-1=1
				//System.out.println(sum);
				if(difference<0)
				{//affects next addition :(
					carryOver=1;
					minusList.add(difference+10);
				}
				else
				{
					carryOver=0;
					minusList.add(difference);
				}
				currSmall=currSmall.getNextNode();
				currBig= currBig.getNextNode();

				//System.out.print(sum+" ");
			}
			int index2 = this.numberOfEntries-rightOp.numberOfEntries;//remaining digits after everything has been subtracted

			if(index2==0&&carryOver==1)
			{
				minusList.add(currBig.getData()-1);//carryOver calc is like reverse adding

			}
			while(index2>0)
			{
				if(carryOver==0)
				{
					minusList.add(currBig.getData());
				}
				else if (carryOver==1)
				{
					if(currBig.getData()!=0) {
						carryOver = 0;
						minusList.add(currBig.getData() - 1);
					}
					else
					{
					carryOver=1;
					minusList.add(9);
					}
				}
				index2--;
				currBig=currBig.next;
			}
			Node currNode = minusList.firstNode.prev;// for zero removal, we start at the one before the zero
			while(true)//front zero remover
			{
				//System.out.println(currNode.data);
				if(currNode.getData()!=0)
				{
					return minusList;
				}

				minusList.remove(minusList.numberOfEntries);
				currNode=currNode.prev;


			}
			//System.out.println(("escaping the loop"));
		}


	}

	// Return new ReallyLongInt which is product of current and argument
	public ReallyLongInt multiply(ReallyLongInt rightOp)//somewhat similar to add and could use a similar structure.
	{

		ReallyLongInt proList = new ReallyLongInt("0");// this will be the running sum for the operation.
		ReallyLongInt tempList = new ReallyLongInt();//this will store the second number to be added to the proList
		/*
		1234
		1001
		----
		1234 (top number times each digit of the second multiplied by a multiple of 10 added all together)
	 1234000
	--------
		1235234
		# the biggest duo that can be multiplied together would be 9*9 = 1 + 8 carryOver.
		 */
		if(this.numberOfEntries<rightOp.numberOfEntries)//make sure that this is always the ReallyLongInt with more or the same amount of digits
		{//communtative prooperty of multiplication
			return rightOp.multiply(this);
		}
		else if(rightOp.numberOfEntries==1&&rightOp.firstNode.getData()==0)
		{
			return proList;
		}

		Node currSmall = rightOp.firstNode;// prep for iteration
		Node currBig = firstNode;
		int carryOver = 0;

		for(int i = 0;i< rightOp.numberOfEntries;i++)//outer loop, rightOp has this index amount of digits
		{
			//System.out.println(("TestOuter"+rightOp.numberOfEntries));
			// we need to iterate through each digit of the longer number and multiply them to hte last digit of rightOP
			for(int j = 0;j<this.numberOfEntries;j++)
			{// this loop computes the top number times the bottom times and works 2/12/24
				//System.out.println(("TestInner"+this.numberOfEntries));
				int digit;
				int product = currBig.getData()* currSmall.getData();//get the product of the two digits as an int.

				if(product>9) {
					digit = product % 10;//((int) Math.pow(10, i + 1));//calculate the digit
				}
				else
					digit = product;
				//System.out.println(digit+", "+carryOver);
				if(carryOver+digit>9)
				{

					//biggest carryOver+digit combo is 8+9=17 which increase the carry over for the next loop by 1

					tempList.add(j+1,(carryOver+digit)%10);
					carryOver = 1;

				}
				else
				{
					tempList.add(j+1,carryOver+digit);
					carryOver=0;
				}
				carryOver += (product/10);// calculate the carryover for the next digit
				currBig=currBig.getNextNode();
				if(j==this.numberOfEntries-1&&carryOver!=0)
					tempList.add(carryOver);


			}

			carryOver=0;
			for(int k =i;k>0;k--)
			{

				tempList.add(1,0);
			}
			Node currNode = tempList.firstNode.prev;

			//System.out.println(proList.firstNode.next.getData());
			while(true)
			{
				//System.out.println(currNode.data);
				if(currNode.getData()!=0||tempList.numberOfEntries==1)
				{
					break;
				}

				tempList.remove(tempList.numberOfEntries);
				currNode=currNode.prev;


			}

			proList = proList.add(tempList);
			//System.out.println(tempList.toString());
			//System.out.println(proList.toString());
			tempList = new ReallyLongInt();

			currSmall=currSmall.getNextNode();//moving on to the next digit of the shorter number


		}

		Node currNode = proList.firstNode.prev;
		//System.out.println(proList.toString());
		//System.out.println(proList.firstNode.next.getData());
	while(true)
			{
				//System.out.println(currNode.data);
				if(currNode.getData()!=0||proList.numberOfEntries==1)
				{
					break;
				}

				proList.remove(proList.numberOfEntries);
				currNode=currNode.prev;


			}
		return proList;
	}

	// Return -1 if current ReallyLongInt is less than rOp
	// Return 0 if current ReallyLongInt is equal to rOp
	// Return 1 if current ReallyLongInt is greater than rOp
	public int compareTo(ReallyLongInt rOp)
	{
		if(this.numberOfEntries>rOp.numberOfEntries)
		{//if L has more digits, it has to be bigger
			return 1;
		}
		else if (rOp.numberOfEntries>this.numberOfEntries)
		{//if r has more digits, it has to be bigger
			return -1;
		}
		else {// have to compare stuff because of equal amount of digits
			Node currThis = firstNode.prev;//most significant digit
			Node currOther = rOp.firstNode.prev;
			if(currThis.getData()>currOther.getData())//first number bigger or smaller
			{
				return 1;
			}
			else if (currThis.getData()< currOther.getData())// this is imporant since the incoming loop stops when fisrt node arrives again
			{
				return -1;
			}
			currThis=firstNode.prev.prev;
			currOther = rOp.firstNode.prev.prev;
			while(currThis.getData() == currOther.getData()&&currThis!=firstNode.prev )
			{//iterate until two numbers are different or we reach the first

				currThis=currThis.prev;
				currOther=currOther.prev;
			}
			if(currThis.getData()>currOther.getData())
			{
				return 1;
			}
			else if (currThis.getData()< currOther.getData())
			{
				return -1;
			}
			return 0;
		}



	}

	// Is current ReallyLongInt equal to rightOp?  Note that the argument
	// in this case is Object rather than ReallyLongInt.  It is written
	// this way to correctly override the equals() method defined in the
	// Object class.
	public boolean equals(Object rightOp)//
	{
		if(this.compareTo((ReallyLongInt) rightOp)==0)//if compareTo yields zero then its equals
		{
			return true;
		}
		return false;
	}
}
