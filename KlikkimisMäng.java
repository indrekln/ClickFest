import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class KlikkimisMäng extends Application {
	
	public void start(Stage peaLava) {
		
		Group juur = new Group();
		Scene stseen1 = new Scene(juur, 500,500);
		peaLava.setTitle("Selektiivne klikkimine");

		//võibolla on vaja paigutamisel
		//BorderPane piiriPaan1 = new BorderPane();
    	//piiriPaan1.setMinWidth(500);
    	//piiriPaan1.setMinHeight(500);

		/*Button nupp1 = new Button(" ");
		nupp1.setLayoutX(50);
    	nupp1.setLayoutY(50);
    	nupp1.setStyle("-fx-base: black;");
    	nupp1.setTextFill(Color.WHITE);
        nupp1.resize(10,10);*/
        
        //Image image = new Image(getClass().getResourceAsStream("konn.png"));
        int väärtus1 = 0;
		Circle ring1 = new Circle(100, 100, 10, Color.RED);
		Text text1 = new Text (String.valueOf(väärtus1));
		StackPane stack1 = new StackPane();
		stack1.setLayoutX(100);
		stack1.setLayoutY(100);
		stack1.getChildren().addAll(ring1, text1);
		
		int väärtus2 = 0;
		Circle ring2 = new Circle(100, 100, 10, Color.ORANGE);
		Text text2 = new Text (String.valueOf(väärtus2));
		StackPane stack2 = new StackPane();
		stack2.setLayoutX(200);
		stack2.setLayoutY(100);
		stack2.getChildren().addAll(ring2, text2);
		
		int väärtus3 = 0;
		Circle ring3 = new Circle(100, 100, 10, Color.GREEN);
		Text text3 = new Text (String.valueOf(väärtus3));
		StackPane stack3 = new StackPane();
		stack3.setLayoutX(300);
		stack3.setLayoutY(100);
		stack3.getChildren().addAll(ring3, text3);
		
        //juur.getChildren().addAll(nupp1);
        juur.getChildren().addAll(stack1, stack2, stack3);
  
        
        /* nupp1.setOnMouseEntered(new EventHandler<MouseEvent>() {
	      	  public void handle(MouseEvent me) {
	      		  double x = stseen1.getWidth();
	      		  double y = stseen1.getHeight();
	      		  //System.out.println(x);
	      		  //System.out.println(y);
	      		  nupp1.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  nupp1.setLayoutY((int)Math.round(Math.random()*(y-50)));
	      		  
	              nupp1.resize((Math.round(Math.random()*50)), Math.round(Math.random()*50));
	      	  }
	    	}); */
        
        stack1.setOnMouseClicked(new EventHandler<MouseEvent>() {
	      	  public void handle(MouseEvent me) {
	      		  double x = stseen1.getWidth();
	      		  double y = stseen1.getHeight();
	      		  stack1.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack1.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring1.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus = 34-(int)ring1.getRadius();
	              text1.setText(String.valueOf(väärtus));
	              // samuti vastava arvu punktide andmine nt klikkides
	      	  }
	    	});
        
        stack2.setOnMouseClicked(new EventHandler<MouseEvent>() {
	      	  public void handle(MouseEvent me) {
	      		  double x = stseen1.getWidth();
	      		  double y = stseen1.getHeight();
	      		  stack2.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack2.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring2.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus = 34-(int)ring2.getRadius();
	              text2.setText(String.valueOf(väärtus));
	              // samuti vastava arvu punktide andmine nt klikkides
	      	  }
	    	});
        
        stack3.setOnMouseClicked(new EventHandler<MouseEvent>() {
	      	  public void handle(MouseEvent me) {
	      		  double x = stseen1.getWidth();
	      		  double y = stseen1.getHeight();
	      		  stack3.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack3.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring3.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus = 34-(int)ring3.getRadius();
	              text3.setText(String.valueOf(väärtus));
	              // samuti vastava arvu punktide andmine nt klikkides
	      	  }
	    	});	

		peaLava.setScene(stseen1);
		peaLava.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}