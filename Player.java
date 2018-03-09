import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player {
   int x, y; 

   Asteroid asteroid;
   boolean upKey = false, downKey = false, rightKey = false, leftKey = false;

   static boolean landing_sequence = false;
   static boolean render_transition = false;
   static boolean moved = false;

   final static int w = 70;
   final static int h = 70;
   final static int SPEED = 4;
   static Image ship = new Image("ship.gif");

	public BoundingBox bounds() {
		return new BoundingBox(x-5, y-5, w-20, h-20);
	}
	
	public BoundingBox bounds2() {
		return new BoundingBox(x+10, y+10, w*.9, h*.9);
	}
   
   public Player(){
      x = 50;
      y = h/2 ;
   }

   public void move(){
	   if (upKey && y > 0 && !landing_sequence)
	       y -= SPEED;
	   if (downKey && y < LaunchSpacePerson.HEIGHT-w && !landing_sequence)
	       y += SPEED;
	   if(landing_sequence)
		   x += SPEED*1.5;
	   if(render_transition) {
		   x = 0;
		   y = 0;
		   x += SPEED*2;
		   y += SPEED*2;
	   }
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
   
   public void render(GraphicsContext gc){
	   if(x+w < LaunchSpacePerson.WIDTH) {
		   gc.drawImage(ship, x, y, w, h);
		   gc.setStroke(Color.WHITE);
		   gc.strokeRect(x+25, y+4, w-60, h-22);
		   gc.strokeRect(x+10, y+10, w*.9, h*.9);
	   }
	   else
		   LaunchSpacePerson.transition_planet = true;    // Begin transition. Do not render player off-screen.
   }
}