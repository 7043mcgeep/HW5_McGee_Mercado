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
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;

/*Authors: Patrick J. McGee
 		   Rene Mercado
SPACE PERSON
Dodge the asteroids and then fight intergalactic crime!
*/
public class Main extends Application {
	
	final static int FPS = 25; // frames per second
	final static int VWIDTH = 1400;
	final static int VHEIGHT = 800;
	
	public static int waves = 0;
	public boolean w_1, w_2, w_3;
	public boolean hit;
	public int passCount = 0;
	int wait = 0;
	int blink_wait = 0;
	static boolean world_coords = false;
	public static double view_x=0, view_y=0;
	
	// Merging scroll
	final static int BWIDTH = 1400;
	final static int SCROLL = 400;  // Set edge limit for scrolling
	public static int vleft = 0;	// Pixel coord of left edge of viewable
									// area (used for scrolling)
	
	HeroSprite hero;
	Grid grid;
	Image background;
	Rock rocks[] = new Rock[5];
	
	public int            fuel_ct = 100;
	public static boolean debug_mode = false;
	public static boolean asteroid_stage = true;
	public static boolean beat_dodge;
	public static boolean transition_planet = false;
	public static int     planet_stage = 0;
	public static boolean player_blink = false;
	public static boolean        left = false;
	
