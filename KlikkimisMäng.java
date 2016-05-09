import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	private static final Integer STARTTIME = 60;
	private Timeline timeline;
	private Label timerLabel = new Label();
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
	private int points = 0;
	private IntegerProperty score = new SimpleIntegerProperty(points);
	
	public class Ring extends Circle {
		
		int asukohtX;
		int asukohtY;
		int raadius;
		Color värv;
		
		Ring(int asukohtX, int asukohtY, int raadius, Color värv) {
			this.asukohtX = asukohtX;
			this.asukohtY = asukohtY;
			this.raadius = raadius;
			this.värv = värv;
		}
		
		
	}

	public void start(Stage peaLava) {

		double suurusX = 500.0;
		double suurusY = 500.0;
		double raadius = 20.0;
		Group juur = new Group();
		Scene stseen1 = new Scene(juur, suurusX, suurusY);
		peaLava.setTitle("Selektiivne klikkimine");

		//võibolla on vaja paigutamisel
		//BorderPane piiriPaan1 = new BorderPane();
		//piiriPaan1.setMinWidth(500);
		//piiriPaan1.setMinHeight(500);


		//Ringide loomine:
		int väärtus1 = 0;
		Circle ring1 = new Circle(suurusX*0.2, suurusY*0.2, raadius, Color.BLACK);
		ring1.setOnMouseMoved(event -> ring1.setFill(Color.RED));
		Text text1 = new Text (String.valueOf(väärtus1));
		text1.setFill(Color.TRANSPARENT);
		ring1.setOnMouseEntered(event -> {
			ring1.setFill(Color.RED);
			text1.setFill(Color.BLACK);
		});
		text1.setOnMouseEntered(event -> {
			ring1.setFill(Color.RED);
			text1.setFill(Color.BLACK);
		});
		ring1.setOnMouseExited(event -> {
			ring1.setFill(Color.BLACK);
			text1.setFill(Color.TRANSPARENT);
		});
		text1.setOnMouseExited(event -> {
			ring1.setFill(Color.BLACK);
			text1.setFill(Color.TRANSPARENT);
		});
		ring1.setOnMouseClicked(event -> {
			ring1.setFill(Color.RED);
			text1.setFill(Color.BLACK);
		});
		StackPane stack1 = new StackPane();
		stack1.setLayoutX(suurusX*0.2);
		stack1.setLayoutY(suurusY*0.2);
		stack1.getChildren().addAll(ring1, text1);

		int väärtus2 = 0;
		Circle ring2 = new Circle(suurusX*0.2, suurusY*0.2, raadius, Color.BLACK);
		Text text2 = new Text (String.valueOf(väärtus2));
		text2.setFill(Color.TRANSPARENT);
		ring2.setOnMouseEntered(event -> {
			ring2.setFill(Color.ORANGE);
			text2.setFill(Color.BLACK);
		});
		text2.setOnMouseEntered(event -> {
			ring2.setFill(Color.ORANGE);
			text2.setFill(Color.BLACK);
		});
		ring2.setOnMouseExited(event -> {
			ring2.setFill(Color.BLACK);
			text2.setFill(Color.TRANSPARENT);
		});
		text2.setOnMouseExited(event -> {
			ring2.setFill(Color.BLACK);
			text2.setFill(Color.TRANSPARENT);
		});
		StackPane stack2 = new StackPane();
		stack2.setLayoutX(suurusX*0.4);
		stack2.setLayoutY(suurusY*0.2);
		stack2.getChildren().addAll(ring2, text2);

		int väärtus3 = 0;
		Circle ring3 = new Circle(suurusX*0.2, suurusY*0.2, raadius, Color.BLACK);
		Text text3 = new Text (String.valueOf(väärtus3));
		text3.setFill(Color.TRANSPARENT);
		ring3.setOnMouseEntered(event -> {
			ring3.setFill(Color.GREEN);
			text3.setFill(Color.BLACK);
		});
		text3.setOnMouseEntered(event -> {
			ring3.setFill(Color.GREEN);
			text3.setFill(Color.BLACK);
		});
		ring3.setOnMouseExited(event -> {
			ring3.setFill(Color.BLACK);
			text3.setFill(Color.TRANSPARENT);
		});
		text3.setOnMouseExited(event -> {
			ring3.setFill(Color.BLACK);
			text3.setFill(Color.TRANSPARENT);
		});
		StackPane stack3 = new StackPane();
		stack3.setLayoutX(suurusX*0.6);
		stack3.setLayoutY(suurusY*0.2);
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
		// timerLabel text property ja timeSeconds property väärtuste sidumine
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

		// Punktiarvestuse kuvamine
		Label scoreLabel = new Label(Integer.toString(points));
		scoreLabel.textProperty().bind(score.asString());
		scoreLabel.setStyle("-fx-font-size: 4em;");
		

		//vbox stopperi paigutamiseks:
		VBox vb1 = new VBox(20);
		vb1.setAlignment(Pos.TOP_RIGHT);
		vb1.setPrefWidth(stseen1.getWidth());
		vb1.setLayoutY(30);

		// objektide paigutamine juure alla:
		vb1.getChildren().addAll(nupp1, timerLabel, scoreLabel);
		juur.getChildren().add(vb1);
		Group ringid = new Group();
		ringid.getChildren().addAll(stack1, stack2, stack3);
		juur.getChildren().add(ringid);

		//ringide ümberpaigutamine hiirega klikates, tuleks erialdi klass teha, et ei kordaks tegevust
		//punane ring
//		stack1.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent me) {
//				double x = stseen1.getWidth();
//				double y = stseen1.getHeight();
//				stack1.setLayoutX((int)Math.round(Math.random()*(x-50)));
//				stack1.setLayoutY((int)Math.round(Math.random()*(y-50)));
//				ring1.setRadius((Math.round(Math.random()*100+2)));
//				int väärtus1 = 104-(int)ring1.getRadius();
//				text1.setText(String.valueOf(väärtus1));
//				stack2.setLayoutX((int)Math.round(Math.random()*(x-50)));
//				stack2.setLayoutY((int)Math.round(Math.random()*(y-50)));
//				ring2.setRadius((Math.round(Math.random()*100+2)));
//				int väärtus2 = 104-(int)ring2.getRadius();
//				text2.setText(String.valueOf(väärtus2));
//				stack3.setLayoutX((int)Math.round(Math.random()*(x-50)));
//				stack3.setLayoutY((int)Math.round(Math.random()*(y-50)));
//				ring3.setRadius((Math.round(Math.random()*100+2)));
//				int väärtus3 = 104-(int)ring3.getRadius();
//				text3.setText(String.valueOf(väärtus3));
//				points+=väärtus1;
//				
//				// punktiarvestus ei toimi õigesti
//				score.set(points);
//			}
//		});

		//kollane ring
		stack2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				double x = stseen1.getWidth();
				double y = stseen1.getHeight();
				int x1 = (int)Math.round(Math.random()*(x-50));
				int y1 = (int)Math.round(Math.random()*(x-50));
				int r1 = (int)(Math.round(Math.random()*100+2));
				int x2 = (int)Math.round(Math.random()*(x-50));
				int y2 = (int)Math.round(Math.random()*(x-50));
				int r2 = (int)(Math.round(Math.random()*100+2));
				int x3 = (int)Math.round(Math.random()*(x-50));
				int y3 = (int)Math.round(Math.random()*(x-50));
				int r3 = (int)(Math.round(Math.random()*100+2));
				
				// kattuvuse vältimise katsetud
				System.out.println("x " + x1 + " " + x2 + " " + x3 + " y " + y1 + " " + y2 + " " + y3 + " r " + r1 + " " + r2 + " " + r3);
				while ((x1+x2)/2 < r1+r2 && (y1+y2)/2 < r1+r2 && (x1+x3)/2 < r1+r3 && (y1+y3)/2 < r1+r3 && (x3+x2)/2 < r3+r2 && (y3+y2)/2 < r3+r2){
					x2 = (int)Math.round(Math.random()*(x-50));
					y2 = (int)Math.round(Math.random()*(x-50));
					r2 = (int)(Math.round(Math.random()*100+2));
					x3 = (int)Math.round(Math.random()*(x-50));
					y3 = (int)Math.round(Math.random()*(x-50));
					r3 = (int)(Math.round(Math.random()*100+2));
				}
				System.out.println("x " + x1 + " " + x2 + " " + x3 + " y " + y1 + " " + y2 + " " + y3 + " r " + r1 + " " + r2 + " " + r3);
				stack1.setLayoutX(x1);
				stack1.setLayoutY(y1);
				ring1.setRadius(r1);
				int väärtus1 = 104-r1;
				text1.setText(String.valueOf(väärtus1));
				stack2.setLayoutX(x2);
				stack2.setLayoutY(y2);
				ring2.setRadius(r2);
				int väärtus2 = 104-r2;
				text2.setText(String.valueOf(väärtus2));
				stack3.setLayoutX(x3);
				stack3.setLayoutY(y3);
				ring3.setRadius(r3);
				int väärtus3 = 104-r3;
				text3.setText(String.valueOf(väärtus3));
				points+=väärtus2;
				score.set(points);
				}
		});

		//roheline ring
