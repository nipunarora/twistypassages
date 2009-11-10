package maze.g3.strategy;

import maze.g3.G3Player;
import maze.g3.data.BagOfHolding;
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
		try{
		Move action;
		/**
		 * Changing Stag Counter to 0 if it reaches 10
		 */
		if(G3Player.StagCounter==10)
			G3Player.StagCounter=0;
		
		/** 
		 * Complete first move
		 */
		if (maze.isFirstRoom){
		
			System.out.println("Entered first room");
			maze.initializeMaze();
			actionDoor=0;
			//populate the known paths
			G3Player.path.addPath(1, 0, 2);
			G3Player.StagCounter=0;
			setItem(maze.previousRoom);
			actionItem= maze.previousRoom.getItem();
			return new Move(actionDoor, actionItem);
		}
		
		//When the room does not have an object and bag has items left
		if(objectDetail==0&&bag.isNotEmpty()){
			System.out.println("No object items left");
		    //Unexplored room & staggered Counter==0, when stagCounter =0 we leave an object in the room
			if(G3Player.StagCounter==0){
				System.out.println("Unexplored room");
				Room r= maze.createNewRoom();
				G3Player.StagCounter++;
				actionDoor=G3Player.rand.nextInt(10);
				maze.currentRoom.doorRoomKey[actionDoor]=r.getId();
				maze.currentRoom.knownEdgesCount++;
				setItem(maze.currentRoom);
				actionItem= maze.currentRoom.getItem();
				
				//Add room to elimination List
				G3Player.eliminationList.put(actionItem, maze.currentRoom.getId());
				maze.currentRoom= r;
				
				action= new Move(1,actionItem);
				return action;
			}
			
				
			if(G3Player.StagCounter==1){
				System.out.println("Unexplored neighbor of elimination room");
				Room r= maze.createNewRoom();
				G3Player.StagCounter++;
				actionDoor=G3Player.rand.nextInt(10);
				maze.currentRoom.doorRoomKey[actionDoor]=r.getId();
				maze.currentRoom.knownEdgesCount++;
				setItem(maze.currentRoom);
				actionItem= maze.currentRoom.getItem();
				maze.currentRoom=r;
				return new Move(actionDoor,actionItem);
			}
			
			if(G3Player.StagCounter>1){
				System.out.println("Rest of unexplored rooms");
				Room r = maze.createNewRoom();
				G3Player.StagCounter++;
				actionDoor=G3Player.rand.nextInt(10);
				maze.currentRoom.doorRoomKey[actionDoor]=r.getId();
				maze.currentRoom.knownEdgesCount++;
				
				maze.currentRoom=r;
			}
		}
		
		/**
		 * Treasure Room //HAVE TO MAKE SURE THAT THE ADJOINING ROOM TO THE TREASURE ROOM IS ADDED TO THE ELIMINATION LIST
		 */
		if(objectDetail==1&&G3Player.treasureRoomFlag==false){
			G3Player.StagCounter=0;
			System.out.println("TreasureRoom");
			G3Player.treasureRoomFlag=true;
			int lastRoom=G3Player.history.getLastRoom();	
			int lastDoor=G3Player.history.getLastDoor();
			maze.getRoom(lastRoom).doorRoomKey[lastDoor]=maze.currentRoom.getId();
			
			for(int i=1; i<=9;i++){
				maze.currentRoom.doorRoomKey[i]=maze.currentRoom.getId();
			}
			maze.currentRoom= maze.previousRoom;
			
			
			//return the action
			actionItem=-1;
			actionDoor=0;
			bag.returnItem(objectDetail);
			action = new Move(actionDoor,actionItem);
			return action;
		}
		/**
		 * We have already dropped an item in this room
		 */
		if(objectDetail>0){
			System.out.println("Already Explored Room");
			int roomid=G3Player.itemMapList.get(objectDetail);
			Room r= maze.getRoom(roomid);
		
			//if we do not know all incoming edges and it is in the elimination list
			if(G3Player.path.destinationPaths.get(roomid).size()<10&&G3Player.eliminationList.containsKey(objectDetail)){
				G3Player.StagCounter=1;
				for (int i=0;i<=9;i++){
					if(r.doorRoomKey[i]==0)
					{	
						actionDoor=i;
						break;
					}
				}
				actionItem=0;
				Room newRoom= maze.createNewRoom();
				maze.previousRoom=maze.currentRoom;
				maze.currentRoom=newRoom;
				
				return new Move(actionDoor,actionItem);
			}
			
			//if we know all the incoming edges and it is in the elimination list
			if(G3Player.path.destinationPaths.get(roomid).size()==10&&G3Player.eliminationList.containsKey(objectDetail)){
				G3Player.StagCounter=1;
				actionItem=-1;
				bag.returnItem(objectDetail);
				
				for(int i=0;i<G3Player.path.startPaths.get(roomid).size();i++){
					int destRoomId=G3Player.path.startPaths.get(roomid).get(i).getDestinationRoom();
					if(maze.getRoom(destRoomId).getItem()>0&&!G3Player.eliminationList.containsKey(maze.getRoom(destRoomId).getItem())){
						//have to find the neighbor with the highest no of found edges
						actionDoor=G3Player.path.startPaths.get(roomid).get(i).getDoor();
						G3Player.eliminationList.remove(objectDetail);
						G3Player.eliminationList.put(maze.getRoom(destRoomId).getItem(), destRoomId);
						break;
					}
				}
				return new Move(actionDoor,actionItem);
			}
			
			//if we are in a neighbor
				if(G3Player.StagCounter==1&&!G3Player.eliminationList.containsKey(objectDetail)){
					G3Player.StagCounter++;
					actionDoor= G3Player.rand.nextInt(10);
					actionItem=0;
					return new Move(actionDoor, actionItem);
				}
			//if we are in a node that is neither neighbor nor in elimination list

			//if we are out of objects and we know an incoming edge and there is an item in that incoming edge room
			
		}
		
		if(bag.isNotEmpty()==false){
			
			actionDoor= G3Player.rand.nextInt(10);
			if(objectDetail>0&&!G3Player.eliminationList.containsKey(objectDetail)){
				actionItem=-1;
				bag.returnItem(objectDetail);
			}
			else
				actionItem=0;
			return new Move(actionDoor, actionItem);
		}
		else{
			actionDoor=G3Player.rand.nextInt(10);
			actionItem=0;
			return new Move(actionDoor,actionItem);
		}
		}catch(Exception e){
			actionDoor=G3Player.rand.nextInt(10);
			actionItem=0;
			return new Move(actionDoor,actionItem);
		}
	}

}