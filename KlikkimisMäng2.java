import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
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
public class KlikkimisMäng2 extends Application {

	private static final Integer STARTTIME = 60;
	private Timeline timeline;
	private Label timerLabel = new Label();
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
	private int points = 0;
	private IntegerProperty score = new SimpleIntegerProperty(points);
	
	public void MuudaRingiJaTekstiVärvSisenedesJaVäljudes(Circle ring, Text tekst, Color ringiVärvSisenemisel) {
		ring.setOnMouseEntered(event -> {
			ring.setFill(ringiVärvSisenemisel);
			tekst.setFill(Color.BLACK);
		});
		tekst.setOnMouseEntered(event -> {
			ring.setFill(ringiVärvSisenemisel);
			tekst.setFill(Color.BLACK);
		});
		ring.setOnMouseClicked(event -> {
			ring.setFill(ringiVärvSisenemisel);
			tekst.setFill(Color.BLACK);
		});
		ring.setOnMouseExited(event -> {
			ring.setFill(Color.BLACK);
			tekst.setFill(Color.TRANSPARENT);
		});
		tekst.setOnMouseExited(event -> {
			ring.setFill(Color.BLACK);
			tekst.setFill(Color.TRANSPARENT);
		});
	}
	
	
	public void start(Stage peaLava) throws FileNotFoundException, UnsupportedEncodingException {

		//Edetabeli faili loomine
		File edetabel = new File("edetabel.txt");
		PrintWriter pw = new PrintWriter(edetabel, "UTF-8");
				
		// luuakse lava
		Stage start = new Stage();
		start.setTitle("Start ClickFest");
		TextField tekst = new TextField();
		tekst.setText("Sisesta siia oma nimi");
		Button startNupp = new Button("START");
		tekst.setOnKeyTyped(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (!tekst.getText().isEmpty() && !tekst.getText().equals("Sisesta siia oma nimi"))  {
					String nimi = String.valueOf(tekst.getText());
					//pw.write(nimi);
					//pw.write("\t");
				} else {
					// sündmuse lisamine nupule Start - nime sisestamisel mäng käivitub
					startNupp.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							start.hide();
							double suurusX = 500.0;
							double suurusY = 500.0;
							double raadius = 20.0;
							
							Group juur = new Group();
							Scene stseen1 = new Scene(juur, suurusX, suurusY);
							peaLava.setTitle("ClickFest");
			
							//Ringide loomine:
							int väärtus1 = 0;
							Circle ring1 = new Ring(suurusX*0.2, suurusY*0.2, raadius, Color.BLACK);
							Text text1 = new Text (String.valueOf(väärtus1));
							text1.setFill(Color.TRANSPARENT);
							StackPane stack1 = new StackPane();
							stack1.setLayoutX(suurusX*0.2);
							stack1.setLayoutY(suurusY*0.2);
							stack1.getChildren().addAll(ring1, text1);
							MuudaRingiJaTekstiVärvSisenedesJaVäljudes(ring1, text1, Color.RED);
							
							int väärtus2 = 0;
							Circle ring2 = new Ring(suurusX*0.2, suurusY*0.2, raadius, Color.BLACK);
							Text text2 = new Text (String.valueOf(väärtus2));
							text2.setFill(Color.TRANSPARENT);
							StackPane stack2 = new StackPane();
							stack2.setLayoutX(suurusX*0.4);
							stack2.setLayoutY(suurusY*0.2);
							stack2.getChildren().addAll(ring2, text2);
							MuudaRingiJaTekstiVärvSisenedesJaVäljudes(ring2, text2, Color.ORANGE);
			
							int väärtus3 = 0;
							Circle ring3 = new Ring(suurusX*0.2, suurusY*0.2, raadius, Color.BLACK);
							Text text3 = new Text (String.valueOf(väärtus3));
							text3.setFill(Color.TRANSPARENT);
							StackPane stack3 = new StackPane();
							stack3.setLayoutX(suurusX*0.6);
							stack3.setLayoutY(suurusY*0.2);
							stack3.getChildren().addAll(ring3, text3);
							MuudaRingiJaTekstiVärvSisenedesJaVäljudes(ring3, text3, Color.GREEN);
			
							//Stopperi loomine:
							timerLabel.setText(timeSeconds.toString());
							timerLabel.setTextFill(Color.RED);
							timerLabel.setStyle("-fx-font-size: 4em;");
							/*
							Text text4 = new Text("");
							text4.setFill(Color.RED);
							text4.setLayoutX(200);
							text4.setLayoutY(200);
							juur.getChildren().add(text4);
							*/
			
							// Start-nupu loomine
							// timerLabel text property ja timeSeconds property väärtuste sidumine
							timerLabel.textProperty().bind(timeSeconds.asString());
							timerLabel.setTextFill(Color.BLACK);
							timerLabel.setStyle("-fx-font-size: 4em;");
			
							//Button nupp1 = new Button();
							//nupp1.setText("Start Game");
			
							if (timeline != null) {
								timeline.stop();
							}
							timeSeconds.set(STARTTIME);
							timeline = new Timeline();
							timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME),new KeyValue(timeSeconds, 0)));
							timeline.playFromStart();
			
