/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.g3;

import java.util.HashMap;

import maze.g3.data.BagOfHolding;
import maze.g3.data.History;
import maze.g3.data.Path;

import maze.g3.data.Maze;
import maze.g3.strategy.Strategy;
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
	History history= new History();
	public  static HashMap<Integer,Integer> eliminationList= new HashMap<Integer,Integer>();
	private BagOfHolding bag = new BagOfHolding();
	
	public Move move(int object_detail, int number_of_objects, int number_of_turns) {
		
		
		Strategy strat;
		strat = new SystematicStrategy(maze,bag);
		int startingRoom= 	maze.roomCount;
		Move action =strat.move(object_detail, number_of_objects, number_of_turns); 
		int door= strat.actionDoor;
		
		
		history.addPath(startingRoom, door, startingRoom+1);
		return action;
	
    }



}
