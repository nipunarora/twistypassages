package maze.g3.strategy;

import java.util.HashSet;

import maze.g3.Logger;
import maze.g3.Logger.LogLevel;
import maze.g3.data.BagOfHolding;
import maze.g3.data.Maze;
import maze.g3.data.Item;
import maze.g3.data.Room;
import maze.ui.Move;

public abstract class Strategy {
	
	protected Maze maze;

	// bag of unique objects that shrinks or grows according to which objects have been dropped
	protected BagOfHolding bag;

	protected Logger log = new Logger(LogLevel.DEBUG, this.getClass());
	
	public Strategy(Maze maze, BagOfHolding bag)
	{
		this.maze = maze;
		this.bag = bag;
	}

	public abstract Move move(int object_detail, int number_of_objects, int number_of_turns);
}
