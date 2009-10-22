/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maze.ui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author sss2171
 */
public class UIController {

    public int number;
    GameConfig gc_local;
    int Goffset, Gradius;
    int circleSize;
    Point centre;
    ArrayList<RoomCircle> roomCircleList;
    BufferedImage bi_local;
    public UIController() {

    number = 5;
    centre = new Point(600, 500);
    Goffset = 100;
    circleSize = 25;
    Gradius = 300;
    bi_local = new BufferedImage(1200, 1000, BufferedImage.TYPE_INT_ARGB);
    
    }

    public void setImage()
    {
        if(gc_local.zoomValue < 5)
        {
            bi_local = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
            centre = new Point(400, 300);
        }
        else
        {
            bi_local = new BufferedImage(1200, 1000, BufferedImage.TYPE_INT_ARGB);
        }
    }
    

    public BufferedImage getImage()
    {
        
        Graphics GraphicsObject = bi_local.getGraphics();
        GraphicsObject.setColor(Color.white);
        GraphicsObject.fillRect(0, 0, 1200, 1000);

        //System.out.println("Paint called");
        //test_paint(GraphicsObject);
        // Let us now use the gameconfig object to paint the canvas.
        // (1)
        //drawOutline(GraphicsObject);

        // (2)
        if(gc_local != null)
        {
            if(gc_local.current_round == 0)
            {
                // Setup circles
                SetRadiusetc();
                SetRoomCircles();
                
            }
            DrawRooms(GraphicsObject);
            DrawBasicPassages(GraphicsObject);
            FillCurrRoom(GraphicsObject);
            //DrawScore(GraphicsObject);
            DrawVisitedRooms(GraphicsObject);
            PutRoomNums(GraphicsObject);
        }

        return bi_local;
    }

    public void SetRadiusetc()
    {
        if(gc_local.zoomValue == 1)
        {
            Gradius = 50;
            //System.out.println("Zoom Called 1" );
        }
        else if(gc_local.zoomValue == 2)
        {
            Gradius = 100;
            //System.out.println("Zoom Called 2");
        }
        else if(gc_local.zoomValue == 3)
        {
            Gradius = 150;
            //System.out.println("Zoom Called 3");
        }
        else if(gc_local.zoomValue == 4)
        {
            Gradius = 200;
            //System.out.println("Zoom Called 4");
        }
        else if(gc_local.zoomValue == 5)
        {
            Gradius = 250;
            //System.out.println("Zoom Called 5");
        }
        else if(gc_local.zoomValue == 6)
        {
            Gradius = 300;
            //System.out.println("Zoom Called 5");
        }
        else if(gc_local.zoomValue == 7)
        {
            Gradius = 350;
            //System.out.println("Zoom Called 5");
        }
        else if(gc_local.zoomValue == 8)
        {
            Gradius = 400;
            //System.out.println("Zoom Called 5");
        }

//        if(gc_local.number_of_rooms < 15)
//        {
//            Gradius = 100;
//        }
//        else if(gc_local.number_of_rooms >= 10 && gc_local.number_of_rooms < 30)
//        {
//            Gradius = 250;
//        }

    }

    public void PutRoomNums(Graphics GraphicsObject)
    {
        GraphicsObject.setColor(Color.BLUE);
        for(int loop=0;loop<roomCircleList.size();loop++)
        {
            Point cpoint = roomCircleList.get(loop).position;
            int room_id = roomCircleList.get(loop).id;
            int object_num = gc_local.RoomObjectQuery(room_id);
            String object = gc_local.objectNumtoName(object_num);

            if(cpoint.y <= centre.y && cpoint.x <= centre.x)
            {
                GraphicsObject.drawString(Integer.toString(room_id)+":" + object,((cpoint.x) - (circleSize/2)), ((cpoint.y)-(circleSize/2) ));
            }
            else if(cpoint.y <= centre.y && cpoint.x > centre.x)
            {
                GraphicsObject.drawString(Integer.toString(room_id)+":" + object,((cpoint.x) + (circleSize/2)), ((cpoint.y)-(circleSize/2) ));
            }
            else if (cpoint.y > centre.y && cpoint.x > centre.x)
            {
                GraphicsObject.drawString(Integer.toString(room_id)+":" + object,((cpoint.x) + (circleSize)), ((cpoint.y)+(circleSize*2) ));
            }else
            {
                GraphicsObject.drawString(Integer.toString(room_id)+":" + object,((cpoint.x) - (circleSize)), ((cpoint.y)+(circleSize*2) ));
            }

           
        }
    }


    public void DrawVisitedRooms(Graphics GraphicsObject)
    {
        for(int loop=0;loop<gc_local.visited_rooms.size();loop++)
        {
            int room = gc_local.visited_rooms.get(loop);
            Point curr_point = giveIdPosition(room);
            GraphicsObject.setColor(Color.BLACK);
            GraphicsObject.fillOval((curr_point.x) + 6, (curr_point.y)+6, circleSize-12, circleSize-12);

        }
        
    }

