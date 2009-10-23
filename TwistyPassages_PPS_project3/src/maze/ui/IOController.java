/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.ui;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Satyajeet
 */
public class IOController {

    public ArrayList<String> getPlayerList()
    {
        ArrayList<String> PlayerNames = new ArrayList<String>();

        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("Game.playerlist");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
              // Print the content on the console
              //System.out.println (strLine);
              PlayerNames.add(strLine);
            }
            //Close the input stream
            in.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
            }



        return PlayerNames;
    }

    public Player getPObject(String playername)
    {
        Player myplayer = null;
        try {
            Class<Player> pc_object = (Class<Player>) Class.forName(playername);
             myplayer = (Player) pc_object.newInstance();

        } catch (InstantiationException ex) {
            System.out.println("Player CLass " + playername + " could not be instantiated!");
        } catch (IllegalAccessException ex) {
            System.out.println("Player CLass " + playername + " Illegal Access!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Player CLass " + playername + " was not found!");
        }
         return myplayer;
    }

    public ArrayList<String> getMazeList()
    {
        ArrayList<String> Mazes = new ArrayList<String>();
        File thisdir = new File("./Mazebox/");
        String[] AllFiles = thisdir.list();
        for(int loop=0;loop<AllFiles.length;loop++)
        {
            //System.out.println("Filename:" + AllFiles[loop]);
            int length = AllFiles[loop].length();
            if(length > 4)
            {
                String last4 = AllFiles[loop].substring(length-4,length);
                //System.out.println("Last4 = " + last4);
                if(last4.equalsIgnoreCase("maze"))
                {
                    //System.out.println("This file contains a grid > " + AllFiles[loop]);
                    Mazes.add(AllFiles[loop]);
                }
            }
        }
        
        return Mazes;
    }


    public GameConfig makeGameConfig(String mazename, String current_playername,int number_of_objects)
    {
        GameConfig gameconfig = new GameConfig();
        try {
            CSVReader csvr = new CSVReader(new FileReader("./Mazebox/" + mazename));
            String[] nextLine;
            int count = 0;
            try {
                while ((nextLine = csvr.readNext()) != null) {

                    if(count == 0)
                    {
                        gameconfig.number_of_rooms = Integer.parseInt(nextLine[0]);
                        gameconfig.number_of_turns = Integer.parseInt(nextLine[1]);
                        gameconfig.visited_rooms = new ArrayList<Integer>();
                        gameconfig.roomobjectlist = new ArrayList<RoomObjectLink>();
                        gameconfig.PassageList = new ArrayList<RoomRoomLink>();
                        gameconfig.PObject = getPObject(current_playername);
                        gameconfig.DroppedObjects = new ArrayList<Integer>();
                        gameconfig.current_round = 0;
                        gameconfig.current_score = 10; // Since you see the first room
                        gameconfig.current_player = current_playername;
                        gameconfig.number_of_objects = number_of_objects;
                        gameconfig.TreasureRoomFound = false;
                        gameconfig.zoomValue = 3;
                        
                        count++;

                        // We assume that '1' is the start room.
                        

                        continue;

                    }
                    else if(count == 1)
                    {
                        // START ROOM DETAILS
                        // We assume that '1' is the start room.
                        gameconfig.start_room = Integer.parseInt(nextLine[0]);
                        // Also add that room to visited rooms
                        gameconfig.visited_rooms.add(gameconfig.start_room);
                        gameconfig.current_room = gameconfig.start_room;
                        int room2 = Integer.parseInt(nextLine[1]);
                        RoomRoomLink rrl = new RoomRoomLink(gameconfig.start_room, room2, Boolean.FALSE,0);
                        gameconfig.PassageList.add(rrl);
                        // Also need to add self loop links
                        for(int selfloop=1;selfloop<=9;selfloop++)
                        {
                        	 rrl = new RoomRoomLink(gameconfig.start_room,gameconfig.start_room , Boolean.FALSE,selfloop);
                            gameconfig.PassageList.add(rrl);
                        }
                        
                        count++;
                        continue;
                    }
                    else if(count > 1 && count == gameconfig.number_of_rooms)
                    {
                        // LAST ROW, TREASURE ROOM
                        gameconfig.treasure_room = Integer.parseInt(nextLine[0]);
                        int room2 = Integer.parseInt(nextLine[1]);
                        RoomRoomLink rrl = new RoomRoomLink(gameconfig.treasure_room, room2, Boolean.FALSE,0);
                        gameconfig.PassageList.add(rrl);
                        // Also need to add self loop links
                        for(int selfloop=1;selfloop<=9;selfloop++)
                        {
                        	 rrl = new RoomRoomLink(gameconfig.treasure_room,gameconfig.treasure_room , Boolean.FALSE,selfloop);
                            gameconfig.PassageList.add(rrl);
                        }
                        count++;
                        continue;
                    }
                    else
                    {
                        // For all the cases except 0, 1 and n
                        Random rand = new Random();
                        int room1 = Integer.parseInt(nextLine[0]);
                        // Now let us pick up the other rooms, given them labels
                        Boolean[] label_bool = new Boolean[10];
                        for(int loop=0;loop<10;loop++)
                        {
                            label_bool[loop] = true; // They are all available.
                        }
                        for(int loop=1;loop<=10;loop++)
                        {
                            int room2 = Integer.parseInt(nextLine[loop]);
                            Boolean label_done = false;
                            while(label_done == false)
                            {
                                int label = rand.nextInt(10);
                                if(label_bool[label] == true)
                                {
                                    label_done = true; // this loop can break
                                    label_bool[label] =  false; // this is not available
                                    // Let us create this roomroomlink and add to passagelist
                                    RoomRoomLink rrl = new RoomRoomLink(room1, room2, Boolean.FALSE, label);
                                    gameconfig.PassageList.add(rrl);

                                }
                            }

                        }
                        count++;
                        continue;
                    }



                }
            } catch (IOException ex) {
                System.out.println("Something went wrong with IO while reading maze file.");
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Could not open maze file.");
        }

        // Before you return it, you are adding the first object to the visited list, and you are creating
        // and ROL for it and the treasure room.
        gameconfig.addROL(gameconfig.treasure_room, 1);
        


        //System.out.println("gameconfig created!");
        return gameconfig;
    }
}
