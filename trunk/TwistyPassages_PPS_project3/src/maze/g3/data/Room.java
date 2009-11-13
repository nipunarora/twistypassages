package maze.g3.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import sun.awt.geom.AreaOp.IntOp;


import maze.g3.G3IndianaHosed;
import maze.g3.Logger;
import maze.g3.Logger.LogLevel;

public class Room {
	private Logger log = new Logger(LogLevel.DEBUG, this.getClass());
	public enum RoomType {
		START, TREASURE, OTHER, ENTRANCE_TO_TREASURE, ENTRANCE_TO_START
	}

	private int roomId;
	private RoomType roomType;
	//Nipun: Why is this item an integer and not the item object?
	private int item=0;
	//Counter for the number of knownEdges 
	public int knownEdgesCount;

	// The key is the door number, value is the Room reached by this door.
	public Map<Integer, Room> doorToRoomMap = new HashMap<Integer, Room>();
	
	// The index represents the door number and the RoomKey represents the room
	public int[] doorRoomKey= new int[10];
	
	//this edge represents a segment of the temporary path used to get to target room via a shortest distance 
	public Edge pathSegment;

	// This is the door that the previous move has taken.-- Nipun : why is this a part of Room??
	private int doorTaken = -1;

	// number of visits
	private int numVisit = 0;
	
	// Max of inward room knowledge will be 10
	private int inward_room_knowledge = 0;
	
	// Max of outward room knowledge will be 10
	private int outward_room_knowledge = 0;
	
	// Max of total room knowledge will be 20
	private int total_room_knowledge = 0;
	
	// This flag indicates that we have complete knowledge of this room
	// and one shud never enter the room 	 
	public boolean isNeverEnter = false;
	
	public boolean isRoomWithNoItem = false;
	
	
	@Override
	public String toString()
	{
		return ""+ roomId;
	}
	
	public void print(String info) {
		log.debug(info);
	}
	
	public Room(int roomId) {
		this.roomId = roomId;
	}
	


	public void setRoomLink(int doorNum, Room room) {
		Room room2 = doorToRoomMap.get(doorNum);
		if (room2 != null) {
			log.debug("A link from room_door " + roomId + "_"
						+ doorNum + " to room " + room.getId() + " exists already.");
			return; 
//			throw new RuntimeException("A link from room_door " + roomId + "_"
//					+ doorNum + " to room " + room.getId() + " exists already.");
		}
		if(room == null)
		{
			throw new RuntimeException("Trying to add a null room");
		}
		doorToRoomMap.put(doorNum, room);
	}
	
	public void calculateKnowledge() {
		outward_room_knowledge = doorToRoomMap.size();
		total_room_knowledge = outward_room_knowledge + inward_room_knowledge;

//		log.debug("Room " + roomId + ", outward_room_knowledge= "
//				+ outward_room_knowledge + " inward_room_knowledge= "
//				+ inward_room_knowledge);
	}
	
	public boolean hasFullKnowledge()
	{
		return total_room_knowledge == 20;
	}
	
	public boolean hasFullInwardKnowledge()
	{
		return inward_room_knowledge == 10;
	}
	
	public boolean hasOutwardKnowledge()
	{
		return outward_room_knowledge==10;
	}
	
	public boolean isKnowledgeGreaterOrEqualTo(int percent)
	{
		return total_room_knowledge >= percent;
	}

	public int getInwardRoomKnowledge()
	{
		return inward_room_knowledge;
	}
	
	public int getOutwardRoomKnowledge()
	{
		return outward_room_knowledge;
	}
	
	public int getTotalRoomKnowledge()
	{
		return total_room_knowledge;
	}
	
	public void visit()
	{
		numVisit++;
	}
	
	
	public int getNumVisits()
	{
		return numVisit;
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
	
	public void setRoomLinksToSelfExcept0() {
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
	/**
	 * returns the number of incoming edges known about this room
	 * @return
	 */
	public int incomingEdgesCount(){
		return G3IndianaHosed.path.destinationPaths.get(this.roomId).size();
	}
	
	/**
	 * returns the number of outgoing edges known about this room 
	 * @return
	 */
	public int outgoingEdgesCount(){
		return G3IndianaHosed.path.startPaths.get(this.roomId).size();
	}

	public void setInwardRoomKnowledge(int count) {

		inward_room_knowledge = count; 
	}

	public boolean isEntranceToStart() {
		// TODO Auto-generated method stub
		return roomType == RoomType.ENTRANCE_TO_START;
	}

	public boolean isEntranceToTreasure() {
		// TODO Auto-generated method stub
		return roomType == RoomType.ENTRANCE_TO_TREASURE;
	}



	public Room getRoomFrom(int doorNum) {
		// TODO Auto-generated method stub
		return doorToRoomMap.get(doorNum);
	}
}
