import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {
   int x, y; 

   boolean upKey = false, downKey = false, rightKey = false, leftKey = false;

   static boolean landing_sequence = false;

   final static int w = 25;
   final static int h = 25;
   final static int SPEED = 4;
   

   
   public Player(){
      x = 50;
      y = h/2 ;
   }

   public void move(){
	   if (upKey && y > 0 && !landing_sequence)
	       y -= SPEED;
	   if (downKey && y < UFO.HEIGHT-w && !landing_sequence)
	       y += SPEED;
	   if(landing_sequence)
		   x += SPEED*1.5;
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
		    (this.x + 25 < b.x)  ||
		    (this.y > b.y + b.h) ||
		    (this.y + 25 < b.y))    
			return false;
		else 
			return true;
	}
   
   public void render(GraphicsContext gc){
	   if(x+w < UFO.WIDTH) {
		   gc.setFill(Color.WHITE);
		   gc.fillRect(x, y, w, h);
	   }
	   else
		   UFO.transition_planet = true;    // Begin transition. Do not render player off-screen.
   }
}