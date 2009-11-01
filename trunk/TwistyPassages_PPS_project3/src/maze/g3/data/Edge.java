package maze.g3.data;
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
}
