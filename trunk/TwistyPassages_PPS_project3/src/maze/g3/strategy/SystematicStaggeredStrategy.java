package maze.g3.strategy;

import maze.g3.G3Player;
import maze.g3.data.BagOfHolding;
import maze.g3.data.Item;
import maze.g3.data.Maze;
import maze.g3.data.Room;
import maze.g3.data.Room.RoomType;
import maze.ui.Move;

public class SystematicStaggeredStrategy extends Strategy {

	public SystematicStaggeredStrategy(Maze maze,BagOfHolding bag) {
		super(maze, bag);
		// TODO Auto-generated constructor stub
	}

	
	public Move move(int objectDetail, int numberOfObjects, int numberOfTurns) {
		// TODO Auto-generated method stub
		Move action;
		if(G3Player.StagCounter==10)
			G3Player.StagCounter=0;
		
		/** 
		 * Complete first move
		 */
		if (maze.isFirstRoom){
			maze.initializeMaze();
			actionDoor=0;
			setItem(maze.previousRoom);
			//populate the known paths
			G3Player.path.addPath(1, 0, 2);
			for(int i=1; i<=9;i++){
				G3Player.path.addPath(1, i, 1);
			}
			actionItem= maze.previousRoom.getItem();
			return new Move(actionDoor, actionItem);
		}
		//action to do when we come back to a fully explored room, also picking up objects here
		if(maze.currentRoom.knownEdgesCount==10){
			
			if(maze.currentRoom.getItem()!=0){
				Item i= new Item(maze.currentRoom.getItem());
				bag.returnItem(i);
				maze.currentRoom.setItem(0);
				
			}
			//rest of what to do
			
			return null;
		}
		
		//When the room does not have an object and bag has items left
		if(maze.currentRoom.getItem()==0&&bag.isNotEmpty()){
			//Unexplored room & staggered Counter==0, when stagCounter =0 we leave an object in the room
			if(G3Player.StagCounter==0&& maze.currentRoom.knownEdgesCount==0){
				Room r= maze.createNewRoom();
				G3Player.StagCounter++;
				maze.currentRoom.doorRoomKey[1]=r.getId();
				maze.currentRoom.knownEdgesCount++;
				setItem(maze.currentRoom);
				actionItem= maze.currentRoom.getItem();
				
				//Add room to elimination List
				G3Player.eliminationList.put(actionItem, maze.currentRoom.getId());
				
				maze.currentRoom= r;
				action= new Move(1,actionItem);
				return action;
			}
		
			if(G3Player.StagCounter==1&& maze.currentRoom.knownEdgesCount==0){
				Room r= maze.createNewRoom();
				G3Player.StagCounter++;
				maze.currentRoom.doorRoomKey[1]=r.getId();
				maze.currentRoom.knownEdgesCount++;
				setItem(maze.currentRoom);
				actionItem= maze.currentRoom.getItem();
				
				maze.currentRoom=r;
				action= new Move(1,actionItem);
				return action;
			}
			return null;
		}
		/**
		 * Treasure Room
		 */
		if(maze.currentRoom.getItem()==1){
				return null;
		}
		/**
		 * We have already dropped an item in this room
		 */
		if(maze.currentRoom.getItem()!=1&&maze.currentRoom.getItem()!=0){
			
		}
		/**
		 * We are out of items and staggered counter is less than 10
		 */
		
		/**
		 * We are out of items and staggered counter is greater than 10
		 */
		return null;
		
	
	}

}
