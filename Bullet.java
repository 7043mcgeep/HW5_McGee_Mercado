import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Bullet extends Sprite {

  public final double RADIUS = 4;
  double bx;
  double by;
  Grid grid;

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
       	gc.setFill(Color.GOLD);
        gc.fillOval(x-Main.vleft, y+10, 6, 6);
    }
  }
  
}