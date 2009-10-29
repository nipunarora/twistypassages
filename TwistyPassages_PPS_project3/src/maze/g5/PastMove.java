package maze.g5;

import maze.ui.Move;

public class PastMove {

	private Room room;
	private int doorTaken;
	private int objectDecision;
	

	/**
	 * @return the doorTaken
	 */
	public int getDoorTaken() {
		return doorTaken;
	}
	/**
	 * @param doorTaken_ the doorTaken to set
	 */
	public void setDoorTaken(int doorTaken_) {
		doorTaken = doorTaken_;
	}
	/**
	 * @return the objectDecision
	 */
	public int getObjectDecision() {
		return objectDecision;
	}
	/**
	 * @param objectDecision_ the objectDecision to set
	 */
	public void setObjectDecision(int objectDecision_) {
		objectDecision = objectDecision_;
	}
	/**
	 * @param room_
	 * @param doorTaken_
	 * @param objectDecision_
	 */
	public PastMove(Room room_, int doorTaken_, int objectDecision_) {
		super();
		room = room_;
		doorTaken = doorTaken_;
		objectDecision = objectDecision_;
	}
	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}
	/**
	 * @param room_ the room to set
	 */
	public void setRoom(Room room_) {
		room = room_;
	}


}
