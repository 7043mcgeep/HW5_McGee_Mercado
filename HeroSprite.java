import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class HeroSprite
{
	public int locx;
	public int locy;
	public int width;
	public int height;
	Image image;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public int jmp = 0;
	public int state;
	public Grid g;
	static final int STAND = 0;
	static final int JUMP = 1;
	static final int GRAVITY = 4;

	public HeroSprite(Grid grid, int x, int y, Image i1)
	{
		// We use locx, locy to store the top-left corner
		// of the sprite
		//
		locx = x;
		locy = y;
		g = grid;
		image = i1;
		width = (int)image.getWidth();
		height = (int)image.getHeight();
		state = STAND;
	}

	public int locx()
	{
		return locx;
	}

	public int width()
	{
		return width;
	}

	public void render(GraphicsContext gc)
	{
		gc.drawImage(image, locx-Scroll.vleft, locy, width, height);
	}

	public void update()
	{
		// Handle movement inputs
		// (check direction flags set by the
		// keyboard listener)
		if (dir == 1)
			dx = -6;
		else if (dir == 2)
			dx = 6;
		else
			dx = 0;
		if ((state == STAND) && (jmp == 1))
		{
			dy = -28;
			state = JUMP;
			jmp = 0;
		}
		// Then do the moving
		updatePosition();
	}

	public void setPosition(int c, int d)
	{
		locx = c; locy = d;
	}

	public void setVelocity(int x, int y)
	{
		dx = x; dy = y;
	}

	public BoundingBox collisionBox()
	{
		return new BoundingBox(locx, locy, width, height);
	}

	public void updatePosition()
	{

		// Note: The g in the following code is the
		// game Grid, not a Graphics context
		//
		// first handle sideways movement
		if (dx > 0)
		{
			dx = g.moveRight(collisionBox(), dx);
		}
		else if (dx < 0)
		{
			dx = -g.moveLeft(collisionBox(), -dx);
		}
		if (dx != 0)
			locx += dx;
		if (state == JUMP)
		{
			// Figure out how far we can move (at our
			// current speed) without running into
			// something
			if (dy > 0)
			{
				dy = g.moveDown(collisionBox(), dy);
			}
			else if (dy < 0)
			{
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
			// Fix that problem of the game crashing
			// after long falls - just impose a terminal
			// velocity (less than block size in the grid)
			if (dy > Grid.CELLSIZE - 1)
				dy = Grid.CELLSIZE - 1;
			//
			// Also, check if we're on the ground (or at the
			// top of the screen)
			if (g.onGround(collisionBox()))
			{
				dy = 0;
				state = STAND;
			}
			else if (g.atTop(collisionBox()))
			{
				dy = 0;
			}
		}
		else
			if (!g.onGround(collisionBox()))
				state = JUMP;
	}
}