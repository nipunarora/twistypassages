/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.ui;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Satyajeet
 */
public class GameConfig {

    // DATA ABOUT THE MAZE
    // 1st room = start room and nth room = treasure room.
     int count;
    int number_of_rooms, number_of_objects, number_of_turns;
    int current_room;
    Boolean TreasureRoomFound;
    ArrayList<Integer> DroppedObjects;
    ArrayList<RoomObjectLink> roomobjectlist;
    ArrayList<Integer> visited_rooms;
    ArrayList<RoomRoomLink> PassageList;
    Player PObject;
    int current_score;
    String current_player;
    int start_room, treasure_room;
     int current_round;
    int zoomValue;


    public GameConfig() {
        count = 0;
    }

    public int addRet()
    {
        count++;
        return count;
    }

    // You can ask the question [room, direction_label] > leads to which room?
    public int passageQuery(int room1, int direction_label)
    {
        int room2 = -1;

        // Go through PassageList to get the answer
        for(int loop=0;loop<PassageList.size();loop++)
        {
            RoomRoomLink rrl_temp = PassageList.get(loop);
            if(rrl_temp.room1 == room1 && rrl_temp.direction_label == direction_label)
            {
                room2 = rrl_temp.room2;
                break;
            }
        }

        return room2;
    }

    // You can check if a particular room1-room2-direction_label edge has been visited before.
    public Boolean isRRLVisted(int room1, int room2, int direction_label)
    {
        Boolean answer = null;
        // Go through PassageList to get the answer
        for(int loop=0;loop<PassageList.size();loop++)
        {
            RoomRoomLink rrl_temp = PassageList.get(loop);
            if(rrl_temp.room1 == room1 && rrl_temp.direction_label == direction_label && rrl_temp.room2 == room2)
            {
                answer = rrl_temp.visited;
                break;
            }
        }


        return answer;
    }

    public void makeRRLVisited(int room1, int room2, int direction_label)
    {
        // Go through PassageList to get the answer
    	boolean wasvisited = false;
        for(int loop=0;loop<PassageList.size();loop++)
        {
            RoomRoomLink rrl_temp = PassageList.get(loop);
            if(rrl_temp.room1 == room1 && rrl_temp.direction_label == direction_label && rrl_temp.room2 == room2)
            {
            	wasvisited = rrl_temp.visited;
                rrl_temp.visited = true;
                break;
            }
        }
        // if this link was already visited, or is a self-loop link,
        // don't mark anything else
        if (wasvisited || room1 == room2) return;
		// Otherwise, also mark some previously unvisited reverse edge visited.
        for(RoomRoomLink rrl_temp  : PassageList)
        {
            if(rrl_temp.room1 == room2 && rrl_temp.room2 == room1 && !rrl_temp.visited )
            {
                rrl_temp.visited = true;
                break;
            }
        }

    }

    public int RoomObjectQuery(int room)
    {
        int object=0;
        // Let us traverse the RoomObjectList to get this
        for(int loop=0;loop<roomobjectlist.size();loop++)
        {
            RoomObjectLink rol_temp = roomobjectlist.get(loop);
            if(rol_temp.Room_number == room)
            {
                object = rol_temp.Object_number;
                break;
            }
        }
        return object; // If no object found zero is returned
    }

    public void addROL(int room, int object)
    {
        RoomObjectLink rol_temp = new RoomObjectLink(room, object);
        roomobjectlist.add(rol_temp);
        // Alse add the object to dropped objects list
        DroppedObjects.add(object);
    }

    public void delROL(int room)
    {
        int removed = 0;
        for(int loop=0;loop<roomobjectlist.size();loop++)
        {
            RoomObjectLink rol_temp = roomobjectlist.get(loop);
            if(rol_temp.Room_number == room)
            {
                removed = rol_temp.Object_number;
                // Delete this link
                roomobjectlist.remove(loop);
                break;
            }
        }
        // Also remove this object from DroppedObjects thereby making it available
        for(int loop=0;loop<DroppedObjects.size();loop++)
        {
            if(removed == DroppedObjects.get(loop))
            {
                DroppedObjects.remove(loop);
            }
        }

    }

    public HashMap<Integer, Integer> numVisits = new HashMap<Integer, Integer>();
    public Boolean addVisitedRoom(int room)
    {
    	if(numVisits.containsKey(room))
    		numVisits.put(room, numVisits.get(room) +1);
    	else
    		numVisits.put(room, 1);

        Boolean add = true;
        for(int loop=0;loop<visited_rooms.size();loop++)
        {
            if(visited_rooms.get(loop) == room)
            {
                add = false;
                break;
            }

        }
        if(add == true)
        {
            visited_rooms.add(room);
        }
        return add;
    }

    public Boolean isObjectAvailable(int object)
    {
        Boolean available = true;

        // Next the object number provided must not be in Dropped objects
            for(int loop=0;loop<DroppedObjects.size();loop++)
            {
                if(DroppedObjects.get(loop) == object)
                {
                    available = false;
                    break;
                }
            }
        return available;
    }

    public String objectNumtoName(int obj_num)
    {
        String charSt = new String();
        switch(obj_num)
        {
            case 0: charSt = ""; break;
            case 1: charSt = "a"; break;
            case 2: charSt = "b"; break;
            case 3: charSt = "c"; break;
            case 4: charSt = "d"; break;
            case 5: charSt = "e"; break;
            case 6: charSt = "f"; break;
            case 7: charSt = "g"; break;
            case 8: charSt = "h"; break;
            case 9: charSt = "i"; break;
            case 10: charSt = "j"; break;
            case 11: charSt = "k"; break;
            case 12: charSt = "l"; break;
            case 13: charSt = "m"; break;
            case 14: charSt = "n"; break;
            case 15: charSt = "o"; break;
            case 16: charSt = "p"; break;
            case 17: charSt = "q"; break;
            case 18: charSt = "r"; break;
            case 19: charSt = "s"; break;
            case 20: charSt = "t"; break;
            case 21: charSt = "u"; break;
            case 22: charSt = "v"; break;
            case 23: charSt = "w"; break;
            case 24: charSt = "x"; break;
            case 25: charSt = "y"; break;
            case 26: charSt = "z"; break;
            default: charSt = "0";break;

        }
        return charSt;
    }



}

class RoomObjectLink
{
    public int Room_number, Object_number;

    public RoomObjectLink(int r, int o) {
        Room_number = r;
        Object_number = o;
    }
}

class RoomRoomLink
{
    int room1, room2;
    Boolean visited; // Will be FALSE for starters
    int direction_label; // this will indicate the direction assigned
    public RoomRoomLink(int r1, int r2, Boolean vis, int label) {
        room1 = r1;
        room2 = r2;
        visited = vis;
        direction_label = label;
    }



}