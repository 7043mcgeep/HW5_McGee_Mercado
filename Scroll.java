import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A simple game demonstrating scrolling landscape
 *
 * Background scenery with parallax scroll and animated
 * foreground scenery
 *
 * @author mike slattery
 * @version feb 2016
 */
public class Scroll extends Application {
	final String appName = "Scroll";
	final int FPS = 25; // frames per second
	final static int VWIDTH = 800;
	final static int VHEIGHT = 600;
	final static int BWIDTH = 1000;
	final static int SCROLL = 400;  // Set edge limit for scrolling
	public static int vleft = 0;	// Pixel coord of left edge of viewable
									// area (used for scrolling)
	
	HeroSprite hero;
	Grid grid;
	Image background;
	Flower flowers[] = new Flower[5];
	BFly flies[] = new BFly[2];
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
		background = new Image("back.png");
		Image guyImage = new Image("Kn1AFh22.gif");
		Image blockImage = new Image("block.png");
		Image f1 = new Image("flwrm.png");
		Image f2 = new Image("flwrl.png");
		Image f3 = new Image("flwrr.png");
		Image b1 = new Image("bfly1.png");
		Image b2 = new Image("bfly2.png");
		Flower.setImages(f1,f2,f3);
		BFly.setImages(b1,b2);

		grid = new Grid(blockImage);
		hero = new HeroSprite(grid,100,499,guyImage);
		setLevel1();
	}
	
	public void setLevel1()
	{
		// Put in a ground level
		for (int i = 0; i < Grid.MWIDTH; i++)
			grid.setBlock(i, Grid.MHEIGHT-1);

		// Now place specific blocks (depends on current map size)
		grid.setBlock(7,13);
		grid.setBlock(8,13); grid.setBlock(8,12);
		grid.setBlock(9,13); grid.setBlock(9,12); grid.setBlock(9,11);
		grid.setBlock(10,13);
		grid.setBlock(14,10); grid.setBlock(15,10);
		grid.setBlock(22,13);
		grid.setBlock(24,13);
		grid.setBlock(25,11); grid.setBlock(26,11);
		grid.setBlock(23,9); grid.setBlock(24,9);
		grid.setBlock(25,7); grid.setBlock(26,7);
		grid.setBlock(22,5); grid.setBlock(23,5); grid.setBlock(24,5);
		flowers[0] = new Flower(120,538,100,0);
		flowers[1] = new Flower(180,538,100,20);
		flowers[2] = new Flower(240,538,100,40);
		flowers[3] = new Flower(1300,538,120,30);
		flowers[4] = new Flower(1360,538,120,0);
		flies[0] = new BFly(140,240);
		flies[1] = new BFly(766,174);
	}
	
	void setHandlers(Scene scene)
	{
		scene.setOnKeyPressed(
				e -> {
					KeyCode key = e.getCode();
					switch (key)
					{
					case LEFT:
					case A: 
						Image guyleftImage = new Image("34JowqGleft.gif");
						hero = new HeroSprite(grid,hero.locx,hero.locy,guyleftImage);
					hero.dir = 1;
					
						break;
					case RIGHT:
					case D:
						Image guyImage = new Image("34JowqG.gif");
						hero = new HeroSprite(grid,hero.locx,hero.locy,guyImage);
						hero.dir = 2;
					
					
						break;
					// add a Jump key here 
					case SPACE:
					case W: hero.jmp = 1;
						break;
					default:
						break;
					}
				});
		scene.setOnKeyReleased(
				e -> {
					KeyCode key = e.getCode();
					if ((key == KeyCode.A)||(key == KeyCode.LEFT)||
							(key == KeyCode.D)||(key == KeyCode.RIGHT)) {
						Image guyImage = new Image("Kn1AFh22.gif");
						hero = new HeroSprite(grid,hero.locx,hero.locy,guyImage);
						hero.dir = 0;
					}
				});
	}
	

	/**
	 *  Update variables for one time step
	 */
	public void update()
	{
		hero.update();
		for (int i = 0; i < flowers.length; i++)
			flowers[i].update();
		for (int i = 0; i < flies.length; i++)
			flies[i].update();
		checkScrolling();
	}
	
	void checkScrolling()
	{
		// Test if hero is at edge of view window and scroll appropriately
		if (hero.locx() < (vleft+SCROLL))
		{
			vleft = hero.locx()-SCROLL;
			if (vleft < 0)
				vleft = 0;
		}
		if ((hero.locx() + hero.width()) > (vleft+VWIDTH-SCROLL))
		{
			vleft = hero.locx()+hero.width()-VWIDTH+SCROLL;
			if (vleft > (grid.width()-VWIDTH))
				vleft = grid.width()-VWIDTH;
		}
	}

	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc) {
		// We're going to cover the whole background,
		// so there's no reason to fill it first
		//gc.setFill(Color.WHITE);
		//gc.fillRect(0.0, 0.0, VWIDTH, VHEIGHT);
		/*
		 * To create the parallax effect, we reduce
		 * the amount of shift for the background
		 * Also use "% BWIDTH" to handle very wide
		 * level maps (not actually needed for level 1)
		 */
		int cut = (vleft/2) % BWIDTH;
		gc.drawImage(background, -cut, 0);
		gc.drawImage(background, BWIDTH-cut, 0);
		grid.render(gc);
		hero.render(gc);
		for (int i = 0; i < flowers.length; i++)
			flowers[i].render(gc);
		for (int i = 0; i < flies.length; i++)
			flies[i].render(gc);
	}
	
	

	/*
	 * Begin boiler-plate code...
	 * [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(VWIDTH, VHEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Initial setup
		initialize();
		setHandlers(theScene);
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
					// draw frame
					render(gc);
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
	/*
	 * ... End boiler-plate code
	 */
}
