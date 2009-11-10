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
	private Maze maze;
	public static int StagCounter=0;
	public static Path path= new Path();
	public static History history= new History();
	public static int number_of_objects;
	//only rooms in the elimination list
	public  static HashMap<Integer,Integer> eliminationList= new HashMap<Integer,Integer>();
	public static Random rand = new Random();
	//current Item to Room mapping
	public static HashMap<Integer,Integer> itemMapList= new HashMap<Integer,Integer>();
	/**boolean flags**/
	public static boolean treasureRoomFlag=false;
	public static int number_of_objects;
	
	boolean first =true;
	public Move move(int object_detail, int number_of_objects, int number_of_turns) {
		if(first){
			maze = new Maze(number_of_objects);
			first =false;
		}
		
//		if(true)
//		{
//			return new SystematicStrategy(maze, maze.getBag()).move(object_detail, number_of_objects, number_of_turns);
//		}
		
		
		Strategy strat;
		strat = new SystematicStaggeredStrategy(maze,maze.getBag());
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
		
		
		System.out.println("door:"+ strat.actionDoor+ " item:"+ strat.actionItem);
		return action;
	
    }



}
