import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class BFly
{
	static Image images[] = new Image[2];
	int counter;
	int locx, locy;
	int state;
	static final int DELAY = 3;

	BFly(int x, int y)
	{
		locx = x; locy = y;
		counter = DELAY;
		state = 0;
	}

	static void setImages(Image b1, Image b2)
	{
		images[0] = b1;
		images[1] = b2;
	}

	public void render(GraphicsContext gr)
	{
		gr.drawImage(images[state],locx-Scroll.vleft, locy);
	}

	public void update()
	{
		if (--counter > 0)
			return;
		state = 1 - state;
		counter = DELAY;
	}
}