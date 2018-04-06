import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Score {

   int score = 0;
   public void render(GraphicsContext gc){
	   gc.setFill(Color.WHITE);
	   gc.setFont(Main.fontSmall);
	   gc.fillText(""+score, Main.VWIDTH-300, 50);
   }

}