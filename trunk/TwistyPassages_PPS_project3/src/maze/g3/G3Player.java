/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.g3;

import java.util.HashSet;
import java.util.Random;


import maze.g3.Logger.LogLevel;
import maze.ui.Move;
import maze.ui.Player;


/**
 *
 * @author Shilpa
 * @author Nipun
 * @author Colin
 */
public class G3Player implements Player {
//test comment
	
	private boolean gameStart = true;
	private Logger log = new Logger( LogLevel.DEBUG, this.getClass() );
	
	// toggle to systematically explore tunnels
	private boolean systematicExploration = true;
	private int whichTunnel = 0;
	private Random random = new Random();
	
	// bag of unique objects that shrinks or grows according to which objects have been dropped
	private HashSet<Item> bagOfItems = new HashSet();
	
	
	public Move move(int object_detail, int number_of_objects, int number_of_turns) {
		
		log.debug("found object: " + object_detail + " num objects: " + number_of_objects + " num turns: " + number_of_turns);
		
		if( gameStart ) {
			initializeBag( number_of_objects );
			whichTunnel = 0;
			gameStart = false;
		}
		else {
			whichTunnel = decideOnATunnel();
		} 
		
		Move myMove = new Move( whichTunnel, 0 );
		return myMove;
    }

	
	//run the first time to populate the bagOfItems hashset
	private void initializeBag(int numberOfObjects) {
		for( int i = 1; i <= numberOfObjects; i++ ) {
			placeInBag( new Item(i) );
		}
		
	}


	//adds to hashset of unique items
	private void placeInBag( Item item ) {
		bagOfItems.add( item );
	}


	/**
	 * basic method for choosing a tunnel
	 * @return whichTunnel is the number of the tunnel to take
	 */
	private int decideOnATunnel() {

		if( systematicExploration ) {
			
			if( whichTunnel < 9 ) {
				whichTunnel++;
			} 
			else {
				systematicExploration = false;
				whichTunnel = random.nextInt(9);
			}
		}
		else {
			whichTunnel = random.nextInt(9);
		}

		log.debug("whichTunnel: "+whichTunnel);
		return whichTunnel;
	}

}
