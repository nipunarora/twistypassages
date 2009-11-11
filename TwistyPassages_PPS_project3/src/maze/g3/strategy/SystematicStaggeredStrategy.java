package maze.g3.strategy;

import maze.g3.G3IndianaHosed;
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
		log.debug("bag size: " + bag.getSize());
		
		try{
		Move action;
		/**
		 * Changing Stag Counter to 0 if it reaches 10
		 */
		if(G3IndianaHosed.StagCounter==10)
			G3IndianaHosed.StagCounter=0;
		
		/** 
		 * Complete first move
		 */
		if (maze.isFirstRoom){
		
			log.debug("Entered first room");
			maze.initializeMaze();
			actionDoor=0;
			//populate the known paths
			
			G3IndianaHosed.StagCounter=0;
			setItem(maze.previousRoom);
			
			maze.previousDoor=0;
			actionItem= maze.previousRoom.getItem();
			
			G3IndianaHosed.eliminationList.put(actionItem, 1);
			
			log.debug("RoomId:"+ maze.previousRoom.getId());
			return new Move(actionDoor, actionItem);
		}
		
		//When the room does not have an object and bag has items left
		if(objectDetail==0&&bag.isNotEmpty()){
			log.debug(" items are left");
		    //Unexplored room & staggered Counter==0, when stagCounter =0 we leave an object in the room
			if(G3IndianaHosed.StagCounter==0){
				log.debug("Unexplored room");
				Room r= maze.createNewRoom();
				setItem(r);
				G3IndianaHosed.StagCounter++;
				actionDoor=G3IndianaHosed.rand.nextInt(10);
				maze.previousRoom.doorRoomKey[maze.previousDoor]=r.getId();
				actionItem= r.getItem();
				
				//advancing previous entities
				maze.previousDoor=actionDoor;
				maze.previousRoom=r;
				
				//Add room to elimination List
				G3IndianaHosed.eliminationList.put(actionItem, maze.previousRoom.getId());
				action= new Move(actionDoor,actionItem);
				
				log.debug("RoomId:"+ maze.previousRoom.getId());
				return action;
			}
			
				
			if(G3IndianaHosed.StagCounter==1){
				log.debug("Unexplored neighbor of elimination room");
				Room r= maze.createNewRoom();
				G3IndianaHosed.StagCounter++;
				setItem(r);
				actionDoor=G3IndianaHosed.rand.nextInt(10);
				maze.previousRoom.doorRoomKey[maze.previousDoor]=r.getId();
				actionItem= r.getItem();
				
				//advancing
				maze.previousDoor=actionDoor;
				maze.previousRoom=r;
				log.debug("action Item"+ actionItem);
				action= new Move(actionDoor,actionItem);
				log.debug("action Door: "+ action.getDoor()+ " action Item "+ action.getItem());

				log.debug("RoomId:"+ maze.previousRoom.getId());
				return action;
			}
			
			if(G3IndianaHosed.StagCounter>1){
				log.debug("Rest of unexplored rooms");
				Room r = maze.createNewRoom();
				maze.previousRoom.doorRoomKey[maze.previousDoor]=r.getId();
				G3IndianaHosed.StagCounter++;
				actionDoor=G3IndianaHosed.rand.nextInt(10);
				actionItem= 0;
				
				//advancing
				maze.previousDoor=actionDoor;
				maze.previousRoom=r;
				
				action = new Move(actionDoor,actionItem);
				
				log.debug("RoomId:"+ maze.previousRoom.getId());
				return action;
			}
		}
		
		/**
		 * Treasure Room //HAVE TO MAKE SURE THAT THE ADJOINING ROOM TO THE TREASURE ROOM IS ADDED TO THE ELIMINATION LIST
		 */
		if(objectDetail==1&&G3IndianaHosed.treasureRoomFlag==false){
			G3IndianaHosed.StagCounter=0;
			log.debug("TreasureRoom");
			G3IndianaHosed.treasureRoomFlag=true;
			actionDoor=0;
			actionItem=-1;
			bag.returnItem(objectDetail);
			
			Room r= maze.createNewRoom();
			maze.previousRoom.doorRoomKey[maze.previousDoor]=r.getId();
			
			for(int i=1; i<=9;i++){
				r.doorRoomKey[i]=r.getId();
				G3IndianaHosed.path.addPath(r.getId(), i, r.getId());
			}
			
			maze.previousDoor=0;
			maze.previousRoom=r;
			
			//return the action
			action = new Move(actionDoor,actionItem);
			
			log.debug("RoomId:"+ maze.previousRoom.getId());
			return action;
		}
		/**
		 * We have already dropped an item in this room
		 */
		if(objectDetail>0){
			log.debug("Already Explored Room");
			int roomid=G3IndianaHosed.itemMapList.get(objectDetail);
			Room r= maze.getRoom(roomid);
			maze.previousRoom.doorRoomKey[maze.previousDoor]=r.getId();
			//if we do not know all incoming edges and it is in the elimination list
			if(r.incomingEdgesCount()<10&&G3IndianaHosed.eliminationList.containsKey(objectDetail)){
				log.debug("Elimination List Room but not fully explored");
				G3IndianaHosed.StagCounter=1;
				
				for (int i=0;i<=9;i++){
					if(r.doorRoomKey[i]==0)
					{	
						actionDoor=i;
						break;
					}
				}
				actionItem=0;
				
				//advancing
				maze.previousRoom=r;
				maze.previousDoor=actionDoor;
				
				log.debug("RoomId:"+ maze.previousRoom.getId()+ "no of incoming edges known"+ maze.previousRoom.incomingEdgesCount());
				return new Move(actionDoor,actionItem);
			}
			
			//if we know all the incoming edges and it is in the elimination list
			if(r.incomingEdgesCount()>=10&&G3IndianaHosed.eliminationList.containsKey(objectDetail)){
				
				log.debug("Elimination List Room fully explored");
				G3IndianaHosed.StagCounter=1;
				actionItem=-1;
				bag.returnItem(objectDetail);
				
				//find the neighbor with the maximum number of edges already explored and add it to the elimination list...
				int maxindex=0;
				int maxsize=0;
				for(int i=0;i<G3IndianaHosed.path.startPaths.get(roomid).size();i++){
					int destRoomId=G3IndianaHosed.path.startPaths.get(roomid).get(i).getDestinationRoom();
					if(maxsize<maze.getRoom(destRoomId).incomingEdgesCount()){
						maxsize=maze.getRoom(destRoomId).incomingEdgesCount();
						maxindex=i;
					}
					if(maze.getRoom(destRoomId).incomingEdgesCount()==10){
						maxsize=0;
					}
				}
				
				int destinationRoom= G3IndianaHosed.path.startPaths.get(roomid).get(maxindex).getDestinationRoom();
					
						actionDoor=G3IndianaHosed.path.startPaths.get(roomid).get(maxindex).getDoor();
						G3IndianaHosed.eliminationList.remove(objectDetail);
						if(maze.getRoom(destinationRoom).incomingEdgesCount()<10){
						G3IndianaHosed.eliminationList.put(maze.getRoom(destinationRoom).getItem(), destinationRoom);
						}
					
						maze.previousRoom=r;
						maze.previousDoor=actionDoor;
						
				log.debug("RoomId:"+ maze.previousRoom.getId()+ "no of incoming edges known"+ maze.previousRoom.incomingEdgesCount());
				return new Move(actionDoor,actionItem);
			}
			
			//if we are in a neighbor
				if(G3IndianaHosed.StagCounter==1&&!G3IndianaHosed.eliminationList.containsKey(objectDetail)){
					
					log.debug("Neighboring Room");
					G3IndianaHosed.StagCounter++;
					for (int i=0;i<=9;i++){
						if(r.doorRoomKey[i]==0)
						{	
							actionDoor=i;
							break;
						}
					}
					actionItem=0;
					
					maze.previousRoom=r;
					maze.previousDoor=actionDoor;
					log.debug("RoomId:"+ maze.previousRoom.getId()+ "no of incoming edges known"+ maze.previousRoom.incomingEdgesCount());
					return new Move(actionDoor, actionItem);
				}
			//if we are in a node that is neither neighbor nor in elimination list

			//if we are out of objects and we know an incoming edge and there is an item in that incoming edge room
			
				
				log.debug("Has an item really don't know where we are?");
				G3IndianaHosed.StagCounter=0;
				G3IndianaHosed.eliminationList.put(objectDetail, r.getId());
				for (int i=0;i<=9;i++){
					if(r.doorRoomKey[i]==0)
					{	
						actionDoor=i;
						break;
					}
				}
				actionItem=0;
				
				maze.previousRoom=r;
				maze.previousDoor=actionDoor;
				
				log.debug("RoomId:"+ maze.previousRoom.getId()+ "no of incoming edges known"+ maze.previousRoom.incomingEdgesCount());
				return new Move(actionDoor, actionItem);
		}
		
		if(bag.isNotEmpty()==false){
			log.debug("bag is empty");
			Room r= maze.createNewRoom();
			maze.previousRoom.doorRoomKey[maze.previousDoor]=r.getId();
			
			actionDoor= G3IndianaHosed.rand.nextInt(10);
			if(objectDetail>0&&!G3IndianaHosed.eliminationList.containsKey(objectDetail)){
				actionItem=-1;
				bag.returnItem(objectDetail);
			}
			else
				actionItem=0;
			
			maze.previousRoom=r;
			maze.previousDoor=actionDoor;
			
			return new Move(actionDoor, actionItem);
		}
		else{
			log.debug("Should never enter here");
			Room r= maze.createNewRoom();
			maze.previousRoom.doorRoomKey[maze.previousDoor]=r.getId();
			
			actionDoor=G3IndianaHosed.rand.nextInt(10);
			actionItem=0;
			

			maze.previousRoom=r;
			maze.previousDoor=actionDoor;
			return new Move(actionDoor,actionItem);
		}
		}catch(Exception e){
			log.debug("Caught Exception");
			actionDoor=G3IndianaHosed.rand.nextInt(10);
			actionItem=0;
			return new Move(actionDoor,actionItem);
		}
		
	}
	
	

}
