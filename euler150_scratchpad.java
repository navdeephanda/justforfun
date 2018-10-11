public class euler150_scratchpad
{
	public static void main(String [] args)
	{
		// TODO
		// 1. Make config files or command line options for test arrays

		// BOUNDARY CONDITIONS TEST CASE
		/*int[] inputArray = new int[20*21/2 +1];
		inputArray[0] = 20;
		for(int i = 1; i < inputArray.length ; i++)
			inputArray[i] = -i;*/
		
		// TEST CASE UNO
		//int[] inputArray = {4, 0, -3, -4, 1, 7, 2, 3, 5, 6, 7};
		
		// TEST CASE DOS
		//int[] inputArray = {10, 273519, -153582, 450905, 5108, 288723, -97242, 394845, -488152, 83831, 341882, 301473, 466844, -200869, 366094, -237787, 180048, -408705, 439266, 88809, 499780, -104477, 451830, 381165, -313736, -409465, -17078, -113359, 13804, 455019, -388898, 359349, -355680, 89743, 127922, 30841, 229524, -480269, -345658, 163709, -166968, 310679, 194330, 70849, -516036, -411781, -491602, 523333, 293360, -262753, -235646, -181751, -477980, 275459, 459414, 332301};
		
		// TEST CASE TRES
		//int[] inputArray = {7, 273519, -153582, 450905, 5108, 288723, -97242, 394845, -488152, 83831, 341882, 301473, 466844, -200869, 366094, -237787, 180048, -408705, 439266, 88809, 499780, -104477, 451830, 381165, -313736, -409465, -17078, -113359, 13804 };
		
		// TEST CASE CUATRO
		//int[] inputArray = {5, 0, -3, -4, 1, 7, 2, 3, 5, 6, 7, 1, 2, 3, 4, 5};
		
		// TEST CASE CINCO
		int[] inputArray = {6, 15, -14, -7, 20, -13, -5, -3, 8, 23, -26, 1, -4, -5, -18, 5, -16, 31, 2, 9, 28, 3};
		
		int numElements = inputArray.length-1;
		int depth = inputArray[0];		
		int [] minArr = new int[numElements];
		
		int index = 0;
		for(int i = 0 ; i < depth ; i++)
		{
			for(int j = 0 ; j < i+1 ; j++) //element index is at i,j where i is the row number and j is each element in row i
			{
				int IJ = index+1;
				int sum = inputArray[IJ];
				int min = sum;
				for(int x = i+1; x < depth ; x++)
				{
					for(int y = j; y <= x - i + j ; y++)
					{
						int XY = twoDtoOneDIndex(x,y);
						// USE THIS FOR DEBUGGING
						//System.out.println("Adding ["+x+"]["+y+"]="+inputArray[XY]+" to "+sum);
						sum = sum + inputArray[XY];
					}
					min = Math.min(sum, min);
				}
				minArr[index] = min;
				index++;
			}
		}
		
		int minimumSum = getMin(minArr);
		
		System.out.println("The minimum sum is " +minimumSum);

	}	
	
	public static int twoDtoOneDIndex(int row, int col)
	{
		return((((row+1)*(row))/2)+col+1);
	}
	
	public static int getMin(int[] inArray)
	{ 
		int minValue = inArray[0]; 
		for(int i=inArray.length-1;i>0;i--)
		{ 
			if(inArray[i] < minValue)
			{ 
			minValue = inArray[i]; 
			} 
		}
		return minValue;
	} 
}