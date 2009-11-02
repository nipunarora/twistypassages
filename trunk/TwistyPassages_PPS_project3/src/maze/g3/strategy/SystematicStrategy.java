/**
 * 
 */
package maze.g3.strategy;

import maze.g3.data.BagOfHolding;
import maze.g3.data.Maze;
import maze.g3.data.Room;
import maze.g3.data.Room.RoomType;
import maze.ui.Move;

/**
 * @author shilpa
 *
 */
public class SystematicStrategy extends Strategy {
	
	/**
	 * @param bag 
	 * 
	 */
	public SystematicStrategy(Maze maze, BagOfHolding bag) {
		super(maze,bag);
		// TODO Auto-generated constructor stub
	}

	public Move move(int objectDetail, int numberOfObjects, int numberOfTurns) {
		
		bag.lastItemLabel = 0;
		
		if (maze.isFirstRoom) {
			// START ROOM
			// TODO if the first room is visited again then update the doors
			// that lead to self loops
			maze.isFirstRoom = false;
			bag.fill( numberOfObjects );
			maze.currentRoom = maze.createNewRoom();
			maze.currentRoom.setDoorToTake(0);
			bag.dropItemIfAvailable();
			maze.currentRoom.setItem( bag.lastItemLabel );
			maze.currentRoom.setRoomType(RoomType.START);
			maze.currentRoom.setStartRoomLinksToSelf();
		} else if (objectDetail == 0) {
			// unvisited room.. so create new room and drop item
			maze.currentRoom = maze.createNewRoom();
			bag.dropItemIfAvailable();
			maze.currentRoom.setItem( bag.lastItemLabel );
			maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(), maze.currentRoom);

			// Dont know any thing about the room so can take any unknown door.
			maze.currentRoom.setDoorToTake(maze.currentRoom.getUnknownDoorExit());

		} else if (objectDetail == 1) {
			// TREASURE ROOM
			maze.currentRoom = maze.getTreasureRoom();
			if (maze.currentRoom == null) {

				// First visit to treasure room
				maze.currentRoom = maze.createTreasureRoom();
				maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(),
						maze.currentRoom);

			} else {
				
				// already treasure room is created and we are visiting it 2nd
				// time
				// WASTE... AVOIDED it in maze
				// getDoorLeadingToChildRoomsWithUnknownDoor()
				// .. but still by chance if it happens then link all other
				// doors to self
				maze.currentRoom.setUnknownLinksToSelf();
			}
			
			int unknownDoor = maze.currentRoom.getUnknownDoorExit();
			if (unknownDoor == -1) {
				// if I have complete information about all the door links
				// then take the door which does not lead to self loop
				unknownDoor = maze.currentRoom.avoidSelfLoopDoor();
			}
			maze.currentRoom.setDoorToTake(unknownDoor);
		} else if (objectDetail == 2) {
			// START ROOM REVISITED only way out is zero
			maze.currentRoom = maze.getRoomByItem(objectDetail);
			maze.currentRoom.setDoorToTake(0);
			maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(), maze.currentRoom);
		} else {

			// Any other room revisited.
			maze.currentRoom = maze.getRoomByItem(objectDetail);
			maze.previousRoom.setRoomLink(maze.previousRoom.getDoorTaken(), maze.currentRoom);

			// 
			int unknownDoor = maze.currentRoom.getUnknownDoorExit();
			if (unknownDoor == -1) {
				// if i have complete information about all the door links
				// then take the door whose room has unknown door links
				unknownDoor = maze
						.getDoorLeadingToChildRoomsWithUnknownDoor(maze.currentRoom);
			}
			maze.currentRoom.setDoorToTake(unknownDoor);
		}

		maze.previousRoom = maze.currentRoom;
		log.debug("MOVE taken .." + maze.currentRoom.getId() + "_"
				+ maze.currentRoom.getDoorTaken() + 
				( bag.lastItemLabel > 0 ? " item dropped " + bag.lastItemLabel : ""));
		return new Move(maze.currentRoom.getDoorToTake(), bag.lastItemLabel);
	}
	
	

}
