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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

/*Authors: Patrick J. McGee
 		   Rene Mercado
 		   
 		   PJM: Added shooting and enemies 					4/10/18
 		   PJM: Added sounds and touched up levels/score	4/11/18
 		   PJM: Finalization and code clean up				4/11/18
 		   
SPACE PERSON
You are Sam Gunn, Space Person. You are an ex-military warrior and political
figure from the United States, circa 19XX.
You are flying when you run out of fuel. Do what Sam Gunn does best and
shoot your way out of the problem!
------------------------------------------------------------------------------
Dodge the asteroids and then fight intergalactic crime! Kill all aliens
to get the fuel needed to get back home.
*/
public class Main extends Application {
	
	final static int FPS = 30; // frames per second
	final static int WIDTH = 1400;
	final static int HEIGHT = 700;
	
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
	public static int scroll_left = 0;	// Left edge of scrollable view
	
	HeroSprite hero;
	Grid grid_1;
	Image background, bk_forest, water;
	Rock rocks[] = new Rock[5];
	static Portal portal;
	
	public int			  ammo_ct = 0;			// Ammo count used in final score calculation.
	public int            fuel_ct = 0;			// Each level beaten, user picks up fuel can, adds 33 to this constant.
												// At end of game, user has 100% fuel and is able to get home.
	public static boolean debug_mode = false;
	
	public int enter = 0;
	
	public static boolean game_over = false;
	
	public static boolean initialized_lv1 = false;
	
	// Timing variables
	int base = (int) System.currentTimeMillis();
	int currTime = 0;
	int wait2 = 0;
	int wait_beep = 0;
	int asteroid_once = 0;
	
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
	public static boolean open_portal = false;
	public static boolean portal_hit = false;
	public static int	  portal_once = 0;
	public static int	  kill_all_lv1 = 0;
	public static int	  kill_all_lv2 = 0;
	public static int	  stop_ct = 0;
	public static boolean left = false;
	public static boolean transition_lv2 = false;
	public static boolean transition_lv3 = false;
	public static boolean lv2 = false;
	public static boolean lv3 = false;
	public static boolean wait_a_sec = false;
	
