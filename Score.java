import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Score {

   int score = 0;
   public void render(GraphicsContext gc){
	   gc.setFill(Color.WHITE);
	   gc.setFont(UFO.fontSmall);
	   gc.fillText(""+score, UFO.WIDTH-300, 50);
   }

}