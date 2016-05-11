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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
/*
 * juur
 * -vBox1
 * --aeg
 * --timerLabel
 * --skoor
 * --scoreLabel
 * -vBox2
 * --tekst
 * --juhend
 * --startNupp
 * -vBox3
 * --label
 * --pane
 * ---okButton
 * -ringid
 * --stack1
 * ---ring1
 * ---text1
 * --stack2
 * ---ring2
 * ---text2
 * --stack3
 * ---ring3
 * ---text3
 */
public class KlikkimisMäng2 extends Application {

	//Ajaarvestus
	private static final Integer STARTTIME = 10;
	private Timeline timeline;
	private Label timerLabel = new Label();
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

	//Punktiarvestus
	private int points = 0;
	private IntegerProperty score = new SimpleIntegerProperty(points);
	private int väärtus1 = juhuslikPunktisumma();
	private int väärtus2 = juhuslikPunktisumma();
	private int väärtus3 = juhuslikPunktisumma();
	private String nimi = null;

	//massiivid testimiseks
	private String[] parimNimi = new String[10];
	private int[] parimSkoor = new int[10];
	
	//Akna vaikesuurus
	private double suurusX = 800.0;
	private double suurusY = 600.0;

	//Ringide X- ja Y-koordinaadid, raadius
	private int raadius = (int)(suurusX/16);
	private int r1x = (int)Math.round(Math.random()*(suurusX-raadius*6)+150);
	private int r1y = (int)Math.round(Math.random()*(suurusY-raadius*4)+raadius*2);
	private int r2x = (int)Math.round(Math.random()*(suurusX-raadius*6)+150);
	private int r2y = (int)Math.round(Math.random()*(suurusY-raadius*4)+raadius*2);
	private int r3x = (int)Math.round(Math.random()*(suurusX-raadius*6)+150);
	private int r3y = (int)Math.round(Math.random()*(suurusY-raadius*4)+raadius*2);

	public void MuudaRingiJaTekstiVärvSisenedesJaVäljudes(Circle ring, Text tekst, Color ringiVärvSisenemisel) {
		ring.setOnMouseEntered(event -> ring.setFill(ringiVärvSisenemisel));
		tekst.setOnMouseEntered(event -> ring.setFill(ringiVärvSisenemisel));
		ring.setOnMouseExited(event -> ring.setFill(Color.BLACK));
	}

	public boolean kontrolliRingiMitteKattuvust(int r1x, int r1y, int r2x, int r2y, int r3x, int r3y) {
		if (Math.sqrt((r2x-r1x)*(r2x-r1x)+(r2y-r1y)*(r2y-r1y)) < raadius*2 || 
				Math.sqrt((r3x-r1x)*(r3x-r1x)+(r3y-r1y)*(r3y-r1y)) < raadius*2 || 
				Math.sqrt((r3x-r2x)*(r3x-r2x)+(r3y-r2y)*(r3y-r2y)) < raadius*2) {
			//System.out.println("Kattuvad ringid");
			return false;
		} else {
			return true;
		}
	}

