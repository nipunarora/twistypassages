package maze.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

public class RoomDisplayWindow  {
	
	JFrame f = new JFrame("Current Room");
	boolean isFirst = true;
	
	public RoomDisplayWindow()
	{
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.pack();
		f.setLocation(100, 100);
		
	}
	
	public void displayRoom(java.util.List<RoomRoomLink> plist, int currentRoom, int oldRoom, Move oldMove)
	{
		if(isFirst)
		{
			f.setVisible(true);
			isFirst = false;
		}
		f.add(new RoomDisplayPanel(plist, currentRoom, oldRoom, oldMove));
		f.pack();
	}
	
	public static class RoomDisplayPanel extends JPanel
	{
		Polygon[] polygons = new Polygon[2];
		int[][] xyCurrentPolygon;
		int[][] xyOldPolygon;
		java.util.List<RoomRoomLink> plist;
		int currentRoom;
		int oldRoom;
		Move oldMove;
		int polygonSide = 10;

		public RoomDisplayPanel(java.util.List<RoomRoomLink> plist, int currentRoom, int oldRoom, Move oldMove) {
			this.plist = plist;
			this.currentRoom = currentRoom;
			this.oldRoom = oldRoom;
			this.oldMove = oldMove;
			

			Dimension d = getPreferredSize();
			int y1 = d.height / 2;
			int x1 = d.width / 4;
			int x2 = d.width * 3/4 ;
			int y2 = d.height / 2;
			
			int R = d.width / 6;
			xyCurrentPolygon = getPolygonArrays(x1, y1, R, polygonSide);
			xyOldPolygon = getPolygonArrays(x2, y2, R, polygonSide);;
			polygons[0] = new Polygon(xyCurrentPolygon[0], xyCurrentPolygon[1], polygonSide);
			polygons[1] = new Polygon(xyOldPolygon[0], xyOldPolygon[1], polygonSide);
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setBackground(Color.white);
			g2.setPaint(Color.blue);
			for (int j = 0; j < polygons.length; j++) {
				g2.draw(polygons[j]);
			}

			Map<Integer, RoomRoomLink> currentRoomLinks = new TreeMap<Integer, RoomRoomLink>();
			for (int i = 0; i < plist.size(); i++) {
				RoomRoomLink r = plist.get(i);
				if (r.room1 == currentRoom) {
					currentRoomLinks.put(r.direction_label, r);
				}
			}
			
			Map<Integer, RoomRoomLink> oldRoomLinks = new TreeMap<Integer, RoomRoomLink>();
			for (int i = 0; i < plist.size(); i++) {
				RoomRoomLink r = plist.get(i);
				if (r.room1 == oldRoom) {
					oldRoomLinks.put(r.direction_label, r);
				}
			}
			
			for (int j = 0; !currentRoomLinks.isEmpty() && j < polygonSide; j++) {
				int x = xyCurrentPolygon[0][j];
				int y = xyCurrentPolygon[1][j];
				RoomRoomLink r = currentRoomLinks.get(j);
				java.awt.Font font = new java.awt.Font("Comic Sans", Font.BOLD, 15);
				g2.setPaint(Color.red);
				g2.setFont(font);
				g2.drawString(r.room1 + "_" + r.direction_label + " ---> "
						+ r.room2, x, y);
			}
			
			for (int j = 0; !oldRoomLinks.isEmpty() && j < polygonSide; j++) {
				int x = xyOldPolygon[0][j];
				int y = xyOldPolygon[1][j];
				RoomRoomLink r = oldRoomLinks.get(j);
				java.awt.Font font = new java.awt.Font("Comic Sans", Font.BOLD, 15);
				g2.setPaint(Color.red);
				g2.setFont(font);
				g2.drawString(r.room1 + "_" + r.direction_label + " ---> "
						+ r.room2, x, y);
			}
			
			g2.setPaint(Color.BLACK);
			java.awt.Font font = new java.awt.Font("Comic Sans", Font.BOLD, 20);
			g2.setFont(font);
			g2.drawString(""+currentRoom, getPreferredSize().width/4, getPreferredSize().height/2);
			g2.drawString(""+oldRoom, getPreferredSize().width*3/4, getPreferredSize().height/2);
			g2.drawString("oldRoom: "+ oldRoom + " oldMove: "+oldMove.direction, 10, 30);
		}

		public Dimension getPreferredSize() {
			return new Dimension(800, 400);
		}

		private int[][] getPolygonArrays(int cx, int cy, int R, int sides) {
			int[] x = new int[sides];
			int[] y = new int[sides];
			double thetaInc = 2 * Math.PI / sides;
			double theta = (sides % 2 == 0) ? thetaInc : -Math.PI / 2;
			for (int j = 0; j < sides; j++) {
				x[j] = (int) (cx + R * Math.cos(theta));
				y[j] = (int) (cy + R * Math.sin(theta));
				theta += thetaInc;
			}
			return new int[][] { x, y };
		}

	}
	
	
	public static void main(String[] args) {
		new RoomDisplayWindow().displayRoom(new ArrayList<RoomRoomLink>(), 0, 0, new Move(0, 0));
	}
}
