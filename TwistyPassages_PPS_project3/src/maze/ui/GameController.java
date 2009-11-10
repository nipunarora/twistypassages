/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Satyajeet
 */
public class GameController {

    private int count;

    public GameController() {
        count = 0;
    }



    public int GamePlay(GameConfig gameconfig)
    {
        // First we check if game has ended or no
        if(gameconfig.current_round == gameconfig.number_of_turns)
        {
            // Now return score, but check to see if it needs to be doubled
           if(gameconfig.TreasureRoomFound == true)
            {
                return 2 * (gameconfig.current_score - 20);
            }
            else
            {
            	return gameconfig.current_score - 10;
            }

        }

        // Irrespective of where this function is called from, it needs to execute gameLogic based on
        // what is stored in gameConfig. Now we have to use Player Pobject to continue playing
        int objectHere = gameconfig.RoomObjectQuery(gameconfig.current_room);
        Move currentMove = gameconfig.PObject.move(objectHere, gameconfig.number_of_objects, gameconfig.number_of_turns);

        // Before the player moves, he decides to take an object decision
        //  Move.object_decision (-1 = pickup), (0, do nothing) and (1-26 possible object)
        // First room needs to be blank
        if(objectHere == 0)
        {
            if(currentMove.object_decision > 0 && currentMove.object_decision <= gameconfig.number_of_objects)
            {
                // Drop command was recieved
                if(gameconfig.isObjectAvailable(currentMove.object_decision))
                {
                    // This object can be dropped
                    gameconfig.addROL(gameconfig.current_room, currentMove.object_decision);
                    // Note this in the ROL list
                    // Also note that this the room that you are leaving

                }
            }

        }
        // First an object needs to exist, in order for you to pick it up
        if(objectHere != 0)
        {
            if(currentMove.object_decision == -1)
            {
                // User wants to pick this object up
                gameconfig.delROL(gameconfig.current_room);
            }
        }
        if(objectHere !=0 && currentMove.object_decision > 0)
        {
            System.out.println("GAME ERROR! There's an object here. First pick it up!");
        }
        if(objectHere == 0 && currentMove.object_decision == -1)
        {
            System.out.println("GAME ERROR! There is no object to pickup");
        }

        // AFTER POCKETING THIS OBJECT OR DROPPING ONE HERE, THE PLAYER MOVES ON

        // Now the player plays the game and returns move.direction [0-9]

        int old_room = gameconfig.current_room;
        gameconfig.current_room = gameconfig.passageQuery(old_room, currentMove.direction);
        // If current_room = -1 means the above move was illegal
        if(gameconfig.current_room==-1){System.out.println("Illegal Move"); return -1;}

        // Use NewRoom for scoring (later)
        Boolean NewRoom = gameconfig.addVisitedRoom(gameconfig.current_room);

        // Now lets see if this passage was visted, else add.
        Boolean NewRRL = gameconfig.isRRLVisted(old_room, gameconfig.current_room,currentMove.direction);
        if(NewRRL == false){gameconfig.makeRRLVisited(old_room, gameconfig.current_room,currentMove.direction);}

        gameconfig.current_round++;

        if(gameconfig.current_room == gameconfig.treasure_room)
        {
            gameconfig.TreasureRoomFound = true;
        }

        // Now let us calculate add to the score

        if(NewRoom == true)
        {
            gameconfig.current_score += 10;
        }
        if(NewRRL == false) // The convention is slightly different
        {
            gameconfig.current_score += 1;
        }

        // -1 indicates that the game is still going on.
        return -1;

    }




}
