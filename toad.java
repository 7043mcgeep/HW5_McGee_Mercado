import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class toad extends Sprite{
	public static int tx;
	public static int ty;
	public Color color;
	public int width = 40;
	public int height = 60;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public Grid g;
	
	 Image image;
	 Image t = new Image("sprites/toad-pixilart.png");

	public toad(Grid grid, int x, int y){	tx = x;
		ty = y;
		g = grid;
		color = Color.RED;
	}
	
	public void render(GraphicsContext gc){
		gc.drawImage(image, tx-Main.vleft, ty-20, 60, 90);
	}
	
	public void update(){
		image = t;
	}

	public void setPosition(int c, int d){
		tx = c; ty = d;
	}


	public BoundingBox collisionBox(){
		return new BoundingBox(tx-50, ty-50, width+100, height+100);
	}
}