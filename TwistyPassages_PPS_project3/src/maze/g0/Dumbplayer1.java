/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.g0;

import maze.ui.Move;
import maze.ui.Player;

/**
 *
 * @author Satyajeet
 */
public class Dumbplayer1 implements Player {
//test comment
    public Move move(int object_detail, int number_of_objects, int number_of_turns) {
        
        Move myMove = new Move(1,0);
        return myMove;

    }

}
