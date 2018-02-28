import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A simple game demonstrating scrolling landscape
 * Basic movement - no images, no score, no bad guys
 *
 * @author mike slattery
 * @version feb 2016
 */
public class Scroll extends Application {
	final String appName = "Scroll";
	final int FPS = 25; // frames per second
	final static int VWIDTH = 800;
	final static int VHEIGHT = 600;
	public static final int SCROLL = 30;  // Set edge limit for scrolling
	public static int vleft = 0;	// Pixel coord of left edge of viewable
									// area (used for scrolling)

	HeroSprite hero;
	Grid grid;
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
		grid = new Grid();
		hero = new HeroSprite(grid,100,499);
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
		grid.setBlock(14,10); grid.setBlock(15,10);
		grid.setBlock(22,13);
		grid.setBlock(24,13);
		grid.setBlock(25,11); grid.setBlock(26,11);
		grid.setBlock(23,9); grid.setBlock(24,9);
		grid.setBlock(25,7); grid.setBlock(26,7);
		grid.setBlock(22,5); grid.setBlock(23,5); grid.setBlock(24,5);
	}
	
	void setHandlers(Scene scene)
	{
		scene.setOnKeyPressed(
				e -> {
					KeyCode key = e.getCode();
					switch (key)
					{
					case J: hero.dir = 1;
						break;
					case K: hero.dir = 2;
						break;
					// add a Jump key here 
					case A: hero.jmp = 1;
						break;
					default:
						break;
					}
				});
		scene.setOnKeyReleased(
				e -> {
					KeyCode key = e.getCode();
					if ((key == KeyCode.J)||(key == KeyCode.K))
						hero.dir = 0;
				});
	}
	

	/**
	 *  Update variables for one time step
	 */
	public void update()
	{
		hero.update();
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
		gc.setFill(Color.WHITE);
		gc.fillRect(0.0, 0.0, VWIDTH, VHEIGHT);
		
		grid.render(gc);
		hero.render(gc);
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