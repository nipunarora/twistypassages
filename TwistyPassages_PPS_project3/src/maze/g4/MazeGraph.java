package maze.g4;

import java.util.*;

public class MazeGraph {

	private Room[] object_locations;
	private Set<Room> roomList = new HashSet<Room>();

	private static final int DOOR_COUNT = 10;

	private Room start_room = new Room(1);
	private Room treasure_room = new Room(1);

	public MazeGraph(int object_count) {
		object_locations = new Room[object_count + 1];
		object_locations[1] = treasure_room;
	}

	public class Room {
		int exit_count;
		Room[] exits;
		Set<Room> enterFrom = new HashSet<Room>();

		public Room() {
			this(DOOR_COUNT);
		}
		public Room(int exit_count_) {
			exit_count = exit_count_;
			exits = new Room[exit_count_];
			roomList.add(this);
		}

		public Room takeDoor(int doornumber) {
			if (null == exits[doornumber]) {
				Room there = new Room();
				there.enterFrom.add(this);
				exits[doornumber] = there;
			}
			return exits[doornumber];
		}

		public boolean isStartRoom() {
			return this == start_room;
		}
		public boolean isTreasureRoom() {
			return this == treasure_room;
		}

		public void swallow(Room victim) {
			for (int i = 0; i < victim.exits.length; i++) {
				Room exitTo = victim.exits[i];
				if (null != exitTo) {
					exits[i] = exitTo;
					exitTo.enterFrom.add(this);
					exitTo.enterFrom.remove(victim);
				}

			}
			for (Room victimEntrance : victim.enterFrom) {
				for (int i = 0; i < victimEntrance.exits.length; i++) {
					if (victim == victimEntrance.exits[i]) {
						victimEntrance.exits[i] = this;
					}
				}
				enterFrom.add(victimEntrance);
			}
			roomList.remove(victim);
		}

		int firstUnexplored() {
			for (int i = 0; i < this.exit_count; i++) {
				if (null == exits[i]) {
					//System.err.println("Exploring " + i);
					return i;
				}
			}
			//System.err.println("All explored: going with 0");
			return 0;
		}

		int bestExit() {
			int chosen = -1;
			for (int i = 0; i < this.exit_count; i++) {
				if (null == exits[i]) {
					//System.err.println("Exploring " + i);
					chosen = i;
					break;
				}
			}
			// if all are explored, avoid start and treasure rooms,
			if (chosen < 0 ) {
				//System.err.println("All exits explored!");
				for (int i = 0; i < this.exit_count; i++) {
					if (exits[i].isStartRoom()
							|| exits[i].isTreasureRoom()
							|| this == exits[i]
							) {
						continue;
					}
					chosen = i;
					break;
				}
			}
			return chosen;
		}

	}

	public void dropObject(Room where, int which) {
		object_locations[which] = where;
	}

	public void pickUpObject(int which) {
		object_locations[which] = null;
	}

	public Room getStartRoom() {
		return start_room;
	}

	public Room foundObject(int object_detail, Room here) {
		//System.err.println("Found object " + object_detail);
		if (here != object_locations[object_detail]) {
			object_locations[object_detail].swallow(here);
		}
		return object_locations[object_detail];
	}

}
