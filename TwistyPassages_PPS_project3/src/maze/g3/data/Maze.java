package maze.g3.data;

import java.util.HashMap;
import java.util.Map;

import maze.g3.Logger;
import maze.g3.Logger.LogLevel;
import maze.g3.data.Room.RoomType;

public class Maze {

	
	HashMap<Integer, Room> map = new HashMap<Integer, Room>();
	
	// we start from 2, as the treasure room already has item 1
	public int itemsDroppedCount = 2;
	public int currentItemToDrop;
	public boolean isFirstRoom = true;
	public int roomCount = 1;
	public Room previousRoom;
	public Room currentRoom;
	
	public  Logger log = new Logger( LogLevel.DEBUG, this.getClass() );

	public int getNewRoomId() {
		return map.size() + 1;
	}

	public void addNewRoom(int roomId, Room n) {
		map.put(roomId, n);
	}

	public void updateRoom(Room room) {
		map.put(room.getId(), room);
	}

	public Room getRoom(int roomId) {
		return map.get(roomId);
	}

	public int sizeOfMaze() {
		return this.map.size();
	}

	public Room getRoomByItem(int item) {

		for (Room r : map.values()) {
			if (r.getItem() == item) {
				return r;
			}
		}
		return null;
	}

	public int getDoorLeadingToChildRoomsWithUnknownDoor(Room currentRoom) {
		// If the currentRoom's has information about all the doors 
		// then take the door whose children has unknown doors.
		// Skip treasure room as it has already been visited and even if it 
		// has unknown doors all are self loops
		if (!currentRoom.hasFullDoorInfo()) {
			return currentRoom.getUnknownDoorExit();
		}

		for (Map.Entry<Integer, Room> entrySet : currentRoom.getNeighborMap()
				.entrySet()) {
			Room r = entrySet.getValue();
			int doorNum = entrySet.getKey();
			if (r.equals(currentRoom)) {
				continue;
			}
			if (!r.hasFullDoorInfo() && !r.isTreasureRoom()) {
				// If the child room has unresolved doors then go their.
				return doorNum;
			}
		}

		// TODO If all the children doors are resolved then u shud recursively check
		// all the grand. grand.. children 
		log.debug("all the children rooms are also resolved so returning 0***");
		// for now returning 0
		return 0;
	}

	public Room getTreasureRoom() {
		// TODO Auto-generated method stub
		return getRoomByItem(1);
	}
	
	
	public Room createTreasureRoom() {
		// TODO we can save rounds in filling the information about the treasure
		// room doors
		// if we are successful in finding atleast one door that does not lead
		// to self.
		Room room = new Room(roomCount);
		room.setRoomType(RoomType.TREASURE);
		addNewRoom(roomCount, room);

		// Treasure room always has item 1
		room.setItem(1);
		roomCount++;
		return room;
	}

	public Room createNewRoomAndDropItem() {
		Room room = new Room(roomCount);
		addNewRoom(roomCount, room);
		currentItemToDrop = itemsDroppedCount;
		room.setItem(currentItemToDrop);
		roomCount++;
		itemsDroppedCount++;
		return room;
	}
	public Room createNewRoom() {
		Room room = new Room(roomCount);
		addNewRoom(roomCount, room);
		roomCount++;
		return room;
	}
	/**
	 * Initialize Maze
	 * adds the first room which is the starting room
	 * adds the room accessed through door 0 in the first move
	 */
	public void initializeMaze(){
		//STARTING ROOM Population
		Room room = new Room(roomCount);
		room.doorRoomKey[0]=2;
		room.knownEdgesCount++;
		for(int i=1; i<=9;i++){
			room.doorRoomKey[i]=1;
			room.knownEdgesCount++;
		}
		addNewRoom(roomCount,room);
		
		//First ROOM population
		roomCount=2;
		Room r= new Room(roomCount);
		addNewRoom(roomCount,r);
		previousRoom=room;
		currentRoom=r;
	}
}
