/* Class:   BouncingBubbles
 * Author:  Ben Partida-Silva
 * Purpose: This Java Program implements Threads to make random size and color
 *          bubbles on the screen. It them makes the bubbles move and bounce
 *          inside the window. Plus a "magic" button....
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class BouncingBubbles extends  JPanel implements Runnable
{	
   private int xCord = 0;  // x position
   private int yCord = 0;  // y position
   private int xChange;   // x change
   private int yChange;   // y change
   private long speed = 10;   // speed of bubbles
   private long adjustSpeed = 80;   // to adjust the speed
   private int red;  // red
   private int green;   // green
   private int blue; // blue
   Color color = Color.red;
   private int bubbleSize; // size of bubble
   private int count;   // counter for the array
   private Thread bubbleThread;  // the tread
   private volatile boolean running = true; // for run loop
   private JPanel status;             // for status bar
   private JTextField statusField;       // text field for status
   private JButton magic;     // button for magic
   ArrayList<BubbleClass> bubbleArray = new ArrayList<BubbleClass>();  // to hold the bubble the
		
   // constructor
   public BouncingBubbles()
   {
		  
      HandlerClass handler = new HandlerClass();
	      
      super.addMouseListener(handler);
      super.addMouseMotionListener(handler);
      super.setBackground(Color.GRAY);
	      
      status = new JPanel();    // makes the frame for status

      statusField = new JTextField( "CLICK ON THE WINDOW TO MAKE A BUBBLE... CLICK BUTTON FOR MAGIC -->" );
      magic = new JButton( "MAGIC!!"); // button with text
      statusField.setForeground(Color.RED);
      statusField.setEditable( false ); // disable editing
      super.setLayout( new BorderLayout());
      status.add(statusField);
      status.add(magic);
      super.add(status, BorderLayout.SOUTH);
      
      magicButtonHandler magicHandler = new magicButtonHandler();
      magic.addActionListener( magicHandler );
	    
			
   }
   // button handler for the magic button
   private class magicButtonHandler implements ActionListener 
   {
      public void actionPerformed( ActionEvent event )
      {

         if(running == true)
         {
         running = false;
         }
         else
         {
            running = true;
            setBackground(getColor()); // magic
            start();
         }
      } // end method actionPerformed
   }
   
   
   // for the magic
   private Color getColor() {
      int rval = (int)Math.floor(Math.random() * 256);
      int gval = (int)Math.floor(Math.random() * 256);
      int bval = (int)Math.floor(Math.random() * 256);
      return new Color(rval, gval, bval);
   }
   
   
   
	// starts 
   public void start()
   {
      bubbleThread = new Thread(this); 
      bubbleThread.start();
		   
   }
	
   // makes the threads
   public void run() 
   {
      
      // System.out.println("Thread is running");  // for debugging
      while(running == true)
      {

         BubbleClass bubble2;
         for(int i = 0; i < bubbleArray.size(); i++)
         {
            bubble2 = bubbleArray.get(i);
            move(bubble2);

         }

          try 
          {
             bubbleThread.sleep(speed);

          } 
          catch (InterruptedException e) 
          {
            System.out.println( 
            "Interrupted while waiting for tasks to finish." );
            
            System.exit(1);

          }
      }
	    
   }
   
   // mouse handler
   public class HandlerClass implements MouseListener, MouseMotionListener
   {
      public void mouseClicked(MouseEvent click) 
      {

         xCord = click.getX() - (bubbleSize / 2);
         yCord = click.getY() - (bubbleSize / 2);
         randomMethod();
         BubbleClass bubble = new BubbleClass( xCord, yCord,  xChange, yChange,
         red, green,  blue, bubbleSize);

         bubbleArray.add(count, bubble);
         count++;

         start();	
      }

      public void mouseDragged(MouseEvent dragged){}

      public void mouseMoved(MouseEvent move){}

      public void mouseEntered(MouseEvent enter){} 

      public void mouseExited(MouseEvent exit){} 

      public void mousePressed(MouseEvent press){}

      public void mouseReleased(MouseEvent release){} 

			
   }
	   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      BubbleClass bubble2;
      for(int i = 0; i < bubbleArray.size(); i++)
      {
         bubble2 = bubbleArray.get(i);
         g.setColor(Color.getHSBColor(bubble2.getR(), bubble2.getG(), 
               bubble2.getB()));
               
         g.fillOval(bubble2.getX(), bubble2.getY(), bubble2.getSize(), 
               bubble2.getSize());
               
         g.setColor(Color.black);
         
         g.drawOval(bubble2.getX(), bubble2.getY(), bubble2.getSize(), 
               bubble2.getSize());	//black outline

      }

   }
   
   // moves the bubbles
   public void move( BubbleClass bubble)
   {

      do
      {
         if((bubble.getX() < 0) || (bubble.getX() > getWidth() - bubble.getSize() ) )
         {
            xChange = bubble.getXv() * -1;
            bubble.setXv(xChange);
         }
         if((bubble.getY() < 0) || ( bubble.getY() > getHeight() - bubble.getSize() - status.getHeight() ))
         {
            yChange = bubble.getYv() * -1;
            bubble.setYv(yChange);
         }
         xCord = bubble.getX();
         xCord += bubble.getXv();
         bubble.setX(xCord);
        
         yCord = bubble.getY();
         yCord += bubble.getYv();
         bubble.setY(yCord);
         
         speed = adjustSpeed;    // to slow down the speed bug
      }
      while(xChange == 0 || yChange == 0);
      
      repaint();
      
   }
   
   // pseudo random numbers factory
   public void randomMethod()
   {
      Random rand = new Random();

      xChange = -3 + rand.nextInt(3);
      yChange = -3 + rand.nextInt(3);
      if(xChange == 0 || yChange == 0)
      {
         xChange = 1;
         yChange = 1;

      }
      else
      {
         bubbleSize = 15 + rand.nextInt(100);
         red = rand.nextInt(255);
         green = rand.nextInt(255);
         blue = rand.nextInt(255);

      }

   }
	   
		
   // good old main
   public static void main(String[] args) 
   {

      BouncingBubbles bubblePanel = new BouncingBubbles( );

      JFrame frame = new JFrame();
      frame.setTitle("BouncingBubbles");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(700, 700);
      frame.setVisible(true);

      frame.add(bubblePanel); // adds the panel to the frame

   }
   
   // for the bubble objects
   class BubbleClass
   {
      private int x;
      private int y;
      private int xV;
      private int yV;
      private int red;
      private int green;
      private int blue;
      private int maxSize;


      public BubbleClass(int xCor, int yCor, int xVel, int yVel,int r,int g, int b,
            int size )
      {
         x = xCor;
         y = yCor;
         xV = xVel;
         yV = yVel;
         red = r;
         green = g;
         blue = b;
         maxSize = size;


      }

      public void setX(int xCor)
      {
         x = xCor;
      }
      public void setY(int yCor)
      {
         y = yCor;
      }
      public void setXv(int xVel)
      {
         xV = xVel;
      }
      public void setYv(int yVel)
      {
         yV = yVel;
      }
      public void setR(int r)
      {
         red = r;
      }
      public void setG(int g)
      {
         green = g;
      }
      public void setB(int b)
      {
         blue = b;
      }
      public void setSize(int size)
      {
         maxSize = size;
      }


      public int getX()
      {
         return x;
      }
      public int getY()
      {
         return y;
      }
      public int getXv()
      {
         return xV;
      }
      public int getYv()
      {
         return yV;
      }
      public int getR()
      {
         return red;
      }
      public int getG()
      {
         return green;
      }
      public int getB()
      {
         return blue;
      }
      public int getSize()
      {
         return maxSize;
      }


   }

}


