package maze.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.ParentMap;
import org.jgraph.graph.Port;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.JGraphModelFacade;
import com.jgraph.layout.graph.JGraphAnnealingLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import com.jgraph.layout.graph.JGraphSpringLayout;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.organic.JGraphOrganicLayout;
import com.jgraph.layout.organic.JGraphSelfOrganizingOrganicLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;



public class GraphPanel extends JPanel{

	protected HashMap<Integer, DefaultGraphCell> vertices = new HashMap<Integer, DefaultGraphCell>();
	protected HashMap<Edge,DefaultGraphCell> edges = new HashMap<Edge, DefaultGraphCell>();
	protected HashMap<Edge,Integer> edgeCounts = new HashMap<Edge, Integer>();
	boolean tained = false;
	protected JGraph graph;
	protected JGraphFacade facade;
	Object parent;
	JGraphLayout layout = new JGraphFastOrganicLayout();
	public void setZoom(double zoom)
	{
		((JGraphFastOrganicLayout) layout).setForceConstant(zoom*10);
		layoutGraph();
	}
	public void clear()
	{

		vertices = new HashMap<Integer, DefaultGraphCell>();
		edges = new HashMap<Edge, DefaultGraphCell>();
		graph = new JGraph(new DefaultGraphModel());
		graph.setMoveable(true);
		graph.setMinimumSize(new Dimension(500,500));

		
		edgeCounts = new HashMap<Edge, Integer>();
//		 layout = new JGraphFastOrganicLayout();
		 
//		((mxOrthogonalLayout) layout).
		removeAll();		
		add(graph);
		facade = null;
	}
	public void addVertex(int v, Color c)
	{

			DefaultGraphCell ins =  new DefaultGraphCell(new VisualNode(v));

			GraphConstants.setBounds(ins.getAttributes(), new Rectangle2D.Double(
					0,0, 50,15));
			GraphConstants.setBackground(ins.getAttributes(), c);
			GraphConstants.setBorderColor(ins.getAttributes(), Color.black);
			GraphConstants.setOpaque(ins.getAttributes(), true);
			GraphConstants.setFont(ins.getAttributes(), new Font("sans-serif",Font.PLAIN,10));
			ins.addPort();
			vertices.put(v, ins);
			graph.getGraphLayoutCache().insert(ins);
	
	}

	public void addEdge(int n1, int n2, Color c) {
		Edge e = new Edge(n1,n2);
		if(edgeCounts.containsKey(e))
		{
			edgeCounts.put(e, edgeCounts.get(e)+1);
//			edges.get(e).setUserObject(edgeCounts.get(e));
			GraphConstants.setLineWidth(edges.get(e).getAttributes(), edgeCounts.get(e));

		}
		else
		{
			edgeCounts.put(e, 1);
			DefaultEdge edge = new DefaultEdge(); //set user object to edgeCounts.get(e) to make it show # of || edges
			edge.setSource(vertices.get(n1).getChildAt(0));
			edge.setTarget(vertices.get(n2).getChildAt(0));
			GraphConstants.setLineColor(edge.getAttributes(), c);
			GraphConstants.setBendable(edge.getAttributes(), true);
			GraphConstants.setLineWidth(edge.getAttributes(), edgeCounts.get(e));
			edges.put(e, edge);
			graph.getGraphLayoutCache().insertEdge(edge, vertices.get(n1).getChildAt(0), vertices.get(n2).getChildAt(0));
		}
			

	}
	public void layoutGraph()
	{
		if(facade == null)
			facade = new JGraphFacade(graph);
		Rectangle2D b = GraphConstants.getBounds(graph.getModel().getAttributes(vertices.get(3)));
//		facade.run(layout, false);
		layout.run(facade);
		Map nested = facade.createNestedMap(true,true);
		graph.getGraphLayoutCache().edit(nested);
		
//		System.out.println(facade.getBounds(vertices.get(3)));
		b = GraphConstants.getBounds(graph.getModel().getAttributes(vertices.get(3)));
//		System.out.println(b);
		
		graph.refresh();
	}
	public GraphPanel()
	{
		clear();
		
		
	}
	 public class VisualNode
	    {
	    	public int n = 0;
	    	public String o = null;
	    	public int v;
	    	public VisualNode(int v)
	    	{
	    		this.v = v;
	    	}
	    	public String toString()
	    	{
	    		return v + " ("+n+")" +(o == null ? "" : ": " + o);
	    	}
	    }
	public class Edge{
		int src, dest;
		public Edge(int s, int d) {
			src = s;
			dest = d;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "[" + src + ", " + dest + "]";
		}
		@Override
		public int hashCode() {
			return new Integer(src).hashCode() ^ new Integer(dest).hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			Edge o = (Edge) obj;
			return (o.src == src && o.dest == dest)|| (o.src == dest && o.dest == src );
		}
	}
}
