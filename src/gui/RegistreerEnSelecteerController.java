package gui;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import domein.DomeinController;
import domein.Speler;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistreerEnSelecteerController extends VBox implements Initializable {
	int spelerGebjr; //geeft issues in inner class indien niet global. Is niet het properste, maar het werkt - KP
	String Difficulty;

	@FXML
	ChoiceBox<String> DifficultyBox;
	String[] difficulties = {"Easy", "Normal", "Hard"};
	public RegistreerEnSelecteerController() {
		Locale Defaultlocale = Locale.getDefault();
		ResourceBundle msgbundle = ResourceBundle.getBundle("resources.MessagesBundle", Defaultlocale);

		System.out.println(Locale.getDefault());
		DomeinController dc = new DomeinController();
		ArrayList<Speler> unselectedSpelers = (ArrayList<Speler>)dc.getSpelers();
		ArrayList<Speler> selectedSpelers = new ArrayList<Speler>();

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("RegistreerEnSelecteer.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Label lblNm = (Label) this.lookup("#lblNm");
		lblNm.setText(msgbundle.getString("Name"));
		Label lblBD = (Label) this.lookup(("#lblBD"));
		lblBD.setText((msgbundle.getString("Birthday")));
		Label lblList = (Label) this.lookup("#lblList");
		lblList.setText(msgbundle.getString("Players"));
		Label lblSelectedList = (Label) this.lookup("#lblSelectedList");
		lblSelectedList.setText(msgbundle.getString("selPlayers"));
		Label lblDifficulty = (Label) this.lookup("#lblDifficulty");
		lblDifficulty.setText("Difficulty");
		ListView lsvSelected = (ListView) this.lookup("#lsvSelected");
		fillLSV(lsvSelected, selectedSpelers);
		ListView lsvUnselected = (ListView) this.lookup("#lsvUnselected");
		fillLSV(lsvUnselected, unselectedSpelers);

		TextField txfSpelernm = (TextField) this.lookup("#txfSpelernm");
		TextField txfSpelerGebjr = (TextField) this.lookup("#txfSpelerGebjr");
		Button btnRegistreerSpeler = (Button) this.lookup("#btnRegistreerSpeler");
		btnRegistreerSpeler.setText(msgbundle.getString("Register"));
		btnRegistreerSpeler.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(txfSpelernm.getText().length() < 5){
					Alert alert = new Alert(AlertType.ERROR, msgbundle.getString("AlertName"));
					alert.show();
				} else if (Integer.parseInt(txfSpelerGebjr.getText()) > Calendar.getInstance().get(Calendar.YEAR) - 6) {
					Alert alert = new Alert(AlertType.ERROR, msgbundle.getString("AlertYear"));
					alert.show();
				}else {
					try {
						spelerGebjr = Integer.parseInt(txfSpelerGebjr.getText());
						dc.registreer(txfSpelernm.getText(),spelerGebjr);
						txfSpelerGebjr.clear();
						txfSpelernm.clear();
						Alert alert = new Alert(AlertType.CONFIRMATION, msgbundle.getString("RegisterSuccess"),ButtonType.OK);
						//TODO: fix update
						//TODO: arme speler werkt niet
						ArrayList<Speler> unselectedSpelers = (ArrayList<Speler>) dc.getSpelers();
						ArrayList<Speler> selectedSpelers = new ArrayList<Speler>();

						fillLSV(lsvSelected, selectedSpelers);
						fillLSV(lsvUnselected, unselectedSpelers);
						alert.show();
					}
					catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR, "Gelieve correcte gegevens in te vullen: \n"
								+ "Naam moet minsten 5 karakters bevatten\n"
								+ "Geboortejaar moet realistisch zijn en numeriek\n"
								+ "De combinatie geboortedatum en naam mag niet al bestaan",ButtonType.OK);
						alert.show();
					}
				}

			}
		});
		
		
		
		
		Button btnSelectSpeler = (Button) this.lookup("#btnSelectSpeler");
		btnSelectSpeler.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(unselectedSpelers.get(lsvUnselected.getSelectionModel().getSelectedIndex()).getAantalSpeelbeurten()>0) {
					try {
						selectedSpelers.add(unselectedSpelers.get(lsvUnselected.getSelectionModel().getSelectedIndex()));
						unselectedSpelers.remove(lsvUnselected.getSelectionModel().getSelectedIndex());
						fillLSV(lsvSelected, selectedSpelers);
						fillLSV(lsvUnselected, unselectedSpelers);
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR, msgbundle.getString("NoItemLeft"), ButtonType.OK);
						alert.show();
					}
				}
				else {
					Alert alert = new Alert(AlertType.ERROR, msgbundle.getString("NoCredits"));
					alert.show();
				}
			}
		});
		
		Button btnDeselectSpeler = (Button) this.lookup("#btnDeselectSpeler");
		btnDeselectSpeler.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					unselectedSpelers.add(selectedSpelers.get(lsvSelected.getSelectionModel().getSelectedIndex()));
					selectedSpelers.remove(lsvSelected.getSelectionModel().getSelectedIndex());
					fillLSV(lsvUnselected, unselectedSpelers);
					fillLSV(lsvSelected, selectedSpelers);
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR, msgbundle.getString("NoItemRight"), ButtonType.OK);
					alert.show();
				}
				
			}
		});

		Button btnStartSpel = (Button) this.lookup("#btnStartSpel");
		btnStartSpel.setText(msgbundle.getString("Start"));
		btnStartSpel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stage currentStage = (Stage) btnStartSpel.getScene().getWindow();
				currentStage.close();//TODO: observablelist naar list<Speler>
//				List<Speler> temp = new ArrayList<Speler>(lsvSelected.getItems());
				dc.setSelectedSpelers(selectedSpelers);
				SpelSpelenController speelParent = new SpelSpelenController(dc, Difficulty);
				Scene speelScene = new Scene(speelParent);
				Stage speelStage = new Stage();
				speelStage.setTitle("Zatre-spelen");
				speelStage.setScene(speelScene);
				speelStage.show();
			}
		});

	}

	public void fillLSV(ListView lsv, ArrayList<Speler> arrayList) {
		//Deze functie is gewoon een snelle manier om een lsv te vullen zonder dat ik dezelfde for-loop 7 keer moet schrijven -KP
		lsv.getItems().clear();
		for (Speler speler : arrayList) {
			lsv.getItems().add(speler.getNaam());
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		DifficultyBox.getItems().addAll(difficulties);
		DifficultyBox.setOnAction(this::setDifficulty);

	}

	public void setDifficulty(ActionEvent event){
		this.Difficulty = DifficultyBox.getValue();
	}
}