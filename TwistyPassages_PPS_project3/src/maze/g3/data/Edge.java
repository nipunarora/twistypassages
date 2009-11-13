package maze.g3.data;

import maze.g3.Logger;
import maze.g3.Logger.LogLevel;

/**
 * 
 * @author Nipun Arora,Shilpa, Colin
 *
 *
 *
 */
public class Edge {

	int StartRoom;
	int door;
	int DestinationRoom;
	public  Logger log = new Logger( LogLevel.DEBUG, this.getClass() );
	
	public void print(String info) {
		log.debug(info);
	}
	
	public String toString()
	{
		return "Edge: [StartRoom ="+StartRoom + " door="+door+" destiR="+DestinationRoom + " ]";
	}

	/**
	 * default constructor
	 * @param StartRoom
	 * @param door
	 * @param DestinationRoom
	 */
	public Edge(int StartRoom, int door, int DestinationRoom){
		this.StartRoom= StartRoom;
		this.door= door;
		this.DestinationRoom=DestinationRoom;
	}
	/**
	 * return the destination room
	 * @return
	 */
	public int getDestinationRoom(){
		return this.DestinationRoom;
	}
	/**
	 * get Door
	 * @return
	 */
	public int getDoor(){
		return this.door;
	}
	/**
	 * comparator for edges
	 * @param e
	 * @return
	 */
	public boolean equals(Edge e){
		if(this.StartRoom==e.StartRoom&&this.DestinationRoom==e.DestinationRoom&&this.door==e.door)
			return true;
		else
			return false;
	}
}
