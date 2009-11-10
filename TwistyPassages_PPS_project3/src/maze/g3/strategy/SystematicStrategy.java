/**
 * 
 */
package maze.g3.strategy;

import java.util.Random;

import maze.g3.G3Player;
import maze.g3.data.BagOfHolding;
import maze.g3.data.Maze;
import maze.g3.data.Room.RoomType;
import maze.ui.Move;

/**
 * @author shilpa
 * 
 */
public class SystematicStrategy extends Strategy {

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

		if (maze.isFirstRoom) {
			// START ROOM
			// TODO if the first room is visited again then update the doors
			// that lead to self loops
			print("Entered start room");
			enteredStartRoom();

		} else if (objectDetail == 0) {
			print("Unvisitied room dropping item and setting link");
			enteredUnvisitedRoom();

		} else if (objectDetail == 1 && maze.getTreasureRoom() == null) {
			// TREASURE ROOM
			enteredTreasureRoom();
		} else if (objectDetail == 2) {
			// START ROOM REVISITED only way out is zero
			print("Revisiting the start room..");
			startRoomRevisit(objectDetail);
		} else {
			print("Any other room revisited");
			// Any other room revisited.
			anyRoomRevisited(objectDetail);
		}

		maze.previousRoom = maze.currentRoom;
		log.debug("MOVE taken .." + maze.currentRoom.getId() + "_"
				+ maze.currentRoom.getDoorTaken() + " item dropped "
				+ maze.currentItemToDrop);

		if (maze.itemsDroppedCount > G3Player.number_of_objects) {
			System.out.println("items over making random move..");
			maze.currentRoom.setDoorToTake(new Random().nextInt(9));
		}

		return new Move(maze.currentRoom.getDoorToTake(),
				maze.currentItemToDrop);
	}

	private void anyRoomRevisited(int objectDetail) {
		maze.currentRoom = maze.getRoomByItem(objectDetail);
		maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(),
				maze.currentRoom);

		// 
		int unknownDoor = maze.currentRoom.getUnknownDoorExit();
		if (unknownDoor == -1) {
			// if i have complete information about all the door links
			// then take the door whose room has unknown door links
			unknownDoor = maze
					.getDoorLeadingToChildRoomsWithUnknownDoor(maze.currentRoom);
		}
		maze.currentRoom.setDoorToTake(unknownDoor);
		maze.currentItemToDrop = 0;
	}

	private void startRoomRevisit(int objectDetail) {
		maze.currentRoom = maze.getRoomByItem(objectDetail);
		maze.currentRoom.setDoorToTake(0);

		// shud pic the object
		maze.currentItemToDrop = -1;
		maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(),
				maze.currentRoom);
		maze.previousRoom.setRoomType(RoomType.ENTRANCE_TO_START);
	}

	private void enteredTreasureRoom() {
		// Treasure room shud be visited only once.. if we visit second time
		// then cry out..
		maze.currentRoom = maze.getTreasureRoom();
		if (maze.currentRoom == null) {

			// First visit to treasure room pick the item and run away
			maze.currentRoom = maze.createTreasureRoom();
			maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(),
					maze.currentRoom);
			maze.previousRoom.setRoomType(RoomType.ENTRANCE_TO_TREASURE);
			maze.currentItemToDrop = -1;
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
		maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(),
				maze.currentRoom);

		// Dont know any thing about the room so can take any unknown door.
		maze.currentRoom.setDoorToTake(maze.currentRoom.getUnknownDoorExit());
	}

	private void enteredStartRoom() {
		maze.isFirstRoom = false;
		maze.currentRoom = maze.createNewRoomAndDropItem();
		maze.currentRoom.setDoorToTake(0);
		maze.currentRoom.setRoomType(RoomType.START);
		maze.currentRoom.setRoomLinksToSelfExcept0();
	}

}