							// Punktiarvestuse kuvamine
							Label scoreLabel = new Label(Integer.toString(points));
							scoreLabel.textProperty().bind(score.asString());
							scoreLabel.setStyle("-fx-font-size: 4em;");
							
							//vbox stopperi paigutamiseks:
							VBox vb1 = new VBox(20);
							vb1.setAlignment(Pos.TOP_LEFT);
							vb1.setPrefWidth(stseen1.getWidth());
							vb1.setLayoutY(30);
			
							// objektide paigutamine juure alla:
							vb1.getChildren().addAll(timerLabel, scoreLabel);
							juur.getChildren().add(vb1);
							Group ringid = new Group();
							ringid.getChildren().addAll(stack1, stack2, stack3);
							juur.getChildren().add(ringid);
			
							//punane ring
							stack1.setOnMouseClicked(new EventHandler<MouseEvent>() {
								public void handle(MouseEvent me) {
									double x = stseen1.getWidth();
									double y = stseen1.getHeight();
									int x1 = (int)Math.round(Math.random()*(x-50));
									int y1 = (int)Math.round(Math.random()*(y-50));
									int r1 = (int)(Math.round(Math.random()*100+2));
									int x2 = (int)Math.round(Math.random()*(x-50));
									int y2 = (int)Math.round(Math.random()*(y-50));
									int r2 = (int)(Math.round(Math.random()*100+2));
									int x3 = (int)Math.round(Math.random()*(x-50));
									int y3 = (int)Math.round(Math.random()*(y-50));
									int r3 = (int)(Math.round(Math.random()*100+2));
									
									// ringide kattuvuse vältimine 
									while (Math.sqrt((x2+r2-x1-r1)*(x2+r2-x1-r1)+(y2+r2-y1-r1)*(y2+r2-y1-r1)) < r1+r2 || 
											Math.sqrt((x3+r3-x1-r1)*(x3+r3-x1-r1)+(y3+r3-y1-r1)*(y3+r3-y1-r1)) < r1+r3 || 
											Math.sqrt((x3+r3-x2-r2)*(x3+r3-x2-r2)+(y3+r3-y2-r2)*(y3+r3-y2-r2)) < r2+r3){
										x2 = (int)Math.round(Math.random()*(x-50));
										y2 = (int)Math.round(Math.random()*(y-50));
										r2 = (int)(Math.round(Math.random()*100+2));
										x3 = (int)Math.round(Math.random()*(x-50));
										y3 = (int)Math.round(Math.random()*(y-50));
										r3 = (int)(Math.round(Math.random()*100+2));
									}
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
									points+=väärtus1;
									
									// punktiarvestus ei toimi õigesti
									score.set(points);
								}
							});
			