	public int juhuslikPunktisumma() {
		return (int)(Math.round(Math.random()*100));
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
		tekst.setText("Sisesta siia oma nimi (max 8 tähte!)");

		Text juhend = new Text("Mängu juhend:\n"
				+ "Mängus on kolm palli, iga pall annab hiirega klikates teatud arvu puhkte (punktisumma on nähtav hiirega peale minnes).\n"
				+ "Mängijal tuleb piiratud aja jooksul koguda võimalikult palju punkte");
		juhend.setTextAlignment(TextAlignment.CENTER);

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
						nimi = String.valueOf(tekst.getText());
						parimNimi[0] = nimi;
						skoorifail.print(nimi + ";");
						skoorifail.flush();

						//Kui nimi korrektselt sisestatud, avaneb teine (mängu)aken ja esimene aken on peidus
						start.hide();

						Group juur = new Group();
						peaLava.setTitle("ClickFest");
						Scene stseen1 = new Scene(juur, suurusX, suurusY);

						while (kontrolliRingiMitteKattuvust(r1x,r1y,r2x,r2y,r3x,r3y)==false){
							r2x = (int)Math.round(Math.random()*(suurusX-raadius*6)+150);
							r2y = (int)Math.round(Math.random()*(suurusY-raadius*4)+raadius*2);
							r3x = (int)Math.round(Math.random()*(suurusX-raadius*6)+150);
							r3y = (int)Math.round(Math.random()*(suurusY-raadius*4)+raadius*2);
						}

						//Ringide loomine:
						Circle ring1 = new Circle(r1x, r1y, raadius, Color.BLACK);
						Text text1 = new Text (String.valueOf(väärtus1));
						text1.setFont(new Font(30));
						StackPane stack1 = new StackPane();
						stack1.setLayoutX(r1x);
						stack1.setLayoutY(r1y);
						stack1.getChildren().addAll(ring1, text1);
						MuudaRingiJaTekstiVärvSisenedesJaVäljudes(ring1, text1, Color.RED);

						Circle ring2 = new Circle(r2x, r2y, raadius, Color.BLACK);
						Text text2 = new Text (String.valueOf(väärtus2));
						text2.setFont(new Font(30));
						StackPane stack2 = new StackPane();
						stack2.setLayoutX(r2x);
						stack2.setLayoutY(r2y);
						stack2.getChildren().addAll(ring2, text2);
						MuudaRingiJaTekstiVärvSisenedesJaVäljudes(ring2, text2, Color.ORANGE);

						Circle ring3 = new Circle(r3x, r3y, raadius, Color.BLACK);
						Text text3 = new Text (String.valueOf(väärtus3));
						text3.setFont(new Font(30));
						StackPane stack3 = new StackPane();
						stack3.setLayoutX(r3x);
						stack3.setLayoutY(r3y);
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
						Label skoor = new Label("PUNKTID:");
						skoor.setStyle("-fx-font-size: 2em;");
						Label scoreLabel = new Label(Integer.toString(points));
						scoreLabel.textProperty().bind(score.asString());
						scoreLabel.setStyle("-fx-font-size: 2em;");

						//vbox stopperi paigutamiseks:
						VBox vb1 = new VBox(0);
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
								logifail.flush();
								score.set(points);
								double x = stseen1.getWidth();
								double y = stseen1.getHeight();
								r1x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r1y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								r2x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r2y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								r3x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r3y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);

								// ringide kattuvuse vältimine 
								while (kontrolliRingiMitteKattuvust(r1x,r1y,r2x,r2y,r3x,r3y)==false){
									r2x = (int)Math.round(Math.random()*(x-raadius*6)+150);
									r2y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
									r3x = (int)Math.round(Math.random()*(x-raadius*6)+150);
									r3y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								}

