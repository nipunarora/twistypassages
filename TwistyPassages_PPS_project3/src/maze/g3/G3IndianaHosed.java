/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.g3;

import java.util.HashMap;
import java.util.Random;

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
	private Maze maze;
	private Logger log = new Logger( LogLevel.DEBUG, this.getClass());
	public static int StagCounter=0;
	public static Path path= new Path();
	public static History history= new History();
	//only rooms in the elimination list
	public  static HashMap<Integer,Integer> eliminationList= new HashMap<Integer,Integer>();
	public static Random rand = new Random();
	//current Item to Room mapping
	public static HashMap<Integer,Integer> itemMapList= new HashMap<Integer,Integer>();
	/**boolean flags**/
	public static boolean treasureRoomFlag=false;
	public static int number_of_objects;
	
	public Path pathcopy= new Path();
	
	boolean first =true;
	
	public Move move(int object_detail, int number_of_objects, int number_of_turns) {
		//stupid debugging help
		pathcopy=path;
		
		if(first){
			maze = new Maze(number_of_objects);
		}
		
//		if(true)
//		{
//			return new SystematicStrategy(maze, maze.getBag()).move(object_detail, number_of_objects, number_of_turns);
//		}
		
		Room previousRoom1=maze.previousRoom;
		int door= maze.previousDoor;
		Strategy strat;
		strat = new SystematicStaggeredStrategy(maze,maze.getBag());
		//strat = new SystematicStrategy(maze,maze.getBag());
		int startingRoom= 	maze.roomCount;
		Move action =strat.move(object_detail, number_of_objects, number_of_turns); 
		
		
		Room currentRoom1= maze.previousRoom;
		
		
		
		//populating item map list
		if(strat.actionItem>0){
			itemMapList.put(strat.actionItem, maze.previousRoom.getId());
		}
		if(strat.actionItem==-1){
			itemMapList.remove(maze.previousRoom.getItem());
		}
		
	    if(!first){
		history.addPath(previousRoom1.getId(), door, currentRoom1.getId());
		path.addPath(previousRoom1.getId(), door, currentRoom1.getId());
	    }
	    first=false;
	    
	//	log.debug("door:"+ strat.actionDoor+ " item:"+ strat.actionItem);
		log.debug("action Door: "+ action.getDoor()+ " action Item "+ action.getItem());
	    return action;
	
    }



}
