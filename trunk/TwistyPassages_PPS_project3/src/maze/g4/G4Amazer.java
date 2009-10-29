package maze.g4;

import maze.ui.Move;
import maze.ui.Player;
import java.util.*;

public class G4Amazer implements Player {

	public static final int NO_OBJECT = 0;
	public static final int PICK_UP_OBJECT = -1;


	protected SortedSet<Integer> unusedMarkers = new TreeSet<Integer>();
	protected MazeGraph maze = null;
	protected MazeGraph.Room here = null;
	protected int current_round = 1;


	public Move move(int object_detail,int number_of_objects, int total_rounds) {
		if (null == maze) {
			init(number_of_objects);
		}

		int to_drop = NO_OBJECT;
		if (object_detail > 0 && 1 < current_round) {
			here = maze.foundObject(object_detail,here);
			if (here.isStartRoom() || here.isTreasureRoom()) {
				maze.pickUpObject(object_detail);
				unusedMarkers.add(object_detail);
				to_drop = PICK_UP_OBJECT;
			}
		} else if (!unusedMarkers.isEmpty()) {
			to_drop = unusedMarkers.first();
			unusedMarkers.remove(to_drop);
			maze.dropObject(here, to_drop);
		}
		int direction = here.bestExit();
		here = here.takeDoor(direction);
		current_round++;
		return new Move(direction,to_drop);
	}

	private void init(int marker_count) {
		maze = new MazeGraph(marker_count);
		here = maze.getStartRoom();
		for (int i = 2; i <= marker_count; i++) {
			unusedMarkers.add(i);
		}

	}

}
