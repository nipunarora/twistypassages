package maze.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;


import maze.ui.GraphPanel;

public class JBVisualizer extends GraphPanel{
    public int number;
    GameConfig gc_local;
    boolean setup = false;
    int Goffset, Gradius;
    int circleSize;
    Point centre;
    ArrayList<RoomCircle> roomCircleList;
    BufferedImage bi_local;

    public JBVisualizer() {

//	    setSize(500,500);
    }

    int cur_room = -1;
	@Override
	public void repaint() {
		super.repaint();
		
		if(gc_local != null && !setup)
		{
			setup = true;
			clear();
			addVertices();
			addEdges();
			layoutGraph();
		}
		if(gc_local != null)
		{
			Edge last_edge = new Edge(gc_local.current_room,cur_room);
			if(edgeCounts.containsKey(last_edge) && edgeCounts.get(last_edge) > 0)
			{
				edgeCounts.put(last_edge, edgeCounts.get(last_edge) - 1);
				Color c = new Color(0,10*edgeCounts.get(last_edge),0);
				GraphConstants.setLineColor(edges.get(last_edge).getAttributes(), c);
			}
			
			if(gc_local.current_room != cur_room)
			{
				if(cur_room > -1)
				{
					GraphConstants.setBackground(vertices.get(cur_room).getAttributes(), Color.YELLOW);
				}
				cur_room = gc_local.current_room;
				GraphConstants.setBackground(vertices.get(cur_room).getAttributes(), Color.RED);				
			}
			for(DefaultGraphCell i : vertices.values())
			{
				((VisualNode) i.getUserObject()).o = null;
			}
			for(RoomObjectLink r : gc_local.roomobjectlist)
			{
				int object_num = r.Object_number;
	            String object = gc_local.objectNumtoName(object_num);
	            ((VisualNode) vertices.get(r.Room_number).getUserObject()).o = object;
//	            vertices.get(r.Room_number).setUserObject(r.Room_number + ": " + object);
			}
			if(!gc_local.numVisits.containsKey(cur_room))
				gc_local.numVisits.put(cur_room, 0);
			((VisualNode) vertices.get(cur_room).getUserObject()).n = gc_local.numVisits.get(cur_room);
			graph.refresh();
			graph.getGraphLayoutCache().reload();
		}
		
	}

 
	private void addEdges()
	{
		for(RoomRoomLink r : gc_local.PassageList)
		{
			addEdge(r.room1, r.room2, new Color(50,255,50));
		}
	}
    private void addVertices() {
		// TODO Auto-generated method stub
		for(int i=1;i<=gc_local.number_of_rooms;i++)
		{
			addVertex(i,new Color(250,250,250));
		}
	}

   
}
