package maze.g3.data;

import java.util.Vector;
/**
 * Mantains complete History of all steps uptil now and items placed in the past
 */
public class History {
	 
	Vector<Edge> historyPath= new Vector<Edge>();
	Vector<Item> itemHistory = new Vector<Item>();
	
	/**
	 * Adds an edge to the starting path
	 * @param startingRoom
	 * @param door
	 * @param destinationRoom
	 */
	void addPath(int startingRoom, int door, int destinationRoom){
		Edge e= new Edge(startingRoom, door,destinationRoom);
		historyPath.add(e);
	}
	/**
	 * Add item to the item History
	 * @param i
	 */
	void addItem(Item i){
		itemHistory.add(i);
	}
	
	
	
}
