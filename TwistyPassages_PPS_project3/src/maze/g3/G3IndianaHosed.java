/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.g3;

import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import maze.g3.Logger.LogLevel;
import maze.g3.data.BagOfHolding;
import maze.g3.data.History;
import maze.g3.data.Path;
import maze.g3.data.Room;

import maze.g3.data.Maze;
import maze.g3.strategy.Strategy;
import maze.g3.strategy.SystematicStaggeredStrategy;
import maze.g3.strategy.SystematicStrategy;
import maze.ui.Move;
import maze.ui.Player;

/**
 * 
 * @author Shilpa
 * @author Nipun
 * @author Colin
 */
public class G3IndianaHosed implements Player {

	/**
	 * A switch to choose a strategy. If true uses SystematicStrategy, false
	 * uses SystematicStaggeredStrategy.
	 **/
	boolean useSystematicStrategy = false;
	public static int NumTurnsBeforePlacingObject = 10;
	private boolean useMerge = true;
	private int mergeThreshold = 7;

	private Maze maze;
	private Logger log = new Logger(LogLevel.ERROR, this.getClass());
	private Logger log = new Logger(LogLevel.NONE, this.getClass());
	public static int StagCounter = 0;
	public static Path path;
	public static History history = new History();
	// only rooms in the elimination list
	public static HashMap<Integer, Integer> eliminationList = new HashMap<Integer, Integer>();
	public static Random rand = new Random();
	// current Item to Room mapping
	public static HashMap<Integer, Integer> itemMapList = new HashMap<Integer, Integer>();
	/** boolean flags **/
	public static boolean treasureRoomFlag = false;
	public static int number_of_objects;

	public Path pathcopy;

	boolean first = true;

	//SystematicStrategy strategy = new SystematicStrategy();
	//SystematicStaggeredStrategy strategy = new SystematicStaggeredStrategy(maze, null);
	
	public Move move(int object_detail, int number_of_objects,
			int number_of_turns) {
		// stupid debugging help

		//NumTurnsBeforePlacingObject = number_of_turns/number_of_objects - number_of_objects;
		
		if (first) {
			pathcopy = path;
			maze = new Maze(number_of_objects);
			path = new Path(maze);
		}

		pathcopy = path;
/*		if (useSystematicStrategy) {
			if (first) {
				first = false;
				strategy.setMaze(maze);
			}
			return strategy.move(object_detail, number_of_objects,
					number_of_turns);
		}*/
		int previousRoom1=1;
		if(!first){
			previousRoom1 = maze.previousRoom.getId();
		}
		 
			
		int door = maze.previousDoor;
		Strategy strat;
		if(useSystematicStrategy)
			strat = new SystematicStrategy(maze,maze.getBag());
		else
			strat = new SystematicStaggeredStrategy(maze, maze.getBag());
		int startingRoom = maze.roomCount;
		if (useMerge ) {
			path.mergeAndRemoveDupes(previousRoom1, Path.PathsToCheck.BOTH, mergeThreshold );
			if( path.roomsThatWereMatched != null 
					&& path.roomsThatWereMatched.size() > 1 ) {
				log.debug("there's a room that was matched");
				for (int i=1; i<path.roomsThatWereMatched.size(); i++) {
					if( path.roomsThatWereMatched.get(i) == previousRoom1 ) {
						previousRoom1 = path.roomsThatWereMatched.get(0);
					}
					for( Entry<Integer, Integer> entry: eliminationList.entrySet()) {
						if(entry.getValue() == path.roomsThatWereMatched.get(i) ){
							entry.setValue(path.roomsThatWereMatched.get(i));
						}
					}
				}
			}
		}

		Move action = strat.move(object_detail, number_of_objects,
				number_of_turns);

		int currentRoom1 = maze.previousRoom.getId();
		int item= action.getItem();
		// populating item map list
		if (strat.actionItem > 0) {
			itemMapList.put(item, currentRoom1);
		}
		if (strat.actionItem == -1) {
			itemMapList.remove(maze.previousRoom.getItem());
		}

		if (!first) {
			history.addPath(previousRoom1, door, currentRoom1);
			path.addPath(previousRoom1, door, currentRoom1);
		}
		first = false;

		// log.debug("door:"+ strat.actionDoor+ " item:"+ strat.actionItem);
		log.debug("action Door: " + action.getDoor() + " action Item " + action.getItem());
		return action;

	}

}
