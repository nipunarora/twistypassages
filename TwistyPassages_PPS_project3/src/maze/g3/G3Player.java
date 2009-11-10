/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.g3;

import java.util.HashMap;
import java.util.Random;

import maze.g3.data.BagOfHolding;
import maze.g3.data.History;
import maze.g3.data.Path;

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
public class G3Player implements Player {
	private Maze maze = new Maze();
	public static int StagCounter=0;
	public static Path path= new Path();
	public static History history= new History();
	
	//only rooms in the elimination list
	public  static HashMap<Integer,Integer> eliminationList= new HashMap<Integer,Integer>();
	public static Random rand = new Random();
	private BagOfHolding bag = new BagOfHolding();
	//current Item to Room mapping
	public static HashMap<Integer,Integer> itemMapList= new HashMap<Integer,Integer>();
	/**boolean flags**/
	public static boolean treasureRoomFlag=false;
	public static int number_of_objects;
	
	boolean first =true;
	public Move move(int object_detail, int number_of_objects, int number_of_turns) {
		this.number_of_objects = number_of_objects;
		
		if(true)
		{
			return new SystematicStrategy(maze, bag).move(object_detail, number_of_objects, number_of_turns);
		}
		
		if(first){
			bag.fill(number_of_objects);
			first =false;
		}
		
		Strategy strat;
		strat = new SystematicStaggeredStrategy(maze,bag);
		int startingRoom= 	maze.roomCount;
		Move action =strat.move(object_detail, number_of_objects, number_of_turns); 
		int door= strat.actionDoor;
		
		
		//populating item map list
		if(strat.actionItem>0){
			itemMapList.put(strat.actionItem, maze.previousRoom.getId());
		}
		if(strat.actionItem==-1){
			itemMapList.remove(maze.previousRoom.getItem());
		}
		
		
	
		path.addPath(maze.previousRoom.getId(), door, maze.currentRoom.getId());
		history.addPath(maze.previousRoom.getId(), door, maze.currentRoom.getId());
		
		System.out.println("door:"+ strat.actionDoor+ " item:"+ strat.actionItem);
		return action;
	
    }



}
