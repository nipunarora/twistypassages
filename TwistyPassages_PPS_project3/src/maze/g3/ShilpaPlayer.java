package maze.g3;

import maze.g3.Logger.LogLevel;
import maze.ui.Move;
import maze.ui.Player;

public class ShilpaPlayer implements Player {

	private Logger log = new Logger( LogLevel.DEBUG, this.getClass() );
	
	@Override
	public Move move(int objectDetail, int numberOfObjects, int totalRounds) {
		
		log.debug("found object: " + objectDetail + " num objects: " + numberOfObjects + " num turns: " + totalRounds);
		return null;
	}

}
