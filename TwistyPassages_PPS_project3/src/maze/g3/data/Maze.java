package maze.g3.data;

import java.util.HashMap;
import java.util.Map;

import maze.g3.G3IndianaHosed;
import maze.g3.Logger;
import maze.g3.Logger.LogLevel;
import maze.g3.data.Room.RoomType;
import maze.g3.strategy.SystematicStrategy;

public class Maze {

	
	HashMap<Integer, Room> map = new HashMap<Integer, Room>();
	
	// we start from 2, as the treasure room already has item 1
	public int currentItemToDrop;
	public boolean isFirstRoom = true;
	public int roomCount = 1;
	public Room previousRoom;
	public Room currentRoom;
	public Room nextRoom;
	public int previousDoor;
	BagOfHolding bag = new BagOfHolding();
	
	public  Logger log = new Logger( LogLevel.DEBUG, this.getClass() );
	
	public Maze(int number_of_objects)
	{
		bag.fill(number_of_objects);
	}

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
			if (r.equals(currentRoom) || r.isNeverEnter) {
				continue;
			}
			if (!r.hasFullDoorInfo() && !r.isTreasureRoom()) {
				// If the child room has unresolved doors then go their.
				return doorNum;
			}
		}
		
		// if all the child rooms have full door info then we shud pick up
		// the object and take the door which leads to a room whose child room
		// info is incomplete
		for(Map.Entry<Integer, Room> entrySet : currentRoom.getNeighborMap()
				.entrySet())
		{
			Room cr1 = entrySet.getValue();
			int doorNum = entrySet.getKey();
			if(cr1.equals(currentRoom) || cr1.isNeverEnter)
			{
				continue;
			}
			
			for(Map.Entry<Integer, Room> entrySet2: cr1.getNeighborMap().entrySet())
			{
				Room cr2 = entrySet2.getValue();
				if(cr2.equals(currentRoom) || cr2.equals(cr1) || cr2.isNeverEnter)
				{
					continue;
				}
				
				if(!cr2.hasFullDoorInfo() && !cr2.isTreasureRoom())
				{
					return doorNum;
				}
			}
		}

		// TODO If all the children doors are resolved then u shud recursively check
		// all the grand. grand.. children 
		log.debug("all the children rooms are also resolved so returning 0***");
		// for now returning 0
		return SystematicStrategy.getRandomMove(currentRoom);
	}

	public Room getStartRoom()
	{
		return getRoomByType(RoomType.START);
	}
	
	public Room getTreasureRoom() {
		return getRoomByType(RoomType.TREASURE);
	}
	
	public Room getRoomByType(Room.RoomType type)
	{
		for(Room m : map.values())
		{
			if(m.getRoomType() == type)
				return m;
		}
		return null;
	}
	
	
	public Room createTreasureRoom() {
		// TODO all are self loops except 0
		Room room = new Room(roomCount);
		room.setRoomType(RoomType.TREASURE);
		addNewRoom(roomCount, room);
		room.setRoomLinksToSelfExcept0();
		// dont set the item as we are going to pick it up
		//room.setItem(1);
		roomCount++;
		return room;
	}

	public Room createNewRoomAndDropItem() {
		Room room = new Room(roomCount);
		addNewRoom(roomCount, room);
		currentItemToDrop = bag.getItem();
		room.setItem(currentItemToDrop);
		roomCount++;
		return room;
	}
	
	public Room createNewRoomWithOutItem() {
		Room room = new Room(roomCount);
		addNewRoom(roomCount, room);
		currentItemToDrop = 0;
		room.setItem(currentItemToDrop);
		room.isRoomWithNoItem = true;
		roomCount++;
		return room;
	}
	
	public Room createNewRoom() {
		roomCount++;
		Room room = new Room(roomCount);
		addNewRoom(roomCount, room);
		return room;
	}
	/**
	 * Initialize Maze
	 * adds the first room which is the starting room
	 * adds the room accessed through door 0 in the first move
	 */
	public void initializeMaze(){
		isFirstRoom=false;
		//STARTING ROOM Population
		roomCount=1;
		Room room = new Room(roomCount);
		room.doorRoomKey[0]=2;
		room.knownEdgesCount++;
		for(int i=1; i<=9;i++){
			room.doorRoomKey[i]=1;
			room.knownEdgesCount++;
			G3IndianaHosed.path.addPath(1, i, 1);
		}
		addNewRoom(roomCount,room);
		
		//First ROOM population
		previousRoom=room;
		currentRoom=room;
	}

	public BagOfHolding getBag() {
		return bag;
	}
	
	public void updateKnowledge() {
		for (Room r : map.values()) {
			inwardRoomKnowledge(r);
			r.calculateKnowledge();
			print("rId =" + r.getId() + " O=" + r.getItem() + " inK="
					+ r.getInwardRoomKnowledge() + " outK="
					+ r.getOutwardRoomKnowledge() + " Vst="+ r.getNumVisits() + " !Enter="+r.isNeverEnter );
		}
	}
	
	public void print(String info) {
		log.debug(info);
	}
	public void inwardRoomKnowledge(Room forRoom)
	{
		int count =0;
		for(Room r: map.values())
		{
			for(Room cr: r.getNeighborMap().values())
			{
				if(cr.equals(forRoom)){
					count++;
				}
			}
		}
		forRoom.setInwardRoomKnowledge(count);
	}

	public void printInfo() {
		for (Room r : map.values()) {
			print("roomId =" + r.getId() + " item=" + r.getItem()
					+ " knownEdgeCount=" + r.knownEdgesCount + " pathSegment=["
					+ r.pathSegment + "]");
			print("doorRoomKey Info ");
			print("{");
			for(int i =0; i < r.doorRoomKey.length; i++){
				print(i +"-->"+r.doorRoomKey[i]);
			}
			print("}");
		}
	}
}
