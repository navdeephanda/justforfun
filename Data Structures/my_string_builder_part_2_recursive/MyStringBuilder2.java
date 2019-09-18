// CS 0445 Spring 2018
// Read this class and its comments very carefully to make sure you implement
// the class properly.  The data and public methods in this class are identical
// to those MyStringBuilder, with the exception of the two additional methods
// shown at the end.  You cannot change the data or add any instance
// variables.  However, you may (and will need to) add some private methods.
// No iteration is allowed in this implementation. 

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class.  
public class MyStringBuilder2
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

	// Create a new MyStringBuilder2 initialized with the chars in String s
	public MyStringBuilder2(String s)
	{
		
      if (s != null && s.length() > 0)

            makeBuilder(s, 0);

      else  // no String so initialize empty MyStringBuilder2

      {

            length = 0;

            firstC = null;

            lastC = null;

      }
	}
	
	private void makeBuilder(String s, int pos)

	{

		  // Recursive case – we have not finished going through the String

		  if (pos < s.length()-1)

		  {

				// Note how this is done – we make the recursive call FIRST, then

				// add the node before it.  In this way the LAST node we add is

				// the front node, and it enables us to avoid having to make a

				// special test for the front node.  However, many of your

				// methods will proceed in the normal front to back way.

				makeBuilder(s, pos+1);

				firstC = new CNode(s.charAt(pos), firstC);

				length++;

		  }

		  else if (pos == s.length()-1) // Special case for last char in String

		  {                             // This is needed since lastC must be

										// set to point to this node

				firstC = new CNode(s.charAt(pos));

				lastC = firstC;

				length = 1;

		  }

		  else  // This case should never be reached, due to the way the

				// constructor is set up.  However, I included it as a

		  {     // safeguard (in case some other method calls this one)

				length = 0;

				firstC = null;

				lastC = null;

		  }

	}

	// Create a new MyStringBuilder2 initialized with the chars in array s
	public MyStringBuilder2(char [] s)
	{
		
      if (s != null && s.length > 0)

            makeBuilder(s, 0);

      else  // no String so initialize empty MyStringBuilder2

      {

            length = 0;

            firstC = null;

            lastC = null;

      }
	}
	
	private void makeBuilder(char [] s, int pos)

	{

		  // Recursive case – we have not finished going through the String

		  if (pos < s.length-1)
			  

		  {

				// Note how this is done – we make the recursive call FIRST, then

				// add the node before it.  In this way the LAST node we add is

				// the front node, and it enables us to avoid having to make a

				// special test for the front node.  However, many of your

				// methods will proceed in the normal front to back way.

				makeBuilder(s, pos+1);

				firstC = new CNode(s[pos], firstC);

				length++;

		  }

		  else if (pos == s.length-1) // Special case for last char in String

		  {                             // This is needed since lastC must be

										// set to point to this node

				firstC = new CNode(s[pos]);

				lastC = firstC;

				length = 1;

		  }

		  else  // This case should never be reached, due to the way the

				// constructor is set up.  However, I included it as a

		  {     // safeguard (in case some other method calls this one)

				length = 0;

				firstC = null;

				lastC = null;

		  }

	}
	

	// Create a new empty MyStringBuilder2
	public MyStringBuilder2()
	{
		length = 0;

		firstC = null;

		lastC = null;
	}

	// Append MyStringBuilder2 b to the end of the current MyStringBuilder2, and
	// return the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(MyStringBuilder2 b)
	{
		CNode oldLastC = lastC;
		CNode oldFirstC = firstC;
		CNode currB = b.firstC;
		int oldLength = length;
		if(b==null)
		{}
		if(lastC == null)
		{
			recAppend(b, 0, currB);
		}
		else
		{
			recAppend(b, 0 , currB);
			oldLastC.next = firstC;
			firstC = oldFirstC;
			length = length + oldLength;
		}
		return this;
	}


	// Append String s to the end of the current MyStringBuilder2, and return
	// the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(String s)
	{
		CNode oldLastC = lastC;
		CNode oldFirstC = firstC;
		int oldLength = length;
		if(s==null)
		{}
		if(lastC == null)
		{
			makeBuilder(s, 0);
		}
		else
		{
			makeBuilder(s, 0);
			oldLastC.next = firstC;
			firstC = oldFirstC;
			length = length + oldLength;
		}
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder2, and
	// return the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(char [] c)
	{
		CNode oldLastC = lastC;
		CNode oldFirstC = firstC;
		int oldLength = length;
		if(c==null)
		{}
		if(lastC == null)
		{
			makeBuilder(c, 0);
		}
		else
		{
			makeBuilder(c, 0);
			oldLastC.next = firstC;
			firstC = oldFirstC;
			length = length + oldLength;
		}
		return this;
	}
	
	private void recAppend(MyStringBuilder2 builder, int pos, CNode currNodeB)
	{
		if (pos < builder.length-1) //recursive case
		{
            recAppend(builder, pos+1, currNodeB.next);
            firstC = new CNode(currNodeB.data, firstC);
            length++;
		}

		else if (pos == builder.length-1) //base case (last char of builder to be added)
		{
			firstC = new CNode(currNodeB.data);
			lastC = firstC;
			length = 1;
		}
	}

	// Append char c to the end of the current MyStringBuilder2, and
	// return the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(char c)
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

	// Return the character at location "index" in the current MyStringBuilder2.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		if(index < 0 || index >= length)
			throw new IndexOutOfBoundsException();
		CNode currNode = firstC;
		
		//RECURSE RECURSE //record scratch// cha cha real smooth
		char ans = recCharAt(0, index, currNode);
		
		return ans;
		
	}
	
	private char recCharAt(int pos, int index, CNode curr)
	{
		char tenCharAtGold; 
		if(pos == index)
		{
			return curr.data;
		}
		else
		{
			tenCharAtGold = recCharAt(pos+1, index, curr.next);
		}
		return tenCharAtGold;
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder2, and return the current MyStringBuilder2.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder2 as is).  If "end" is past the end of the MyStringBuilder2, 
	// only remove up until the end of the MyStringBuilder2. Be careful for 
	// special cases!
	public MyStringBuilder2 delete(int start, int end)
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
			deletingFrom = navTo(deletingFrom, 0, nodeLocToDel);
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
			deletingTo = navTo(deletingFrom, nodeLocToDel, endingLoc);
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
	// MyStringBuilder2 and return the current MyStringBuilder2.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder2 as is).
	// Be careful for special cases!
	public MyStringBuilder2 deleteCharAt(int index)
	{
		if(index < 0 || index >= length)
		{
			return this;
		}
		if(index == 0)
		{
			firstC = firstC.next;
		}
		else
		{
			CNode nodeBefore = firstC;
			nodeBefore = navTo(nodeBefore, 0, index-1);
			CNode nodeToRemove = nodeBefore.next;
			CNode nodeAfter = nodeToRemove.next;
			nodeBefore.next=nodeAfter;
		}
		length--;
		return this;
	}
	
	//recursive version of a loop iterator through a linked list
	private CNode navTo(CNode startNode, int startLoc, int endLoc)
	{
		CNode endNode;
		if(startLoc == endLoc)
		{
			return startNode;
		}
		else
			endNode = navTo(startNode.next, startLoc+1, endLoc);
		return endNode;
	}

	// Find and return the index within the current MyStringBuilder2 where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder2.  If str does not match any sequence of characters
	// within the current MyStringBuilder2, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str) //FINISH THIS METHOD PLEASE
	{
		int retIndex = -1;
		CNode currNode = firstC;
		return (recIndexOf(str, 0, 0, firstC, retIndex));
	}
	
		/*char tenCharAtGold; 
		if(pos == index)
		{
			return curr.data;
		}
		else
		{
			tenCharAtGold = recCharAt(pos+1, index, curr.next);
		}
		return tenCharAtGold;*/
	
	private int recIndexOf(String inputString, int stringLoc, int i, CNode currNode, int returnIndex) //i tracks the current index if the string is a potential match
	{ 
		if(currNode.data == inputString.charAt(i))
		{
			if(i==0)
				returnIndex = stringLoc;
			if(i==inputString.length() - 1)
				return returnIndex;
			if(currNode == lastC)
				return -1;
			returnIndex=recIndexOf(inputString, stringLoc+1, i+1, currNode.next, returnIndex);
		}
		else
		{
			if(currNode == lastC)
				return -1;
			else
			{
				returnIndex = -1;
				returnIndex = recIndexOf(inputString, stringLoc+1, 0, currNode.next, returnIndex);
			}
			
		} 
		return returnIndex;
	}

	// Insert String str into the current MyStringBuilder2 starting at index
	// "offset" and return the current MyStringBuilder2.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder2 insert(int offset, String str)
	{
		if(offset < 0 || offset > length)
			return this;
		
		if(offset == 0)
		{
			CNode newFirstNode = new CNode(str.charAt(0));
			length++;
			CNode currNode = newFirstNode;
			currNode=navToAndAddChar(currNode, 1, str.length()-1, str);
			currNode.next = firstC;
			firstC = newFirstNode;
		}
			
		else if(offset == length)
		{
			append(str);
		}
		
		else
		{
			CNode nodeToInsertAt = firstC;
			
			nodeToInsertAt = navTo(nodeToInsertAt, 0, offset-1);
			
			CNode nodeToLinkTo = nodeToInsertAt.next;
			
			nodeToInsertAt.next = new CNode(str.charAt(0));
			length++;
			CNode currNode = nodeToInsertAt.next;
			currNode=navToAndAddChar(currNode, 1, str.length()-1, str);
			currNode.next = nodeToLinkTo;
		}
		
		return this;
	}
	
	private CNode navToAndAddChar(CNode startNode, int startLoc, int endLoc, String inputString)
	{
		CNode endNode;
		if(startLoc == endLoc)
		{
			CNode newNode = new CNode(inputString.charAt(startLoc));
			startNode.next = newNode;
			length++;
			return startNode.next;
		}
		else
		{
			CNode newNode = new CNode(inputString.charAt(startLoc));
			startNode.next = newNode;
			length++;
			endNode = navToAndAddChar(startNode.next, startLoc+1, endLoc, inputString);
		}
		return endNode;
	}
	
	

	// Insert character c into the current MyStringBuilder2 at index
	// "offset" and return the current MyStringBuilder2.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder2 insert(int offset, char c)
	{
		return this;
	}

	// Insert char array c into the current MyStringBuilder2 starting at index
	// index "offset" and return the current MyStringBuilder2.  If "offset" is
	// invalid, do nothing.
	public MyStringBuilder2 insert(int offset, char [] c)
	{
		if(offset < 0 || offset > length)
			return this;
		
		if(offset == 0)
		{
			CNode newFirstNode = new CNode(c[0]);
			length++;
			CNode currNode = newFirstNode;
			currNode=navToAndAddChar(currNode, 1, c.length-1, c);
			currNode.next = firstC;
			firstC = newFirstNode;
		}
			
		else if(offset == length)
		{
			append(c);
		}
		
		else
		{
			CNode nodeToInsertAt = firstC;
			
			nodeToInsertAt = navTo(nodeToInsertAt, 0, offset-1);
			
			CNode nodeToLinkTo = nodeToInsertAt.next;
			
			nodeToInsertAt.next = new CNode(c[0]);
			length++;
			CNode currNode = nodeToInsertAt.next;
			currNode=navToAndAddChar(currNode, 1, c.length-1, c);
			currNode.next = nodeToLinkTo;
		}
		
		return this;
	}
	
	private CNode navToAndAddChar(CNode startNode, int startLoc, int endLoc, char [] inputArr)
	{
		CNode endNode;
		if(startLoc == endLoc)
		{
			CNode newNode = new CNode(inputArr[startLoc]);
			startNode.next = newNode;
			length++;
			return startNode.next;
		}
		else
		{
			CNode newNode = new CNode(inputArr[startLoc]);
			startNode.next = newNode;
			length++;
			endNode = navToAndAddChar(startNode.next, startLoc+1, endLoc, inputArr);
		}
		return endNode;
	}

	// Return the length of the current MyStringBuilder2
	public int length()
	{
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder2, then insert String "str" into the current
	// MyStringBuilder2 starting at index "start", then return the current
	// MyStringBuilder2.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder2, only delete until the
	// end of the MyStringBuilder2, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder2 replace(int start, int end, String str)
	{
		//if index is invalid or end is less than or equal to start
		if(start < 0 || start >= length || end <= start)
			return this;
		
		//creates the new set of nodes that correspond to the input string
		CNode babyFirstNode = new CNode(str.charAt(0));
		length++;
		CNode babyLastNode = navToAndAddChar(babyFirstNode, 1, str.length()-1, str);
		
		//sets node before start node of deleted section
		CNode replacingFrom;
		int nodeLocToRepl;
		if(start == 0)
		{
			nodeLocToRepl = 0;
			replacingFrom = firstC;
		}
		else
		{
			nodeLocToRepl = start - 1;
			replacingFrom = navTo(firstC, 0, nodeLocToRepl);
		}
			
		//sets node to delete to and inserts new node
		int endingLoc = end;
		CNode replacingTo;
		if(endingLoc >= length)
		{
			endingLoc = length-1;
			replacingFrom.next = babyFirstNode; //HERE IS WHERE THE INSERT LOGIC//
		}
		else
		{
			replacingTo = navTo(replacingFrom, nodeLocToRepl, endingLoc);
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

	// Reverse the characters in the current MyStringBuilder2 and then
	// return the current MyStringBuilder2.
	public MyStringBuilder2 reverse()
	{
		lastC = firstC;
		CNode curr = firstC;
		CNode next = null;
		CNode prev = null;
		 
		recReverse(next, curr, prev);
		 
		return this;
	}
	
	public void recReverse(CNode nextC, CNode currC, CNode prevC)
	{
		if(currC == null)
			firstC = prevC;
		else
		{
			nextC = currC.next;
			currC.next = prevC;
			prevC = currC;
			currC = nextC;
			recReverse(nextC, currC, prevC);
		}
	}
	
	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder2
	public String substring(int start, int end)
	{
		//if index is invalid or end is less than or equal to start
		if(start < 0 || start >= length || end <= start)
			return null;
		
		//sets node to start at. ok also my code practically comments itself why are u readin dis jk lmao ik it's confusing
		CNode startFrom = firstC;
		startFrom = navTo(startFrom, 0, start);
			
		//sets location upon which you stop (hammer time)
		
		int endingLoc = end;
		if(endingLoc >= length)
		{
			endingLoc = length-1;
		}
		
		//char array creation out of nodes
		char [] output = new char[endingLoc-start];
		
		getSubstring(output, 0, endingLoc-start-1, startFrom);
		
		return new String(output);
	}
	
	private void getSubstring(char [] c, int pos, int endpos, CNode curr)
	{
		if (pos == endpos)
		{
			c[pos] = curr.data;
		}
		else
		{
			c[pos] = curr.data;
			getSubstring(c, pos+1, endpos, curr.next);
		}
	}

	// Return the entire contents of the current MyStringBuilder2 as a String
	
	public String toString()
	{
		char [] c = new char[length];
		getString(c, 0, firstC);
		return (new String(c));
	}
	
	// Here we need the char array to store the characters, the pos value to
	// indicate the current index in the array and the curr node to access
	// the data in the actual MyStringBuilder2.  Note that these rec methods
	// are private – the user of the class should not be able to call them.

	private void getString(char [] c, int pos, CNode curr)
	{
		if (curr != null)
		{
			c[pos] = curr.data;
			getString(c, pos+1, curr.next);
		}
	}

	// Find and return the index within the current MyStringBuilder2 where
	// String str LAST matches a sequence of characters within the current
	// MyStringBuilder2.  If str does not match any sequence of characters
	// within the current MyStringBuilder2, return -1.  Think carefully about
	// what you need to do for this method before implementing it.  For some
	// help with this see the Assignment 3 specifications.
	public int lastIndexOf(String str)
	{
		int returnIndex=-1;
		return recLastIndexOf(firstC, 0, str, returnIndex);
	}
	
	public int recLastIndexOf(CNode curr, int strPos, String inString, int retInd)
	{
		/*MyStringBuilder2 builder = new MyStringBuilder2("hello");
		String string = "hello";
		System.out.println("Are the test strings equal? "+isEqual(builder.firstC, string, 0));*/
		if(curr == null)
			return -1;
		retInd = recLastIndexOf(curr.next, strPos+1, inString, retInd);
		if(retInd == -1)
		{
			if(isEqual(curr, inString, 0))
			{
				retInd = strPos;
				return retInd;
			}
			else
				return -1;
		}
		return retInd;
	}
	
	public boolean isEqual(CNode n, String str, int i)
	{
		if(n == null && str == null)
			return true;
		else if(n == null || str == null)
			return false;
		else if(i == str.length()-1&& n.data == str.charAt(i))
			return true;
		else if(n.data != str.charAt(i))
			return false;
		else
			return isEqual(n.next, str, i+1);
	}
	
	// Find and return an array of MyStringBuilder2, where each MyStringBuilder2
	// in the return array corresponds to a part of the match of the array of
	// patterns in the argument.  If the overall match does not succeed, return
	// null.  For much more detail on the requirements of this method, see the
	// Assignment 3 specifications.
	public MyStringBuilder2 [] regMatch(String [] pats)
	{
		MyStringBuilder2[] ansArray = new MyStringBuilder2[pats.length];
		for(int i = 0 ; i < pats.length ; i++)
			ansArray[i]= new MyStringBuilder2();
		CNode currBuilderNode = firstC;
		boolean match = recRegMatch(pats, currBuilderNode, 0, ansArray);
		if(match)
			return ansArray;
		return null;
	}
	
	private boolean recRegMatch(String [] patterns, CNode currNode, int i, MyStringBuilder2 [] answerArray)//i is current pattern index
	{
		boolean ans = false;
		if(currNode == null&&i!=patterns.length-1)
		{
			return false;
		}
		if(charIsInPattern(currNode.data, patterns[i], i))
		{
			answerArray[i].append(currNode.data);
			ans = recRegMatch(patterns, currNode.next, i, answerArray);
		}
		if(i==patterns.length-1)
			return true;
		ans = recRegMatch(patterns, currNode, i+1, answerArray);
		ans = recRegMatch(patterns, currNode.next, i, answerArray);
		return ans;
	}
	
	private boolean charIsInPattern(char a, String s, int pos)
	{
		boolean ans;
		if(pos == s.length())
			return false;
		if(s.charAt(pos)== a)
			return true;
		ans = charIsInPattern(a, s, pos+1);
		return ans;
	}

	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder2 class MAY access the
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

