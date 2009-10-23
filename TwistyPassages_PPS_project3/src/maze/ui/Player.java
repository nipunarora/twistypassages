/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.ui;

/**
 *
 * @author Satyajeet
 */
public interface Player {

    // Object detail reflects the state of the room that you have entered
    // if object_detail = (1 to n) that means the room contains that object
    // if object_detail = 0 then room does not contain an object
    
    public Move move(int object_detail,int number_of_objects, int total_rounds);

}

