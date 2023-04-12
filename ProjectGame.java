import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;

class PegCanvas extends Canvas
{
   public PegCanvas()
   {
      setWidth(80);
      setHeight(80);
      GraphicsContext gc = getGraphicsContext2D();
      draw(gc);
   }
   
   
   public void draw(GraphicsContext gc) //drawing my individual ball
   {
      gc.setFill(Color.PALEVIOLETRED);
      gc.fillOval(0, 0, 80, 80);
   }
}

public class ProjectGame extends Application
{
   //creating all my global objects
   BorderPane bp = new BorderPane();
   GridPane gp = new GridPane();
   GamePane [][] gamePanes = new GamePane [4][4];
   int num = 0;
   int balls = 15;
   Label label = new Label("Moves: 2 Balls: 15");
   FlowPane fp = new FlowPane();
   
  public void start (Stage stage)
  {
      bp.setPrefSize(600,600);
      gp.setHgap(10);
      gp.setVgap(10);
   
   //placing all of my GamePanes into a 4x4 array on my stage
   for (int i = 0; i < 4; i++)
   {
      for (int j = 0; j < 4; j++)
      {
         gamePanes[i][j] = new GamePane(i,j); 
         gp.add(gamePanes[i][j],i,j);
      }
   }
      fp.setBackground(new Background(new BackgroundFill(Color.HOTPINK,CornerRadii.EMPTY,Insets.EMPTY))); //coloring my label pink
      fp.getChildren().add(label);
      gamePanes[0][2].setVisible(false); //making GamePane[0][2] invisible to start
      gp.setAlignment(Pos.CENTER);
      fp.setAlignment(Pos.CENTER);
      bp.setCenter(gp);
      bp.setTop(fp);
      moves();
   
      Scene scene = new Scene(bp, 600, 600); 
      stage.setScene(scene);
      stage.setTitle("Peg Game");
      stage.show();    
   }  
   
   public void moves() //method to see if a move can be made
   {
      num = 0;
      
      //begin by making all the buttons invisible
      for (int i = 0; i < 4; i++)
      {
         for (int j = 0; j < 4; j++)
         {
            gamePanes[i][j].getTop().setVisible(false);
            gamePanes[i][j].getLeft().setVisible(false);
            gamePanes[i][j].getRight().setVisible(false);
            gamePanes[i][j].getBottom().setVisible(false);
         }
         
      }

      //loop through all available GamePanes 
      for (int i = 0; i < 4; i++)
      {
         for (int j = 0; j < 4; j++)
         { 
         if (gamePanes[i][j].isVisible() == true) //if GamePane is visible
         {  
            if (j + 2 <= 3) //boundaries to avoid out of bounds error 
            {
               if (gamePanes[i][j+1].isVisible() == true && gamePanes[i][j+2].isVisible() == false)
               {
                  gamePanes[i][j].getTop().setVisible(true); //sets appropriate button visible
                  num++;
               }
            }
            
            if (i + 2 <= 3) //boundaries to avoid out of bounds error 
            {
               if (gamePanes[i+1][j].isVisible() == true && gamePanes[i+2][j].isVisible() == false)
               {
                  gamePanes[i][j].getLeft().setVisible(true); //sets appropriate button visible
                  num++;
               }
            }
            
            if (i - 2 >= 0) //boundaries to avoid out of bounds error 
            {
               if (gamePanes[i-1][j].isVisible() == true && gamePanes[i-2][j].isVisible() == false)
               {
                  gamePanes[i][j].getRight().setVisible(true); //sets appropriate button visible
                  num++;
               }
            } 
             
            if (j - 2 >= 0) //boundaries to avoid out of bounds error 
            {
               if (gamePanes[i][j-1].isVisible() == true && gamePanes[i][j-2].isVisible() == false)
               {
                  gamePanes[i][j].getBottom().setVisible(true); //sets appropriate button visible
                  num++;
               }
            }
         
         }
         
         }
         numMoves();
         
      }
      
   }
   
