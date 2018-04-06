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
   
   static int wait_blink = 0;
   static int blink_ct = 0;

   final static int w = 70;
   final static int h = 70;
   final static int SPEED = 4;
   static Image ship = new Image("ship.gif");
   static Image ship_dead = new Image("ship_dead.gif");

	public BoundingBox bounds() {
		return new BoundingBox(x-5, y-5, w-20, h-20);
	}
	
	public BoundingBox bounds2() {
		return new BoundingBox(x+40, y+10, w-68, h-34);
	}
	
	public BoundingBox bounds3() {
		return new BoundingBox(x+50, y+19, w-70, h-52);
	}
	
	public BoundingBox bounds4() {
		return new BoundingBox(x+55, y+24, w-68, h-62);
	}
   
   public Player(){
      x = 50;
      y = h/2 ;
   }

   public void move(){
	   if (upKey && y > 0 && !landing_sequence)
	       y -= SPEED+2;
	   if (downKey && y < Main.VHEIGHT-w/1.2 && !landing_sequence)
	       y += SPEED+2;
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
	   if(x+w < Main.VWIDTH) {
		   if(!Main.player_blink)
			   gc.drawImage(ship, x, y, w, h);
		   
		   if(Main.player_blink && blink_ct < 45) {
			   
			   // Wait for some time
			   if(wait_blink < 50) {
				   gc.drawImage(ship_dead, x, y, w, h);
				   System.out.println(wait_blink);
			   }
			   wait_blink++;
			   blink_ct++;
		   }
		   else {
			   Main.player_blink = false;
			   wait_blink = 0;
			   blink_ct = 0;
			   
		   }
		   
		   if(Main.debug_mode) {
			   gc.setStroke(Color.WHITE);
			   gc.strokeRect(x+29, y+4, w-65, h-22);
			   gc.setStroke(Color.RED);
			   gc.strokeRect(x+40, y+10, w-68, h-34);
			   gc.strokeRect(x+50, y+19, w-70, h-52);
			   gc.strokeRect(x+55, y+24, w-68, h-62);
		   }
	   }
	   else
		   Main.transition_planet = true;    // Begin transition. Do not render player off-screen.
   }
}