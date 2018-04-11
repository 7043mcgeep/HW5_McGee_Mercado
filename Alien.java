import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Alien{
	public int vilx;
	public int vily;
	public Color color;
	public int width = 40;
	public int height = 60;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public Grid g;
	public int hits;
	
	  boolean active=true, visible=true, deadB=false;
	
	 Image image;
	 Image v1 = new Image("sprites/AlienB.gif");
	 Image v1Left = new Image("sprites/AlienB.gif");
	 Image dead = new Image("sprites/AlienBdeath.gif");
	 Image deadLeft = new Image("sprites/AlienBdeath.gif");
	 
	 int counter = 0;
	 
	 BulletAlien bullet;

	public Alien(Grid grid, int x, int y, BulletAlien b2){
		vilx = x;
		vily = y;
		g = grid;
		color = Color.RED;
		bullet = b2;
	}
	
	public void render(GraphicsContext gc){
		if(visible && !deadB) {
			gc.drawImage(image, vilx-Main.vleft, vily-20, 60, 90);
		}
		if(deadB)
			gc.drawImage(image, vilx-Main.vleft, vily+10, 90, 90);
	}
	
	public void update(){
		if (active){
			counter ++;
			// Handle movement inputs
			// (check direction flags set by the
			// keyboard listener)
			if (HeroSprite.locx <= vilx) {
				image = v1Left;
					//fireLeft();
					if(HeroSprite.locx > vilx-700)
						//Main.boom.play();
					counter = 0;
				}
			}
			else if (HeroSprite.locx > vilx) {
				image = v1;
					fire();
					if(HeroSprite.locx < vilx+700)
						//Main.boom.play();
					counter = 0;
			}
			else {
				image = v1;
			}
		}
	public BoundingBox collisionBox(){
		return new BoundingBox(vilx+10, vily-20, width-10, height+20);
	}

	public void fire(){
	     bullet.setPosition(vilx+20, vily);
	     bullet.setVelocity(20, 0);
	     bullet.resume();
	   }
	
	/*public void fireLeft(){
		 System.out.println(vilx + " " + vily);
	     bullet.setPosition(vilx, vily);
	     bullet.setVelocity(-20, 0);
	     bullet.resume();
	   }*/
	
	 void suspend(){
		 deadB = true;
		 //Main.impact.play();
		 if (HeroSprite.locx <= vilx)
		 	image = deadLeft;
		 else if (HeroSprite.locx > vilx)
			 image = dead;
	    active = false; visible = false;
	  }	  
}