/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.g3;

import maze.g3.data.BagOfHolding;
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
	private BagOfHolding bag = new BagOfHolding();
	
	public Move move(int object_detail, int number_of_objects, int number_of_turns) {
	
		Strategy strat;
		strat = new SystematicStrategy(maze,bag);
			
		
		return strat.move(object_detail, number_of_objects, number_of_turns);
	
    }



}
