import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Bullet extends Sprite {

  public final double RADIUS = 4;
  double bx;
  double by;
  Grid grid;
  static int count = 0;

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
       	gc.setFill(Color.GREEN);
        gc.fillOval(x-Main.vleft, y+25, 10, 10);
    }
  }
  
}