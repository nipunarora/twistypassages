package maze.g5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import maze.ui.Move;
import maze.ui.Player;

/**
 * G5 Player ....
 * 
 * @author shary
 *
 */
public class G5Player implements Player {
	/*
	 * TO SET THE RANDOM SEED, PLEASE CHANGE THE STATEMENT IMMEDIATELY FOLLOWING:
	 */
	private long   randSeed = (long)(Math.random()*1000000) /* replace with customized seed */;
	private Random rand = new Random(randSeed);

	/** Set of rooms **/
	private HashSet<Room> rooms = new HashSet<Room>();
	
	/** Mapping from object to room in which the object was left **/
	private HashMap<Integer,Room> objectToRoomMap = new HashMap<Integer, Room>();
	
	/** List of unused objects from which object can be dequeued**/
	private ArrayList<Integer> unusedObjectList = null;
	
	/** Set of used objects **/
	private HashSet<Integer> usedObjectSet = null;
	
	/** Total number of rounds **/
	private int totalRounds = -1;
	
	/** History of rooms visited **/
	private ArrayList<PastMove> history = new ArrayList<PastMove>();
	
	@Override
	public Move move(int object_detail_, int number_of_objects_,
			int total_rounds_) {
		
		/** Initialize information **/
		init(number_of_objects_,total_rounds_);
		
		/** Have a default move set **/
		int doorToBeTaken = rand.nextInt(Constants.NO_OF_DOORS);
		int objectDecision = 0;
		
		Room currentRoom = null;
		
		if(object_detail_ == Constants.ROOM_WITHOUT_OBJECT){
			/** Create new room  **/
			currentRoom = new Room();

			/** Add it to list of rooms **/
			rooms.add(currentRoom);
			
			/** Room doesnt have object, and object list is not empty **/
			if(unusedObjectList.size() != 0){
				Integer object = unusedObjectList.remove(0);

				/** Update object decison **/
				objectDecision = object;
				
				/** Add mapping information **/
				objectToRoomMap.put(object, currentRoom);
							
			}
		}else{
			/** Get current room based on mapping **/
			currentRoom = objectToRoomMap.get(object_detail_);
			
			if(currentRoom == null){
				/** This is target room **/
				currentRoom = new Room();
				
				/** Add to map **/
				objectToRoomMap.put(object_detail_, currentRoom);
			}
			else{
				/** Get unused door **/
				ArrayList<Integer> unusedDoorList = currentRoom.getUnusedDoorList();
				
				if(unusedDoorList.size() != 0){
					/** Set it to random unused door **/
					doorToBeTaken = unusedDoorList.remove(rand.nextInt(unusedDoorList.size()));
				}
			}
		}
		
		/** Add to history at first position and update previous room info**/
		history.add(0,new PastMove(currentRoom,doorToBeTaken,objectDecision));
		updatePreviousRoom(currentRoom);
		
		return new Move(doorToBeTaken,objectDecision);
	}

	/**
	 * Updates the previous room's door to room connection
	 * @param currentRoom
	 */
	private void updatePreviousRoom(Room currentRoom) {
		PastMove pm = history.get(0);
		Room previousRoom = pm.getRoom();
		previousRoom.updateDoorToRoom(pm.getDoorTaken(), currentRoom);
		
	}

	/**
	 * Init function, initializes object set and the rest
	 * @param number_of_objects_
	 * @param total_no_rounds_
	 */
	private void init(int number_of_objects_,int total_no_rounds_) {
		if(unusedObjectList == null){
			unusedObjectList = new ArrayList<Integer>();
			
			/** Adds object to unused list **/
			/** From 2 because 1 is the object in treasure room **/
			for (int i = 2; i < number_of_objects_; i++) {
				unusedObjectList.add(i);
			}
			
			totalRounds = total_no_rounds_;
		}
	}

	/**
	 * Constructor
	 */
	public G5Player() {
		
	}

}
