package maze.g5;

import java.util.ArrayList;

/**
 * Class representing any information about a room
 * 
 * This includes paths from doors to other rooms
 * 
 * and perhaps history
 * 
 * @author shary
 *
 */
public class Room {
	
	/** Array of unused doors of this room **/
	private ArrayList<Integer> unusedDoorList = new ArrayList<Integer>();
	
	/** Array of rooms. Index of array represents the door number */
	private Room[] connectedRooms;
	
	/** Object in room. Will be 0 if no object otherwise will be 1 to n */
	private int objectInRoom = 0;

	/**
	 * 
	 */
	public Room() {
		connectedRooms = new Room[Constants.NO_OF_DOORS];
		for (int i = 0; i < Constants.NO_OF_DOORS; i++) {
			connectedRooms[i] = null;
			unusedDoorList.add(i);
		}
	}

	/**
	 * @return the connectedRooms
	 */
	public Room[] getConnectedRooms() {
		return connectedRooms;
	}

	/**
	 * @param connectedRooms_ the connectedRooms to set
	 */
	public void setConnectedRooms(Room[] connectedRooms_) {
		connectedRooms = connectedRooms_;
	}

	/**
	 * @return the object
	 */
	public int getObject() {
		return objectInRoom;
	}

	/**
	 * @param object_ the object to set
	 */
	public void setObject(int object_) {
		objectInRoom = object_;
	}
	
	/**
	 * @TODO must merge information about rooms
	 * @param room_
	 */
	public void mergeRoom(Room room_){

	}
	
	/**
	 * Returns a list of unused doors
	 * @return
	 */
	public ArrayList<Integer> getUnusedDoorList(){
		return unusedDoorList;
	}
	
	/**
	 * Sets the door as going to the room given
	 * @param door
	 * @param room
	 */
	public void updateDoorToRoom(int door,Room room){
		connectedRooms[door] = room;
	}
	
}
