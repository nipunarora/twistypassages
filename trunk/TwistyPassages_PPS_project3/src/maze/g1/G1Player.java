package maze.g1;

import java.util.Random;

import maze.ui.Move;
import maze.ui.Player;


public class G1Player implements Player 
{
	
	Random r;
	int[] possibleMoves = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	int[] randomMoves;
	int currentRandomMove;
	int currentMove;
	
	public G1Player()
	{
		r = new Random();
		currentRandomMove = 0;
		currentMove = 0;
		randomMoves = makePermutation(possibleMoves);
	}

	public Move move(int object_detail,int number_of_objects, int total_rounds)
    {
    	int dropID = 0; //What item we will drop. Initially none.
    	
    	//Take next random move
    	int mv = randomMoves[currentRandomMove];
    	
    	//Drop object 2 in start room.
    	if(currentMove == 0)
    	{
    		mv = 0;
    		dropID = 2;
    	}
    		
    	currentMove++;
    	
    	//Take exit 0 in start or treasure room.
    	//Assumes object 1 in treasure room, object 2 in start room always.
    	if(object_detail == 1 || object_detail == 2)
    		mv = 0;    
    	
        Move myMove = new Move(mv,dropID);
        
        //If finished random walk, permute array again
        currentRandomMove++;
        if(currentRandomMove >= randomMoves.length)
        {
        	currentRandomMove = 0;
        	randomMoves = makePermutation(possibleMoves);
        }
        
        return myMove;
    }
    
    /**
     * Makes a random permutation of integer array arr.
     * Non-destructive. Does not modify arr.
     * @param arr An integer array.
     * @return A new permutation of arr.
     */
    public int[] makePermutation(int[] arr)
    {
    	int n = arr.length;
    	int[] a = new int[n];
    	
    	//Copy array to avoid changing arr
    	for(int i = 0; i < n; i++)
    		a[i] = arr[i];
    	
    	for(int i = 0; i < n; i++)
    	{
    		//Pick two random elements
    		int i1 = r.nextInt(n);
    		int i2 = r.nextInt(n);
    		
    		//Swap them
    		int temp = a[i1];
    		a[i1] = a[i2];
    		a[i2] = temp;
    	}
    	
    	return a;
    }
    
    public void printArray(int[] a)
    {
    	System.out.print("[");
    	for(int i = 0; i < a.length; i++)
    	{
    		System.out.print(a[i]);
    		if(i < a.length - 1)
    			System.out.print(",");
    		System.out.print("\t");
    	}
    	System.out.print("]");
    }

}
