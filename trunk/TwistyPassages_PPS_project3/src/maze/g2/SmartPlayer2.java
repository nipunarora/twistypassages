package maze.g2;

import java.util.*;

import maze.ui.Move;
import maze.ui.Player;

/* Strategy:
 * Drop an object. Explore from this "basecamp" and try to get back to somewhere with an object. If it's been T turns and we haven't gotten there, drop an object, continue
 */
public class SmartPlayer2 implements Player{
	private Random rand = new Random();
	boolean first = true;
	private HashSet<Integer> objects = new HashSet<Integer>();
	private HashMap<Integer, Room> object_locations = new HashMap<Integer, Room>();
	
	private HashMap<Room, Path> known_paths = new HashMap<Room, Path>();
	
	private int current_turn = 0;
	private boolean foundTreasure = false; 
	
	private int  prev_dir;
	private Path cur_path;
	
	/* Since this can change with the object in treasure room, easier to save ahead of time */
	private int num_Objects;
	
	private static final boolean DEBUG_MODE = false;
	
	public Move move(int object_detail, int number_of_objects, int total_rounds) {
		if(objects.size() != number_of_objects)
		{
			for(int i = objects.size();i<number_of_objects;i++)
			{
				objects.add(i);
			}
		}
		
		int dir, item = 0;
		
		if(current_turn == 0) {
			/* Initial room, semi-special handling */
			if (DEBUG_MODE) System.out.println("Starting G2 Player, " + number_of_objects + " objects, " + total_rounds + " turns");
			this.num_Objects = number_of_objects;
			
			Room curRoom = new Room();
			item = dropNextItem(curRoom);
			
			/* We know starting room is mostly self loops */
			for(int i = 1; i < curRoom.known_neighbors.length; ++i) {
				curRoom.known_neighbors[i] = curRoom;
			}
			
			cur_path = new Path();
			cur_path.origin = curRoom;
			
			dir = 0;
		}
		else {
			Room curRoom = null;
			if(object_detail != 0) {
				curRoom = object_locations.get(object_detail);
				if(curRoom == null && foundTreasure == false && object_detail == 1) {
					/* Sanity check, this can happen the first time into treasure room */
					
					/* We now have one more object to work with */
					this.num_Objects++;
					
					curRoom = new Room();
					curRoom.cur_obj = 1;
					
					/* We know treasure room is mostly self-loops */
					for(int i = 1; i < curRoom.known_neighbors.length; ++i) {
						curRoom.known_neighbors[i] = curRoom;
					}
					
					curRoom.known_neighbors[0] = null;
					foundTreasure = true;
				}
				
				//System.out.println("We have found room " + object_detail + "!");

				object_locations.put(object_detail, curRoom);
				/* We know something about the previous door to this room, change previous door */
				if(cur_path.length() == 1) {
					Room prevRoom = cur_path.origin;
					prevRoom.known_neighbors[cur_path.steps.get(0)] = curRoom;
					
					if (DEBUG_MODE) System.out.println("Room with object #" + prevRoom.cur_obj + " can reach room with object #" + object_detail + " with door #" + cur_path.steps.get(0));
				}
				finishCurPath(curRoom);
			}
			else {  /* object detail is 0, we know pretty much nothing */
				
				if(current_turn == 1) {
					/* special handling for room right outside start room */
					
					curRoom = new Room();
					Room prevRoom = cur_path.origin;
					prevRoom.known_neighbors[0] = curRoom;
					
					item = dropNextItem(curRoom);
					
					finishCurPath(curRoom);
				}
			}
			
			
			if(curRoom == null) {
				/* we know nothing, may as well do random walk */
				dir = rand.nextInt(10);
			}
			else {
				/* we know something ... for now, try unknown doors first, or try an exit
				 * that is not a self loop.
				 */
				ArrayList<Integer> directions = curRoom.Unknown_Doors();
				if(directions.size() == 0) {
					/* we know where all doors lead, now find non self loop doors */
					directions = curRoom.Outside_Doors();
					if (DEBUG_MODE) System.out.print("Room has " + directions.size() + " exit(s) that are not self loops:  ");
					for(Integer i : directions) {
						if (DEBUG_MODE) System.out.print(i.toString() + "   " );
					}
					if (DEBUG_MODE) System.out.println();
				}
				else {
					if (DEBUG_MODE) System.out.println("\tWe know this room, and it has " + directions.size() + " unknown doors");
				}
				dir = directions.get(rand.nextInt(directions.size()));
			}
		}
		
		Move myMove = new Move(dir, item);
		if (DEBUG_MODE) System.out.println("Trying to move "+ dir);
		prev_dir = dir;
		cur_path.steps.add(dir);
		current_turn++;
        return myMove;
    }
	
	protected int dropNextItem(Room current_room)
	{
		/* Where is the main directory of what more items are there? */
		Set<Integer> dropped = object_locations.keySet();
		
		if(dropped.size() < num_Objects) {
			for(int i = ((foundTreasure) ? 1 : 2); i <= num_Objects; ++i) {
				if(!dropped.contains(i)) {
					current_room.cur_obj = i;
					object_locations.put(i, current_room);
					if (DEBUG_MODE) System.out.println("\t\t\tDropping item #" + i);
					return i;
				}
			}	
		}
		return 0;
	}
	
	protected int finishCurPath(Room current_room)
	{
		int rv = 0;
		cur_path.dest = current_room;
		known_paths.put(cur_path.origin, cur_path);
		
		cur_path = new Path();
		cur_path.origin = current_room; 
		return rv;
	}

}
