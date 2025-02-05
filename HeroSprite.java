import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

class HeroSprite
{
	public static int locx;
	public static int locy;
	
	int x = locx;
	int y = locy;
	static final int STAND = 0;
	static final int JUMP = 1;
	static final double GRAVITY = 2.7;
	public boolean powerup = false;
	public int powerup_t = 0;
	public int longhit_t = 0;
	public int render_hint = 0;
	
	public Bullet bullet;
	public Color color;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public int jmp = 0;
	public int spr = 0;
	public int width = 40;
	public int height = 60;
	public int state;
	public Grid g;
	
	Image img;
	Image stag_r = new Image("sprites/person_stag_r.gif");
	Image stag_l = new Image("sprites/person_stag_l.gif");
	Image walk_r = new Image("sprites/person_right.gif");
	Image walk_l = new Image("sprites/person_left.gif");
	Image jump_r = new Image("sprites/jump_r.gif");
	Image jump_l = new Image("sprites/jump_l.gif");
    Image run_r = new Image("sprites/run_r.gif");
	Image run_l = new Image("sprites/run_l.gif");
	
	Image illusion = new Image("sprites/swirl.gif");
	 
	public HeroSprite(Grid grid, int x, int y, Bullet b){
		// locx, locy = top left corner of sprite
		locx = x;
		locy = y;
		g = grid;
		color = Color.RED;
		state = STAND;
		bullet = b;;
	}
	
	public int locx(){
		return locx;
	}

	public int width(){
		return width;
	}

	public void update(){
		// Handle movement inputs
		// (check direction flags set by the
		// keyboard listener)
		if (dir == 1) {
			dx = -7;
			img = walk_l;
			if(spr == 1) {
				dx = -12;
				img = run_l;
				
			}
		}
		else if (dir == 2) {
			dx = 7;
			img = walk_r;
			if(spr == 1) {
				dx = 12;
				img = run_r;
			}
		}
		else if (dir == 3) {
			//if ()
			powerup = true;
		}
		else {
			dx = 0;
			if(Main.left == false)
				img = stag_r;
			else
				img = stag_l;
		}
		if ((state == STAND) && (jmp == 1)){
			dy = -35;
			state = JUMP;
			jmp = 0;
			//Main.jump.play();
		}
		if(state == JUMP) {
			if(Main.left == true)
				img = jump_l;
			else
				img = jump_r;
			if(dir == 1)
				img = jump_l;
			else if(dir == 2)
				img = jump_r;
		}
		// Then do the moving
		updatePosition();
	}
	
	public void fireBullet(){
		 Main.ammo_ct++;
	     bullet.setPosition(locx+20, locy);
	     bullet.setVelocity(18, 0);
	     bullet.resume();
	   }
	
	public void fireBulletLeft(){
		 Main.ammo_ct++;
	     bullet.setPosition(locx, locy);
	     bullet.setVelocity(-18, 0);
	     bullet.resume();
	   }
	
	public void updatePosition(){

		// Note: The g in the following code is the
		// game Grid, not a Graphics context
		//
		// first handle sideways movement
		
		// If level 1 complete, go to asteroid stage 2.
		
		//System.out.println("locx: " + locx + " locy: " + locy + " planet_stage= " + Main.planet_stage);
		if(Main.planet_stage == 1) {
			if(Main.portal_hit) {
				Main.planet_stage = 2;
				Main.wait_a_sec = true;
				Main.lv2 = true;
			}
		}
//		if(Main.planet_stage == 2) {
//			if(locx >= 7500) {
//				System.out.println("HEREAAAAAH");
//				Main.lv2 = false;
//				Main.lv3 = true;
//			}
//		}

		if (dx > 0){
			dx = g.moveRight(collisionBox(), dx);
		}
		else if (dx < 0){
			dx = -g.moveLeft(collisionBox(), -dx);
		}
		if (dx != 0)
			locx += dx;
		if (state == JUMP){
			
			if (dy > -200) {
				if(locy > 580) { Main.game_over = true; Main.planet_stage = 0;}
				dy = g.moveDown(collisionBox(), dy);
			}
			else if (dy < 0){
				dy = -g.moveUp(collisionBox(), -dy);
			}
			// Adjust position
			if (dy != 0)
				locy += dy;
			
			// Adjust y axis gravity
			dy += GRAVITY;
		
			// If we're on the ground, we're standing.
			if (g.onGround(collisionBox())){
				dy = 0;
				state = STAND;
			}
			else if (g.atTop(collisionBox())){
				dy = 0;
			}
		}
		else
			if (!g.onGround(collisionBox()))
				state = JUMP;
	}
	
	public BoundingBox collisionBox(){
		return new BoundingBox(locx+7, locy-13, width-6, height+13);
	}
	
	public void render(GraphicsContext gc){
		
		if(Main.planet_stage == 1) {
			
		}
		
		if(Main.render_fuel)
			Main.wait_a_sec = false;
		if(Main.wait_a_sec) {
			gc.drawImage(illusion, locx-Main.scroll_left - Main.WIDTH/1.4, locy - Main.HEIGHT,  Main.WIDTH*1.5, Main.HEIGHT*2);
			gc.setFill(Color.GREEN);
			gc.setFont(Main.font);
			gc.fillText("WELCOME TO\n  THE JUNGLE", locx-Main.scroll_left-70, locy - 70);
		}
		gc.drawImage(img, locx-Main.scroll_left, locy - 20, 60, 90);
		if(powerup) powerup_t++;
		if(powerup && powerup_t < 70) {
			gc.setFill(Color.RED);
			gc.setFont(Main.font);
			gc.fillText("POWERUP!", locx-Main.scroll_left - 20, locy - 20);
		}
		else if(powerup_t >= 70) {
			powerup = false;
			powerup_t = 0;
		}
		
		if(Main.render_longhit) longhit_t++;
		if(Main.render_longhit && longhit_t < 70) {
			gc.setFill(Color.TURQUOISE);
			gc.setFont(Main.font);
			gc.fillText("LONG SHOT! +500", locx-Main.scroll_left - 20, locy - 20);
		}
		else if(longhit_t >= 70) {
			Main.render_longhit = false;
			longhit_t = 0;
		}
		if(Main.kill_all_lv2 == 8) {
			
			if(render_hint < 70 && locx < 8000) {
				gc.setFill(Color.WHITE);
				gc.setFont(Main.font);
				gc.fillText("I need to find fuel!\n  Maybe it's this way! -->", locx-Main.scroll_left - 250, locy - 150);
			}
			else if(render_hint >= 70) {
				render_hint = 0;
			}
			
			if(locx >= 8100 && locx < 8600) {
				if(render_hint < 100) {
					gc.setFill(Color.WHITE);
					gc.setFont(Main.font);
					gc.fillText("Surely this planet must\nhave fuel somewhere...", locx-Main.scroll_left - 250, locy - 150);
				}
				else if(render_hint >= 100) {
					render_hint = 0;
				}
			}
			else if(locx >= 8900 && !Main.ending_sequence) {
				if(render_hint < 100) {
					gc.setFill(Color.WHITE);
					gc.setFont(Main.font);
					gc.fillText("Over here!\nI knew they would have it!", locx-Main.scroll_left - 250, locy - 150);
				}
				else if(render_hint >= 100) {
					render_hint = 0;
				}
			}
		}
	}
}