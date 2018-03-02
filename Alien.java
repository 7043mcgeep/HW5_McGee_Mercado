import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Alien {

	public boolean hit = false;
	public static boolean render = false;
	static Random rng = new Random();
	
	int x, y;   
	int w, h;   
	int vx, vy;
	
	Random r = new Random();
	int Low = 100;
	int High = 501;
	int Result = r.nextInt(High-Low) + Low;
	public Alien()
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
		
		if (x+w < 0) {
			x = UFO.WIDTH + Result;
		}
		
	}
	
	public boolean overlaps(Alien b)
	{
		if ((this.x > b.x + b.w) ||
		    (this.x + this.w < b.x) ||
		    (this.y > b.y + b.h) ||
		    (this.y + this.h < b.y))    
		return true;
		else 
			return false;
	}
	
	int curr = 0, sec = 0;
	int base = (int) System.currentTimeMillis();
	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.SADDLEBROWN);
		gc.fillRect(x,  y,  w,  h);
	}
	
}