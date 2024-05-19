import java.io.*;

@SuppressWarnings("unchecked")
public class MyBuffer<T> implements QueueInterface<T>, Reverser,SaveRestore
{
    private T[] theQueue;//the array that the queue is in
    private int size;//represents the size of the queue
    private int front=-1;// represents the front of the queue and initalilly at -1
    private int back=-1;//represents teh back of the queue.
    public MyBuffer(int i)
    {
        size = 0;// initialize size and the Queue Array
        theQueue = (T []) new Object[i];

    }


    @Override
    public void enqueue(T newEntry)
    {

        //System.out.println("Enqueue : Front: "+front+"back: "+back);
        if(isEmpty())
        {
            //if the array is empty, then make the first spot both the head and the tail.
            theQueue[0]=newEntry;
            front = 0;
            back = 0;
            size++;

            return;

        }
        //System.out.println("front: "+theQueue[front]+ "number "+front);
        //System.out.println("back: "+theQueue[back]+" number "+back);
        if(size== theQueue.length)
        {
            //the logical and physical size are the same so the array size needs to bee increased to put the new value in the queue
            //System.out.println(("Expand"));

            T [] tempArray = (T []) new Object[theQueue.length*2];

            //System.out.println("Array size doubled");
            theQueue = copyQueue(tempArray);
            back = size-1;
            front =0;

            theQueue[back+1]=newEntry;
            back++;

            return;
        }
        //no situtation where capacity is filled after this.
        if(back== theQueue.length-1)//(if the space after the 1 is beyond the span of the queue.
        {//case where back is not wrapped around
            //System.out.println("Backgreaterthanqueue");
            theQueue[0]=newEntry;
            back = 0;
            size++;
        }
        else
        {//case where the back is wrapped around the fornt
            theQueue[back+1]=newEntry;
            back++;
            size++;
        }

    }
    public T[] copyQueue(T[] bigger)
    {// this is used in both reverse and enqueue to copy the queue to either be be placed in a bigger array or to be prepped for reverse
        int index =0;

        if(bigger.length<theQueue.length)
            throw new IllegalArgumentException("New array smaller than the queue");
        if(back>=front)// back is after front
        {
          //  System.out.println("front"+front+"back"+back);
            for(int i=front;i<=back;i++)
            {
                bigger[index]=theQueue[i];
                index++;
            }
           // System.out.println("OPTION 1 Needs FIxing");
        }
        else //back is wrapped around.
        {

            for(int i=front;i<theQueue.length;i++)
            {
               // System.out.println(theQueue[i]); 9,10,11,12
                //System.out.println(index);
                bigger[index]=theQueue[i];
                index++;
            }
            for(int i=0;i<=back;i++)
            {
                bigger[index]=theQueue[i];
                index++;
            }

        }
//        System.out.println("\n\n\n" +"Bigger Array print");
//        for(Object s:bigger)
//            System.out.println(s);
        return bigger;
    }

    @Override
    public T dequeue() {
       // System.out.println("Dequeue Front: "+front+"back: "+back);
        if (isEmpty())
            throw new EmptyQueueException("Queue is empty");
        T result = theQueue[front];
        theQueue[front]=null;// removes the value at front.
        if (front != theQueue.length - 1)
        {// general case
            front++;
        } else
        {//if front is at the very end
            front = 0;
        }
        size--;
        return result;
    }

    @Override
    public T getFront() {
        if(isEmpty())
            throw new EmptyQueueException("Queue is empty");
        return theQueue[front];
    }

    @Override
    public boolean isEmpty() {
        //if the logical size of the queue is zero then it is empty
        if(size==0)
        {
            //System.out.println("empty array");
            return true;
        }
    //        return true;
        return false;
    }

    @Override
    public void clear()
    {// makes a new empty array to delete all the values;
        T [] tempArray = (T []) new Object[size];
        size = 0;//it doesnt matter if we reset front of back since as soon as anything is enqueued, the front and back will snap there.
        theQueue=tempArray;
    }

    @Override
    public void reverse()
    {
        T [] reverseQueue = (T []) new Object[theQueue.length];
        int index =0;//make a new array to store the reversed queue
        if(back>=front)// front is in front of back
        {
            for(int i=back;i>=front;i--)
            {
                reverseQueue[index]=theQueue[i];
                index++;
            }
        }
        else //back is wrapped around.
        {
            for(int i=back;i>=0;i--)
            {
                reverseQueue[index]=theQueue[i];
                index++;
            }
            for(int i=theQueue.length-1;i>=front;i--)
            {
                reverseQueue[index]=theQueue[i];
                index++;
            }
        }
        front = 0;
        back = size-1;
        theQueue=reverseQueue;

    }

    @Override
    public boolean saveToFile(String fileName) {
        try
        {
            FileOutputStream out = new FileOutputStream(fileName);
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            //oOut.writeObject(theQueue);
            //oOut.close();
            if( back>front)
            {
                for(int i = front;i<=back;i++)
                {
                    oOut.writeObject(theQueue[i]);
                }
            }
            else
            {
                for(int i = front;i<theQueue.length;i++)
                {
                    oOut.writeObject(theQueue[i]);
                }
                for(int i = 0;i<back;i++)
                {
                    oOut.writeObject(theQueue[i]);
                }
            }
            oOut.close();
            return true;
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong with writing the objects to the file");
            return false;
        }

    }

    @Override
    public boolean restoreFromFile(String fileName) {
        T [] safetyArray = (T []) new Object[theQueue.length];

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));

            int index =0;
            while (true)
            {
                // Note the casting here.  The readObject() method returns
                // the objects using Java Object references, so if we want
                // to access them fully we need to cast to the correct type.
                // If we cast incorrectly that will clearly throw an exception.
                T newData = (T)  input.readObject();
                System.out.println("Read in " + newData.toString());
                safetyArray[index]=newData;
                index++;
            }



        }
        catch (EOFException e1)
        {
            //System.out.println("Read in " + newData.toString());

            theQueue=safetyArray;
           front = 0;
           for(Object t:theQueue)
           {
               size++;
           }
           back = front+size;

            return true;
        }
        catch (ClassNotFoundException e2)
        {
            // If the .class file for the object in the file cannot be found we will
            // get a ClassNotFoundException.  This is a checked exception so we must
            // catch it here, even if we are confident that it will not occur.
            System.out.println("Cannot create object from serialized form");
            return false;
        }
        catch (IOException e3)
        {
            // IOException is also checked so we must also catch this (ex: in case
            // the file is not found)
            System.out.println("Some other I/O error");
            return false;
        }


    }
    @Override
    public String toString()
    {

        String result = "Size "+size+" Capacity: "+theQueue.length+" Contents:\n";
        if(size == 0)
        {
            return result;
        }
        if(front==back)
            return result+getFront();
        if(back>=front)
        {
            for(int i = front;i<= back;i++)
            {
                result += theQueue[i]+" ";
            }
        }
        else if(back<front)
        {
            for(int i = front;i< theQueue.length;i++)
            {
                result += theQueue[i]+" ";
            }
            for(int i = 0;i<=back;i++)
            {
                result+=theQueue[i]+" ";
            }
        }
       // System.out.println("blob"+theQueue[1]);
       // System.out.println("Front is "+ front+"; back is "+back);
        return result;
    }
}
