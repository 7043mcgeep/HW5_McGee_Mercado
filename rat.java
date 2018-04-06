import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class rat{
	public int rx;
	public int ry;
	public Color color;
	public int width = 48;
	public int height = 32;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public Grid g;
	
	 Image image;
	 Image r = new Image("sprites/rat.gif");
	 Image rLeft = new Image("sprites/ratLeft.gif");
	 public boolean active=true, alive=true;

	 int counter = 0;
	 int MaxCount = (int) (Main.FPS * 2);
	 
	public rat(Grid grid, int x, int y){	
		rx = x;
		ry = y;
		g = grid;
		color = Color.RED;
		
		dx = -8;
	}
	
	public void render(GraphicsContext gc){
		if(alive)
			gc.drawImage(image, rx-Main.vleft, ry-20, 48, 32);
	}
	
	public void update(){
		if(alive) {
			if(dx < 0)
				image = rLeft;
			else
				image = r;
			updatePosition();
		}
		counter ++;
	}

	public void setPosition(int c, int d){
		rx = c; ry = d;
	}

	public void updatePosition() {
		if(alive) {
			rx = rx + dx;
		
			if(rx >= 950) 
				dx = -dx;
			if(rx < 0) 
				dx = - dx;
		}
	}

	public BoundingBox collisionBox(){
		return new BoundingBox(rx, ry, width, height);
	}
	
	void suspendTime() {
		if(counter <= counter%MaxCount) {
			active = false;
		}		
	}
	
	void suspend(){
	    alive = false;
	  }
}