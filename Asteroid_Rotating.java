import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Asteroid_Rotating {

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
	
	public Asteroid_Rotating()
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
	
	private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
	
	private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
    }
	
	public void render(GraphicsContext gc)
	{
		if(!LaunchSpacePerson.transition_planet) {

			drawRotatedImage(gc, asteroid2,  40,   x,   y);

			// Drawing the rotated image at the required drawing locations
			//gc.drawImage(asteroid2, x, y, w, h);
		}
	}
	
}
