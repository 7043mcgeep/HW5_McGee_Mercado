import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Asteroid {

	public boolean hit = false;
	public static boolean render = false;
	static Random rng = new Random();
	
	Image asteroid2 = new Image("Asteroid2.png");
	
	int x, y;   
	int w, h;   
	int vx, vy;
	public boolean fullPass;
	
	Random r = new Random();
	int Low = 100;
	int High = 501;
	int random = r.nextInt(High-Low) + Low;
	public Asteroid()
	{
		// Choose random size and movement
		w = 20 + rng.nextInt(51);
		h = 20 + rng.nextInt(51);
		vx = -5;
		
		// Choose random position on the Canvas
		x = UFO.WIDTH+500 + rng.nextInt(1001);
		y = 5 + rng.nextInt(1001);
	}
	
	public void move()
	{
		x += vx;
		
		// Only resets them to the right of the screen if user has not beaten level yet.
		if (x+w < 0 && UFO.waves < 2) {
			x = UFO.WIDTH + random;
			fullPass = true;
		}
		
	}
	
	public boolean overlaps(Asteroid b)
	{
		if ((this.x > b.x + b.w) ||
		    (this.x + this.w < b.x) ||
		    (this.y > b.y + b.h) ||
		    (this.y + this.h < b.y))    
		return true;
		else 
			return false;
	}
	
	public void render(GraphicsContext gc)
	{
		if(!UFO.transition_planet) {
			double rotationRequired = Math.toRadians (45);
			double locationX = asteroid2.getWidth() / 2;
			double locationY = asteroid2.getHeight() / 2;
			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

			// Drawing the rotated image at the required drawing locations
			gc.drawImage(asteroid2, x, y, w, h);
		}
	}
	
}