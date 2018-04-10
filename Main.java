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
	
	final static int FPS = 30; // frames per second
	final static int WIDTH = 1400;
	final static int HEIGHT = 800;
	
	public static int waves = 0;
	public boolean hit;
	public int passCount = 0;
	int wait = 0;
	int blink_wait = 0;
	static boolean world_coords = false;
	public static double view_x=0, view_y=0;
	
	// Merging scroll
	final static int BWIDTH = 1400;
	final static int SCROLL = 700;  // Set edge limit for scrolling
	public static int vleft = 0;	// Pixel coord of left edge of viewable
									// area (used for scrolling)
	
	HeroSprite hero;
	Grid grid_1;
	Image background, bk_forest, water;
	Rock rocks[] = new Rock[5];
	
	public int			  ammo_ct = 0;			// Ammo count used in final score calculation.
	public int            fuel_ct = 0;			// Each level beaten, user picks up fuel can, adds 33 to this constant.
												// At end of game, user has 100% fuel and is able to get home.
	public static boolean debug_mode = false;
	
	public int enter = 0;
	
	public static boolean game_over = false;
	
	// Timing variables
	int base = (int) System.currentTimeMillis();
	int currTime = 0;
	
	int wait2 = 0;
	
	// Main menu flags
	public static boolean main_menu = true;
	
	// Asteroid stage flags
	public static int asteroid_stage = 0;				// 1 upon game start (first stage)
	public static boolean player_blink = false;			// True after collision w/ asteroid. Player goes into invincibility
														// for a short time.
	public static boolean beat_dodge = false;			// Crash landing alert flag

	// Planet stage flags
	public static boolean transition_planet = false;	// True after player dodges all asteroids in wave 3.
														// Used to stop rendering/initializing new asteroids.
	public static int     planet_stage = 0;				// 1 is the first stage. After the 3rd stage, player wins.
	public static boolean left = false;
	public static boolean transition_lv2 = false;
	public static boolean transition_lv3 = false;
	public static boolean lv2 = false;
	public static boolean lv3 = false;
	
	static Font font = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 32);
	static Font fontSmall = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 22);
	static Font fontSmaller = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 12);
	
	Asteroid[] asteroids = new Asteroid[50];
	Bullet bullet, bullet2, bullet3, bullet4, bullet5, bullet6, bullet7, bullet8;
	Sprite chrctr[] = new Sprite[12];
	Alien aliens1[] = new Alien[7];
	Alien aliens2[] = new Alien[7];
	Player p =  new Player();
	Score score;
	Lives lives;
	
	// Set up initial variables
	void initialize()
	{
		
		chrctr[0] = bullet = new Bullet();
		
		for (int i = 0; i < asteroids.length; i++)
			asteroids[i] = new Asteroid();
		
		p = new Player();
		score = new Score();
		lives = new Lives();
		
		background = new Image("bkgnd.jpg");
		bk_forest = new Image("sprites/forest.jpeg");
		water = new Image("sprites/WaterWorld.png");
		Image r1 = new Image("rock_glow.gif");
		Image r2 = new Image("rock_glow2.gif");
		Image r3 = new Image("rock_glow3.gif");
		Rock.setImages(r1,r2,r3);
		
		grid_1 = new Grid();
		
	    if(planet_stage == 1 && !lv2 && !lv3) {
	    	System.out.println("SETTING LVL 1 " + System.currentTimeMillis());
	    	Levels.setLevel1(grid_1, rocks);
	    	hero = new HeroSprite(grid_1,100,499, bullet);
			// These values were obtained by printing location of the hero (loxc, locy).
			// Thus, I know where to exactly place the aliens instead of guess/test/repeat.
			// Aliens for level 1:
		    aliens1[0] = new Alien(grid_1, 600, 499, bullet2);
		    aliens1[1] = new Alien(grid_1, 1125, 370, bullet3);
		    aliens1[2] = new Alien(grid_1, 1650, 499, bullet4);
		    aliens1[3] = new Alien(grid_1, 3650, 340, bullet5);
		    aliens1[4] = new Alien(grid_1, 5229, 339, bullet6);
		    aliens1[5] = new Alien(grid_1, 6403, 339, bullet7);
		    aliens1[6] = new Alien(grid_1, 7414, 499, bullet8);
	    }
	    else if(lv2) {
	    	System.out.println("SETTING LVL 2 " + System.currentTimeMillis());
			Levels.setLevel2(grid_1);
			planet_stage = 2;
			hero = new HeroSprite(grid_1,100,499, bullet);			    // Hero for level 2.
		    aliens2[0] = new Alien(grid_1, 600, 499, bullet2);
		    aliens2[1] = new Alien(grid_1, 1066, 339, bullet3);
		    aliens2[2] = new Alien(grid_1, 1650, 499, bullet4);
		    aliens2[3] = new Alien(grid_1, 2464, 339, bullet5);
		    aliens2[4] = new Alien(grid_1, 3494, 499, bullet6);
		    aliens2[5] = new Alien(grid_1, 4607, 339, bullet7);
		    aliens2[6] = new Alien(grid_1, 5871, 2191, bullet8);
		    aliens2[7] = new Alien(grid_1, 7414, 499, bullet8);
		}
	    
	    /*
	    else if(lv3) {
	    	System.out.println("SETTING LVL 3 " + System.currentTimeMillis());
	    	planet_stage = 3;
			Levels.setLevel3(grid_1);
			hero = new HeroSprite(grid_1,100,499, bullet);		// Hero for level 3.
		    // Aliens for level 2. Previous bullet can be reused because level is new.
		    aliens2[0] = new Alien(grid_1, 600, 499, bullet2);
		    aliens2[1] = new Alien(grid_1, 1125, 370, bullet3);
		    aliens2[2] = new Alien(grid_1, 1650, 499, bullet4);
		    aliens2[3] = new Alien(grid_1, 3650, 340, bullet5);
		    aliens2[4] = new Alien(grid_1, 5229, 339, bullet6);
		    aliens2[5] = new Alien(grid_1, 6403, 339, bullet7);
		    aliens2[6] = new Alien(grid_1, 7414, 499, bullet8);
		}*/
		
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
		
		if(planet_stage >= 1) {
			
			chrctr[0].updateSprite();
			
			if(planet_stage == 1) {
				for(int i = 0; i < aliens1.length; i++) {
					aliens1[i].update();
				}
			}
			
			if(planet_stage == 2) {
				for(int i = 0; i < aliens2.length; i++) {
					aliens2[i].update();
				}
			}
			
			hero.update();
			for (int i = 0; i < rocks.length; i++)
				rocks[i].update();

			checkScrolling();
		}
		
		p.move();
		
		for (int k = 0; k < asteroids.length; k++) {
			// Check if player is hit by asteroid k
			if (collided(k) && asteroids[k].hit == false && player_blink == false && asteroids[k].fullPass == false) {		// Check collision (w/ invincibility frame)
				player_blink = true;
				lives.lives -= 1;
				asteroids[k].hit = true;
			}
			
			// Asteroid k leaves the screen on the left side
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
			
			if(waves == 3)
				beat_dodge = true;
			
			passCount = 0;
		}
				
		if(planet_stage < 1 && !main_menu) {
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
						if(planet_stage >= 1) {	hero.jmp = 1; break; }
						else {	p.setUpKey(true); break; }	
					case S:
					case DOWN:
						if(planet_stage >= 1) {	hero.dir = 3; break; }
						else if (asteroid_stage == 1){	p.setDownKey(true); break; }
					case SHIFT: hero.spr = 1;
						break;
					case SPACE:
					case K:
						if (!bullet.isActive() && hero.dir == 1) {
							hero.fireBulletLeft();
							//boom.play();
						}else if (!bullet.isActive() && hero.dir == 2) {
							hero.fireBullet();
							//boom.play();
						}else if (!bullet.isActive() && hero.dir == 3) {
							//boom.stop();
						}else if(!bullet.isActive() && left && hero.spr == 1) {
							hero.fireBulletLeft();
							//boom.play();
						}else if(!bullet.isActive() && hero.spr == 1){
							hero.fireBullet();
							//boom.play();
						}else if(!bullet.isActive() && left) {
							hero.fireBulletLeft();
							//boom.play();
						}else if(!bullet.isActive()){
							hero.fireBullet();
							//boom.play();
						}
						break;
					case X:
						debug_mode = true;
						break;
					case C:
						if(main_menu) {
							
						}
						debug_mode = false;
						break;
					case ENTER:
						if(enter < 2 && main_menu){
							base = (int) System.currentTimeMillis();
							asteroid_stage = 1;
							//planet_stage = 1;
							main_menu = false;
							enter++;
						}
						else break;
					default:
						break;
					}
				});
		scene.setOnKeyReleased(
				e -> {
					KeyCode key = e.getCode();
					if (planet_stage >= 1 && key == KeyCode.D|| key == KeyCode.RIGHT) {
							hero.dir = 0;
							left = false;
					}
					if(planet_stage >= 1 && key == KeyCode.A || key == KeyCode.LEFT) {
							hero.dir = 0;
							left = true;
					}
					if(key == KeyCode.S || key == KeyCode.DOWN) {
						if(planet_stage >= 1) {
							if(left) {
								hero.dir = 0;
								left = true;
							}else {
								hero.dir = 0;
								left = false;
							}
						}
						else if(asteroid_stage == 1)
							p.setDownKey(false);
					}
					if(key == KeyCode.W || key == KeyCode.UP) {
						if(planet_stage >= 1) {
							if(left) {
								hero.jmp = 0;
								left = true;
							}else {
								hero.jmp = 0;
								left = false;
							}
						}
						else if(asteroid_stage == 1)
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
		if ((hero.locx() + hero.width()) > (vleft+WIDTH-SCROLL)){
			vleft = hero.locx()+hero.width()-WIDTH+SCROLL;
			if (vleft > (grid_1.width()-WIDTH))
				vleft = grid_1.width()-WIDTH;
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

	    // Draw the world.
		void render(GraphicsContext gc) {
			
			currTime = Timer.getTimeSec(base);
			
			if(main_menu) {
					// Black background
					gc.setFill(Color.BLACK);
					gc.fillRect(0, 0, WIDTH, HEIGHT);

					gc.setFont(font);
					gc.setFill(Color.WHITE);
					gc.fillText("SPACEPERSON", WIDTH/3, 50);
					
					gc.setFont(fontSmall);
					gc.setFill(Color.WHITE);
					gc.fillText("LEADERBOARD", WIDTH/2.7, 120);
					
					gc.setFont(fontSmall);
					gc.setFill(Color.WHITE);
					gc.fillText("SCORE\tNAME", WIDTH/2.5, 160);
					gc.fillText("1ST ", WIDTH/3.2, 200);
					gc.fillText("2ND ", WIDTH/3.2, 250);
					gc.fillText("3RD ", WIDTH/3.2, 300);
					gc.fillText("4TH ", WIDTH/3.2, 350);
					gc.fillText("5TH ", WIDTH/3.2, 400);
					
					gc.fillText("PRESS 'ENTER' TO PLAY", WIDTH/3.4, 480);
					gc.fillText("'I' FOR INSTRUCTIONS", WIDTH/3.4, 520);
					gc.fillText("'C' FOR CREDITS", WIDTH/3.4, 560);
				
			}
			
			else if(game_over) {
				// Black background
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, WIDTH, HEIGHT);
				
				gc.setFont(fontSmall);
				gc.setFill(Color.WHITE);
				gc.fillText("GAME OVER", WIDTH/2.5, 160);
			}
			
			else if(asteroid_stage == 1 && (planet_stage < 1)) {
					
					//currTime = Timer.getTimeSec(base);
					
					// Black background
					gc.setFill(Color.BLACK);
					gc.fillRect(0, 0, WIDTH, HEIGHT);
					
					// Draw asteroids
					for (Asteroid b: asteroids)
						b.render(gc);
						
					if(waves == 0) {
						gc.setFill(Color.WHITE);
						gc.setFont(font);
						gc.fillText("WAVE 1", WIDTH/3, 50);
					}
					
					else if(waves == 1) {
						gc.setFill(Color.WHITE);
						gc.setFont(font);
						gc.fillText("WAVE 2", WIDTH/3, 50);
						
						if(currTime == 31)
							waves = 2;
					}
					
					else if(waves == 2) {
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
							else {
								planet_stage = 1;
								initialize();
							}
					
					}	
					
					// Text goes last to overlay previous items.
					if(currTime <= 5) {
						gc.setFill(Color.BLUE);
						gc.setFont(font);
						gc.fillText("ASTEROIDS INCOMING", WIDTH/4, HEIGHT/2);
						
						gc.setFill(Color.AQUA);
						gc.setFont(fontSmall);
						gc.fillText("press 'X' to enable debug mode.", WIDTH/4, HEIGHT/2 + 60);
						gc.fillText("press 'C' to disable debug mode.", WIDTH/4, HEIGHT/2 + 90);
					}
					
					if(fuel_ct == 0) {		// If fuel = 0 blink red
						if(currTime % 2 == 0) {
							gc.setFill(Color.RED);
							gc.setFont(fontSmaller);
							gc.fillText("FUEL = " + fuel_ct, 150, HEIGHT-25);
						}
					}
					else if(fuel_ct > 0) {	// If fuel > 0,  render solid green
						gc.setFill(Color.rgb(66, 244, 69));
						gc.setFont(fontSmaller);
						gc.fillText("FUEL = " + fuel_ct, 150, HEIGHT-25);
					}
					
					p.render(gc);
			}
			
			else if(planet_stage == 1) {
				
				int cut = (vleft/2) % BWIDTH;
				gc.drawImage(background, -cut, 0);
				gc.drawImage(background, BWIDTH-cut, 0);
				
				for (int i = 0; i < rocks.length; i++)
					rocks[i].render(gc);
				
				grid_1.render(gc);
				hero.render(gc);
				chrctr[0].render(gc);
				
				
				for(int i = 0; i < 7; i++) {
					aliens1[i].render(gc);
				}
			}
			
			else if(planet_stage == 2) {
				
				int cut = (vleft/2) % BWIDTH;
				gc.drawImage(bk_forest, -cut, 0);
				gc.drawImage(bk_forest, BWIDTH-cut, 0);
				
				for (int i = 0; i < rocks.length; i++)
					rocks[i].render(gc);
				
				grid_1.render(gc);
				hero.render(gc);
				chrctr[0].render(gc);	
				
				for(int i = 0; i < 7; i++) {
					aliens2[i].render(gc);
				}
					
			}
				
			// For now, always render score and current time.
			gc.setFill(Color.WHITE);
			gc.setFont(fontSmall);
			gc.fillText(Integer.toString(currTime) + " sec", 850, 50);
			score.render(gc);
			lives.render(gc);
		}
		
    // Cycle color of a given string.
	public void colorText(GraphicsContext gc, String s, double x, double y){
		int i;
		for(i = 0; i < 10000;) {
			gc.setFill(Color.AQUA);
			gc.fillText(s, x, y);
			gc.setFill(Color.YELLOW);
			gc.fillText(s, x, y);
			gc.setFill(Color.RED);
			gc.fillText(s, x, y);
			i++;
		}
	}
		

	/* Begin boiler-plate code...
	 * [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle("SPACE PERSON");

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
	 //... End boiler-plate code
}