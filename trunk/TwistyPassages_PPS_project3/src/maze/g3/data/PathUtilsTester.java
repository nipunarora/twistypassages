package maze.g3.data;

import java.util.Vector;

public class PathUtilsTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Path path = new Path();
		path.addPath(3, 5, 3);
		path.addPath(3, 6, 5);
		path.addPath(3, 4, 2);
		path.addPath(2, 3, 7);
		path.addPath(2, 4, 3);
		path.addPath(1, 1, 3);
		path.addPath(1, 0, 2);
		path.addPath(7, 6, 5);
		path.addPath(7, 5, 5);
		
		
		System.out.println( "BFS" );
		Vector<Edge> shortestPath = path.getShortestPath(1, 5);
		for( Edge e: shortestPath ) {
			System.out.println( e.StartRoom + "->" + e.door + "->" + e.DestinationRoom );
		}
		
		//MERGE
		System.out.println( "MERGE");
//		path.displayPaths();
//		Vector<Integer> matchedRooms = new Vector<Integer>();
//		matchedRooms.add(3);
//		matchedRooms.add(7);
//		path.mergeRooms(matchedRooms);
//		for ( int r: matchedRooms){
//			System.out.println("match "+r);
//		}
//		path.displayPaths();
//		
//		path.addPath(3, 5, 3);
//		path.addPath(3,6,5);
//		path.addPath(3, 4, 2);
//		path.addPath(2, 3, 7);
//		path.addPath(2, 4, 3);
//		path.addPath(1, 1, 3);
//		path.addPath(1, 0, 2);
//		path.addPath(7, 6, 5);
//		path.addPath(7, 5, 5);

		path.displayPaths();
		path.mergeAllSimilarRooms();
		path.displayPaths();
		
	}

}
