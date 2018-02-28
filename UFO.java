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
 Rene Mercado
 Dodge the alien
 SURVIVE or else...
 */

public class UFO extends Application {
	final String appName = "FloatingBoxes";
	final int FPS = 30; // frames per second
	static final int WIDTH = 750*2;
	static final int HEIGHT = 500*2;

	Alien[] aliens = new Alien[50];
	Player p =  new Player();
	Score score;
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
		for (int i = 0; i < aliens.length; i++)
			aliens[i] = new Alien();
			p = new Player();
			score = new Score();
	}

	/**
	 *  Update variables for one time step
	 */
	void update() {
		p.move();
		for (int k = 0; k < aliens.length; k++)
			if (p.collision(aliens[k])) {	
				score.score--;
				
			}
		for (Alien b: aliens)
			b.move();
			
		// Run through pairs of aliens checking for collisions
		// (i > j to avoid duplicate pairs)
		for (int i = 1; i < aliens.length; i++)
			for (int j = 0; j < i; j++)
				if (aliens[i].passed)
				{
					aliens[i].x = -1000;
				}
			
	}

	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc) {
		// Clear background
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		// Draw buildings
		for (Alien b: aliens)
			b.render(gc);
			p.render(gc);
			score.render(gc);
	}

	/*
	 * Begin boiler-plate code...
	 * [Animation with initialization]
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

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
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
	void setHandlers(Scene scene)
	{
		scene.setOnKeyPressed(
			e -> {
				KeyCode c = e.getCode();
				switch (c) {
					case W: p.setUpKey(true);
								break;
					case S: p.setDownKey(true);
								break;
					case D: p.setRightKey(true);
								break;
					case A: p.setLeftKey(true);
								break;
					default:
								break;
				}
			}
		);
		
		scene.setOnKeyReleased(
				e -> {
					KeyCode c = e.getCode();
					switch (c) {
						case W: p.setUpKey(false);
									break;
						case S: p.setDownKey(false);
									break;
						case D: p.setRightKey(false);
									break;
						case A: p.setLeftKey(false);
									break;
						default:
									break;
					}
				}
			);
	}
}