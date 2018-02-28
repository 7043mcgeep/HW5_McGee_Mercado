import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {
   int x, y; 

   boolean upKey = false, downKey = false, rightKey = false, leftKey = false;

   final static int WIDTH = 25;
   final static int HEIGHT = 25;
   final static int SPEED = 4;
   

   
   public Player(){
      x = 50;
      y = HEIGHT/2 ;
   }

   public void move(){
	   if (upKey)
	       y -= SPEED;
	   if (downKey)
	       y += SPEED;
	  
   }

   public void render(GraphicsContext gc){
	   gc.setFill(Color.WHITE);
	   gc.fillRect(x, y, WIDTH, HEIGHT);
   }

   public void setUpKey(Boolean val){
	   upKey = val;
   }

   public void setDownKey(Boolean val){
	   downKey = val;
   }
   
   public void setRightKey(Boolean val){
	   rightKey = val;
   }

   public void setLeftKey(Boolean val){
	   leftKey = val;
   }
   public boolean collision(Alien b)
	{
		if ((this.x > b.x + b.w) ||
		    (this.x + 25 < b.x) ||
		    (this.y > b.y + b.h) ||
		    (this.y + 25 < b.y))    
		return false;
		else 
			return true;
	}
}