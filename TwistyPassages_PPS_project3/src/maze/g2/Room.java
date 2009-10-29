package maze.g2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Room {
	public Room[] known_neighbors = new Room[10];
	public Set<Path> ways_to_get = new HashSet<Path>();
	public int cur_obj = -1;
	
	public ArrayList<Integer> Unknown_Doors() {
		ArrayList<Integer> rv = new ArrayList<Integer>();
		
		for(int i = 0; i < known_neighbors.length; ++i) {
			if(known_neighbors[i] == null) {
				rv.add(i);
			}
		}
		
		return rv;
	}
	
	public ArrayList<Integer> Outside_Doors() {
		ArrayList<Integer> rv = new ArrayList<Integer> ();
		for(int i = 0; i < known_neighbors.length; ++i) {
			if(known_neighbors[i] != null && known_neighbors[i] != this) {
				rv.add(i);
			}
		}
		return rv;
	}
}
