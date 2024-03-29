package maze.g3.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

import maze.g3.Logger;
import maze.g3.Logger.LogLevel;

/**
 * This class keeps the paths we have travelled similar to an adjacency matrix 
 */
public class Path {
	
	/**HashMap all paths stores all paths with starting room as key 
	 * 
	 */
	public HashMap<Integer,Vector<Edge>> startPaths= new HashMap<Integer,Vector<Edge>>();
	public HashMap<Integer,Vector<Edge>> destinationPaths= new HashMap<Integer,Vector<Edge>>();
	private Maze maze;
	public static enum PathsToCheck { START, DESTINATION, BOTH };
	
	private Logger log = new Logger( LogLevel.ERROR, this.getClass() );
	
	//an ugly, ugly private global variable
	public Vector<Integer> roomsThatWereMatched = new Vector<Integer>();
	
	public Path(Maze maze) {
		this.maze = maze;
	}


	public void print(String info) {
		log.debug(info);
	}
	
	
	/**
	 * adds Edge information to a Path HashMap
	 * @param startRoom
	 * @param room
	 * @param destinationRoom
	 */
	public void addPath(int startRoom, int door, int destinationRoom){
		Edge e= new Edge(startRoom,door,destinationRoom);
		print("Adding edge "+e);
		Boolean flag=false;
		if(startPaths.containsKey(startRoom)){
			print("Start path contains "+startRoom);
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

		if(destinationPaths.containsKey(destinationRoom)){
			print("Destination path contains "+destinationRoom);
			for(int i=0;i<destinationPaths.get(destinationRoom).size();i++){
				if(e.equals(destinationPaths.get(i))){
					flag=true;
				}
			}
			if(flag==false){
				destinationPaths.get(destinationRoom).add(e);
			}
		}
		else{
			Vector<Edge> v= new Vector<Edge>();
			v.add(e);
			destinationPaths.put(destinationRoom, v);
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
						Vector<Edge> constructShortestPath = constructShortestPath( parentPath, e.DestinationRoom );
						print("Shortes path from "+startRoom + " to "+targetRoom + " ="+constructShortestPath);
						return constructShortestPath;
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
			parentEdge = parentPath.get( parentRoom );
		}
//		for( Edge e: path ) {
//			log.debug( e.StartRoom + "->" + e.door + "->" + e.DestinationRoom );
//		}
		return( flipPath(path) );
	}

	/**
	 * @param path (a vector of edges) from target point to starting point
	 * @return path (a vector of edges) from the starting point to the target point
	 */
	
	private Vector<Edge> flipPath(Vector<Edge> path) {
		// TODO Auto-generated method stub
		Vector<Edge> flippedPath = new Vector<Edge>();
		for (int i = path.size()-1; i>=0; i--) {
			flippedPath.add(path.get(i));
		}
		return flippedPath;
	}
	
	/**
	 * don't use this
	 * @param threshold
	 * @return
	 */
	public Vector<Integer> mergeAllSimilarRooms( Integer threshold ) {
		for( Map.Entry<Integer, Vector<Edge>> e: startPaths.entrySet() ) {
			int targetRoom = e.getKey();
			if ( hasMatchingRoom(targetRoom, Path.PathsToCheck.BOTH, threshold)) {
				mergeRooms(roomsThatWereMatched);
			}
		}
		Vector<Integer> matches = roomsThatWereMatched;
		for (int m=1; m<roomsThatWereMatched.size(); m++) { //notice that index 0 is for the room we are using
			startPaths.remove(roomsThatWereMatched.get(m));
			destinationPaths.remove(roomsThatWereMatched.get(m));
		}
		return matches;
	}
	
	/**
	 * Merge two rooms by comparing startpaths and endpaths, within a tolerance threshold
	 * @param room int that tells the room to search for
	 * @param pathsFlag check destination paths, start paths, or both
	 * @param numMatchesNeeded how many successful matches is enough
	 */
	public boolean hasMatchingRoom( int room, PathsToCheck pathsFlag, int numMatchesNeeded ) {
		Vector<Edge> roomStartPaths = startPaths.get(room);
		Vector<Edge> roomDestPaths = destinationPaths.get(room);
		
		boolean foundMatch = false;
		
		if( pathsFlag == PathsToCheck.DESTINATION ) {
			if( roomDestPaths != null 
					&& getEdgeMatches(roomDestPaths, destinationPaths, numMatchesNeeded, PathsToCheck.DESTINATION)) {
				foundMatch = true;
			}
		}
		else if( pathsFlag == PathsToCheck.START ) {
			if( roomStartPaths != null && getEdgeMatches( roomStartPaths, startPaths, numMatchesNeeded, PathsToCheck.START )) {
				foundMatch = true;
			}
		}
		else {
			if( 	( roomStartPaths != null 
					&& getEdgeMatches( roomStartPaths, startPaths, numMatchesNeeded, PathsToCheck.START) )
					|| 
					( roomDestPaths != null 
					&& getEdgeMatches(roomDestPaths, destinationPaths, numMatchesNeeded, PathsToCheck.DESTINATION)) )
			{
				foundMatch = true;
			}
		}
		
		if ( foundMatch ) roomsThatWereMatched.add(0, room);
		log.debug("found match: "+foundMatch);

		return foundMatch;
//		if( pathsFlag == PathsToCheck.START ) return numStartMatches >= numMatchesNeeded;
//		else if( pathsFlag == PathsToCheck.DESTINATION ) return numDestMatches >= numMatchesNeeded;
//		else return numStartMatches >= numMatchesNeeded && numDestMatches >= numMatchesNeeded;
	}

	public void mergeRooms(Vector<Integer> roomThatWasMatched2) {
		// TODO Auto-generated method stub
		int room = roomThatWasMatched2.get(0);
		for (int m=1; m<roomThatWasMatched2.size(); m++) { //notice that index 0 is for the room we are using
			maze.map.remove(roomsThatWereMatched.get(m));
//			startPaths.remove(roomThatWasMatched2.get(m));
//			destinationPaths.remove(roomThatWasMatched2.get(m));
			for ( Map.Entry<Integer, Vector<Edge>> entry: startPaths.entrySet() ) {
				Vector<Edge> vectorOfEdges = entry.getValue();
				for (int test=0; test<vectorOfEdges.size(); test++) { 
					if( roomThatWasMatched2.get(m) == vectorOfEdges.get(test).StartRoom ){
						log.debug("room "+roomThatWasMatched2.get(m)+" merged with "+room);
						vectorOfEdges.get(test).StartRoom = room;
					}
					if( roomThatWasMatched2.get(m) == vectorOfEdges.get(test).DestinationRoom ){
						log.debug("room "+roomThatWasMatched2.get(m)+" merged with "+room);
						vectorOfEdges.get(test).DestinationRoom = room;
					}
				}
			}
		}
	}
	
	/**
	 * just for debugging, log.debugs both start and dest paths in room->door->room format
	 */
	public void displayPaths() {
		log.debug("startPaths");
		for ( Map.Entry<Integer, Vector<Edge>> entry: startPaths.entrySet()) {
			for ( Edge edge: entry.getValue()) {
				log.debug( entry.getKey() + ": " + edge.StartRoom + "->" + edge.door + "->" + edge.DestinationRoom );
			}
		}
		log.debug("destinationPaths");
		for ( Map.Entry<Integer, Vector<Edge>> entry: destinationPaths.entrySet()) {
			for ( Edge edge: entry.getValue()) {
				log.debug( entry.getKey() + ": " + edge.StartRoom + "->" + edge.door + "->" + edge.DestinationRoom );
			}
		}
	}

	public boolean getEdgeMatches(Vector<Edge> roomPaths,
			HashMap<Integer, Vector<Edge>> startPaths2, int threshold, PathsToCheck whichTest) {
		int numMatches=0;
		if( roomPaths.size() > 0 ) {
			for (Map.Entry<Integer, Vector<Edge>> entrySet : startPaths2.entrySet()) {
				Vector<Edge> queriedPaths = entrySet.getValue();
				int queriedRoom = entrySet.getKey();
				for( int i=0; i<queriedPaths.size(); i++ ) {
					for( int j=0; j<roomPaths.size(); j++ ) {
						if( whichTest == PathsToCheck.START
								&& queriedPaths.get(i).getStartRoom() != roomPaths.get(0).getStartRoom()
								&& queriedPaths.get(i).getDestinationRoom() == roomPaths.get(j).getDestinationRoom() 
								&& queriedPaths.get(i).getDoor() == roomPaths.get(j).getDoor()) {
							numMatches++; 
							log.debug("matched destination "+queriedPaths.get(i).getStartRoom() + " " + roomPaths.get(j).getStartRoom() + " num matches "+numMatches);
						}
						else if( whichTest == PathsToCheck.DESTINATION	
								&& queriedPaths.get(i).getDestinationRoom() != roomPaths.get(0).getDestinationRoom()
								&& queriedPaths.get(i).getStartRoom() == roomPaths.get(j).getStartRoom() 
								&& queriedPaths.get(i).getDoor() == roomPaths.get(j).getDoor()) {
							numMatches++;
							log.debug("matched destination "+queriedPaths.get(i).getStartRoom() + " " + roomPaths.get(j).getStartRoom() + " num matches "+numMatches);
						}
					}
				}
				if (numMatches >= threshold) {
					if (roomsThatWereMatched.size() == 0
							|| roomsThatWereMatched.get(0) != queriedRoom)
						roomsThatWereMatched.add(queriedRoom);
				}
			}
		}
		return (numMatches >= threshold);
	}
	
	public boolean removeDupes( Vector<Edge> edges ) {
		boolean found = false;
		if( edges != null ) {
			for ( int i=0; i<edges.size(); i++ ){
				for ( int j=i+1; j<edges.size(); j++ ){
					if( edges.get(i).getStartRoom() == edges.get(j).getStartRoom() 
							&& edges.get(i).getDestinationRoom() == edges.get(j).getDestinationRoom()
							&& edges.get(i).getDoor() == edges.get(j).getDoor()
							) {
						edges.remove(j);
					}
				}
			}
		}
		return found;
	}


	public void mergeAndRemoveDupes(int roomIndex, PathsToCheck both,
			int matchThreshold) {
		if( hasMatchingRoom(3, PathsToCheck.BOTH, matchThreshold)) {
			mergeRooms(roomsThatWereMatched);
		}
		displayPaths();
		for (int m=1; m<roomsThatWereMatched.size(); m++) { //notice that index 0 is for the room we are using
			System.out.println(roomsThatWereMatched.get(m));
			startPaths.remove(roomsThatWereMatched.get(m));
			destinationPaths.remove(roomsThatWereMatched.get(m));
		}
		for ( Map.Entry<Integer, Vector<Edge>> entry: startPaths.entrySet() ) {
			removeDupes(entry.getValue());
		}
		for ( Map.Entry<Integer, Vector<Edge>> entry: destinationPaths.entrySet() ) {
			removeDupes(entry.getValue());
		}
		displayPaths();
	}
	
}
