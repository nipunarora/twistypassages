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
		path.addPath(3,6,5);
		path.addPath(3, 4, 2);
		path.addPath(2, 3, 7);
		path.addPath(2, 4, 3);
		path.addPath(1, 1, 3);
		path.addPath(1, 0, 2);
		path.addPath(7, 6, 5);
		path.addPath(7, 5, 5);
		
		Vector<Edge> shortestPath = path.getShortestPath(1, 5);
		
		System.out.println( "BFS" );
		for( Edge e: shortestPath ) {
			System.out.println( e.StartRoom + "->" + e.door + "->" + e.DestinationRoom );
		}
		
		Vector<Edge> v = new Vector<Edge>();
		v.add(new Edge(1,0,2));
		v.add(new Edge(1,1,3));
		v.add(new Edge(2,4,3));
		v.add(new Edge(2,3,7));
		v.add(new Edge(3,4,2));
		v.add(new Edge(3,6,5));
		v.add(new Edge(3, 5, 3));
		
	}

}
