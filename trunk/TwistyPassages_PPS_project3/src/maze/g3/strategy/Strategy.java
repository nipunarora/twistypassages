package maze.g3.strategy;

import java.util.HashSet;

import maze.g3.data.Graph;
import maze.g3.data.Item;
import maze.g3.data.Node;
import maze.ui.Move;

public abstract class Strategy {
	
	protected Graph g;
	// bag of unique objects that shrinks or grows according to which objects have been dropped
	protected HashSet<Item> bagOfItems = new HashSet();
	
	public abstract void addNode(Node n);
	
	public abstract Move move(int object_detail, int number_of_objects, int number_of_turns);
	
}
