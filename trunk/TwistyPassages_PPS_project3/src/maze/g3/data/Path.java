package maze.g3.data;

import java.util.HashMap;
import java.util.Vector;

/**
 * 
 * This class is not yet complete only there as a placeholder
 * Mantains a HashMap of all paths that have been traversed
 * Calculates shortest path between different nodes
 * We assume there can't be more than 1000 rooms
 */
public class Path {
	
	/**HashMap all paths stores all paths with starting room as key 
	 * 
	 */
	public HashMap<Integer,Vector<Edge>> startPaths= new HashMap<Integer,Vector<Edge>>();
	public HashMap<Integer,Vector<Edge>> destinationPaths= new HashMap<Integer,Vector<Edge>>();
	
	/**
	 * adds Edge information to a Path HashMap
	 * @param startRoom
	 * @param room
	 * @param destinationRoom
	 */
	public void addPath(int startRoom, int room, int destinationRoom){
		Edge e= new Edge(startRoom,room,destinationRoom);
		if(startPaths.containsKey(startRoom)){
			//Nipun: need to put checks if edge already exists in the vector? 
			startPaths.get(startRoom).add(e);
		}
		else{
			Vector<Edge> v= new Vector<Edge>();
			v.add(e);
			startPaths.put(startRoom, v);
		}
		if(destinationPaths.containsKey(startRoom)){
			//Nipun: need to put checks if edge already exists in the vector? 
			destinationPaths.get(startRoom).add(e);
		}
		else{
			Vector<Edge> v= new Vector<Edge>();
			v.add(e);
			destinationPaths.put(startRoom, v);
		}
	}
}
