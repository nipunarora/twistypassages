package maze.g3.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	public void addPath(int startRoom, int door, int destinationRoom){
		Edge e= new Edge(startRoom,door,destinationRoom);
		Boolean flag=false;
		if(startPaths.containsKey(startRoom)){
			//Nipun: need to put checks if edge already exists in the vector? 
			for(int i=0;i<startPaths.get(startRoom).size();i++){
				if(e.equals(startPaths.get(i))){
					flag=true;
				}
			}
			if(flag==false){
			startPaths.get(startRoom).add(e);
			}
		}
		else{
			Vector<Edge> v= new Vector<Edge>();
			v.add(e);
			startPaths.put(startRoom, v);
		}
		if(destinationPaths.containsKey(startRoom)){
			//Nipun: need to put checks if edge already exists in the vector? 
			for(int i=0;i<startPaths.get(startRoom).size();i++){
				if(e.equals(startPaths.get(i))){
					flag=true;
				}
			}
			if(flag==false){
			destinationPaths.get(startRoom).add(e);
			}
		}
		else{
			Vector<Edge> v= new Vector<Edge>();
			v.add(e);
			destinationPaths.put(startRoom, v);
		}
	}
	
	/**
	 * 
	 * @param startRoom
	 * @param targetRoom must be different than startRoom
	 * @return Vector of edges in order, or null if no path
	 */
	public Vector<Edge> getShortestPath( int startRoom, int targetRoom ) {
		boolean found = false;
		//each room connected with its parent edge
		HashMap<Integer, Edge> parentPath = new HashMap<Integer, Edge>();
		//a list of vectors of edges l->v->e
		ArrayList<Vector<Edge>> queueOfVectorsOfEdges = new ArrayList<Vector<Edge>>();
		//ArrayList<Edge> edgesToVisit = new ArrayList<Edge>();
		HashSet<Integer> visitedRooms = new HashSet<Integer>();
		//add the vectors of edges to the list of vectors of edges
		queueOfVectorsOfEdges.add(startPaths.get(startRoom));
		parentPath.put(startRoom, null);
		int thisRoom = -1;
		//cycle through the list to look at each vector of edges
		while( !queueOfVectorsOfEdges.isEmpty()) {
			//get the vector of edges coming from one room
			Vector<Edge> v = queueOfVectorsOfEdges.get(0);
			queueOfVectorsOfEdges.remove(0);
			//cycle through the vector of edges
			int vsize = v.size();
			//if (vsize>0) thisRoom = v.get(0).StartRoom; else thisRoom = -1; 
			for (int i=0; i<vsize; i++) {
				//look at an edge
				Edge e = v.get(i);
				thisRoom = e.StartRoom;
				if ( ! visitedRooms.contains(e.StartRoom)) {
					if ( ! visitedRooms.contains(e.DestinationRoom) && !parentPath.containsKey(e.DestinationRoom)) 
						queueOfVectorsOfEdges.add(startPaths.get(e.DestinationRoom));
					//edgesToVisit.add(e);
					if ( !parentPath.containsKey(e.DestinationRoom))
						parentPath.put(e.DestinationRoom, e);
					if ( e.DestinationRoom == targetRoom ) {
						return constructShortestPath( parentPath, e.DestinationRoom );
					}
				}
			}
			visitedRooms.add(thisRoom);
		}
		return null;
	}

	/**
	 * @param parentPath
	 * @param destinationRoom
	 * @return path (a vector of edges) from the starting point to the target point
	 */
	private Vector<Edge> constructShortestPath(HashMap<Integer, Edge> parentPath,
			int destinationRoom) {
		// TODO Auto-generated method stub
		Vector<Edge> path = new Vector<Edge>();
//		Edge currentEdge = null;
		int parentRoom;
		Edge parentEdge = parentPath.get(destinationRoom);
		while ( parentEdge != null ) {
			path.add(parentEdge);
			parentRoom = parentEdge.StartRoom;
//			currentEdge = parentPath.get( parentEdge.StartRoom );
//			path.add( parentPath.get( destinationRoom ));
			parentEdge = parentPath.get( parentRoom );
		}
		return( flipPath(path) );
	}

	/**
	 * @param path (a vector of edges) from target point to starting point
	 * @return path (a vector of edges) from the starting point to the target point
	 */
	
	private Vector<Edge> flipPath(Vector<Edge> path) {
		// TODO Auto-generated method stub
		Vector<Edge> flippedPath = null;
		for (int i = path.size()-1; i>=0; i--) {
			flippedPath.add(path.get(i));
		}
		return flippedPath;
	}
}
