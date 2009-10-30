package maze.g3.strategy;

import maze.g3.data.Graph;
import maze.g3.data.Node;
import maze.ui.Move;

public abstract class Strategy {
	
	protected Graph g;
	
	public abstract void addNode(Node n);
	
	public abstract Move move(int object_detail, int number_of_objects, int number_of_turns);
	
}
