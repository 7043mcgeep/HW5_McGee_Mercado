import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class Bullet extends Sprite {

  double bx;
  double by;
  Grid grid;
  static int count = 0;
  Image laser = new Image("sprites/laser_beam.gif");

  void updateSprite(){
	// Check if sprite is active
    if (active){
    	bounds();
      updatePosition();
      // If bullet exceeds bounds, suspend.
      if (x > Main.WIDTH+Main.scroll_left)
    	  suspend();
      else if (x < Main.scroll_left)
    	  suspend();
    }
  }  

  public BoundingBox collisionBox(){
	  return new BoundingBox(x-Main.scroll_left, y, 32, 32);
  }
	
  void render(GraphicsContext gc){
    if (visible){
    	System.out.println("SHOULD BE DRAWING ME IMAGE");
    	gc.strokeRect(x-Main.scroll_left, y, 32, 32);
        gc.drawImage(laser,x-Main.scroll_left, y, 32, 32);
    }
  }
  
}