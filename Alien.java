import java.util.Random;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Alien{
	public int ax;
	public int ay;
	public Color color;
	public int width = 40;
	public int height = 60;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public Grid g;
	public int hits;
	
	boolean active=true, visible=true, deadB=false;
	
	 Image img;
	 // Level 1 aliens
	 Image r_attack = new Image("sprites/AlienRAttack.gif");
	 Image r_attack_l = new Image("sprites/AlienRAttack_l.gif");
	 Image r_dead = new Image("sprites/AlienRdeath.gif");
	 Image r_dead_l = new Image("sprites/AlienRdeath_l.gif");
	 
	 // Level 2 aliens
	 Image g_attack = new Image("sprites/AlienGAttack.gif");
	 Image g_attack_l = new Image("sprites/AlienGAttack_l.gif");
	 Image g_dead = new Image("sprites/AlienGdeath.gif");
	 Image g_dead_l = new Image("sprites/AlienGdeath_l.gif");
	 
	 BulletAlien bullet;
	 
	 Random r = new Random(); 
	 int timer = r.nextInt(80-50+1)+50;
	 int ct = 0;

	public Alien(Grid grid, int x, int y, BulletAlien b2){
		ax = x;
		ay = y;
		g = grid;
		bullet = b2;
	}
	
	public void render(GraphicsContext gc){
		if(visible && !deadB) {
			gc.setStroke(Color.AQUA);
			gc.drawImage(img, ax-Main.scroll_left, ay-20, 60, 90);
			gc.strokeRect(ax - Main.scroll_left, ay, width, height);
		}
		if(deadB) {
			gc.drawImage(img, ax-Main.scroll_left, ay+10, 90, 90);
		}
	}
	
	public void update(){
		if (active){
			ct ++;
			if (HeroSprite.locx <= ax) {
				img = r_attack_l;
				System.out.println("ct = " + ct + " timer = " + timer);
				if(ct == timer) {
					shoot_l();
					if(HeroSprite.locx > ax-700);
						Main.shoot2.play();
					ct = 0;
				}
			}
			else if (HeroSprite.locx > ax) {
				img = r_attack;
				System.out.println("ct = " + ct + " timer = " + timer);
				if(ct == timer) {
					shoot();
					if(HeroSprite.locx < ax+700)
						Main.shoot2.play();
					ct = 0;
					}
			}
			else 
				img = r_attack;
		}
	}
	
	public BoundingBox collisionBox(){
		return new BoundingBox(ax - Main.scroll_left, ay, width, height);
	}

	public void shoot(){
	     bullet.setPosition(ax+20, ay);
	     bullet.setVelocity(8, 0);
	     bullet.resume();
	   }
	
	public void shoot_l(){
	     bullet.setPosition(ax, ay);
	     bullet.setVelocity(-8, 0);
	     bullet.resume();
	   }
	
	 void suspend(){
		 deadB = true;
		 //Main.impact.play();
		 if (HeroSprite.locx <= ax)
		 	img = r_dead_l;
		 else if (HeroSprite.locx > ax)
			 img = r_dead;
	    active = false; visible = false;
	  }	  
}