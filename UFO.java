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
	public static int waves = 0;
	public boolean hit;
	public int passCount = 0;
	
	public static boolean asteroid_stage = true;
	public static boolean beat_dodge;
	public static boolean transition_planet = false;
	public static boolean planet_stage = true;
	
	static Font font = Font.loadFont(UFO.class.getResource("PressStart2P.ttf").toExternalForm(), 32);
	static Font fontSmall = Font.loadFont(UFO.class.getResource("PressStart2P.ttf").toExternalForm(), 22);

	Asteroid[] asteroids = new Asteroid[50];
	Player p =  new Player();
	Score score;
	Lives lives;
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
		for (int i = 0; i < asteroids.length; i++)
			asteroids[i] = new Asteroid();
		
		p = new Player();
		score = new Score();
		lives = new Lives();
	}

	/**
	 *  Update variables for one time step
	 */
	int u_base = (int) System.currentTimeMillis();
	void update() {
		
		p.move();
		
		for (int k = 0; k < asteroids.length; k++) {
			// Check if player is hit by the asteroid
			if (p.collision(asteroids[k]) && asteroids[k].hit == false) {		// Check collision
				lives.lives -= 1;
				asteroids[k].hit = true;
			}
			
			// An asteroid leaves the screen on the left side
			if(asteroids[k].x + asteroids[k].w/2 < 50 && passCount != 50 && !asteroids[k].fullPass && !Player.landing_sequence){
				score.score += 100;
				asteroids[k].fullPass = true;
				passCount++;
			}
		}

		// When the same asteroid comes back again, it will still have the false attribute.
		// Reset it here.
		if(passCount == 50) {
			for (int k = 0; k < asteroids.length; k++) {
				asteroids[k].hit = false;
				asteroids[k].fullPass = false;
			}
			if(waves < 3)
				waves++;
			else
				waves = 0;

			passCount = 0;
		}
		
		if(waves == 2)
			beat_dodge = true;
				
		for (Asteroid b: asteroids)
			b.move();
	}

	// Draw the game world.
	int base = (int) System.currentTimeMillis();
	void render(GraphicsContext gc) {
		
		int currTime = Timer.getTimeSec(base);
		
		if(asteroid_stage) {
				
				// Black background
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, WIDTH, HEIGHT);
				
				// Draw asteroids
				for (Asteroid b: asteroids)
					b.render(gc);
					
				if(beat_dodge) {
					if(waves == 3) {
						Player.landing_sequence = true;
						gc.setFill(Color.YELLOW);
						gc.setFont(font);
						for(int i = 0; i < 4; i++) {
							if(currTime % 2 == 0)
								gc.fillText("CRASH LANDING.\nBRACE FOR IMPACT.", WIDTH/3, HEIGHT/2);
						}
						beat_dodge = false;
					}
				}	
				
				// Text goes last to overlay previous items.
				if(currTime >= 2 && currTime <= 5) {
					gc.setFill(Color.BLUE);
					gc.setFont(font);
					gc.fillText("ASTEROIDS INCOMING", WIDTH/4, HEIGHT/2);
				}
				
				if(!transition_planet) {
					if(currTime % 2 == 0) {
						gc.setFill(Color.RED);
						gc.setFont(fontSmall);
						gc.fillText("FUEL LOW", WIDTH/3+100, 50);
					}
				}
				
				p.render(gc);
		}
		
		else if(transition_planet) {	
			// Black background
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			// Text goes last to overlay previous items.
			gc.setFill(Color.WHITE);
			gc.setFont(font);
			gc.fillText("YOU HAVE CRASH LANDED ON PLANET ZORG", WIDTH/3, HEIGHT/2);
			
			Player.render_transition = true;
		}	
		
		else if(planet_stage) {
			// Bisque background
			gc.setFill(Color.BISQUE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
		}
			
		// For now, always render score and current time.
		gc.setFill(Color.WHITE);
		gc.setFont(fontSmall);
		gc.fillText(Integer.toString(currTime) + " sec", 850, 50);
		score.render(gc);
		lives.render(gc);
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