    private void numMoves() //method to change label based on moves and balls &  checks if game was won/lost
    {
       label.setText("Moves: "+num+" Balls: "+balls);
    
       if (num == 0 && balls > 1)
       {
         loser();
       }
       
       if (num == 0 && balls == 1)
       {
         winner();
       }
    }
    
    private void winner() //method to change my label when won
    {
       label.setText("WINNER!!!");
    }
    
    private void loser() //method to change my label when lost
    {
       label.setText("You lose:(");
    }

   int location;
   public void click(int x_in, int y_in, int location_in) //method to make move if button is clicked
   {
      int x = x_in;
      int y = y_in;
      location = location_in;
      
      if (location == 0) //if top button pressed
      {
         gamePanes[x][y].setVisible(false);
         gamePanes[x][y+1].setVisible(false);
         gamePanes[x][y+2].setVisible(true);
      }
      
      if (location == 1) //if bottom button pressed
      {
         gamePanes[x][y].setVisible(false);
         gamePanes[x][y-1].setVisible(false);
         gamePanes[x][y-2].setVisible(true);
      }
      
      if (location == 2) //if right button pressed
      {
         gamePanes[x][y].setVisible(false);
         gamePanes[x-1][y].setVisible(false);
         gamePanes[x-2][y].setVisible(true);
      }
      
      if (location == 3) //if left button pressed
      {
         gamePanes[x][y].setVisible(false);
         gamePanes[x+1][y].setVisible(false);
         gamePanes[x+2][y].setVisible(true);
      }
      moves();
      
   }
      


   public class GamePane extends GridPane //GamePane class creates individual GamePanes
   {
      //creating my local objects
      private int x;
      private int y;
      private Button top;
      private Button left;
      private Button right;
      private Button bottom;
      private PegCanvas pc;
      private boolean circle = true;
      private boolean topButton = true;
      private boolean leftButton = true;
      private boolean rightButton = true;
      private boolean bottomButton = true;
      
      public GamePane(int i, int j)
      {
         x = i;
         y = j;
         //creating and adding top button to GamePane
         top = new Button();
         top.setOnAction(new ButtonListener());
         top.setPrefSize(80,20);
         add(top, 1, 0);
         
         //creating and adding left button to GamePane
         left = new Button();
         left.setOnAction(new ButtonListener());
         left.setPrefSize(20,80);
         add(left, 0, 1);
         
         //creating and adding PegCanvas to GamePane
         pc = new PegCanvas();
         add(pc, 1, 1);
         
         //creating and adding right button to GamePane
         right = new Button();
         right.setOnAction(new ButtonListener());
         right.setPrefSize(20,80);
         add(right, 2, 1);
         
         //creating and adding bottom button to GamePane
         bottom = new Button();
         bottom.setOnAction(new ButtonListener());
         bottom.setPrefSize(80,20);
         add(bottom, 1, 2);
      }
      
     public class ButtonListener implements EventHandler<ActionEvent> //ButtonListener class
     {
      public void handle(ActionEvent e) 
      {
         balls--;
         
         if (e.getSource() == top) //performs action of top button
         {
            click(x,y,0);
         }
         
         if (e.getSource() == bottom) //performs action of bottom button
         {
            click(x,y,1);
         }
         
         if (e.getSource() == right) //performs action of right button
         {
            click(x,y,2);
         }
         
         if (e.getSource() == left) //performs action of left button
         {
            click(x,y,3);
         }
      }
   } 
      public boolean isCircle() //method to determine if PegCanvas is visible
      {
         if (pc.isVisible())
         {
            return true;
         }
         if (!pc.isVisible())
         {
            return false;
         }
         return false;
      }
      
      public PegCanvas getCircle() //method to get PegCanvas
      {
         return pc;
      }
      
      public Button getTop() //method to get top button
      {
         return top;
      }
      
      public Button getLeft() //method to get left button
      {
         return left;
      }      

      public Button getRight() //method to get right button
      {
         return right;
      }

      public Button getBottom() //method to get bottom button
      {
         return bottom;
      }    
      
   }
   
   public static void main(String[] args)
   {
      launch(args);
   } 
}
      
   
   
   