//		stack3.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent me) {
//				double x = stseen1.getWidth();
//				double y = stseen1.getHeight();
//				stack1.setLayoutX((int)Math.round(Math.random()*(x-50)));
//				stack1.setLayoutY((int)Math.round(Math.random()*(y-50)));
//				ring1.setRadius((Math.round(Math.random()*100+2)));
//				int väärtus1 = 104-(int)ring1.getRadius();
//				text1.setText(String.valueOf(väärtus1));
//				stack2.setLayoutX((int)Math.round(Math.random()*(x-50)));
//				stack2.setLayoutY((int)Math.round(Math.random()*(y-50)));
//				ring2.setRadius((Math.round(Math.random()*100+2)));
//				int väärtus2 = 104-(int)ring2.getRadius();
//				text2.setText(String.valueOf(väärtus2));
//				stack3.setLayoutX((int)Math.round(Math.random()*(x-50)));
//				stack3.setLayoutY((int)Math.round(Math.random()*(y-50)));
//				ring3.setRadius((Math.round(Math.random()*100+2)));
//				int väärtus3 = 104-(int)ring3.getRadius();
//				text3.setText(String.valueOf(väärtus3));
//				points+=väärtus3;
//				score.set(points);
//			}
//		});	

		peaLava.setScene(stseen1);
		peaLava.show();

		//jälgib akna X/Y-mõõtmete muutumist ning muudab vastavalt ringi mõõtmeid
		//Object-tüüpi väärtuseid ei saanud teisendada, selle asemel kasutusel Number-tüüp
		ChangeListener<Number> listenerY = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println( "Y: observable: " + observable + ", oldValue: " + oldValue.doubleValue() + ", newValue: " + newValue.doubleValue());
				ring1.setRadius(100*(newValue.doubleValue()/500.0));
			}
		};

		ChangeListener<Number> listenerX = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println( "X: observable: " + observable + ", oldValue: " + oldValue.doubleValue() + ", newValue: " + newValue.doubleValue());
				ring1.setRadius(100*(newValue.doubleValue()/500.0));
			}
		};

		stseen1.widthProperty().addListener(listenerX);
		stseen1.heightProperty().addListener(listenerY);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
