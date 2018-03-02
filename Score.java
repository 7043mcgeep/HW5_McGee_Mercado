import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score {

   int score = 10; 

   Font font;

   public Score(){
	   font = Font.font("SansSerif", FontWeight.BOLD, 24);
   }

   public void render(GraphicsContext gc){
	   gc.setFill(Color.WHITE);
	   gc.setFont(font);
	   if (score>0)
		   gc.fillText(""+score, 700, 50);
	   else
		   gc.fillText("You LOST! and was promptly digested by aliens...", 100, 50);   
   }

}