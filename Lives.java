import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Lives {

   int lives = 3;
   static Image ship_lives = new Image("ship.png");
   
   static Image planet_lives = new Image("guy_lives.gif");

   public void render(GraphicsContext gc){

	   if(Main.planet_stage != 1) {
		   if(lives == 3) {
			   gc.drawImage(ship_lives, 275, 25, 30, 30);
			   gc.drawImage(ship_lives, 250, 25, 30, 30);
			   gc.drawImage(ship_lives, 225, 25, 30, 30);
		   }
		   
		   if(lives == 2) {
			   gc.drawImage(ship_lives, 250, 25, 30, 30);
			   gc.drawImage(ship_lives, 225, 25, 30, 30);
		   }
		   
		   if(lives == 1)
			   gc.drawImage(ship_lives, 225, 25, 30, 30);
	   }
		   
       else if(Main.planet_stage == 1) {
    	   if(lives == 3) {
			   gc.drawImage(planet_lives, 275, 25, 30, 30);
			   gc.drawImage(planet_lives, 250, 25, 30, 30);
			   gc.drawImage(planet_lives, 225, 25, 30, 30);
		   }
		   
		   if(lives == 2) {
			   gc.drawImage(planet_lives, 250, 25, 30, 30);
			   gc.drawImage(planet_lives, 225, 25, 30, 30);
		   }
		   
		   if(lives == 1)
			   gc.drawImage(planet_lives, 225, 25, 30, 30);
       }
   }

}
