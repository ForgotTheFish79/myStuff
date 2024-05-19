// CS 0445 Spring 2024
// This is a partial implementation of the ReallyLongInt class.  You need to
// complete the implementations of the remaining methods.  Also, for this class
// to work, you must complete the implementation of the LinkedListPlus class.
// See additional comments below.

public class ReallyLongInt2 extends LinkedListPlus2<Integer>
							implements Comparable<ReallyLongInt2>
{
	private ReallyLongInt2()
	{
		super();
	}


	public ReallyLongInt2(String s)
	{
		super();

		StringConstructorHelper(s,0);

	}
	public void StringConstructorHelper(String s,int index)
	{

		if(index==s.length())//base case, exceed the end and no longer continue, failsafe
		{
			return;
		}
		else if (index == s.length()-1)//at the last one, stop the recursive calls
		{

			char c = s.charAt(index);
			int digit=-1;
			if (('0' <= c) && (c <= '9')) {
				digit = c - '0';
				this.add(1, Integer.valueOf(digit));
			}	else throw new NumberFormatException("Illegal digit " + c);
			if (digit == 0 && this.getLength() == 0)
			{
				this.add(1, Integer.valueOf(digit));
			}
		}
		else
		{
			//System.out.println(s.charAt(index));
			char c = s.charAt(index);
			//System.out.println(c);
			int digit=-1;
			if (('0' <= c) && (c <= '9')) {
				digit = c - '0';
				//System.out.println(digit);
				if (!(digit == 0 && this.getLength() == 0)) {
					//System.out.println(digit);
					this.add(1, Integer.valueOf(digit));

				}
			}
			else throw new NumberFormatException("Illegal digit " + c);
			StringConstructorHelper(s,index+1);
		}
	}


	// Copy constructor can just call super()
	public ReallyLongInt2(ReallyLongInt2 rightOp)
	{
		super(rightOp);
	}
	
	// Constructor with a long argument.  You MUST create the ReallyLongInt
	// digits by parsing the long argument directly -- you cannot convert to a String
	// and call the constructor above.  As a hint consider the / and % operators to
	// extract digits from the long value.
	public ReallyLongInt2(long X)
	{
		if(X ==0)
		{//special case if X is set to 0;
			this.add(0);
			return;
		}
		LongConstructorHelper(X);
	}
	private void LongConstructorHelper(long X)
	{
		if(X==0)//same idea as string constructor but fail safe unncessary
		{
			return;
		}
		else
		{
			this.add((int)(X%10));
			LongConstructorHelper(X / 10);
		}
	}

	
	// Method to put digits of number into a String.  Note that toString()
	// has already been written for LinkedListPlus, but you need to
	// override it to show the numbers in the way they should appear.
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		Node curr = firstNode.prev;


		if (curr != null)
		{
			str.append(curr.getData());

		}

		toStringHelper(curr.prev, str);



		return str.toString();
	}
	private String toStringHelper(Node curr, StringBuilder B)
	{

		if (curr != firstNode.prev)
		{
			B.append(curr.getData());

			return toStringHelper(curr.getPrevNode(), B);
		}
		else//reach the end and return the string
			return B.toString();
	}


	// See notes in the Assignment sheet for the methods below.  Be sure to
	// handle the (many) special cases.  Some of these are demonstrated in the
	// RLITest.java program.

	// Return new ReallyLongInt which is sum of current and argument
	public ReallyLongInt2 add(ReallyLongInt2 rightOp)
	{
		ReallyLongInt2 result =new ReallyLongInt2();
		if(this.numberOfEntries<rightOp.numberOfEntries)//make sure that this is always the ReallyLongInt with more or the same digits
		{
			return rightOp.add(this);
		}
		if(rightOp.numberOfEntries==1&&rightOp.firstNode.getData()==0)//special case to save time
		{
			ReallyLongInt2 sumList= new ReallyLongInt2(this);//copy constructor
			return sumList;
		}
		int left = numberOfEntries;
		int right = rightOp.numberOfEntries;
		addHelper(result,this.firstNode,rightOp.firstNode,0,right,left);
		result.zeroRemove(result.firstNode.prev, result);
		return result;
	}
	private void zeroRemove(Node currNode,ReallyLongInt2 list)//make currNode FirstNode.prevNode to use//CRITICAL, else the code will break and stack overflow!!!
	{//used at the end of methods to remove leading zeroes if there are any. not sure if necessary but if not provides no run time inefficiency anyway
		if(currNode.getData()!=0||numberOfEntries==1)
		{
			return ;
		}
		else{
			list.remove(list.numberOfEntries);
			 zeroRemove(currNode.prev,list);

		}


	}
	private void addHelper(ReallyLongInt2 result,Node left,Node right,int carry,int indexR,int indexL)
	{
		int sum = left.data+right.data+carry;
		if(indexR>0)// while non carry adding still needs to occur
		{
			if(sum>9)// need carry
			{
				result.add(sum%10);
				 addHelper(result, left.next, right.next, 1,indexR-1,indexL);
			}
			else//no need carry
			{
				result.add(sum);
				 addHelper(result, left.next, right.next, 0,indexR -1,indexL);
			}
		}
		else{
		if(indexL-result.numberOfEntries>0)// only carry add needs to occur
		{
			sum = left.data+carry;
			//System.out.println(indexL);
			if(sum>9)
			{
				result.add(0);
				 addHelper(result, left.next, right.next, 1,indexR,indexL);
			}
			else {
				result.add(left.data+carry);
				 addHelper(result, left.next, right.next, 0,indexR,indexL);
			}
		}
		else if(carry==1)//reach the end of the longer number and carry still is 1
		{
			result.add(1);
		}

		//
	}}

	
	// Return new ReallyLongInt which is difference of current and argument
	public ReallyLongInt2 subtract(ReallyLongInt2 rightOp)
	{
		//similar idea to add
		ReallyLongInt2 result= new ReallyLongInt2();
		if(this.compareTo(rightOp)==-1)//right op is always smaller than left op
		{
			throw new ArithmeticException();
		}
		else if(this.compareTo(rightOp)==0) {
			return new ReallyLongInt2(0);
		}
		subtractHelper(result,this.firstNode,rightOp.firstNode,0,rightOp.numberOfEntries,numberOfEntries);

		 result.zeroRemove(result.firstNode.prev, result);
		return result;
	}
	private void subtractHelper(ReallyLongInt2 result,Node left,Node right,int carry,int indexR,int indexL)
	{//same as add except subtracting
		int diff = left.data-right.data-carry;
		if(indexR>0)
		{
			if(diff<0)// need carry calculation aka 7-9 =-2
			{
				result.add(diff+10);
				 subtractHelper(result, left.next, right.next, 1,indexR-1,indexL);
			}
			else//no need carry
			{
				result.add(diff);
				 subtractHelper(result, left.next, right.next, 0,indexR -1,indexL);
			}
		}
		else {
			int index2 = indexL - result.numberOfEntries;//remaining digits after everything has been subtracted

			if (index2 == 0 && carry == 1) {
				result.add(left.getData() - 1);//carryOver calc is like reverse adding

			}
			//NOT DONE AFTER THIS
			if (indexL - result.numberOfEntries > 0) {//if longer number still has digits left
				diff = left.data - carry;
				//System.out.println(indexL);
				if (diff < 0) {
					result.add(9);
					 subtractHelper(result, left.next, right.next, 1, indexR, indexL);
				} else {
					result.add(diff);
					 subtractHelper(result, left.next, right.next, 0, indexR, indexL);
				}
			}
		}
		//return result;
	}


	// Return new ReallyLongInt which is product of current and argument
	public ReallyLongInt2 multiply(ReallyLongInt2 rightOp)
	{
		ReallyLongInt2 result= new ReallyLongInt2("0");
		if(this.numberOfEntries<rightOp.numberOfEntries)//make sure that this is always the ReallyLongInt with more or the same amount of digits
		{//communtative prooperty of multiplication
			return rightOp.multiply(this);
		}
		else if(rightOp.numberOfEntries==1&&rightOp.firstNode.getData()==0)
		{
			//System.out.println("0");
			return new ReallyLongInt2("0");
		}
		int cycle = rightOp.numberOfEntries;

		//singleMult(result,this.firstNode,rightOp.firstNode,0,numberOfEntries);
		result =cycleMult( result, firstNode,  rightOp.firstNode, 0, numberOfEntries,rightOp.numberOfEntries, 0);
		 result.zeroRemove(result.firstNode.prev, result);
		return result;
	}
	/*two steps, multipy top number by a digit. complete
	 step 2, add each of the digit onto a global result.
	* */

	private ReallyLongInt2 cycleMult(ReallyLongInt2 result,Node left, Node right, int carry, int indexL,int indexR,int mult)
	{
		ReallyLongInt2 temp = new ReallyLongInt2();
		if(indexR>0)
		{
			temp = temp.singleMult(temp,left,right,carry,indexL);
			temp=temp.addZero(temp,mult);
			//produces single mult results that adjusts to the significance of digits
			result =result.add(temp);
			//adds to the running sum saved in each iteration of cycleMult
			return cycleMult( result, left,  right.next, carry, indexL,indexR-1, mult+1);
		}
		return result;
	}
	private ReallyLongInt2 addZero(ReallyLongInt2 target, int times)
	{//used to add zeroes at the end of the singleMults for more significant digits
		if(times>0)
		{
			target.add(1,0);
			target.addZero(target,times-1);
		}
		return target;
	}


	private ReallyLongInt2 singleMult(ReallyLongInt2 result,Node left,Node digit,int carry,int indexL)
	{//indexR is always shorter // works for any number* single digit which would be the rigth node.
		int product = left.data*digit.data+carry;
		if(digit.data==0)
		{
			result = new ReallyLongInt2(0);

			 return result;
		}
		else if(indexL>0)
		{
			result.add(product%10);
			singleMult(result, left.next, digit, product/10,indexL-1);
		}
		else if(carry>0)
		{
			result.add(carry);
		}
		return result;// produces any product between a arbitraily long number and one digit.
	}

	
	// Return -1 if current ReallyLongInt is less than rOp
	// Return 0 if current ReallyLongInt is equal to rOp
	// Return 1 if current ReallyLongInt is greater than rOp
	public int compareTo(ReallyLongInt2 rOp)
	{
		if(this.numberOfEntries>rOp.numberOfEntries)//simple cases where one number is longer than the other
		{
			return 1;
		}
		else if (rOp.numberOfEntries>this.numberOfEntries)
		{
			return -1;
		}
		else//have to compare for equal case.
		{
			return compareToHelper(firstNode.prev,rOp.firstNode.prev,this,rOp);
		}

	}
	private int compareToHelper(Node currLeft,Node currRight,ReallyLongInt2 left,ReallyLongInt2 right)
	{
		if(currLeft==left.firstNode)//most siginicant digit is always hte first one and has not looped back to start
		{
			if(currLeft.data>currRight.getData())// simple case where one number is bigger than other
			{
				return 1;
			}
			else if(currLeft.data<currRight.getData())
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
		else {
			if(currLeft.data>currRight.getData())
			{
				return 1;
			}
			else if(currLeft.data<currRight.getData())
			{
				return -1;
			}
			else
			{
				return compareToHelper(currLeft.prev,currRight.prev,left,right);
			}
		}
	}


	// Is current ReallyLongInt equal to rightOp?  Note that the argument
	// in this case is Object rather than ReallyLongInt.  It is written
	// this way to correctly override the equals() method defined in the
	// Object class.
	public boolean equals(Object rightOp)
	{
		if(compareTo((ReallyLongInt2) rightOp)==0)//simple solution
		{
			return true;
		}
		return false;
	}
}