	static Font font = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 32);
	static Font fontSmall = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 22);
	static Font fontSmaller = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 12);
	
	Asteroid[] asteroids = new Asteroid[50];
	Player p =  new Player();
	Score score;
	Lives lives;
	
	
	public void setLevel1()
	{
		// Put in a ground level
				for (int i = 0; i < Grid.MWIDTH; i++)
					if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
					grid.setBlock(i, Grid.MHEIGHT-1);
				
				// Put in a ground level
				for (int i = 0; i < Grid.MWIDTH; i++)
					if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
					grid.setBlock(i, Grid.MHEIGHT-2);
				
				// Put in a ground level
				for (int i = 0; i < Grid.MWIDTH; i++)
					if (!((i == 59 || i == 60) || (i == 76 || i == 77 || i == 78) || (i == 144 || i == 145)))
					grid.setBlock(i, Grid.MHEIGHT-3);

		// Place blocks
		grid.setBlock(6,9);
		grid.setBlock(10,9);
		grid.setBlock(11,9);
		grid.setBlock(12,9);grid.setBlock(12,6);
		grid.setBlock(13,9);
		grid.setBlock(14,9);
		grid.setBlock(18,13);grid.setBlock(18,12);
		grid.setBlock(19,13);grid.setBlock(19,12);
		grid.setBlock(28,13);grid.setBlock(28,12);grid.setBlock(28,11);
		grid.setBlock(29,13);grid.setBlock(29,12);grid.setBlock(29,11);
		grid.setBlock(36,13);grid.setBlock(36,12);grid.setBlock(36,11);grid.setBlock(36,10);
		grid.setBlock(37,13);grid.setBlock(37,12);grid.setBlock(37,11);grid.setBlock(37,10);
		grid.setBlock(47,13);grid.setBlock(47,12);grid.setBlock(47,11);grid.setBlock(47,10);
		grid.setBlock(48,13);grid.setBlock(48,12);grid.setBlock(48,11);grid.setBlock(48,10);
		grid.setBlock(54,9);
		grid.setBlock(67,10);
		grid.setBlock(68,10);
		grid.setBlock(69,10);
		grid.setBlock(70,6);
		grid.setBlock(71,6);
		grid.setBlock(71,6);
		grid.setBlock(72,6);
		grid.setBlock(73,6);
		grid.setBlock(74,6);
		grid.setBlock(75,6);
		grid.setBlock(76,6);
		grid.setBlock(77,6);
		grid.setBlock(82,6);
		grid.setBlock(83,6);
		grid.setBlock(84,6);
		grid.setBlock(85,6);grid.setBlock(85,10);
		grid.setBlock(91,10);
		grid.setBlock(92,10);
		grid.setBlock(97,10);
		grid.setBlock(100,10);grid.setBlock(100,6);
		grid.setBlock(103,10);
		grid.setBlock(109,10);
		grid.setBlock(112,6);
		grid.setBlock(113,6);
		grid.setBlock(114,6);
		grid.setBlock(119,6);
		grid.setBlock(120,6);
		grid.setBlock(121,6);grid.setBlock(121,10);
		grid.setBlock(122,6);grid.setBlock(122,10);
		grid.setBlock(125,13);
		grid.setBlock(126,13);grid.setBlock(126,12);
		grid.setBlock(127,13);grid.setBlock(127,12);grid.setBlock(127,11);
		grid.setBlock(128,13);grid.setBlock(128,12);grid.setBlock(128,11);grid.setBlock(128,10);
		grid.setBlock(131,13);grid.setBlock(131,12);grid.setBlock(131,11);grid.setBlock(131,10);
		grid.setBlock(132,13);grid.setBlock(132,12);grid.setBlock(132,11);
		grid.setBlock(133,13);grid.setBlock(133,12);
		grid.setBlock(134,13);
		grid.setBlock(139,13);
		grid.setBlock(140,13);grid.setBlock(140,12);
		grid.setBlock(141,13);grid.setBlock(141,12);grid.setBlock(141,11);
		grid.setBlock(142,13);grid.setBlock(142,12);grid.setBlock(142,11);grid.setBlock(142,10);
		grid.setBlock(143,13);grid.setBlock(143,12);grid.setBlock(143,11);grid.setBlock(143,10);
		grid.setBlock(146,13);grid.setBlock(146,12);grid.setBlock(146,11);grid.setBlock(146,10);
		grid.setBlock(147,13);grid.setBlock(147,12);grid.setBlock(147,11);
		grid.setBlock(148,13);grid.setBlock(148,12);
		grid.setBlock(149,13);
		grid.setBlock(154,13);grid.setBlock(154,12);
		grid.setBlock(155,13);grid.setBlock(155,12);
		grid.setBlock(159,10);
		grid.setBlock(160,10);
		grid.setBlock(161,10);
		grid.setBlock(162,10);
		grid.setBlock(170,13);grid.setBlock(170,12);
		grid.setBlock(171,13);grid.setBlock(171,12);
		grid.setBlock(172,13);
		grid.setBlock(173,13);grid.setBlock(173,12);
		grid.setBlock(174,13);grid.setBlock(174,12);grid.setBlock(174,11);
		grid.setBlock(175,13);grid.setBlock(175,12);grid.setBlock(175,11);grid.setBlock(175,10);
		grid.setBlock(176,13);grid.setBlock(176,12);grid.setBlock(176,11);grid.setBlock(176,10);grid.setBlock(176,9);
		grid.setBlock(177,13);grid.setBlock(177,12);grid.setBlock(177,11);grid.setBlock(177,10);grid.setBlock(177,9);grid.setBlock(177,8);
		grid.setBlock(178,13);grid.setBlock(178,12);grid.setBlock(178,11);grid.setBlock(178,10);grid.setBlock(178,9);grid.setBlock(178,8);grid.setBlock(178,7);
		grid.setBlock(179,13);grid.setBlock(179,12);grid.setBlock(179,11);grid.setBlock(179,10);grid.setBlock(179,9);grid.setBlock(179,8);grid.setBlock(179,7);grid.setBlock(179,6);
		grid.setBlock(180,13);grid.setBlock(180,12);grid.setBlock(180,11);grid.setBlock(180,10);grid.setBlock(180,9);grid.setBlock(180,8);grid.setBlock(180,7);grid.setBlock(180,6);
		grid.setBlock(190,13);grid.setBlock(190,12);grid.setBlock(190,11);grid.setBlock(190,10);grid.setBlock(190,9);grid.setBlock(190,8);grid.setBlock(190,7);grid.setBlock(190,6);grid.setBlock(190,5);grid.setBlock(190,4);
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
		
		background = new Image("bkgnd.jpg");
		Image guyImage = new Image("Kn1AFh22.gif");
		Image r1 = new Image("rock_glow.gif");
		Image r2 = new Image("rock_glow2.gif");
		Image r3 = new Image("rock_glow3.gif");
		Rock.setImages(r1,r2,r3);

		grid = new Grid();
		hero = new HeroSprite(grid,100,499);
		setLevel1();
	}

	public boolean collided(int k) {
		if(p.bounds().intersects(asteroids[k].bounds())
				|| p.bounds2().intersects(asteroids[k].bounds())
				|| p.bounds3().intersects(asteroids[k].bounds())
				|| p.bounds4().intersects(asteroids[k].bounds()))
				return true;
		else
			return false;
	}
	
	// Update all game variables
	int u_base = (int) System.currentTimeMillis();
	void update() {
		
		if(planet_stage == 1) {
			hero.update();
			for (int i = 0; i < rocks.length; i++)
				rocks[i].update();

			checkScrolling();
		}
		
		p.move();
		
		for (int k = 0; k < asteroids.length; k++) {
			// Check if player is hit by the asteroid
			if (collided(k) && asteroids[k].hit == false && player_blink == false) {		// Check collision (w/ invincibility frame)
				player_blink = true;
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
			System.out.println(waves);
			for (int k = 0; k < asteroids.length; k++) {
				asteroids[k].hit = false;
				asteroids[k].fullPass = false;
			}
			passCount = 0;
			
			if(waves < 4)
				waves++;
			
			if(waves == 0) w_1 = true;
			
			if(waves == 1) w_2 = true;
			
			if(waves == 2) w_3 = true;
			
			if(waves == 3)
				beat_dodge = true;
		}
				
		if(planet_stage != 1) {
			for (Asteroid b: asteroids)
				b.move();
		}
	}
	
	void setHandlers(Scene scene){
		scene.setOnKeyPressed(
				e -> {
					KeyCode key = e.getCode();
					switch (key){
					case A: 
					case LEFT:
						hero.dir = 1;
						break;
					case D:
					case RIGHT: hero.dir = 2;
						break;
					case UP:
					case W:
						if(planet_stage == 1) {	hero.jmp = 1; break; }
						else {	p.setUpKey(true); break; }
					case S:
					case DOWN:
						if(planet_stage == 1) {	hero.dir = 3; break; }
						else if (asteroid_stage){	p.setDownKey(true); break; }
					case SHIFT: hero.spr = 1;
						break;
					case SPACE:
						break;
					default:
						break;
					}
				});
		scene.setOnKeyReleased(
				e -> {
					KeyCode key = e.getCode();
					if (planet_stage == 1 && key == KeyCode.D|| key == KeyCode.RIGHT) {
							hero.dir = 0;
							left = false;
					}
					if(planet_stage == 1 && key == KeyCode.A || key == KeyCode.LEFT) {
							hero.dir = 0;
							left = true;
					}
					if(key == KeyCode.S || key == KeyCode.DOWN) {
						if(planet_stage == 1) {
							if(left) {
								hero.dir = 0;
								left = true;
							}else {
								hero.dir = 0;
								left = false;
							}
						}
						else if(asteroid_stage)
							p.setDownKey(false);
					}
					if(key == KeyCode.W || key == KeyCode.UP) {
						if(planet_stage == 1) {
							if(left) {
								hero.jmp = 0;
								left = true;
							}else {
								hero.jmp = 0;
								left = false;
							}
						}
						else if(asteroid_stage)
							p.setUpKey(false);
					} 
					if(key == KeyCode.SHIFT)
						hero.spr = 0;
				});
	}
	
	void checkScrolling(){
		// Test if hero is at edge of view window and scroll appropriately
		if (hero.locx() < (vleft+SCROLL)){
			vleft = hero.locx()-SCROLL;
			if (vleft < 0)
				vleft = 0;
		}
		if ((hero.locx() + hero.width()) > (vleft+VWIDTH-SCROLL)){
			vleft = hero.locx()+hero.width()-VWIDTH+SCROLL;
			if (vleft > (grid.width()-VWIDTH))
				vleft = grid.width()-VWIDTH;
		}
	}
	
	static void useWorldCoords(GraphicsContext gc){
		if (!world_coords){
			gc.translate(-view_x, -view_y);
			world_coords = true;
		}
	}

	static void useScreenCoords(GraphicsContext gc){
		if (world_coords){
			gc.setTransform(new Affine());
			world_coords = false;
		}
	}

	// Draw the game world.
		int base = (int) System.currentTimeMillis();
		void render(GraphicsContext gc) {
			
			int currTime = Timer.getTimeSec(base);
			
			if(asteroid_stage && (planet_stage != 1)) {
				
					// Black background
					gc.setFill(Color.BLACK);
					gc.fillRect(0, 0, VWIDTH, VHEIGHT);
					
					// Draw asteroids
					for (Asteroid b: asteroids)
						b.render(gc);
						
					if(w_1) {
						gc.setFill(Color.WHITE);
						gc.setFont(font);
						gc.fillText("WAVE 1", VWIDTH/3, 50);
					}
					
					if(w_2) {
						w_1 = false;
						gc.setFill(Color.WHITE);
						gc.setFont(font);
						gc.fillText("WAVE 2", VWIDTH/3, 50);
					}
					
					if(w_3) {
						w_2 = false;
						gc.setFill(Color.WHITE);
						gc.setFont(font);
						gc.fillText("WAVE 3", VWIDTH/3, 50);
					}
					
					if(beat_dodge) {
							transition_planet = true;			// Stop rendering new asteroids
							Player.landing_sequence = true;		// Advance player
							gc.setFill(Color.YELLOW);
							gc.setFont(font);
							if(wait < 260) {
								wait++;
								if(currTime % 2 == 0)
									gc.fillText("CRASH LANDING.\nBRACE FOR IMPACT.", VWIDTH/3, VHEIGHT/2);
							}
							else
								planet_stage = 1;
					
					}	
					
					// Text goes last to overlay previous items.
					if(currTime >= 2 && currTime <= 5) {
						gc.setFill(Color.BLUE);
						gc.setFont(font);
						gc.fillText("ASTEROIDS INCOMING", VWIDTH/4, VHEIGHT/2);
						
						gc.setFill(Color.AQUA);
						gc.setFont(fontSmall);
						gc.fillText("press 'X' to enable debug mode.", VWIDTH/4, VHEIGHT/2 + 60);
						gc.fillText("press 'C' to disable debug mode.", VWIDTH/4, VHEIGHT/2 + 90);
					}
					
					if(fuel_ct == 0) {		// If fuel = 0 blink red
						if(currTime % 2 == 0) {
							gc.setFill(Color.RED);
							gc.setFont(fontSmaller);
							gc.fillText("FUEL = " + fuel_ct, 150, VHEIGHT-25);
						}
					}
					else if(fuel_ct > 0) {	// If fuel > 0,  render solid green
						gc.setFill(Color.rgb(66, 244, 69));
						gc.setFont(fontSmaller);
						gc.fillText("FUEL = " + fuel_ct, 150, VHEIGHT-25);
					}
					
					p.render(gc);
			}
			
			else if(transition_planet) {	
				// Black background
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, VWIDTH, VHEIGHT);
				
				// Text goes last to overlay previous items.
				gc.setFill(Color.WHITE);
				gc.setFont(font);
				gc.fillText("YOU HAVE CRASH LANDED ON PLANET ZORG", VWIDTH/3, VHEIGHT/2);
				
				Player.render_transition = true;
			}	
			
			if(planet_stage == 1) {
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
				gc.fillText("\"Weird... no ammo.\nAnd no enemies to fight...\nMaybe in the final version.\"", VWIDTH/3, 100);
			}
				
			// For now, always render score and current time.
			gc.setFill(Color.WHITE);
			gc.setFont(fontSmall);
			gc.fillText(Integer.toString(currTime) + " sec", 850, 50);
			score.render(gc);
			lives.render(gc);
		}

	/* Begin boiler-plate code...
	 * [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle("Super Mafio Bros");

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
	 //... End boiler-plate code
}