    public void DrawScore(Graphics GraphicsObject)
    {
        int score = gc_local.current_score;
        String score_string = "Current Score: " + score;
        GraphicsObject.setFont(new Font("Arial bold",Font.PLAIN,16));
        GraphicsObject.setColor(Color.BLACK);
        GraphicsObject.drawString(score_string, 20, 20);
    }

    public void FillCurrRoom(Graphics GraphicsObject)
    {
        int current_room = gc_local.current_room;
        Point curr_point = giveIdPosition(current_room);
        //System.out.println("Current Room = " + current_room);
        GraphicsObject.setColor(Color.MAGENTA);
        GraphicsObject.fillOval((curr_point.x) + 3, (curr_point.y)+3, circleSize-6, circleSize-6);

    }

    public void DrawBasicPassages(Graphics GraphicsObject)
    {
        int count = 0;
        for(int loop=0;loop<gc_local.PassageList.size();loop++)
        {
            RoomRoomLink rrl = gc_local.PassageList.get(loop);
            if(rrl.visited == false)
            {
                Point p1 = giveIdPosition(rrl.room1);
                Point p2 = giveIdPosition(rrl.room2);
                GraphicsObject.setColor(Color.GREEN);
                GraphicsObject.drawLine(p1.x + (circleSize/2),p1.y + (circleSize/2),p2.x + (circleSize/2),p2.y + (circleSize/2));
                GraphicsObject.drawLine((p1.x + (circleSize/2))+1,(p1.y + (circleSize/2))+1,(p2.x + (circleSize/2))+1,(p2.y + (circleSize/2))+1);
                GraphicsObject.drawLine((p1.x + (circleSize/2))-1,(p1.y + (circleSize/2))+1,(p2.x + (circleSize/2))-1,(p2.y + (circleSize/2))+1);

            }
            else
            {
//                Point p1 = giveIdPosition(rrl.room1);
//                Point p2 = giveIdPosition(rrl.room2);
//                GraphicsObject.setColor(Color.BLACK);
//                GraphicsObject.drawLine(p1.x + (circleSize/2),p1.y + (circleSize/2),p2.x + (circleSize/2),p2.y + (circleSize/2));
//                //GraphicsObject.drawLine((p1.x + (circleSize/2))+1,(p1.y + (circleSize/2))+1,(p2.x + (circleSize/2))+1,(p2.y + (circleSize/2))+1);
//                //GraphicsObject.drawLine((p1.x + (circleSize/2))-1,(p1.y + (circleSize/2))+1,(p2.x + (circleSize/2))-1,(p2.y + (circleSize/2))+1);
//                count++;
            }
        }

        for(int loop=0;loop<gc_local.PassageList.size();loop++)
        {
            RoomRoomLink rrl = gc_local.PassageList.get(loop);
            if(rrl.visited == false)
            {
//                Point p1 = giveIdPosition(rrl.room1);
//                Point p2 = giveIdPosition(rrl.room2);
//                GraphicsObject.setColor(Color.GREEN);
//                GraphicsObject.drawLine(p1.x + (circleSize/2),p1.y + (circleSize/2),p2.x + (circleSize/2),p2.y + (circleSize/2));
//                GraphicsObject.drawLine((p1.x + (circleSize/2))+1,(p1.y + (circleSize/2))+1,(p2.x + (circleSize/2))+1,(p2.y + (circleSize/2))+1);
//                GraphicsObject.drawLine((p1.x + (circleSize/2))-1,(p1.y + (circleSize/2))+1,(p2.x + (circleSize/2))-1,(p2.y + (circleSize/2))+1);

            }
            else
            {
                Point p1 = giveIdPosition(rrl.room1);
                Point p2 = giveIdPosition(rrl.room2);
                GraphicsObject.setColor(Color.BLACK);
                GraphicsObject.drawLine(p1.x + (circleSize/2),p1.y + (circleSize/2),p2.x + (circleSize/2),p2.y + (circleSize/2));
                //GraphicsObject.drawLine((p1.x + (circleSize/2))+1,(p1.y + (circleSize/2))+1,(p2.x + (circleSize/2))+1,(p2.y + (circleSize/2))+1);
                //GraphicsObject.drawLine((p1.x + (circleSize/2))-1,(p1.y + (circleSize/2))+1,(p2.x + (circleSize/2))-1,(p2.y + (circleSize/2))+1);
                count++;
            }
        }
        //System.out.println("Pass Viss = " + count);
    }

