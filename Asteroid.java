import java.util.Random;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Asteroid {

	public boolean hit = false;
	public static boolean render = false;
	static Random rng = new Random();
	
	Image asteroid1 = new Image("asteroid_3.png");
	
	int x, y;   
	int w, h;   
	int vx, vy;
	public boolean fullPass;
	
	public BoundingBox bounds() {
		return new BoundingBox(x+5, y+5, w-10, h-10);
	}
	
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
		x = LaunchSpacePerson.WIDTH+500 + rng.nextInt(1001);
		y = 5 + rng.nextInt(1001);
	}
	
	public void move()
	{
		x += vx;
		
		// Only resets them to the right of the screen if user has not beaten level yet.
		if (x+w < 0 && LaunchSpacePerson.waves < 2) {
			x = LaunchSpacePerson.WIDTH + random;
			fullPass = true;
		}
		
	}
	
	public void render(GraphicsContext gc)
	{
		if(!LaunchSpacePerson.transition_planet) {

			// Drawing the rotated image at the required drawing locations
			gc.drawImage(asteroid1, x, y, w, h);
			
			if(LaunchSpacePerson.debug_mode) {
				gc.setStroke(Color.AQUA);
				gc.strokeRect(x+2, y+2, w-4, h-4);
			}
		}
	}
	
}