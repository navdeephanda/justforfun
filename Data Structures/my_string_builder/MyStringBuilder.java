// CS 0445 Spring 2018
// Read this class and its comments very carefully to make sure you implement
// the class properly.  Note the items that are required and that cannot be
// altered!  Generally speaking you will implement your MyStringBuilder using
// a singly linked list of nodes.  See more comments below on the specific
// requirements for the class.

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class.  
public class MyStringBuilder
{
	// These are the only three instance variables you are allowed to have.
	// See details of CNode class below.  In other words, you MAY NOT add
	// any additional instance variables to this class.  However, you may
	// use any method variables that you need within individual methods.
	// But remember that you may NOT use any variables of any other
	// linked list class or of the predefined StringBuilder or 
	// or StringBuffer class in any place in your code.  You may only use the
	// String class where it is an argument or return type in a method.
	private CNode firstC;	// reference to front of list.  This reference is necessary
							// to keep track of the list
	private CNode lastC; 	// reference to last node of list.  This reference is
							// necessary to improve the efficiency of the append()
							// method
	private int length;  	// number of characters in the list

	// You may also add any additional private methods that you need to
	// help with your implementation of the public methods.

	// Create a new MyStringBuilder initialized with the chars in String s
	public MyStringBuilder(String s)
	{
		if(s == null || s.length() == 0) //if string is initally empty
		{
			length = 0;
			firstC = null;
			lastC = null;
		}
		else //normal case
		{
			firstC = new CNode(s.charAt(0));
			length = 1;
			CNode currNode = firstC;
			for(int i = 1; i < s.length() ; i++)
			{
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			lastC = currNode;
		}
	}

	// Create a new MyStringBuilder initialized with the chars in array s
	public MyStringBuilder(char [] s)
	{
		if(s == null || s.length == 0)
		{
			length = 0;
			firstC = null;
			lastC = null;
		}
		else
		{
			firstC = new CNode(s[0]);
			length = 1;
			CNode currNode = firstC;
			for(int i = 1; i < s.length ; i++)
			{
				CNode newNode = new CNode(s[i]);
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
	}

	// Create a new empty MyStringBuilder
	public MyStringBuilder()
	{
		length = 0;
		firstC = null;
		lastC = null;
	}

	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(MyStringBuilder b)
	{
		if(lastC != null) //normal case
		{
			CNode currThis = lastC;
			CNode currb = b.firstC;
			for(int i = 0; i < b.length ; i++)
			{
				CNode newNode = new CNode(currb.data);
				currThis.next = newNode;
				currThis = currThis.next;
				currb = currb.next;
				length++;
			}
			lastC = currThis;
			
		}
		else //corner case, appending to empty string
		{
			firstC = b.firstC;
			lastC = b.lastC;
			length = b.length;
		}
		return this;
	}


	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(String s)
	{
		if(lastC != null)
		{
			CNode currNode = lastC;
			for(int i = 0; i < s.length() ; i++)
			{
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			lastC = currNode;
		}
		else
		{
			firstC = new CNode(s.charAt(0));
			length = 1;
			CNode currNode = firstC;
			for(int i = 1; i < s.length() ; i++)
			{
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			lastC = currNode;
		}
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char [] c)
	{
		if(lastC != null)
		{
			CNode currNode = lastC;
			for(int i = 0; i < c.length ; i++)
			{
				CNode newNode = new CNode(c[i]);
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			lastC = currNode;
		}
		else
		{
			firstC = new CNode(c[0]);
			length = 1;
			CNode currNode = firstC;
			for(int i = 1; i < c.length ; i++)
			{
				CNode newNode = new CNode(c[i]);
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			lastC = currNode;
		}
		return this;
	}

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char c)
	{
		if(lastC != null)
		{
			CNode newNode = new CNode(c);
			lastC.next = newNode;
			length++;
			lastC = newNode;
		}
		else
		{
			firstC = new CNode(c);
			length = 1;
			lastC = firstC;
		}
		return this;
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		if(index < 0 || index >= length)
			throw new IndexOutOfBoundsException();
		CNode currNode = firstC;
		for(int i = 0 ; i < index ; i++)
		{
			currNode = currNode.next;
		}
		return currNode.data;
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder, 
	// only remove up until the end of the MyStringBuilder. Be careful for 
	// special cases!
	public MyStringBuilder delete(int start, int end)
	{
		//if index is invalid or end is less than or equal to start
		if(start < 0 || start >= length || end <= start)
			return this;
		
		//sets node before start node of deleted section
		CNode deletingFrom = firstC;
		int nodeLocToDel = start - 1;
		if(start == 0)
		{
			nodeLocToDel = 0;
		}
		else
		{
			for(int i = 0 ; i < nodeLocToDel ; i++)
			{
				deletingFrom = deletingFrom.next;
			}
		}
			
		//sets node which you will delete from
		int endingLoc = end;
		CNode deletingTo = deletingFrom;
		if(endingLoc >= length)
		{
			endingLoc = length-1;
			deletingFrom.next = null;
		}
		else
		{
			for(int i = nodeLocToDel ; i < endingLoc ; i++)
			{
				deletingTo = deletingTo.next;
			}
			deletingFrom.next = deletingTo;
		}
			
		
		if(start == 0)
		{
			firstC = deletingTo;
		}
		
		else
		{}
		
		if(end >= length)
		{
			lastC = deletingTo;
			length = start;
		}
		
		else
			length = length - end + start;
		
		return this;
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).
	// Be careful for special cases!
	public MyStringBuilder deleteCharAt(int index)
	{
		if(index < 0 || index >= length)
			return this;
		if(index == 0)
		{
			firstC = firstC.next;
		}
		else
		{
			CNode nodeBefore = firstC;
			for(int i = 0 ; i < index-1 ; i++)
			{
				nodeBefore = nodeBefore.next;
			}
			CNode nodeToRemove = nodeBefore.next;
			CNode nodeAfter = nodeToRemove.next;
			nodeBefore.next=nodeAfter;
		}
		length--;
		return this;
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str)
	{
		int retIndex = -1;
		
		CNode currNode = firstC;
		int trackIndex = 0;
		boolean match = false;
		while(currNode!=null)
		{
			if(currNode.data == str.charAt(0))
			{
				currNode = currNode.next;
				int inputTrackIndex = 1;
				match = true;
				while(inputTrackIndex < str.length() && currNode != null)
				{
					if(currNode.data != str.charAt(inputTrackIndex))
						match = false;
					currNode = currNode.next;
					inputTrackIndex++;
				}
				if(currNode == null && inputTrackIndex < str.length())
				{
					match = false;
				}
				if(match)
					break;
				trackIndex = trackIndex + inputTrackIndex - 1;
			}
			else
			{
				currNode = currNode.next;
			}
			trackIndex++;
		}
		
		if(match)
			retIndex = trackIndex;
		
		return retIndex;
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str)
	{
		if(offset < 0 || offset > length)
			return this;
		
		if(offset == 0)
		{
			CNode newFirstNode = new CNode(str.charAt(0));
			length++;
			CNode currNode = newFirstNode;
			for(int i = 1 ; i < str.length() ; i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			currNode.next = firstC;
			firstC = newFirstNode;
		}
			
		else if(offset == length)
		{
			CNode currNode = lastC;
			for(int i = 0; i < str.length() ; i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			lastC = currNode;
		}
		
		else
		{
			CNode nodeToInsertAt = firstC;
			for(int i = 0 ; i < offset-1 ; i++)
			{
				nodeToInsertAt = nodeToInsertAt.next;
			}
			
			CNode nodeToLinkTo = nodeToInsertAt.next;
			
			nodeToInsertAt.next = new CNode(str.charAt(0));
			length++;
			CNode currNode = nodeToInsertAt.next;
			for(int i = 1 ; i < str.length() ; i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			currNode.next = nodeToLinkTo;
		}
		
		
		return this;
	}

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder insert(int offset, char c)
	{
		if(offset < 0 || offset > length)
			return this;
		
		if(offset == 0)
		{
			CNode newFirstNode = new CNode(c);
			length++;
			newFirstNode.next = firstC;
			firstC = newFirstNode;
		}
			
		else if(offset == length)
		{
			CNode newNode = new CNode(c);
			lastC.next = newNode;
			length++;
			lastC = newNode;
		}
		
		else
		{
			CNode nodeToInsertAt = firstC;
			for(int i = 0 ; i < offset-1 ; i++)
			{
				nodeToInsertAt = nodeToInsertAt.next;
			}
			
			CNode nodeToLinkTo = nodeToInsertAt.next;
			
			CNode newNode = new CNode(c);
			nodeToInsertAt.next = newNode;
			length++;
			newNode.next = nodeToLinkTo;
		}
		
		
		return this;
	}

	// Insert char array c into the current MyStringBuilder starting at index
	// index "offset" and return the current MyStringBuilder.  If "offset" is
	// invalid, do nothing.
	public MyStringBuilder insert(int offset, char [] c)
	{
		if(offset < 0 || offset > length)
			return this;
		
		if(offset == 0)
		{
			CNode newFirstNode = new CNode(c[0]);
			length++;
			CNode currNode = newFirstNode;
			for(int i = 1 ; i < c.length ; i++)
			{
				CNode newNode = new CNode(c[i]);
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			currNode.next = firstC;
			firstC = newFirstNode;
		}
			
		else if(offset == length)
		{
			CNode currNode = lastC;
			for(int i = 0; i < c.length ; i++)
			{
				CNode newNode = new CNode(c[i]);
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			lastC = currNode;
		}
		
		else
		{
			CNode nodeToInsertAt = firstC;
			for(int i = 0 ; i < offset-1 ; i++)
			{
				nodeToInsertAt = nodeToInsertAt.next;
			}
			
			CNode nodeToLinkTo = nodeToInsertAt.next;
			
			nodeToInsertAt.next = new CNode(c[0]);
			length++;
			CNode currNode = nodeToInsertAt.next;
			for(int i = 1 ; i < c.length ; i++)
			{
				CNode newNode = new CNode(c[i]);
				currNode.next = newNode;
				currNode = currNode.next;
				length++;
			}
			currNode.next = nodeToLinkTo;
		}
		
		
		return this;
	}

	// Return the length of the current MyStringBuilder
	public int length()
	{
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder replace(int start, int end, String str)
	{
		//if index is invalid or end is less than or equal to start
		if(start < 0 || start >= length || end <= start)
			return this;
		
		//creates the new set of nodes that correspond to the input string
		
		CNode babyFirstNode = new CNode(str.charAt(0));
		length++;
		CNode currNode = babyFirstNode;
		for(int i = 1 ; i < str.length() ; i++)
		{
			CNode newNode = new CNode(str.charAt(i));
			currNode.next = newNode;
			currNode = currNode.next;
			length++;
		}
		CNode babyLastNode = currNode;
		
		
		
		//sets node before start node of deleted section
		CNode replacingFrom = firstC;
		int nodeLocToRepl = start - 1;
		if(start == 0)
		{
			nodeLocToRepl = 0;
		}
		else
		{
			for(int i = 0 ; i < nodeLocToRepl ; i++)
			{
				replacingFrom = replacingFrom.next;
			}
		}
			
		//sets node which you will delete from
		int endingLoc = end;
		CNode replacingTo = replacingFrom;
		if(endingLoc >= length)
		{
			endingLoc = length-1;
			replacingFrom.next = babyFirstNode; //HERE IS WHERE THE INSERT LOGIC//
		}
		else
		{
			for(int i = nodeLocToRepl ; i < endingLoc ; i++)
			{
				replacingTo = replacingTo.next;
			}
			replacingFrom.next = babyFirstNode; //HERE IS WHERE THE INSERT LOGIC//
			babyLastNode.next = replacingTo;
		}
			
		
		if(start == 0)
		{
			babyLastNode.next = firstC;
			firstC = babyFirstNode; //DAT NEW NODE DOE//
			
		}
		
		else
		{}
		
		if(end >= length)
		{
			lastC = babyLastNode; //DAT NEW NODE DOE//
			length = start + str.length();
		}
		
		else
			length = length - end + start;
		
		return this;
	}

	// Reverse the characters in the current MyStringBuilder and then
	// return the current MyStringBuilder.
	public MyStringBuilder reverse()
	{
		 lastC = firstC;
		 CNode curr = firstC;
		 CNode next = null;
		 CNode prev = null;
		 while(curr!=null)
		 {
			 next = curr.next;
			 curr.next = prev;
			 prev = curr;
			 curr = next;
		 }
		 firstC = prev;
		return this;
	}
	
	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder
	public String substring(int start, int end)
	{
		//if index is invalid or end is less than or equal to start
		if(start < 0 || start >= length || end <= start)
			return null;
		
		//sets node to start at. ok also my code practically comments itself why are u readin dis jk lmao ik it's confusing
		CNode startFrom = firstC;
		int nodeLocToStartFrom = start;
		if(start == 0)
		{
			nodeLocToStartFrom = 0;
		}
		else
		{
			for(int i = 0 ; i < nodeLocToStartFrom ; i++)
			{
				startFrom = startFrom.next;
			}
		}
			
		//sets node upon which you cease your COUNTING
		int endingLoc = end;
		CNode endAt = startFrom;
		if(endingLoc >= length)
		{
			endingLoc = length-1;
		}
		else
		{
			for(int i = nodeLocToStartFrom ; i < endingLoc ; i++)
			{
				endAt = endAt.next;
			}
		}
		
		//char array creation out of nodes
		char [] output = new char[endingLoc-nodeLocToStartFrom];
		
		CNode currNode = startFrom;
		for(int i = 0 ; i < endingLoc-nodeLocToStartFrom ; i++)
		{
			output[i]=currNode.data;
			currNode = currNode.next;
		}
		
		return new String(output);
	}

	// Return the entire contents of the current MyStringBuilder as a String
	public String toString()
	{
		char [] arr = new char[length];
		CNode curr = firstC;
		int i = 0;
		while(curr != null)
		{
			arr[i] = curr.data;
			curr = curr.next;
			i++;
		}
		return(new String(arr));
	}

	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data and next fields directly.
	private class CNode
	{
		private char data;
		private CNode next;

		public CNode(char c)
		{
			data = c;
			next = null;
		}

		public CNode(char c, CNode n)
		{
			data = c;
			next = n;
		}
	}
}