	static Font font = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 32);
	static Font fontSmall = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 22);
	static Font fontSmaller = Font.loadFont(Main.class.getResource("PressStart2P.ttf").toExternalForm(), 12);
	
	// Audio clips: a very special thanks to: http://www.downloadfreesound.com/8-bit-sound-effects/
	AudioClip alien_grunt, alien_death, engine, portal_open, shoot1, portal_enter;
	static AudioClip shoot2;
	AudioClip win;
	AudioClip asteroid_hit;
	AudioClip alert_crash;
	
	// Media player for longer songs. Media2 used for layering background music in forest level.
	MediaPlayer media, media2;
	
	// Longer songs
	Media mars_s, lost, forest_s, forest_noise, asteroid_song;
	
	Asteroid[] asteroids = new Asteroid[50];
	Bullet b1;
	BulletAlien b2, b3, b4, b5, b6, b7, b8, b9;
	Sprite pic[] = new Sprite[9];
	Alien aliens1[] = new Alien[7];
	Alien aliens2[] = new Alien[8];
	Player p =  new Player();
	Score score;
	Lives lives;
	
	// Set up initial variables
	void initialize()
	{
		engine = new AudioClip(ClassLoader.getSystemResource("audio/engine.wav").toString());
		engine.setVolume(0.1);
		asteroid_hit = new AudioClip(ClassLoader.getSystemResource("audio/asteroid_hit.wav").toString());
		asteroid_hit.setVolume(0.4);
		alert_crash = new AudioClip(ClassLoader.getSystemResource("audio/alert_crash.wav").toString());
		alert_crash.setVolume(0.3);
		shoot1 = new AudioClip(ClassLoader.getSystemResource("audio/shoot1.wav").toString());
		shoot1.setVolume(0.4);
		shoot2 = new AudioClip(ClassLoader.getSystemResource("audio/shoot2.wav").toString());
		shoot2.setVolume(0.4);
		
		alien_grunt = new AudioClip(ClassLoader.getSystemResource("audio/alien_grunt.wav").toString());
		alien_grunt.setVolume(0.5);
		
		portal_open = new AudioClip(ClassLoader.getSystemResource("audio/portal.wav").toString());
		portal_open.setVolume(0.5);
		
		asteroid_song = new Media(ClassLoader.getSystemResource("audio/asteroid_song.wav").toString());
		
		lost = new Media(ClassLoader.getSystemResource("audio/lost.mp3").toString());
		
		portal_enter = new AudioClip(ClassLoader.getSystemResource("audio/portal_enter.wav").toString());
		portal_enter.setVolume(0.5);
		portal_enter.setCycleCount(5);
		
		for (int i = 0; i < asteroids.length; i++)
			asteroids[i] = new Asteroid();
		
		pic[0]= b1 = new Bullet();
		pic[1]= b2 = new BulletAlien();
		pic[2]= b3 = new BulletAlien();
		pic[3]= b4 = new BulletAlien();
		pic[4]= b5 = new BulletAlien();
		pic[5]= b6 = new BulletAlien();
		pic[6]= b7 = new BulletAlien();
		pic[7]= b8 = new BulletAlien();
		pic[8]= b9 = new BulletAlien();
		
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
	    	
	    	mars_s = new Media(ClassLoader.getSystemResource("audio/mars.mp3").toString());
	    	
	    	// Stop asteroid song
	    	media.stop();
	    	
	    	// On stage 1, play mars song.
	    	media = new MediaPlayer(mars_s);
			media.setCycleCount(5);
			media.play();
			media.setVolume(0.7);
	    	
	    	System.out.println("SETTING LVL 1 " + System.currentTimeMillis());
	    	Levels.setLevel1(grid_1, rocks);
	    	hero = new HeroSprite(grid_1,100,499, b1);
			// These values were obtained by printing location of the hero (loxc, locy).
			// Thus, I know where to exactly place the aliens instead of guess/test/repeat.
			// Aliens for level 1:
		    aliens1[0] = new Alien(grid_1, 600, 499, b2);
		    aliens1[1] = new Alien(grid_1, 1125, 370, b3);
		    aliens1[2] = new Alien(grid_1, 1650, 499, b4);
		    aliens1[3] = new Alien(grid_1, 3650, 340, b5);
		    aliens1[4] = new Alien(grid_1, 5229, 339, b6);
		    aliens1[5] = new Alien(grid_1, 6403, 339, b7);
		    aliens1[6] = new Alien(grid_1, 7414, 499, b8);
		    
		    initialized_lv1 = true;
	    }
	    else if(planet_stage == 2) {
	    	forest_s = new Media(ClassLoader.getSystemResource("audio/forest.mp3").toString());
	    	forest_noise = new Media(ClassLoader.getSystemResource("audio/forest_noise.mp3").toString());
	    	media.stop();
	    	
			media = new MediaPlayer(forest_s);
			media2 = new MediaPlayer(forest_noise);
			
			media.play();
			media.setVolume(0.6);
			
			media2.play();
			media2.setVolume(0.4);
	    	
	    	System.out.println("SETTING LVL 2 " + System.currentTimeMillis());
			Levels.setLevel2(grid_1);
			hero = new HeroSprite(grid_1,100,499, b1);		 // Hero for level 2. Always reuses Bullet 'b1'.
		    aliens2[0] = new Alien(grid_1, 600, 499, b2);
		    aliens2[1] = new Alien(grid_1, 1066, 339, b3);
		    aliens2[2] = new Alien(grid_1, 1650, 499, b4);
		    aliens2[3] = new Alien(grid_1, 2464, 339, b5);
		    aliens2[4] = new Alien(grid_1, 3494, 499, b6);
		    aliens2[5] = new Alien(grid_1, 4607, 339, b7);
		    aliens2[6] = new Alien(grid_1, 5871, 2191, b8);
		    aliens2[7] = new Alien(grid_1, 7414, 499, b9);
		}
		
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

			// Update all bullets
			for(int j = 0; j < pic.length; j++)
				pic[j].updateSprite();
			
			if(planet_stage == 1 && initialized_lv1) {
				for(int i = 0; i < aliens1.length; i++) {
					aliens1[i].update();
					
					for(int j = 0; j < pic.length; j++) {
						
						// Check if alien is hit by hero's bullet
						if(b1.collisionBox().intersects(aliens1[i].collisionBox()) && aliens1[i].active) {
							if(pic[0].active) {
								aliens1[i].hits--;
								pic[0].suspend();
							}
							if(aliens1[i].hits <= 0) {
								alien_grunt.play();
								aliens1[i].suspend();
								kill_all_lv1++;
							}
							if(kill_all_lv1 == aliens1.length) {
								open_portal = true;
								portal_open.play();
							}
						}
						if(hero.collisionBox().intersects(portal.collisionBox()))
							portal_hit = true;
					}
					
				}
			}
			
			if(planet_stage == 2 && !wait_a_sec) {
				for(int i = 0; i < aliens2.length; i++) {
					aliens2[i].update();
					
					for(int j = 0; j < pic.length; j++) {

						// Check if alien lv2 is hit by hero's bullet
						if(b1.collisionBox().intersects(aliens2[i].collisionBox()) && aliens2[i].active) {
							if(pic[0].active) {
								aliens2[i].hits--;
								pic[0].suspend();
							}
							if(aliens2[i].hits <= 0)
								aliens2[i].suspend();
						}	
						
					}

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
				asteroid_hit.play();
				player_blink = true;
				lives.lives -= 1;
				asteroids[k].hit = true;
			}
			
			// Asteroid k leaves the screen on the left side
			if((asteroids[k].x + asteroids[k].w/2 < 50) && (passCount < 50) && (!asteroids[k].fullPass) && !Player.landing_sequence){
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
						if (!b1.isActive() && hero.dir == 1) {
							hero.fireBulletLeft();
							shoot1.play();
						}else if (!b1.isActive() && hero.dir == 2) {
							hero.fireBullet();
							shoot1.play();
						}else if (!b1.isActive() && hero.dir == 3) {
							shoot1.stop();
						}else if(!b1.isActive() && left && hero.spr == 1) {
							hero.fireBulletLeft();
							shoot1.play();
						}else if(!b1.isActive() && hero.spr == 1){
							hero.fireBullet();
							//boom.play();
						}else if(!b1.isActive() && left) {
							hero.fireBulletLeft();
							shoot1.play();
						}else if(!b1.isActive()){
							hero.fireBullet();
							shoot1.play();
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
							beat_dodge = true;
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
		if (hero.locx() < (scroll_left+SCROLL)){
			
			scroll_left = hero.locx()-SCROLL;
			if (scroll_left < 0)
				scroll_left = 0;
		}
		if ((hero.locx() + hero.width()) > (scroll_left+WIDTH-SCROLL)){
			scroll_left = hero.locx()+hero.width()-WIDTH+SCROLL;
			if (scroll_left > (grid_1.width()-WIDTH))
				scroll_left = grid_1.width()-WIDTH;
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
				
				// Do this once to prevent scary Smash Mouth echoing... :
				if(stop_ct < 1) {
					
					// Stop both audio tracks
					media.stop();
					
					if(planet_stage == 2)
						media2.stop();
					
					media = new MediaPlayer(lost);
					media.play();
					media.setVolume(0.7);
					stop_ct++;
				}
				
				// Black background
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, WIDTH, HEIGHT);
				
				gc.setFont(fontSmall);
				gc.setFill(Color.WHITE);
				gc.fillText("GAME OVER", WIDTH/2.5, 160);
			}
			
			else if(asteroid_stage == 1 && (planet_stage < 1)) {
					
					// Engine noise is very short... must play continuously to sound correct
					engine.play();
				    // Make sure that certain audio clips are only being played one time to avoid overlap
				    if(asteroid_once < 1) {
				    	media = new MediaPlayer(asteroid_song);
						media.setCycleCount(25);
						media.play();
						media.setVolume(0.4);
				    }
				    asteroid_once++;
				
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
							if(wait < 160) {
								wait++;
								if(currTime % 2 == 0) {
									if(wait_beep % 10 == 0)
										alert_crash.play();
									gc.fillText("CRASH LANDING.\nBRACE FOR IMPACT.", WIDTH/3, HEIGHT/2);
									wait_beep++;
								}
							}
							else {
								wait = 0;
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
				
				int cut = (scroll_left/2) % BWIDTH;
				gc.drawImage(background, -cut, 0);
				gc.drawImage(background, BWIDTH-cut, 0);
				
				for (int i = 0; i < rocks.length; i++)
					rocks[i].render(gc);
				
				grid_1.render(gc);
				hero.render(gc);
				
				// Render the pictures (bullets)
				for(int i = 0; i < pic.length; i++)
					pic[i].render(gc);
				
				
				for(int i = 0; i < aliens1.length; i++) {
					aliens1[i].render(gc);
				}
				
				if(open_portal) {
					System.out.println("\n\nOPEN PORTALLLLL!!!!!!!!\n\n");
					portal.render(gc);
				}
			}
			
			else if(wait_a_sec) {
				
				if(wait < 80) {
					if(portal_once < 1) {
						portal_enter.play();
					}
					wait++;
				}
				else {
					portal_enter.stop();
					planet_stage = 2;
					initialize();
					wait_a_sec = false;
				}
			}
			
			if(planet_stage == 2) {
				
				int cut = (scroll_left/2) % BWIDTH;
				gc.drawImage(bk_forest, -cut, 0);
				gc.drawImage(bk_forest, BWIDTH-cut, 0);
				
				for (int i = 0; i < rocks.length; i++)
					rocks[i].render(gc);
				
				grid_1.render(gc);
				hero.render(gc);
				
				// Render the pictures (bullets)
				for(int i = 0; i < pic.length; i++)
					pic[i].render(gc);
				
				for(int i = 0; i < aliens2.length; i++) {
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