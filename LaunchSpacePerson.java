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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
	
/**
 Authors: Patrick McGee
 		  Rene Mercado
 SPACE PERSON
 */

public class LaunchSpacePerson extends Application {
	final String appName = "Space Person";
	final int FPS = 30; // frames per second
	static final int WIDTH = 1400;
	static final int HEIGHT = 650;
	public static int waves = 0;
	public boolean w_1, w_2, w_3;
	public boolean hit;
	public int passCount = 0;
	int wait = 0;
	
	// Merging scroll
	final static int BWIDTH = 2000;
	final static int SCROLL = 400;  // Set edge limit for scrolling
	public static int vleft = 0;	// Pixel coord of left edge of viewable
									// area (used for scrolling)
	
	HeroSprite hero;
	Grid grid;
	Image background;
	Rock rocks[] = new Rock[5];
	
	public static boolean asteroid_stage = true;
	public static boolean beat_dodge;
	public static boolean transition_planet = false;
	public static boolean planet_stage = false;
	
	static Font font = Font.loadFont(LaunchSpacePerson.class.getResource("PressStart2P.ttf").toExternalForm(), 32);
	static Font fontSmall = Font.loadFont(LaunchSpacePerson.class.getResource("PressStart2P.ttf").toExternalForm(), 22);
	static Font fontSmaller = Font.loadFont(LaunchSpacePerson.class.getResource("PressStart2P.ttf").toExternalForm(), 12);
	
	Asteroid[] asteroids = new Asteroid[50];
	Player p =  new Player();
	Score score;
	Lives lives;
	
	
	public void setLevel1()
	{
		// Put in a ground level
		for (int i = 0; i < Grid.MWIDTH; i++)
			grid.setBlock(i, Grid.MHEIGHT-1);

		// Place blocks
		grid.setBlock(6,13);
		grid.setBlock(7,13);
		grid.setBlock(8,13); grid.setBlock(8,12); grid.setBlock(8,11); 
		grid.setBlock(9,13); grid.setBlock(9,12); grid.setBlock(9,11);
		grid.setBlock(10,13);
		grid.setBlock(14,10);
		grid.setBlock(22,13);
		grid.setBlock(24,13);
		grid.setBlock(25,11);
		grid.setBlock(26, 12);
		grid.setBlock(27, 12);
		grid.setBlock(28, 12);
		grid.setBlock(29, 12);
		grid.setBlock(31, 10);
		grid.setBlock(32, 10);
		rocks[0] = new Rock(0,250,100,0);
		rocks[1] = new Rock(400,200,100,20);
		rocks[2] = new Rock(100,300,100,40);
		rocks[3] = new Rock(1000,200,120,30);
		rocks[4] = new Rock(1200,250,120,0);
	}
	
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
		
		background = new Image("mars.jpg");
		Image guyImage = new Image("Kn1AFh22.gif");
		Image blockImage = new Image("ground.png");
		Image r1 = new Image("rock_glow.gif");
		Image r2 = new Image("rock_glow2.gif");
		Image r3 = new Image("rock_glow3.gif");
		Rock.setImages(r1,r2,r3);

		grid = new Grid(blockImage);
		hero = new HeroSprite(grid,100,499,guyImage);
		setLevel1();
	}

	/**
	 *  Update variables for one time step
	 */
	int u_base = (int) System.currentTimeMillis();
	void update() {
		
		if(planet_stage) {
			hero.update();
			for (int i = 0; i < rocks.length; i++)
				rocks[i].update();

			checkScrolling();
		}
		
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
			if(waves < 4)
				waves++;

			passCount = 0;
		}
		
		if(waves == 3)
			beat_dodge = true;
				
		for (Asteroid b: asteroids)
			b.move();
		
		if(waves == 0)
			w_1 = true;
		
		if(waves == 1)
			w_2 = true;
		
		if(waves == 2)
			w_3 = true;
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
		if ((hero.locx() + hero.width()) > (vleft+WIDTH-SCROLL))
		{
			vleft = hero.locx()+hero.width()-WIDTH+SCROLL;
			if (vleft > (grid.width()-WIDTH))
				vleft = grid.width()-WIDTH;
		}
	}
	
	// Draw the game world.
	int base = (int) System.currentTimeMillis();
	void render(GraphicsContext gc) {
		
		int currTime = Timer.getTimeSec(base);
		
		if(asteroid_stage && !planet_stage) {
				
				// Black background
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, WIDTH, HEIGHT);
				
				// Draw asteroids
				for (Asteroid b: asteroids)
					b.render(gc);
					
				if(w_1) {
					gc.setFill(Color.WHITE);
					gc.setFont(font);
					gc.fillText("WAVE 1", WIDTH/3, 50);
				}
				
				if(w_2) {
					w_1 = false;
					gc.setFill(Color.WHITE);
					gc.setFont(font);
					gc.fillText("WAVE 2", WIDTH/3, 50);
				}
				
				if(w_3) {
					w_2 = false;
					gc.setFill(Color.WHITE);
					gc.setFont(font);
					gc.fillText("WAVE 3", WIDTH/3, 50);
				}
				
				if(beat_dodge) {
						transition_planet = true;			// Stop rendering new asteroids
						Player.landing_sequence = true;		// Advance player
						gc.setFill(Color.YELLOW);
						gc.setFont(font);
						if(wait < 260) {
							wait++;
							if(currTime % 2 == 0)
								gc.fillText("CRASH LANDING.\nBRACE FOR IMPACT.", WIDTH/3, HEIGHT/2);
						}
						else
							planet_stage = true;
				
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
						gc.setFont(fontSmaller);
						gc.fillText("FUEL LOW", 150, HEIGHT-25);
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
		
		if(planet_stage) {
			// Merging scroll
			int cut = (vleft/2) % BWIDTH;
			gc.drawImage(background, -cut, 0);
			gc.drawImage(background, BWIDTH-cut, 0);
			
			for (int i = 0; i < rocks.length; i++)
				rocks[i].render(gc);
			
			grid.render(gc);
			hero.render(gc);
			gc.setFill(Color.WHITE);
			gc.setFont(fontSmall);
			gc.fillText("\"Weird... no ammo.\nAnd no enemies to fight...\nMaybe in the final version.\"", WIDTH/3, 100);
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
					case W: 
						if(planet_stage) {
							hero.jmp = 1;
						}
						else
							p.setUpKey(true);
								break;
					case S:
						if(planet_stage) {

						}
						else
							p.setDownKey(true);
								break;
					case D:
						if(planet_stage) {
							Image guyImage = new Image("34JowqG.gif");
							hero = new HeroSprite(grid,hero.locx,hero.locy,guyImage);
							hero.dir = 2;
						}
						else
							p.setRightKey(true);
								break;
					case A:
						if(planet_stage) {
							Image guyleftImage = new Image("34JowqGleft.gif");
							hero = new HeroSprite(grid,hero.locx,hero.locy,guyleftImage);
							hero.dir = 1;
						}
						else
							p.setLeftKey(true);
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
						case W:
							p.setUpKey(false);
									break;
						case S:
							p.setDownKey(false);
									break;
						case D:
							p.setRightKey(false);
									break;
						case A:
							p.setLeftKey(false);
									break;
						default:
									break;
					}
				}
			);
	}
}