public class Assig2B
{
	public static void main(String [] args)
	{
		int N = Integer.valueOf(args[0]);
		long appendStart;
		long appendEnd;
		long deleteStart;
		long deleteEnd;
		long insertStart;
		long insertEnd;
		
		double elapsedAppend;
		double aveAppend;
		double elapsedDelete;
		double aveDelete;
		double elapsedInsert;
		double aveInsert;
		
		
		for(int i = 0 ; i < 3 ; i++)
		{
			if(i == 0)
			{
				System.out.println();
				//testing myStringBuilder
				MyStringBuilder linkedSB = new MyStringBuilder();
				
				//append
				appendStart = System.nanoTime();
				for(int j = 0 ; j < N ; j++)
					linkedSB.append('a');
				appendEnd = System.nanoTime();
				elapsedAppend = appendEnd - appendStart;
				aveAppend = elapsedAppend / N ;
				System.out.println("The total time for " + N + " append operations with my StringBuilder is " + elapsedAppend);
				System.out.println("The average time per append operation with my StringBuilder is " + aveAppend);
				
				//delete
				deleteStart = System.nanoTime();
				for(int j = 0 ; j < N ; j++)
					linkedSB.delete(0,1);
				deleteEnd = System.nanoTime();
				elapsedDelete = deleteEnd - deleteStart;
				aveDelete = elapsedDelete / N ;
				
				System.out.println("The total time for " + N + " delete operations with my StringBuilder is " + elapsedDelete);
				System.out.println("The average time per delete operation with my StringBuilder is " + aveDelete);
				
				//insert
				insertStart = System.nanoTime();
				int mid=0;
				for(int j = 0 ; j < N ; j++)
				{	
					mid = linkedSB.length()/2;
					linkedSB.insert(mid,'a');
				}
				insertEnd = System.nanoTime();
				elapsedInsert = insertEnd - insertStart;
				aveInsert = elapsedInsert / N ;
				
				System.out.println("The total time for " + N + " insert operations with my StringBuilder is " + elapsedInsert);
				System.out.println("The average time per insert operation with my StringBuilder is " + aveInsert);

				
			}
			
			if(i == 1)
			{
				System.out.println();
				//testing myStringBuilder
				StringBuilder javaSB = new StringBuilder();
				
				//append
				appendStart = System.nanoTime();
				for(int j = 0 ; j < N ; j++)
					javaSB.append('a');
				appendEnd = System.nanoTime();
				elapsedAppend = appendEnd - appendStart;
				aveAppend = elapsedAppend / N ;
				System.out.println("The total time for " + N + " append operations with the Java StringBuilder is " + elapsedAppend);
				System.out.println("The average time per append operation with the Java StringBuilder is " + aveAppend);
				
				//delete
				deleteStart = System.nanoTime();
				for(int j = 0 ; j < N ; j++)
					javaSB.delete(0,1);
				deleteEnd = System.nanoTime();
				elapsedDelete = deleteEnd - deleteStart;
				aveDelete = elapsedDelete / N ;
				
				System.out.println("The total time for " + N + " delete operations with the Java StringBuilder is " + elapsedDelete);
				System.out.println("The average time per delete operation with the Java StringBuilder is " + aveDelete);
				
				//insert
				insertStart = System.nanoTime();
				int mid=0;
				for(int j = 0 ; j < N ; j++)
				{	
					mid = javaSB.length()/2;
					javaSB.insert(mid,'a');
				}
				insertEnd = System.nanoTime();
				elapsedInsert = insertEnd - insertStart;
				aveInsert = elapsedInsert / N ;
				
				System.out.println("The total time for " + N + " insert operations with the Java StringBuilder is " + elapsedInsert);
				System.out.println("The average time per insert operation with the Java StringBuilder is " + aveInsert);
			}
			
			if(i == 2)
			{
				System.out.println();
				//testing myStringBuilder
				String str = new String();
				
				//append
				appendStart = System.nanoTime();
				for(int j = 0 ; j < N ; j++)
					str = str + 'a';
				appendEnd = System.nanoTime();
				elapsedAppend = appendEnd - appendStart;
				aveAppend = elapsedAppend / N ;
				System.out.println("The total time for " + N + " append operations with the Java String class is " + elapsedAppend);
				System.out.println("The average time per append operation with the Java String class is " + aveAppend);
				
				//delete
				deleteStart = System.nanoTime();
				for(int j = 0 ; j < N ; j++)
					str = str.substring(1,str.length());
				deleteEnd = System.nanoTime();
				elapsedDelete = deleteEnd - deleteStart;
				aveDelete = elapsedDelete / N ;
				
				System.out.println("The total time for " + N + " delete operations with the Java String class is " + elapsedDelete);
				System.out.println("The average time per delete operation with the Java String class is " + aveDelete);
				
				//insert
				insertStart = System.nanoTime();
				int mid=0;
				for(int j = 0 ; j < N ; j++)
				{	
					mid = str.length()/2;
					str = str.substring(0,mid) + 'a' + str.substring(mid, str.length());
				}
				insertEnd = System.nanoTime();
				elapsedInsert = insertEnd - insertStart;
				aveInsert = elapsedInsert / N ;
				
				System.out.println("The total time for " + N + " insert operations with the Java String class is " + elapsedInsert);
				System.out.println("The average time per insert operation with the Java String class is " + aveInsert);
			}
		}
	}
}