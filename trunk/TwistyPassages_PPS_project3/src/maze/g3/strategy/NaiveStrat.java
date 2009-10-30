package maze.g3.strategy;

import java.util.Random;

import maze.g3.Logger;
import maze.g3.Logger.LogLevel;
import maze.g3.data.Item;
import maze.g3.data.Node;
import maze.ui.Move;

public class NaiveStrat extends Strategy {

	private boolean gameStart = true;
	private Logger log = new Logger( LogLevel.DEBUG, this.getClass() );
	
	// toggle to systematically explore tunnels
	private boolean systematicExploration = true;
	private int whichTunnel = 0;
	private Random random = new Random();
	
	public void addNode(Node n) {
		int key= this.g.sizeOfGraph()+1;
		
	}
	
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
