package maze.g3.data;

import java.util.Vector;
/**
 * Mantains complete History of all steps uptil now and items placed in the past
 */
public class History {
	 
	Vector<Edge> historyPath= new Vector<Edge>();
	Vector<Integer> itemHistory = new Vector<Integer>();
	
	/**
	 * Adds an edge to the starting path
	 * @param startingRoom
	 * @param door
	 * @param destinationRoom
	 */
	public void addPath(int startingRoom, int door, int destinationRoom){
		Edge e= new Edge(startingRoom, door,destinationRoom);
		historyPath.add(e);
	}
	/**
	 * Add item to the item History
	 * @param i
	 */
	void addItem(Integer i){
		itemHistory.add(i);
	}
	/**
	 * get the last Edge Traversed
	 * @return
	 */
	public Edge getLastEdge(){
		Edge e= historyPath.get(historyPath.size());
		return  e;
	}
	
	/**
	 * get the id of the last Room encountered from the history
	 * @return
	 */
	public int getLastRoom(){
		int lastRoomId= historyPath.get(historyPath.size()).StartRoom;
		return lastRoomId;
	}
	
	public int getLastDoor(){
		int lastDoor=historyPath.get(historyPath.size()).door;
		return lastDoor;
	}
	
}