								stack1.setLayoutX(r1x);
								stack1.setLayoutY(r1y);
								ring1.setRadius(raadius);
								väärtus1 = juhuslikPunktisumma();
								text1.setText(String.valueOf(väärtus1));
								stack2.setLayoutX(r2x);
								stack2.setLayoutY(r2y);
								ring2.setRadius(raadius);
								väärtus2 = juhuslikPunktisumma();
								text2.setText(String.valueOf(väärtus2));
								stack3.setLayoutX(r3x);
								stack3.setLayoutY(r3y);
								ring3.setRadius(raadius);
								väärtus3 = juhuslikPunktisumma();
								text3.setText(String.valueOf(väärtus3));
							}
						});

						//hiirega kliki sündmused: kollase ringi liikumine ja punktiväärtus
						ring2.setOnMouseClicked(new EventHandler<MouseEvent>() {
							public void handle(MouseEvent me) {
								points+=väärtus2;
								logifail.println(String.valueOf(väärtus2));
								logifail.flush();
								score.set(points);
								double x = stseen1.getWidth();
								double y = stseen1.getHeight();
								r1x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r1y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								r2x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r2y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								r3x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r3y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);

								// ringide kattuvuse vältimine 
								while (kontrolliRingiMitteKattuvust(r1x,r1y,r2x,r2y,r3x,r3y)==false){
									r2x = (int)Math.round(Math.random()*(x-raadius*6)+150);
									r2y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
									r3x = (int)Math.round(Math.random()*(x-raadius*6)+150);
									r3y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								}
								stack1.setLayoutX(r1x);
								stack1.setLayoutY(r1y);
								ring1.setRadius(raadius);
								väärtus1 = juhuslikPunktisumma();
								text1.setText(String.valueOf(väärtus1));
								stack2.setLayoutX(r2x);
								stack2.setLayoutY(r2y);
								ring2.setRadius(raadius);
								väärtus2 = juhuslikPunktisumma();
								text2.setText(String.valueOf(väärtus2));
								stack3.setLayoutX(r3x);
								stack3.setLayoutY(r3y);
								ring3.setRadius(raadius);
								väärtus3 = juhuslikPunktisumma();
								text3.setText(String.valueOf(väärtus3));

							}
						});

						//hiirega kliki sündmused: rohelise ringi liikumine ja punktiväärtus
						ring3.setOnMouseClicked(new EventHandler<MouseEvent>() {
							public void handle(MouseEvent me) {
								points+=väärtus3;
								logifail.println(String.valueOf(väärtus3));
								logifail.flush();
								score.set(points);
								double x = stseen1.getWidth();
								double y = stseen1.getHeight();
								r1x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r1y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								r2x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r2y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								r3x = (int)Math.round(Math.random()*(x-raadius*6)+150);
								r3y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);

								// ringide kattuvuse vältimine 
								while (kontrolliRingiMitteKattuvust(r1x,r1y,r2x,r2y,r3x,r3y)==false){
									r2x = (int)Math.round(Math.random()*(x-raadius*6)+150);
									r2y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
									r3x = (int)Math.round(Math.random()*(x-raadius*6)+150);
									r3y = (int)Math.round(Math.random()*(y-raadius*4)+raadius*2);
								}

								stack1.setLayoutX(r1x);
								stack1.setLayoutY(r1y);
								ring1.setRadius(raadius);
								väärtus1 = juhuslikPunktisumma();

								text1.setText(String.valueOf(väärtus1));
								stack2.setLayoutX(r2x);
								stack2.setLayoutY(r2y);
								ring2.setRadius(raadius);
								väärtus2 = juhuslikPunktisumma();
								text2.setText(String.valueOf(väärtus2));
								stack3.setLayoutX(r3x);
								stack3.setLayoutY(r3y);
								ring3.setRadius(raadius);
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
								if (stseen1.getHeight()/stseen1.getWidth() > 3/4){
									raadius = (int)(stseen1.getHeight()/12);
								}
								else {
									raadius = (int)(stseen1.getWidth()/16);
								}
								ring1.setRadius(raadius);
								ring2.setRadius(raadius);
								ring3.setRadius(raadius);
								text1.setFont(new Font(raadius/2));
								text2.setFont(new Font(raadius/2));
								text3.setFont(new Font(raadius/2));
							}
						};

						ChangeListener<Number> listenerX = new ChangeListener<Number>() {
							@Override
							public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
								if (stseen1.getHeight()/stseen1.getWidth() < 3/4){
									raadius = (int)(stseen1.getHeight()/12);
								}
								else {
									raadius = (int)(stseen1.getWidth()/16);
								}
								ring1.setRadius(raadius);
								ring2.setRadius(raadius);
								ring3.setRadius(raadius);
								text1.setFont(new Font(raadius/2));
								text2.setFont(new Font(raadius/2));
								text3.setFont(new Font(raadius/2));
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
			vBox2.getChildren().addAll(tekst, juhend, startNupp);

			//stseeni loomine ja näitamine
			Scene stseen2 = new Scene(vBox2, 800, 200);

			start.setScene(stseen2);
			start.show();

			// Mängu lõpu akna ja tulemuse kuvamine
			timeSeconds.addListener((observable, oldTimeValue, newTimeValue) -> {
				if (newTimeValue.intValue()==0) {
					// uue aknasündmuse lisamine - mängu lõpetamine ja tulemuse kuvamine või uuesti alustamine
					// peaLava.setOnHiding(new EventHandler<WindowEvent>() {
					//public void handle(WindowEvent event) {
					// luuakse teine lava
					for (int i = 0; i < 10; i++) {
						skoorifail.print(parimNimi[i] + ";");
					}
					skoorifail.println("");
					skoorifail.println(points);
					skoorifail.flush();
					
					for (int i = 0; i < 10; i++) {
						System.out.print(parimNimi[i] + ";");
					}
					System.out.println("hhhh");
					System.out.println(points);
					
					peaLava.hide();
					Stage kusimus = new Stage();
					kusimus.setTitle("Mängu tulemus");
					logifail.println("Kokku: "+String.valueOf(points));
					logifail.flush();
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


		} catch (NimeErind e) {
			tekst.setText("Palun sisesta nimi korrektselt!");
		} finally {
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
