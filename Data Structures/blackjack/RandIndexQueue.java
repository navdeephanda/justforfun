import java.util.Random;
public class RandIndexQueue<T> implements MyQ<T>, Indexable<T>, Shufflable
{
	@SuppressWarnings("unchecked")
	int numMoves;
	T [] arr;
	int size;
	int front;
	int back;
	
	
	//constructor
	public RandIndexQueue(int initSize)
	{
		arr = (T []) new Object[initSize+1];
		size=0;
		front=0;
		back=initSize;
	}
	
	// Add a new Object to the MyQ in the next available location.  If
	// all goes well, return true; otherwise return false.
	public boolean offer(T item)
	{
		if(front==((back+2)%arr.length))
		{
			resize();
			back=(back+1)%arr.length;
			arr[back]=item;
			numMoves++;
			size++;
			//for(int i = 0; i<arr.length; i++)
				//System.out.println("array loc "+i+" is equal to "+arr[i]);
		}
		else
		{
			back=(back+1)%arr.length;
			arr[back]=item;
			numMoves++;
			size++;
			//for(int i = 0; i<arr.length; i++)
				//System.out.println("array loc "+i+" is equal to "+arr[i]);
		}
		
		return true;
	}
	
	// Remove and return the logical front item in the MyQ.  If the MyQ
	// is empty, return null
	public T poll()
	{
		if(!isEmpty())
		{
			T frontItem = arr[front];
			arr[front]=null;
			front = (front+1)%arr.length;
			size--;
			return frontItem;
		}
		else return null;
	}
	
	// Get and return the logical front item in the MyQ without removing it.
	// If the MyQ is empty, return null
	public T peek()
	{
		if(!isEmpty())
			return(arr[front]);
		else
			return null;
	}
	
	// Return true if the MyQ is full, and false otherwise
	public boolean isFull()
	{
		return false;
	}
	
	// Return true if the MyQ is empty, and false otherwise
	public boolean isEmpty()
	{
		return (front==((back+1)%arr.length));
	}
	
	// Return the number of items currently in the MyQ.  Determining the
	// length of a queue can sometimes very useful.
	public int size()
	{
		return size;
	}

	// Reset the MyQ to empty status by reinitializing the variables
	// appropriately
	public void clear()
	{
		for(int i=0;i<arr.length;i++)
			arr[i]=null;
		front = 0;
		back = arr.length-1;
		size=0;
	}
	
	//resizes array if full
	public void resize()
	{
		T [] temp = (T[]) new Object[2*arr.length];
		
		for(int i=0;i<arr.length;i++)
		{
			temp[i]=arr[front];
			front=(front+1)%arr.length;
		}
		arr=temp;
		front=0;
		back=size-1;
	}
	
	// Methods to get and set the value for the moves variable.  The idea for
	// this is the same as shown in Recitation Exercise 1 -- but now instead
	// of a separate interface these are incorporated into the MyQ<T>
	// interface.  The value of your moves variable should be updated during
	// an offer() or poll() method call.  However, any data movement required
	// to resize the underlying array should not be counted in the moves.
	public int getMoves()
	{
		return numMoves;
	}
	public void setMoves(int moves)
	{
		numMoves = moves;
	}
	
	// Get and return the value located at logical location i in the implementing
	// collection, where location 0 is the logical beginning of the collection.
	// If the collection has fewer than (i+1) items, throw an IndexOutOfBoundsException 
	public T get(int i)
	{
		if(i<=size)
			return(arr[(front+i)%arr.length]);
		else
			throw new IndexOutOfBoundsException();
	}
	
	// Assign item to logical location i in the implementing collection, where location
	// 0 is the logical beginning of the collection.  If the collection has fewer than
	// (i+1) items, throw an IndexOutOfBoundsException
	public void set(int i, T item)
	{
		if(i<=size)
			arr[(front+i)%arr.length]=item;
		else
			throw new IndexOutOfBoundsException();
	}
	
	// Reorganize the items in the object in a pseudo-random way.  The exact
	// way is up to you but it should utilize a Random object (see Random in 
	// the Java API).  Note that this should not change the size of the
	// collection.
	public void shuffle()
	{
		Random rand = new Random();
		for (int i = 0; i < size; i++) 
		{
            // Pick 2 random indices from front to back
            
			int j = rand.nextInt(size%arr.length);
			
			while(arr[j]==null)
			{
				j = rand.nextInt(size%arr.length);
			}
			
            int k = rand.nextInt(size%arr.length);
			
			while(arr[k]==null)
			{
				k = rand.nextInt(size%arr.length);
			}
			
            // Swap arr[i] with the element at random index
            T temp = arr[k];
            arr[k] = arr[j];
            arr[j] = temp;
        }
	}
	
	public String toString()
	{
		String result="Contents: ";
		int firstindex = front;
		for(int i=0;i<size;i++)
		{
			result=result+" "+arr[firstindex];
			firstindex=(firstindex+1)%arr.length;
		}
		return result;
	}
	/*INSERT EXCEPTION HANDLER HERE*/
	
}