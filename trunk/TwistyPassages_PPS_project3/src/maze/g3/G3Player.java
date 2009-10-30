/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.g3;

import java.util.HashSet;
import java.util.Random;


import maze.g3.Logger.LogLevel;
import maze.g3.data.Item;
import maze.g3.strategy.NaiveStrat;
import maze.g3.strategy.Strategy;
import maze.ui.Move;
import maze.ui.Player;


/**
 *
 * @author Shilpa
 * @author Nipun
 * @author Colin
 */
public class G3Player implements Player {
	
	public Move move(int object_detail, int number_of_objects, int number_of_turns) {
	
		Strategy strat;
		strat = new NaiveStrat();
		
		return strat.move(object_detail, number_of_objects, number_of_turns);
	
    }



}