							//kollane ring
							stack2.setOnMouseClicked(new EventHandler<MouseEvent>() {
								public void handle(MouseEvent me) {
									double x = stseen1.getWidth();
									double y = stseen1.getHeight();
									int x1 = (int)Math.round(Math.random()*(x-50));
									int y1 = (int)Math.round(Math.random()*(y-50));
									int r1 = (int)(Math.round(Math.random()*100+2));
									int x2 = (int)Math.round(Math.random()*(x-50));
									int y2 = (int)Math.round(Math.random()*(y-50));
									int r2 = (int)(Math.round(Math.random()*100+2));
									int x3 = (int)Math.round(Math.random()*(x-50));
									int y3 = (int)Math.round(Math.random()*(y-50));
									int r3 = (int)(Math.round(Math.random()*100+2));
									
									// ringide kattuvuse vältimine
									while (Math.sqrt((x2+r2-x1-r1)*(x2+r2-x1-r1)+(y2+r2-y1-r1)*(y2+r2-y1-r1)) < r1+r2 || 
											Math.sqrt((x3+r3-x1-r1)*(x3+r3-x1-r1)+(y3+r3-y1-r1)*(y3+r3-y1-r1)) < r1+r3 || 
											Math.sqrt((x3+r3-x2-r2)*(x3+r3-x2-r2)+(y3+r3-y2-r2)*(y3+r3-y2-r2)) < r2+r3){
										x2 = (int)Math.round(Math.random()*(x-50));
										y2 = (int)Math.round(Math.random()*(y-50));
										r2 = (int)(Math.round(Math.random()*100+2));
										x3 = (int)Math.round(Math.random()*(x-50));
										y3 = (int)Math.round(Math.random()*(y-50));
										r3 = (int)(Math.round(Math.random()*100+2));
									}
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
							stack3.setOnMouseClicked(new EventHandler<MouseEvent>() {
								public void handle(MouseEvent me) {
									double x = stseen1.getWidth();
									double y = stseen1.getHeight();
									int x1 = (int)Math.round(Math.random()*(x-50));
									int y1 = (int)Math.round(Math.random()*(y-50));
									int r1 = (int)(Math.round(Math.random()*100+2));
									int x2 = (int)Math.round(Math.random()*(x-50));
									int y2 = (int)Math.round(Math.random()*(y-50));
									int r2 = (int)(Math.round(Math.random()*100+2));
									int x3 = (int)Math.round(Math.random()*(x-50));
									int y3 = (int)Math.round(Math.random()*(y-50));
									int r3 = (int)(Math.round(Math.random()*100+2));
									
									// ringide kattuvuse vältimine 
									while (Math.sqrt((x2+r2-x1-r1)*(x2+r2-x1-r1)+(y2+r2-y1-r1)*(y2+r2-y1-r1)) < r1+r2 || 
											Math.sqrt((x3+r3-x1-r1)*(x3+r3-x1-r1)+(y3+r3-y1-r1)*(y3+r3-y1-r1)) < r1+r3 || 
											Math.sqrt((x3+r3-x2-r2)*(x3+r3-x2-r2)+(y3+r3-y2-r2)*(y3+r3-y2-r2)) < r2+r3){
										x2 = (int)Math.round(Math.random()*(x-50));
										y2 = (int)Math.round(Math.random()*(y-50));
										r2 = (int)(Math.round(Math.random()*100+2));
										x3 = (int)Math.round(Math.random()*(x-50));
										y3 = (int)Math.round(Math.random()*(y-50));
										r3 = (int)(Math.round(Math.random()*100+2));
									}
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
									points+=väärtus3;
									score.set(points);
								}
							});
			
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
					});
				}	
			}
		});
			 
		// textfieldi ja nupu paigutamine
		VBox vBox2 = new VBox(10);
		vBox2.setAlignment(Pos.CENTER);
		vBox2.getChildren().addAll(tekst, startNupp);
		
		//stseeni loomine ja näitamine
		Scene stseen2 = new Scene(vBox2, 300, 100);
		
		start.setScene(stseen2);
		start.show();
		
		
		
		// uue aknasündmuse lisamine - mängu lõpetamine ja tulemuse kuvamine või uuesti alustamine
		  peaLava.setOnHiding(new EventHandler<WindowEvent>() {
		    public void handle(WindowEvent event) {
		      // luuakse teine lava
		      Stage kusimus = new Stage();
		      Label label = new Label("Sinu tulemus "+String.valueOf(points)+" punkti on salvestatud edetabelisse! Kas tahad uuesti mängida?");
		      Button okButton = new Button("Jah");
		      Button cancelButton = new Button("Ei");
		 
		      // sündmuse lisamine nupule Jah
		      okButton.setOnAction(new EventHandler<ActionEvent>() {
		    	  public void handle(ActionEvent event) {
		        	peaLava.show();
		        	kusimus.hide();
		        	//siin käivitada mäng uuesti (stopper, ringid ja punktiarvestus algasendisse)	  
		        }
		      });
		 
		      // sündmuse lisamine nupule Ei
		      cancelButton.setOnAction(new EventHandler<ActionEvent>() {
		    	  public void handle(ActionEvent event) {
		    		  kusimus.hide();
		        }
		      });
		 
		      // nuppude grupeerimine
		      FlowPane pane = new FlowPane(10, 10);
		      pane.setAlignment(Pos.CENTER);
		      pane.getChildren().addAll(okButton, cancelButton);
		 
		      // küsimuse ja nuppude gruppi paigutamine
		      VBox vBox3 = new VBox(10);
		      vBox3.setAlignment(Pos.CENTER);
		      vBox3.getChildren().addAll(label, pane);
		 
		      //stseeni loomine ja näitamine
		      Scene stseen3 = new Scene(vBox3);
		      kusimus.setScene(stseen3);
		      kusimus.show();
		    }
		  });
		  
		 pw.close();	 
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}