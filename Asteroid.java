import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Asteroid {

	public boolean hit = false;
	public static boolean render = false;
	static Random rng = new Random();
	
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
		if (x+w < 0 && !UFO.beat_dodge) {
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
			gc.setFill(Color.SADDLEBROWN);
			gc.fillRect(x,  y,  w,  h);
		}
	}
	
}