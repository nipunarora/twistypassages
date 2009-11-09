/**
 *
 */
package maze.ui;

import java.util.ArrayList;

/**
 * @author ben
 *
 */
public class MazeRunner {
	enum runType {RUN_ONCE, RUN_MULTIPLE, RUN_TOURNEY};
	static final String PACKAGE_PREFIX = "maze.";
	/**
	 * Argument forms allowed:
	 * Player mazename object_count [repeats]
	 * mazename object_count repeats [player-name-filter]
	 *
	 * In the first case, the given player will be run through the given maze with the given number of objects, and the score printed.
	 * If the optional "repeats" argument is supplied, it will be run that number of times, and the scores listed on a single line.
	 * In the second form, all players will be run with the given object count and repetitions.  If the optional
	 * filter argument is supplied, then only players whose names start with that string (e.g. "maze.g4") will be evaluated.
	 * @param args
	 */
	public static void main(String[] args) {
		IOController iocontrol = new IOController();
		String playerName = null;
		String mazeName = null;
		int object_count = -1;
		int rep_count = 1;
		runType type = runType.RUN_ONCE;
		String msg;

		if (args[0].startsWith(PACKAGE_PREFIX)) {
			playerName = args[0];
			mazeName = args[1];
			object_count = Integer.parseInt(args[2]);
			msg = String.format(
					"Running maze %s with player %s and %d objects",
					new Object[]{mazeName,playerName, object_count}
			);
			if (4 == args.length) {
				rep_count = Integer.parseInt(args[3]);

				msg = String.format(
					"Repeating maze %s with player %s and %d objects, %d times",
					new Object[]{mazeName,playerName, object_count, rep_count}
				);
			}
			System.out.println(msg);
			runRepeats(iocontrol, playerName, mazeName, object_count, rep_count);
		} else {
			mazeName = args[0];
			object_count = Integer.parseInt(args[1]);
			rep_count = Integer.parseInt(args[2]);
			String prefix = 4 == args.length ? args[3] : PACKAGE_PREFIX;
			ArrayList<String> allPlayers = iocontrol.getPlayerList();
			for (String p : allPlayers) {
				if (p.startsWith(prefix)) {
					runRepeats(iocontrol, p, mazeName, object_count, rep_count);
				}
			}
		}


	}

	/**
	 * @param iocontrol
	 * @param playerName
	 * @param mazeName
	 * @param object_count
	 * @param rep_count
	 */
	private static void runRepeats(IOController iocontrol, String playerName, String mazeName, int object_count, int rep_count) {
		int scores[] = new int[rep_count];
		for (int i = 0; i < rep_count; i++) {
			scores[i] = runMaze(iocontrol, playerName, mazeName, object_count);
		}
		System.out.print(playerName);
		for (int s : scores) {
			System.out.print('\t');
			System.out.print(Integer.toString(s));
		}
		System.out.print('\n');
	}

	/**
	 * @param i the IOController that will actually find the maze and player objects
	 * @param playerName the name of the class of player to be run
	 * @param mazeName the name of the maze file to be run
	 * @param object_count the number of objects to be used
	 * @return the score
	 */
	private static int runMaze(IOController i, String playerName, String mazeName, int object_count) {
		GameConfig simpleConfig = i.makeGameConfig(mazeName, playerName, object_count);
		GameController r = new GameController();
		int score = -1;
		while(0 > score) {
			score = r.GamePlay(simpleConfig);
		}
		return score;
	}

}
