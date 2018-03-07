import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Lives {

   int lives = 3;
   static Image ship_lives = new Image("ship.png");

   public void render(GraphicsContext gc){
	   gc.setFont(UFO.fontSmall);
	   gc.setFill(Color.WHITE);
	   
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

}
