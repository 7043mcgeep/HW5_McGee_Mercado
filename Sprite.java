import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;

class Sprite{
  double x,y;
  int dx,dy;
  public Grid g;
  int width, height;

  boolean active=false, visible=false;

  void setPosition(double x2, double y2){
  	x = x2; y = y2;
  }
  
  void setVelocity(int a, int b){
    dx = a; dy = b;
  }

  boolean isActive(){
  	return active;
  }

  // Inactive player upon death.
  void suspend(){
    active = false; visible = false;
  }
  
  void resume(){
    active = true; visible = true;
  }

  void updateSprite() {}
  void render(GraphicsContext gc) {}
  
  void updatePosition(){
  	x += dx;
  	y += dy;
  }
  
  public BoundingBox bounds(){
		return new BoundingBox(x, y, width, height);
	}
}