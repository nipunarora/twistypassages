package maze.g3.data;

import java.util.Vector;

public class Node {
	
	private int object;
	private int[] doors= new int[10]; 
	private Vector<Integer> neighbors= new Vector<Integer>();
	
	public void setObject(int object){
		this.object=object;
	}
	
	public void setDoor(int direction, int n){
		this.doors[direction]= n;
	}
	
	public void addNeighbor(int p){
		this.neighbors.add(p);
	}
}
