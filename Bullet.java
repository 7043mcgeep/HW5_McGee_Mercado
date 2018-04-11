import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class Bullet extends Sprite {

  public final double RADIUS = 4;
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
      if (x > Main.WIDTH+Main.vleft)
    	  suspend();
      else if (x < Main.vleft)
    	  suspend();
    }
  }  

  void render(GraphicsContext gc){
    if (visible){
        gc.drawImage(laser,x-Main.vleft, y+37, 32, 32);
    }
  }
  
}