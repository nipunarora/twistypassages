/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.ui;

/**
 *
 * @author Satyajeet
 */
 public class Move {

    // 10 directions from 0 to 9
    int direction;

    // If you want to pick up an object say -1
    // If you want to drop an object, use the object id (1 to n) n could be upto 26 I guess.
    // If you dont want to do anything use 0
    // Note: If a room contains an object you cant drop another object in it.
    int object_decision;

    public Move(int MYdirection, int MYobject_decision)
    {
        direction = MYdirection;
        object_decision = MYobject_decision;
    }
}
