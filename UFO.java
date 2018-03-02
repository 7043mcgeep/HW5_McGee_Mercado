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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
	
/**
 Rene Mercado
 Dodge the alien
 SURVIVE or else...
 */

public class UFO extends Application {
	final String appName = "Space Person";
	final int FPS = 30; // frames per second
	static final int WIDTH = 1400;
	static final int HEIGHT = 650;
	public boolean hit;
	public boolean beat_level;
	
	Font font = Font.font("Droid Sans", FontWeight.BOLD, 32);
	Font fontSmall = Font.font("Droid Sans", FontWeight.BOLD, 22);

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
	int u_base = (int) System.currentTimeMillis();
	void update() {
		int currTime = Timer.getTimeSec(u_base);	
		p.move();
		
		for (int k = 0; k < aliens.length; k++) {
			if (p.collision(aliens[k]) && aliens[k].hit == false) {		// Check collision
				score.score++;
				aliens[k].hit = true;
			}
		}

		System.out.println(currTime);
		// When the same asteroid comes back again, it will still have the false attribute.
		// Reset it here.
		if(currTime == 20 || currTime == 30) {
			for (int k = 0; k < aliens.length; k++) {
				aliens[k].hit = false;
			}
			
			if(currTime == 30)
				beat_level = true;
		}
				
		for (Alien b: aliens)
			b.move();
			
		// Run through pairs of aliens checking for collisions
		// (i > j to avoid duplicate pairs)
		//for (int i = 1; i < aliens.length; i++)
			//for (int j = 0; j < i; j++)

					//aliens[i].x = -1000;
			
	}

	/**
	 *  Draw the game world
	 *  
	 *  IDEAS:
	 *  Before game begins, have a ~ 6second period of time where
	 *  the spaceship is in the middle flying normally with fire
	 *  in engines,
	 *  then "fuel low" comes up and an animation of smoke comes out of
	 *  engines instead of fire. During this sequence, controls are disabled.
	 *  Then "ASTEROIDS INCOMING". User dodges asteroids for like maybe 30 seconds.
	 *  60 seems too long.
	 *  "FUEL LOW" alert stays on screen. Each alert render, there should be a beeping noise.
	 *  But at the end of the 30seconds the asteroids explode because the user beat the level.
	 *  Then controls are disabled again, the spaceship moves toward the left, and off the screen.
	 *  Then transition screen comes up "YOU'VE CRASH LANDED ON PLANET ZORG" and you fight a boss.
	 */
	int base = (int) System.currentTimeMillis();
	void render(GraphicsContext gc) {
		
		int currTime = Timer.getTimeSec(base);	
		
		// Black background
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		
		// Draw asteroids
		for (Alien b: aliens)
			b.render(gc);
		
		if(beat_level) {
			
			// Explode all asteroids... then "brace for impact"... then crash land on planet.
			// Or maybe just let them pass...
			// How? Add a flag that only resets them to the right of the screen if beat_level == false.
			
			if(currTime >= 32 && currTime <= 34) {
				gc.setFill(Color.YELLOW);
				gc.setFont(font);
				gc.fillText("CRASH LANDING.\nBRACE FOR IMPACT.", WIDTH/3, HEIGHT/2);
			}
				
			//Alien.render = false;
		}
		
		// Text goes last to overlay previous items.
		if(currTime >= 2 && currTime <= 5) {
			gc.setFill(Color.BLUE);
			gc.setFont(font);
			gc.fillText("ASTEROIDS INCOMING", WIDTH/3, HEIGHT/2);
		}
		
		gc.setFill(Color.RED);
		if(currTime % 2 == 0) {
			gc.setFont(fontSmall);
			gc.fillText("FUEL LOW", WIDTH/3+100, 60);
		}
		
		gc.setFill(Color.WHITE);
		gc.setFont(fontSmall);
		gc.fillText(Integer.toString(currTime) + " sec", UFO.WIDTH-400, 50);
		
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