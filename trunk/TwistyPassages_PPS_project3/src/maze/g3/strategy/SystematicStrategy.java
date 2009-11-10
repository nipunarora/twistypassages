/**
 * 
 */
package maze.g3.strategy;

import java.util.Random;

import maze.g3.G3IndianaHosed;
import maze.g3.data.BagOfHolding;
import maze.g3.data.Maze;
import maze.g3.data.Room.RoomType;
import maze.ui.Move;

/**
 * @author shilpa
 * 
 */
public class SystematicStrategy extends Strategy {

	int objectDetail ;
	int numberOfObjects;
	int numberOfTurns;
	boolean isSecondRoom;
	/**
	 * @param bag
	 */
	public SystematicStrategy(Maze maze, BagOfHolding bag) {
		super(maze, bag);
	}

	public void print(String info) {
		log.debug(info);
	}

	public Move move(int objectDetail, int numberOfObjects, int numberOfTurns) {
		this.objectDetail = objectDetail;
		this.numberOfObjects = numberOfObjects;
		this.numberOfTurns = numberOfTurns;
		
		if (maze.isFirstRoom) {
			// START ROOM
			// Take 0 door and go out.. pick the item on next visit
			print("Entered start room");
			enteredStartRoom();

		} else if (objectDetail == 0) {
			print("Unvisitied room dropping item and setting link");
			enteredUnvisitedRoom();

		} else if (objectDetail == 1 && maze.getTreasureRoom() == null) {
			// TREASURE ROOM
			enteredTreasureRoom();
			print("Treasure room visit= "+ maze.currentRoom.getNumVisits());
		} else if (objectDetail == 2 && maze.getStartRoom().getNumVisits() == 2) {
			// START ROOM REVISITED 2nd time only way out is zero
			// Now pick the item and leave via door 0
			print("Revisiting the start room..");
			startRoomRevisit();
		} else {
			// Any other room revisited.
			anyRoomRevisited(objectDetail);
			print("Revisiting a room containing "+objectDetail + "  item, visit= " + maze.currentRoom.getNumVisits());
		}

		maze.currentRoom.visit();
		maze.previousRoom = maze.currentRoom;
		log.debug("MOVE taken .." + maze.currentRoom.getId() + "_"
				+ maze.currentRoom.getDoorTaken() + " item dropped "
				+ maze.currentItemToDrop);

		if (maze.getBag().isEmpty()) {
			System.out.println("items over making random move..");
			maze.currentRoom.setDoorToTake(new Random().nextInt(9));
		}

		return new Move(maze.currentRoom.getDoorToTake(),
				maze.currentItemToDrop);
	}

	private void anyRoomRevisited(int objectDetail) {
		maze.currentRoom = maze.getRoomByItem(objectDetail);
		setRoomLink();

		// 
		int unknownDoor = maze.currentRoom.getUnknownDoorExit();
		if (unknownDoor == -1) {
			// if i have complete information about all the door links
			// then take the door whose room has unknown door links
			unknownDoor = maze
					.getDoorLeadingToChildRoomsWithUnknownDoor(maze.currentRoom);
		}
		maze.currentRoom.setDoorToTake(unknownDoor);
		
		if(maze.currentRoom.hasFullKnowledge() || maze.currentRoom.hasFullInwardKnowledge() ||
				maze.currentRoom.hasOutwardKnowledge()) {
			print(maze.currentRoom.getId() + " has some full knowledge so picking item "+ objectDetail);
			pickItem();
		}
		else {
		        maze.currentItemToDrop = 0;
		}
	}

	private void startRoomRevisit() {
		maze.currentRoom = maze.getStartRoom();
		maze.currentRoom.setDoorToTake(0);

		// shud pic the object
		pickItem();
		setRoomLink();
		maze.previousRoom.setRoomType(RoomType.ENTRANCE_TO_START);
	}

	private void pickItem() {
		maze.currentItemToDrop = -1;
		maze.getBag().returnItem(objectDetail);
	}

	private void enteredTreasureRoom() {
		// Treasure room shud be visited only once.. if we visit second time
		// then cry out..
		maze.currentRoom = maze.getTreasureRoom();
		if (maze.currentRoom == null) {

			// First visit to treasure room pick the item and run away
			maze.currentRoom = maze.createTreasureRoom();
			setRoomLink();
			maze.previousRoom.setRoomType(RoomType.ENTRANCE_TO_TREASURE);
			pickItem();
			print("first visit to treasure room");

		} else {
			// already treasure room is created and we are visiting it 2nd
			// time
			maze.currentItemToDrop = 0;
			print("second visit to treasure room");
			new RuntimeException("Visiting the treasure room again..");
		}

		maze.currentRoom.setDoorToTake(0);
	}

	private void enteredUnvisitedRoom() {
		// unvisited room.. so create new room and drop item
		maze.currentRoom = maze.createNewRoomAndDropItem();
		setRoomLink();

		// Dont know any thing about the room so can take any unknown door.
		maze.currentRoom.setDoorToTake(maze.currentRoom.getUnknownDoorExit());
	}

	private void setRoomLink() {
		maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(),
				maze.currentRoom);
		
		// outward edge count changes for previous Room
		maze.previousRoom.calculateKnowledge();
		
		// Inward edge count changes for current room
		maze.inwardRoomKnowledge(maze.currentRoom);
		maze.currentRoom.calculateKnowledge();
	}

	private void enteredStartRoom() {
		maze.isFirstRoom = false;
		isSecondRoom = true;
		maze.currentRoom = maze.createNewRoomAndDropItem();
		maze.currentRoom.setDoorToTake(0);
		maze.currentRoom.setRoomType(RoomType.START);
		maze.currentRoom.setRoomLinksToSelfExcept0();
	}
	
}
