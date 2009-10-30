package maze.g3;


import java.awt.*;
import javax.swing.*;
 
public class Polygons extends JPanel {
    Polygon[] polygons = new Polygon[1];
    int[][] xy;
 
    public Polygons() {
    	
    	 JFrame f = new JFrame();
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         f.add(this);
         f.pack();
         f.setLocation(100,100);
         f.setVisible(true);
         
        Dimension d = getPreferredSize();
        int y1 = d.height/2;
        int x2 = d.width/2;
        int R = d.width/3;
        xy = getPolygonArrays(x2, y1, R, 10);
        polygons[0] = new Polygon(xy[0], xy[1], 10);
    }
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.blue);
        for(int j = 0; j < polygons.length; j++) {
            g2.draw(polygons[j]);
        }
        
        for(int j=0; j< 5; j++) {
        int x = xy[0][j];
		int y = xy[1][j];
		System.out.println("x,y" + x + " " + y);
		g2.drawString("("+x+","+y+")", x, y);
		//g2.drawLine(x, y, x+200, y+200);
        }
    }
 
    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }
 
    private int[][] getPolygonArrays(int cx, int cy, int R, int sides) {
        int[] x = new int[sides];
        int[] y = new int[sides];
        double thetaInc = 2*Math.PI/sides;
        double theta = (sides % 2 == 0) ? thetaInc : -Math.PI/2;
        for(int j = 0; j < sides; j++) {
            x[j] = (int)(cx + R*Math.cos(theta));
            y[j] = (int)(cy + R*Math.sin(theta));
            theta += thetaInc;
        }
        return new int[][]{ x, y };
    }
 
    public static void main(String[] args) {
     new Polygons();  
    }
}

