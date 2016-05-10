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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
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
	private int väärtus1 = 0;
	private int väärtus2 = 0;
	private int väärtus3 = 0;
	
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

	public boolean kontrolliRingiMitteKattuvust(int x1, int y1, int x2, int y2, int x3, int y3) {
		int r1 = 50;
		int r2 = 50;
		int r3 = 50;

		if (Math.sqrt((x2+r2-x1-r1)*(x2+r2-x1-r1)+(y2+r2-y1-r1)*(y2+r2-y1-r1)) < r1+r2 || 
				Math.sqrt((x3+r3-x1-r1)*(x3+r3-x1-r1)+(y3+r3-y1-r1)*(y3+r3-y1-r1)) < r1+r3 || 
				Math.sqrt((x3+r3-x2-r2)*(x3+r3-x2-r2)+(y3+r3-y2-r2)*(y3+r3-y2-r2)) < r2+r3) {
			return false;
		} else {
			return true;
		}
	}

	public int juhuslikPunktisumma() {
		return 104-(int)(Math.round(Math.random()*100+2));
	}


	public void start(Stage peaLava) throws FileNotFoundException, UnsupportedEncodingException {

		//Edetabeli faili loomine
		File fail1 = new File("edetabel.txt");
		PrintWriter skoorifail = new PrintWriter(fail1, "UTF-8");
		//Mängija logifail: mängija kõiki (palli)valikuid sisaldav fail
		File fail2 = new File("logifail.txt");
		PrintWriter logifail = new PrintWriter(fail2, "UTF-8");

		// Luuakse esimene lava/aken
		Stage start = new Stage();
		start.setTitle("Start ClickFest");
		TextField tekst = new TextField();
		Text juhend1 = new Text("Mängu juhend:");
		Text juhend2 = new Text("Mängus on kolm palli, iga pall annab hiirega klikates teatud arvu puhkte (punktisumma on nähtav hiirega peale minnes).");
		Text juhend3 = new Text("Mängijal tuleb piiratud aja jooksul koguda võimalikult palju punkte");
		tekst.setText("Sisesta siia oma nimi (max 8 tähte!)");
		Button startNupp = new Button("START");
		try {
			// sündmuse lisamine nupule Start - nime sisestamisel mäng käivitub
			startNupp.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					if (tekst.getText().length()<1 || tekst.getText().length()>8 || tekst.getText().equals("Sisesta siia oma nimi"))  {
						NimeErind e = new NimeErind("Nimi on ebakorrektselt sisestatud!");
						tekst.setText("Palun sisesta nimi korrektselt!");
						throw e;
					} else {
						//Skooride faili mängija nime sisestamine
						String nimi = String.valueOf(tekst.getText());
						skoorifail.print(nimi);
						skoorifail.print(" ");
						
						//Kui nimi korrektselt sisestatud avaneb teine (mängu)aken ja esimene aken on peidus
						start.hide();
						double suurusX = 800.0;
						double suurusY = 600.0;
						int raadius = 50;

						Group juur = new Group();
						peaLava.setTitle("ClickFest");
						Scene stseen1 = new Scene(juur, suurusX, suurusY);

						//Ringide loomine:
						Circle ring1 = new Ring(100,100, raadius, Color.BLACK);
						Text text1 = new Text (String.valueOf(väärtus1));
						text1.setFill(Color.TRANSPARENT);
						StackPane stack1 = new StackPane();
						stack1.setLayoutX(suurusX*0.2);
						stack1.setLayoutY(suurusY*0.2);
						stack1.getChildren().addAll(ring1, text1);
						MuudaRingiJaTekstiVärvSisenedesJaVäljudes(ring1, text1, Color.RED);

						Circle ring2 = new Ring(200,100, raadius, Color.BLACK);
						Text text2 = new Text (String.valueOf(väärtus2));
						text2.setFill(Color.TRANSPARENT);
						StackPane stack2 = new StackPane();
						stack2.setLayoutX(suurusX*0.2+100);
						stack2.setLayoutY(suurusY*0.2);
						stack2.getChildren().addAll(ring2, text2);
						MuudaRingiJaTekstiVärvSisenedesJaVäljudes(ring2, text2, Color.ORANGE);

						Circle ring3 = new Ring(300,100, raadius, Color.BLACK);
						Text text3 = new Text (String.valueOf(väärtus3));
						text3.setFill(Color.TRANSPARENT);
						StackPane stack3 = new StackPane();
						stack3.setLayoutX(suurusX*0.2+200);
						stack3.setLayoutY(suurusY*0.2);
						stack3.getChildren().addAll(ring3, text3);
						MuudaRingiJaTekstiVärvSisenedesJaVäljudes(ring3, text3, Color.GREEN);

						Group ringid = new Group();
						ringid.getChildren().addAll(stack1, stack2, stack3);
						ringid.maxHeight(suurusY);
						ringid.maxWidth(suurusX);

						//Stopperi loomine:
						// timerLabel text property ja timeSeconds property väärtuste sidumine
						Label aeg = new Label("AEG:");
						aeg.setStyle("-fx-font-size: 2em;");
						timerLabel.textProperty().bind(timeSeconds.asString());
						timerLabel.setTextFill(Color.BLACK);
						timerLabel.setStyle("-fx-font-size: 2em;");

						if (timeline != null) {
							timeline.stop();
						}
						timeSeconds.set(STARTTIME);
						timeline = new Timeline();
						timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME),new KeyValue(timeSeconds, 0)));
						timeline.playFromStart();

						// Punktiarvestuse kuvamine
						Label skoor = new Label("PUNKTID: ");
						skoor.setStyle("-fx-font-size: 2em;");
						Label scoreLabel = new Label(Integer.toString(points));
						scoreLabel.textProperty().bind(score.asString());
						scoreLabel.setStyle("-fx-font-size: 2em;");

						//vbox stopperi paigutamiseks:
						VBox vb1 = new VBox(20);
						vb1.setAlignment(Pos.TOP_LEFT);
						//vb1.setPrefWidth(stseen1.getWidth());
						vb1.setLayoutX(10);
						vb1.setLayoutY(10);
						vb1.setMaxSize(150,100);

						// objektide paigutamine juure alla:
						vb1.getChildren().addAll(aeg, timerLabel, skoor, scoreLabel);
						juur.getChildren().addAll(vb1, ringid);


						//hiirega kliki sündmused: punase ringi liikumine ja punktiväärtus
						ring1.setOnMouseClicked(new EventHandler<MouseEvent>() {
							public void handle(MouseEvent me) {
								points+=väärtus1;
								logifail.println(String.valueOf(väärtus1));
								score.set(points);
								double x = stseen1.getWidth();
								double y = stseen1.getHeight();
								int x1 = (int)Math.round(Math.random()*(x-275)+150);
								int y1 = (int)Math.round(Math.random()*(y-225)+100);
								//int r1 = (int)(Math.round(Math.random()*100+2));
								int r1 = 50;
								int x2 = (int)Math.round(Math.random()*(x-275)+150);
								int y2 = (int)Math.round(Math.random()*(y-225)+100);
								//int r2 = (int)(Math.round(Math.random()*100+2));
								int r2 = 50;
								int x3 = (int)Math.round(Math.random()*(x-275)+150);
								int y3 = (int)Math.round(Math.random()*(y-225)+100);
								//int r3 = (int)(Math.round(Math.random()*100+2));
								int r3 = 50;

								// ringide kattuvuse vältimine 
								while (kontrolliRingiMitteKattuvust(x1,y1,x2,y2,x3,y3)==false){
									x2 = (int)Math.round(Math.random()*(x-275)+150);
									y2 = (int)Math.round(Math.random()*(y-225)+100);
									//r2 = (int)(Math.round(Math.random()*100+2));
									x3 = (int)Math.round(Math.random()*(x-275)+150);
									y3 = (int)Math.round(Math.random()*(y-225)+100);
									//r3 = (int)(Math.round(Math.random()*100+2));
								}
								stack1.setLayoutX(x1);
								stack1.setLayoutY(y1);
								ring1.setRadius(r1);
								//int väärtus1 = 104-r1;
								väärtus1 = juhuslikPunktisumma();
								text1.setText(String.valueOf(väärtus1));
								stack2.setLayoutX(x2);
								stack2.setLayoutY(y2);
								ring2.setRadius(r2);
								//int väärtus2 = 104-r2;
								väärtus2 = juhuslikPunktisumma();
								text2.setText(String.valueOf(väärtus2));
								stack3.setLayoutX(x3);
								stack3.setLayoutY(y3);
								ring3.setRadius(r3);
								//int väärtus3 = 104-r3;
								väärtus3 = juhuslikPunktisumma();
								text3.setText(String.valueOf(väärtus3));
							}
						});

						//hiirega kliki sündmused: kollase ringi liikumine ja punktiväärtus
						ring2.setOnMouseClicked(new EventHandler<MouseEvent>() {
							public void handle(MouseEvent me) {
								points+=väärtus2; // punktide arvestus ikka ei tööta!
								logifail.println(String.valueOf(väärtus2));
								score.set(points);
								double x = stseen1.getWidth();
								double y = stseen1.getHeight();
								int x1 = (int)Math.round(Math.random()*(x-275)+150);
								int y1 = (int)Math.round(Math.random()*(y-225)+100);
								//int r1 = (int)(Math.round(Math.random()*100+2));
								int r1 = 50;
								int x2 = (int)Math.round(Math.random()*(x-275)+150);
								int y2 = (int)Math.round(Math.random()*(y-225)+100);
								//int r2 = (int)(Math.round(Math.random()*100+2));
								int r2 = 50;
								int x3 = (int)Math.round(Math.random()*(x-275)+150);
								int y3 = (int)Math.round(Math.random()*(y-225)+100);
								//int r3 = (int)(Math.round(Math.random()*100+2));
								int r3 = 50;

								// ringide kattuvuse vältimine 
								while (kontrolliRingiMitteKattuvust(x1,y1,x2,y2,x3,y3)==false){
									x2 = (int)Math.round(Math.random()*(x-275)+150);
									y2 = (int)Math.round(Math.random()*(y-225)+100);
									//r2 = (int)(Math.round(Math.random()*100+2));
									x3 = (int)Math.round(Math.random()*(x-275)+150);
									y3 = (int)Math.round(Math.random()*(y-225)+100);
									//r3 = (int)(Math.round(Math.random()*100+2));
								}
								stack1.setLayoutX(x1);
								stack1.setLayoutY(y1);
								ring1.setRadius(r1);
								//int väärtus1 = 104-r1;
								väärtus1 = juhuslikPunktisumma();
								text1.setText(String.valueOf(väärtus1));
								stack2.setLayoutX(x2);
								stack2.setLayoutY(y2);
								ring2.setRadius(r2);
								//int väärtus2 = 104-r2;
								väärtus2 = juhuslikPunktisumma();
								text2.setText(String.valueOf(väärtus2));
								stack3.setLayoutX(x3);
								stack3.setLayoutY(y3);
								ring3.setRadius(r3);
								//int väärtus3 = 104-r3;
								väärtus3 = juhuslikPunktisumma();
								text3.setText(String.valueOf(väärtus3));

							}
						});

						//hiirega kliki sündmused: rohelise ringi liikumine ja punktiväärtus
						ring3.setOnMouseClicked(new EventHandler<MouseEvent>() {
							public void handle(MouseEvent me) {
								points+=väärtus3;
								logifail.println(String.valueOf(väärtus3));
								score.set(points);
								double x = stseen1.getWidth();
								double y = stseen1.getHeight();
								int x1 = (int)Math.round(Math.random()*(x-275)+150);
								int y1 = (int)Math.round(Math.random()*(y-225)+100);
								//int r1 = (int)(Math.round(Math.random()*100+2));
								int r1 = 50;
								int x2 = (int)Math.round(Math.random()*(x-275)+150);
								int y2 = (int)Math.round(Math.random()*(y-225)+100);
								//int r2 = (int)(Math.round(Math.random()*100+2));
								int r2 = 50;
								int x3 = (int)Math.round(Math.random()*(x-275)+150);
								int y3 = (int)Math.round(Math.random()*(y-225)+100);
								//int r3 = (int)(Math.round(Math.random()*100+2));
								int r3 = 50;

								// ringide kattuvuse vältimine 
								while (kontrolliRingiMitteKattuvust(x1,y1,x2,y2,x3,y3)==false){
									x2 = (int)Math.round(Math.random()*(x-275)+150);
									y2 = (int)Math.round(Math.random()*(y-225)+100);
									//r2 = (int)(Math.round(Math.random()*100+2));
									x3 = (int)Math.round(Math.random()*(x-275)+150);
									y3 = (int)Math.round(Math.random()*(y-225)+100);
									//r3 = (int)(Math.round(Math.random()*100+2));
								}
								stack1.setLayoutX(x1);
								stack1.setLayoutY(y1);
								ring1.setRadius(r1);
								//int väärtus1 = 104-r1;
								väärtus1 = juhuslikPunktisumma();
								
								text1.setText(String.valueOf(väärtus1));
								stack2.setLayoutX(x2);
								stack2.setLayoutY(y2);
								ring2.setRadius(r2);
								//int väärtus2 = 104-r2;
								väärtus2 = juhuslikPunktisumma();
								text2.setText(String.valueOf(väärtus2));
								stack3.setLayoutX(x3);
								stack3.setLayoutY(y3);
								ring3.setRadius(r3);
								//int väärtus3 = 104-r3;
								väärtus3 = juhuslikPunktisumma();
								text3.setText(String.valueOf(väärtus3));	
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
								ring1.setRadius(100*(newValue.doubleValue()/1500.0));
								ring2.setRadius(100*(newValue.doubleValue()/1500.0));
								ring3.setRadius(100*(newValue.doubleValue()/1500.0));
							}
						};

						ChangeListener<Number> listenerX = new ChangeListener<Number>() {
							@Override
							public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
								System.out.println( "X: observable: " + observable + ", oldValue: " + oldValue.doubleValue() + ", newValue: " + newValue.doubleValue());
								ring1.setRadius(100*(newValue.doubleValue()/1500.0));
								ring2.setRadius(100*(newValue.doubleValue()/1500.0));
								ring3.setRadius(100*(newValue.doubleValue()/1500.0));
							}
						};

						stseen1.widthProperty().addListener(listenerX);
						stseen1.heightProperty().addListener(listenerY);
					}
				}
			});
				 
			// textfieldi ja nupu paigutamine
			VBox vBox2 = new VBox(10);
			vBox2.setAlignment(Pos.CENTER);
			vBox2.getChildren().addAll(tekst, juhend1, juhend2, juhend3, startNupp);
			
			//stseeni loomine ja näitamine
			Scene stseen2 = new Scene(vBox2, 900, 200);
			
			start.setScene(stseen2);
			start.show();
			
			// Mängu lõpu akna ja tulemuse kuvamine
			timeSeconds.addListener((observable, oldTimeValue, newTimeValue) -> {
			if (newTimeValue.intValue()==0) {
			// uue aknasündmuse lisamine - mängu lõpetamine ja tulemuse kuvamine või uuesti alustamine
			 // peaLava.setOnHiding(new EventHandler<WindowEvent>() {
			    //public void handle(WindowEvent event) {
			      // luuakse teine lava
				  peaLava.hide();
			      Stage kusimus = new Stage();
			      kusimus.setTitle("Mängu tulemus");
			      skoorifail.println(String.valueOf(points));
			      logifail.println("Kokku: "+String.valueOf(points));
			      Label label = new Label("Sinu tulemus "+String.valueOf(points)+" punkti on salvestatud edetabelisse!");
			      //Button okButton = new Button("Jah");
			      //Button cancelButton = new Button("Ei");
			      Button okButton = new Button("OK");
			      
			      // sündmuse lisamine nupule OK
			      okButton.setOnAction(new EventHandler<ActionEvent>() {
			    	  public void handle(ActionEvent event) {
			    		  kusimus.hide(); 
			        }
			      });
			 
			      /* sündmuse lisamine nupule Jah
			      okButton.setOnAction(new EventHandler<ActionEvent>() {
			    	  public void handle(ActionEvent event) {
			        	peaLava.show();
			        	kusimus.hide();
			        	//siin käivitada mäng uuesti (stopper, ringid ja punktiarvestus algasendisse), praegu ei toimi	  
			        }
			      });
			 	
			      // sündmuse lisamine nupule Ei
			      cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			    	  public void handle(ActionEvent event) {
			    		  kusimus.hide(); 
			        }
			      });*/
			 
			      // nuppude grupeerimine
			      FlowPane pane = new FlowPane(10, 10);
			      pane.setAlignment(Pos.CENTER);
			      pane.getChildren().addAll(okButton);
			 
			      // küsimuse ja nuppude gruppi paigutamine
			      VBox vBox3 = new VBox(10);
			      vBox3.setAlignment(Pos.CENTER);
			      vBox3.getChildren().addAll(label, pane);
			 
			      //stseeni loomine ja näitamine
			      Scene stseen3 = new Scene(vBox3, 600, 150);
			      kusimus.setScene(stseen3);
			      kusimus.show();
			    }
			  //});
			});
			  
			 logifail.close();
			 skoorifail.close();
			
			
		} catch (NimeErind e) {
			tekst.setText("Palun sisesta nimi korrektselt!");
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}