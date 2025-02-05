import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Portal extends Sprite{
	public int tx;
	public int ty;
	public Color color;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public Grid g;
	
	 Image img;
	 Image portal = new Image("sprites/portal.gif");
	 boolean active=true, visible=true;

	public Portal(Grid grid, int x, int y){	
		tx = x;
		ty = y;
		g = grid;
	}
	
	public void render(GraphicsContext gc){
		if(visible)
			gc.drawImage(portal, tx-Main.scroll_left, ty, 70, 70);
	}
	
	public void update(){
		if(active)
			img = portal;
	}

	public void setPosition(int c, int d){
		tx = c; ty = d;
	}


	public BoundingBox collisionBox(){
		return new BoundingBox(tx, ty, 70, 70);
	}
	
	void suspend(){
	    active = false; visible = false;
	  }	 
}