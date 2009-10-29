package maze.g2;

import java.util.ArrayList;

public class Path {
	/* This can change, but adding some interface */
	public Room origin;
	public Room dest;
	
	public ArrayList<Integer> steps = new ArrayList<Integer>();

	public int length() {
		return steps.size();
	}
}
