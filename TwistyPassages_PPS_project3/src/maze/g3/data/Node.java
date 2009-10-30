package maze.g3.data;

import java.util.Vector;

public class Node {
	
	private int object;
	private Node[] doors= new Node[10]; 
	private Vector<Integer> neighbors= new Vector<Integer>();
	
	public void setObject(int object){
		this.object=object;
	}
	
	public void setDoor(int direction, Node n){
		this.doors[direction]= n;
	}
	
	public void addNeighbor(int p){
		this.neighbors.add(p);
	}
}