    public void DrawRooms(Graphics GraphicsObject)
    {
//        Graphics2D g2 = (Graphics2D) GraphicsObject;
//        g2.setStroke(new BasicStroke(3));
        // You need to translate the points from 450, 350
        for(int loop=0;loop<roomCircleList.size();loop++)
        {
            int x = roomCircleList.get(loop).position.x;
            int y = roomCircleList.get(loop).position.y;
            
            GraphicsObject.setColor(Color.red);
            GraphicsObject.drawOval(x, y, circleSize, circleSize);
            //GraphicsObject.drawString("Test", 200, 200);
        }
    }


    public Point giveIdPosition(int id)
    {
        Point returnPoint = null;
        for(int loop=0;loop<roomCircleList.size();loop++)
        {
            RoomCircle rc = roomCircleList.get(loop);
            if(rc.id == id)
            {
                returnPoint = rc.position;
            }
            
        }
        if(returnPoint == null)
        {
            int i = 2;
        }
        return returnPoint;

    }

    public void SetRoomCircles()
    {
        roomCircleList = new ArrayList<RoomCircle>();
        // Let us read the gc_local and dynamically assign co-ordinates to the rooms


        
        // Since the start and treasure rooms dont count, and the 
        // 2 connected to them also are fixed
       
        int radius = Gradius;
        int offset = Goffset;
        double total_rooms = gc_local.number_of_rooms-4;
        // HARDCODE ROOMS
        //double total_rooms = 30;

        int half_count = (int)Math.ceil(total_rooms/2);
        int angle_unit = (180/(half_count+1));

        


        Point ps = new Point((centre.x - radius - offset),centre.y - 20);
        Point near_ps = new Point((centre.x - radius),centre.y );
        Point pt = new Point((centre.x + radius + offset),centre.y + 20);
        Point near_pt = new Point((centre.x + radius),centre.y );

        int room_counter = 1;
        RoomCircle rc = new RoomCircle(ps, room_counter);
        roomCircleList.add(rc);
        room_counter++;
         rc = new RoomCircle(near_ps, room_counter);
         roomCircleList.add(rc);
        room_counter++;


        for(int loop=1;loop<=half_count;loop++)
        {
            double curr_angle = loop * angle_unit;
            if(curr_angle < 90)
            {
                curr_angle = Math.toRadians(curr_angle);
                int rcos = (int)(radius * Math.cos(curr_angle));
                int x1 = centre.x - rcos;
                int x2 = x1;
                int rsin = (int)(radius * Math.sin(curr_angle));
                int y1 = centre.y - rsin;
                int y2 = centre.y + rsin;
                Point p1 = new Point(x1, y1);
                Point p2 = new Point(x2,y2);


                 rc = new RoomCircle(p1, room_counter);
                roomCircleList.add(rc);
                room_counter++;
                 rc = new RoomCircle(p2, room_counter);
                 roomCircleList.add(rc);
                room_counter++;

            }
            else if(curr_angle == 90)
            {
                curr_angle = Math.toRadians(curr_angle);
                int y1 = centre.y - radius;
                int y2 = centre.y + radius;
                int x = centre.x;
                Point p1 = new Point(x,y1);
                Point p2 = new Point(x,y2);

               rc = new RoomCircle(p1, room_counter);
                roomCircleList.add(rc);
                room_counter++;
                 rc = new RoomCircle(p2, room_counter);
                 roomCircleList.add(rc);
                room_counter++;

            }
            else if(curr_angle > 90)
            {
                curr_angle = 180 - curr_angle;
                curr_angle = Math.toRadians(curr_angle);
                
                int rcos = (int)(radius * Math.cos(curr_angle));
                int x1 = centre.x + rcos;
                int x2 = x1;
                int rsin = (int)(radius * Math.sin(curr_angle));
                int y1 = centre.y - rsin;
                int y2 = centre.y + rsin;
                Point p1 = new Point(x1, y1);
                Point p2 = new Point(x2,y2);
                
                rc = new RoomCircle(p1, room_counter);
                roomCircleList.add(rc);
                room_counter++;
                 rc = new RoomCircle(p2, room_counter);
                 roomCircleList.add(rc);
                room_counter++;


            }
        }

        if(total_rooms%2 == 1)
        {
            roomCircleList.remove(roomCircleList.size()-1);
            room_counter--;
        }



       

        rc = new RoomCircle(near_pt, room_counter);
        roomCircleList.add(rc);
        room_counter++;
        rc = new RoomCircle(pt, room_counter);
        roomCircleList.add(rc);
        room_counter++;



        
    }

    public void drawOutline(Graphics GraphicsObject)
    {
        GraphicsObject.draw3DRect(0, 0, 670, 500,true);
    }

    public void test_paint(Graphics GraphicsObject)
    {
        //System.out.println("Paint called");
        //setBackground(Color.red);
        
        if(gc_local != null)
        {
            number = gc_local.current_round;
            GraphicsObject.drawString("No of puzzles" + number + " times", 50, 50);
        }
    }


}

class RoomCircle
{
    Point position;
    int id;

    public RoomCircle(Point p,int i)
    {
        position = p;
        id = i;
    }

}