import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class Flower
{
	static Image mid,left,right;
	Image image;
	int delay, counter;
	int locx, locy;
	final static int WAIT = 0;
	final static int LEFT = 1;
	final static int RIGHT = 2;
	final static int MID = 3;
	int state;

	Flower(int x, int y, int d, int p)
	{
		locx = x; locy = y;
		delay = d;
		counter = p;
		state = WAIT;
		image = mid;
	}

	static void setImages(Image f1, Image f2, Image f3)
	{
		mid = f1;
		left = f2;
		right = f3;
	}

	public void render(GraphicsContext gr)
	{
		gr.drawImage(image,locx-2*Scroll.vleft, locy);
	}

	public void update()
	{
		if (--counter > 0)
			return;
		switch(state)
		{
			case WAIT:	state = LEFT;
						image = left;
						counter = 4;
						break;
			case LEFT:	state = MID;
						image = mid;
						counter = 4;
						break;
			case MID:	state = RIGHT;
						image = right;
						counter = 4;
						break;
			case RIGHT:	state = WAIT;
						image = mid;
						counter = delay;
						break;
		}
	}
}