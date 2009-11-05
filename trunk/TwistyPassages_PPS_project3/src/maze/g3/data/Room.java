package maze.g3.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import maze.g3.Logger;
import maze.g3.Logger.LogLevel;

public class Room {
	private Logger log = new Logger(LogLevel.DEBUG, this.getClass());
	public enum RoomType {
		START, TREASURE, OTHER
	}

	private int roomId;
	private RoomType roomType;
	//Nipun: Why is this item an integer and not the item object?
	private int item=0;
	//Counter for the number of knownEdges 
	public int knownEdgesCount;
	//Counter for knownRoom Connects
	public Vector knownRoomConnects;

	// The key is the door number, value is the Room reached by this door.
	public Map<Integer, Room> doorToRoomMap = new HashMap<Integer, Room>();
	
	// The index represents the door number and the RoomKey represents the room
	public int[] doorRoomKey= new int[10];

	// This is the door that the previous move has taken.-- Nipun : why is this a part of Room??
	private int doorTaken = -1;

	public Room(int roomId) {
		this.roomId = roomId;
	}

	public void setRoomLink(int doorNum, Room room) {
		Room room2 = doorToRoomMap.get(doorNum);
		if (room2 != null) {
			log.debug("A link from room_door " + roomId + "_"
						+ doorNum + " to room " + room.getId() + " exists already.");
			//throw new RuntimeException("A link from room_door " + roomId + "_"
				//	+ doorNum + " to room " + room.getId() + " exists already.");
		}
		doorToRoomMap.put(doorNum, room);
	}

	public boolean isStartRoom() {
		return roomType == RoomType.START;
	}

	public boolean isTreasureRoom() {
		return roomType == RoomType.TREASURE;
	}

	public void setRoomType(RoomType type) {
		roomType = type;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setItem(int object) {
		this.item = object;
	}
	
	public void setItem(Item item){
		this.item= item.getLabel();
	}

	public int getId() {
		return roomId;
	}

	public int getDoorTaken() {
		return doorTaken;
	}

	public void setDoorToTake(int doorToTake) {
		doorTaken = doorToTake;

	}

	public int getUnknownDoorExit() {
		int count = 0;
		while (doorToRoomMap.keySet().contains(count) && count++ < 10)
			;
		if (count == 10) {
			return -1;
		}
		return count;
	}

	public int getDoorToTake() {
		return doorTaken;
	}

	public int getItem() {
		return item;
	}

	public boolean hasFullDoorInfo() {
		return getUnknownDoorExit() == -1;
	}

	public Map<Integer, Room> getNeighborMap() {

		return Collections.unmodifiableMap(doorToRoomMap);
	}

	public void setUnknownLinksToSelf() {
		// This is required for treasure room
		int i;
		while ((i = getUnknownDoorExit()) != -1) {
			doorToRoomMap.put(i, this);
		}
	}
	
	public void setStartRoomLinksToSelf() {
		// START Room; except for 0 door all are self links
		for(int i = 1; i < 10 ; i++)
		{
			doorToRoomMap.put(i, this);
		}
	}

	public int avoidSelfLoopDoor() {

		for (Map.Entry<Integer, Room> entry : doorToRoomMap.entrySet()) {
			int door = entry.getKey();
			Room childRoom = entry.getValue();
			if (!childRoom.equals(this)) {
				return door;
			}
		}

		return 0;
	}
}
