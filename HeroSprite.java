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
	
	public Color color;
	public int width = 40;
	public int height = 60;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public int jmp = 0;
	public int crch = 0;
	public int spr = 0;
	public int state;
	public Grid g;
	static final int STAND = 0;
	static final int JUMP = 1;
	static final double GRAVITY = 2.7;
	public boolean powerup = false;
	public int powerup_t = 0;
	
	 Image image;
	 Image one = new Image("sprites/HeroOne.gif");
	 Image oneLeft = new Image("sprites/HeroOneLeft.gif");
	 Image walk = new Image("sprites/walk.gif");
	 Image walkLeft = new Image("sprites/walkLeft.gif");
	 Image jump = new Image("sprites/jump.gif");
	 Image jumpLeft = new Image("sprites/jumpLeft.gif");
	 Image run = new Image("sprites/run.gif");
	 Image runLeft = new Image("sprites/runLeft.gif");
	 
	public HeroSprite(Grid grid, int x, int y){
		// We use locx, locy to store the top-left corner
		// of the sprite
		//
		locx = x;
		locy = y;
		g = grid;
		color = Color.RED;
		state = STAND;
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
			image = walkLeft;
			if(spr == 1) {
				dx = -12;
				image = runLeft;
			}
		}
		else if (dir == 2) {
			dx = 7;
			image = walk;
			if(spr == 1) {
				dx = 12;
				image = run;
			}
		}
		else if (dir == 3) {
			//if ()
			powerup = true;
		}
		else {
			dx = 0;
			if(Main.left == false)
				image = one;
			else
				image = oneLeft;
		}
		if ((state == STAND) && (jmp == 1)){
			dy = -35;
			state = JUMP;
			jmp = 0;
			//Main.jump.play();
		}
		if(state == JUMP) {
			if(Main.left == true)
				image = jumpLeft;
			else
				image = jump;
			if(dir == 1)
				image = jumpLeft;				
		}
		// Then do the moving
		updatePosition();
	}

	public BoundingBox collisionBox(){
		return new BoundingBox(locx+10, locy-10, width-10, height+10);
	}
	
/*
	public void fireBullet(){
	     bullet.setPosition(locx+20, locy);
	     bullet.setVelocity(20, 0);
	     bullet.resume();
	   }
	
	public void fireBulletLeft(){
	     bullet.setPosition(locx, locy);
	     bullet.setVelocity(-20, 0);
	     bullet.resume();
	   }
*/
	
	public void updatePosition(){

		// Note: The g in the following code is the
		// game Grid, not a Graphics context
		//
		// first handle sideways movement
		if (dx > 0){
			dx = g.moveRight(collisionBox(), dx);
		}
		else if (dx < 0){
			dx = -g.moveLeft(collisionBox(), -dx);
		}
		if (dx != 0)
			locx += dx;
		if (state == JUMP){
			// Figure out how far we can move (at our
			// current speed) without running into
			// something
			if (dy > 0){
				System.out.println(locy + " " + dy + " " + y);
				if(locy > 600) { Main.game_over = true; Main.planet_stage = 0;}
				dy = g.moveDown(collisionBox(), dy);
			}
			else if (dy < 0){
				dy = -g.moveUp(collisionBox(), -dy);
			}
			// Adjust our position
			if (dy != 0)
				locy += dy;
			//
			// Next we adjust dy to allow for the force
			// (acceleration) of gravity
			//
			dy += GRAVITY;
			//
			// Also, check if we're on the ground (or at the
			// top of the screen)
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
	
	public void render(GraphicsContext gc){
		gc.drawImage(image, locx-Main.vleft, locy - 20, 60, 90);
		gc.setStroke(Color.CYAN);
		gc.strokeRect(locx-Main.vleft, locy-20, 60, 90);
		if(powerup) powerup_t++;
		if(powerup && powerup_t < 70) {
			gc.setFill(Color.RED);
			gc.setFont(Main.font);
			gc.fillText("POWERUP!", locx-Main.vleft - 20, locy - 20);
		}
		else if(powerup_t >= 70) {
			powerup = false;
			powerup_t = 0;
		}
	}
}