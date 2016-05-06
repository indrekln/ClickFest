import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/*
 * Juur
 * -Vbox
 * --Nupp
 * --TimerLabel
 * -Ringid
 * --Stackpane1
 * ---Ring1
 * ---Text1
 * --Stackpane2
 * ---Ring2
 * ---Text2
 * --Stackpane3
 * ---Ring3
 * ---Text3
 * -Text4(?)
 */
 
public class KlikkimisMäng extends Application {
	
	private static final Integer STARTTIME = 10;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    private int points = 0;
    
	public void start(Stage peaLava) {
		
		Group juur = new Group();
		Scene stseen1 = new Scene(juur, 500,500);
		peaLava.setTitle("Selektiivne klikkimine");

		//võibolla on vaja paigutamisel
		//BorderPane piiriPaan1 = new BorderPane();
    	//piiriPaan1.setMinWidth(500);
    	//piiriPaan1.setMinHeight(500);

		
		//Ringide loomine:
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
	
		
		//Stopperi loomine:
        timerLabel.setText(timeSeconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");
        
        Text text4 = new Text("");
        text4.setFill(Color.RED);
        text4.setLayoutX(200);
        text4.setLayoutY(200);
        juur.getChildren().add(text4);
 
        // Start-nupu loomine
        //  timerLabel text property ja timeSeconds property väärtuste sidumine
        timerLabel.textProperty().bind(timeSeconds.asString());
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.setStyle("-fx-font-size: 4em;");

        Button nupp1 = new Button();
        nupp1.setText("Start Game");
        
        nupp1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds.set(STARTTIME);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(STARTTIME),
                        new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
            	//text4.setText("Mäng on läbi! Said "+String.valueOf(points)+" punkti!");
                
            }
            
        });
        
        //vbox stopperi paigutamiseks:
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.TOP_RIGHT);
        vb.setPrefWidth(stseen1.getWidth());
        vb.setLayoutY(30);
        
        // objektide paigutamine juure alla:
        vb.getChildren().addAll(nupp1, timerLabel);
	    juur.getChildren().add(vb);
	    Group ringid = new Group();
	    ringid.getChildren().addAll(stack1, stack2, stack3);
        juur.getChildren().add(ringid);
        
        //ringide ümberpaigutamine hiirega klikates, tuleks erialdi klass teha, et ei kordaks tegevust
        stack1.setOnMouseClicked(new EventHandler<MouseEvent>() {
	      	  public void handle(MouseEvent me) {
	      		  double x = stseen1.getWidth();
	      		  double y = stseen1.getHeight();
	      		  stack1.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack1.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring1.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus1 = 34-(int)ring1.getRadius();
	              text1.setText(String.valueOf(väärtus1));
	              stack2.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack2.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring2.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus2 = 34-(int)ring2.getRadius();
	              text2.setText(String.valueOf(väärtus2));
	              stack3.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack3.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring3.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus3 = 34-(int)ring3.getRadius();
	              text3.setText(String.valueOf(väärtus3));
	              points+=väärtus1;
	      	  }
	    	});
        
        stack2.setOnMouseClicked(new EventHandler<MouseEvent>() {
	      	  public void handle(MouseEvent me) {
	      		  double x = stseen1.getWidth();
	      		  double y = stseen1.getHeight();
	      		  stack1.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack1.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring1.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus1 = 34-(int)ring1.getRadius();
	              text1.setText(String.valueOf(väärtus1));
	              stack2.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack2.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring2.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus2 = 34-(int)ring2.getRadius();
	              text2.setText(String.valueOf(väärtus2));
	              stack3.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack3.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring3.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus3 = 34-(int)ring3.getRadius();
	              text3.setText(String.valueOf(väärtus3));
	              points+=väärtus2;
	      	  	}
	    	});
        
        stack3.setOnMouseClicked(new EventHandler<MouseEvent>() {
	      	  public void handle(MouseEvent me) {
	      		  double x = stseen1.getWidth();
	      		  double y = stseen1.getHeight();
	      		  stack1.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack1.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring1.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus1 = 34-(int)ring1.getRadius();
	              text1.setText(String.valueOf(väärtus1));
	              stack2.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack2.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring2.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus2 = 34-(int)ring2.getRadius();
	              text2.setText(String.valueOf(väärtus2));
	              stack3.setLayoutX((int)Math.round(Math.random()*(x-50)));
	      		  stack3.setLayoutY((int)Math.round(Math.random()*(y-50)));
	              ring3.setRadius((Math.round(Math.random()*30+2)));
	              int väärtus3 = 34-(int)ring3.getRadius();
	              text3.setText(String.valueOf(väärtus3));
	      		  points+=väärtus3;
	      	  }
	    	});	

		peaLava.setScene(stseen1);
		peaLava.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
