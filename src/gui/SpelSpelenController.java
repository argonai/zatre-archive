package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import domein.DomeinController;
import domein.Speler;
import domein.Vakje;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class SpelSpelenController extends VBox implements Initializable {
	private DomeinController dc;
	private Vakje[][] spelbord;

	@FXML
//	AnchorPane apBord = (AnchorPane) this.lookup("#apBord");
	BorderPane bpBord = (BorderPane) this.lookup("bpBord");
	GridPane gdpnSpeelbord = new GridPane();

	int selectedx = 0;
	int selectedy = 0;
	@FXML
	Label lblSpeler;
	@FXML
	GridPane gpSteentjes;


	int selectedSteentje;//al deze variabelen moeten hier staan aangezien andere functies er aan moeten
	boolean spelbezig = true;
	int selection = 0;
	boolean eersteSteentje = true;
	//debug functie
	boolean beurtBezig;
	int ronde = 0; //houd ronde bij voor bonus
	boolean spelAfgebroken = false;
	boolean isEerstBeurt = true;
	boolean eersteHand = true;
	int currentplayer = 0;

//	VBox root = (VBox) this.lookup("#rootVBox");
	public SpelSpelenController(DomeinController dc, String Difficulty) {
		Locale Defaultlocale = Locale.getDefault();
		ResourceBundle msgbundle = ResourceBundle.getBundle("resources.MessagesBundle", Defaultlocale);
		this.dc = dc;
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("SpelSpelen.fxml"));
		dc.startSpel(Difficulty);
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
//			e.printStackTrace();
		}
		Label lblSelected = (Label) this.lookup("#lblSelected");
		Label lblSteentjes = (Label) this.lookup("#lblSteentjes");
		lblSteentjes.setText("Stones");
		gdpnSpeelbord.setGridLinesVisible(true);

		gdpnSpeelbord.setPrefSize(450,450);
		gdpnSpeelbord.setMaxSize(450,450);
		gdpnSpeelbord.setMinSize(450,450);



		spelbord = this.dc.getSpel().getSpelBord();
		this.setSpelbord();

		bpBord.setLeft(gdpnSpeelbord);

//		this.UpdateSpeler();

		
//		GridPane gdpnScorebord = (GridPane) this.lookup("#gdpnScorebord");
//		gdpnScorebord.add(new Label("Test"), 0, 1);
	}
	public void UpdateSpeler(){
		Label lblSpeler = (Label) this.lookup("lblSpeler");
		lblSpeler.setText(dc.getSpel().getHuidigespeler().getNaam());
	}


	public void setSpelbord(){
		spelbord = this.dc.getSpel().getSpelBord();
		for (int i = 0; i < spelbord.length; i++) {
			for (int j = 0; j < spelbord.length; j++) { // we kunnen dezelfde variabele als limiet stellen aangezien het spelbord een vierkant is - KP
				// 0 = empty, 1 = White, 2 = grijs
				int finalJ = j; //x coord
				int finalI = i; //y coord

				if(spelbord[i][j].getKleur() == 2){
					Button btn = new Button(spelbord[i][j].getSteentje() == null ? "": Integer.toString(spelbord[i][j].getSteentje().getGetal()));

					btn.setFont(Font.font(7));
					btn.setStyle("-fx-background-color: gray");
					btn.setOnAction(actionEvent -> {
						this.setSelectedy(finalJ);
						this.setSelectedx(finalI);
					});
					btn.setPrefSize(450/ (double) spelbord.length,450/ (double) spelbord.length ); //grootte van spelbord hangt af van grootte buttons
//					btn.setPrefSize(30, 30);
					gdpnSpeelbord.add(btn, i, j);
				} else if (spelbord[i][j].getKleur()==1){
					Button btn = new Button(spelbord[i][j].getSteentje() == null ? "": Integer.toString(spelbord[i][j].getSteentje().getGetal()));
					btn.setFont(Font.font(7));
					btn.setStyle("-fx-background-color: white");


					btn.setOnAction(actionEvent -> {
						this.setSelectedy(finalJ);
						this.setSelectedx(finalI);
					});

					btn.setPrefSize(450/ (double) spelbord.length,450/ (double) spelbord.length ); //grootte van spelbord hangt af van grootte buttons
//					btn.setPrefSize(30, 30);
					gdpnSpeelbord.add(btn, i, j);
				} else if (spelbord[i][j].getKleur() ==0){
					Button btn = new Button("");
					btn.setFont(Font.font(7));
					btn.setStyle("-fx-background-color: black");

					btn.setPrefSize(450/ (double) spelbord.length,450/ (double) spelbord.length ); //grootte van spelbord hangt af van grootte buttons

//					btn.setPrefSize(30, 30);
					gdpnSpeelbord.add(btn, i, j);
				}
			}
		}
	}
	public void updateButton(Button output) {
		output.setText(Integer.toString(dc.getSpel().getSpelerhand().get(selectedSteentje).getGetal()));
	}
	public int getSelectedx() {
		return selectedx;
	}

	public void setSelectedx(int selectedx) {
		this.selectedx = selectedx;
	}

	public int getSelectedy() {
		return selectedy;
	}

	public void setSelectedy(int selectedy) {
		this.selectedy = selectedy;
	}

	public int getSelectedSteentje() {
		return selectedSteentje;
	}

	public void setSelectedSteentje(int selectedSteentje) {
		this.selectedSteentje = selectedSteentje;
	}

	/**
	 * Deze functie ververst de gridpane met steentjes om de actuele data te tonen
	 */
	public void refreshSteentjes(){

		if (gpSteentjes.getChildren().isEmpty()){
			for (int i = 0; i < dc.getSpel().getSpelerhand().size(); i++) {
				int finalI = i;
				Button btn = new Button(Integer.toString(dc.getSpel().getSpelerhand().get(i).getGetal()));
				btn.setOnAction(actionEvent -> {
					this.setSelectedSteentje(finalI);
				});
				gpSteentjes.add(btn,i,0);
			}
		} else {
			gpSteentjes.getChildren().clear();
			this.refreshSteentjes();
		}

	}

	/**
	 * Deze functie is zorgt dat spelers en beurten worden bijgehouden
	 */
	public void updateBeurt(){
		if (currentplayer < dc.getSelectedSpelers().size()-1){
			currentplayer++;

		}else {
			currentplayer = 0;
			ronde++;
		}
		lblSpeler.setText(dc.getSelectedSpelers().get(currentplayer).getNaam());
		dc.geefSteentjes(false);
		this.refreshSteentjes();
		dc.resetGelegd();

	}

	/**
	 * Deze functie vutl de scrollpane met het scoreblad
	 */
	public void updateScoreBoard(){

	}
	//todo
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Locale Defaultlocale = Locale.getDefault();
		ResourceBundle msgbundle = ResourceBundle.getBundle("resources.MessagesBundle", Defaultlocale);
		lblSpeler.setText(dc.getSelectedSpelers().get(currentplayer).getNaam());
		Button btnEndGame = (Button) this.lookup("#btnEndGame");
		Button btnTerug = new Button(msgbundle.getString("btnTerug"));
		Label lblUitleg = (Label) this.lookup("#lblUitleg");
		lblUitleg.setText(msgbundle.getString("msgFirstRound"));
		btnTerug.setOnAction(actionEvent -> {
			//b ug omdat spelerhand niet geregenerate word
//			System.out.println(currentplayer);
			dc.getSpel().steekSteentjeInPot(selectedSteentje);

			this.refreshSteentjes();
			if(gpSteentjes.getChildren().isEmpty()){
				updateBeurt();
			}
		});
		dc.geefSteentjes(eersteHand);
		Button btnLegSteentje = new Button(msgbundle.getString("btnPut"));
		btnLegSteentje.setOnAction(actionEvent -> {
			Button oldbtn = (Button) gdpnSpeelbord.getChildren().get(selectedx*(dc.getSpel().getSpelBord().length)+selectedy +1);
//			System.out.println(dc.getSpel().getSpelerhand());
			try {
				if(eersteSteentje){

					lblUitleg.setText(msgbundle.getString("msgExplanation"));
					dc.getSpel().legSteentjeEersteRonde(selectedx,selectedy,selectedSteentje);
					dc.updateScoreBlad(currentplayer,selectedSteentje,dc.getSpel().getSpelBord().length/2,dc.getSpel().getSpelBord().length/2, ronde);
					oldbtn.setText((Integer.toString(dc.getSpel().getSpelerhand().get(selectedSteentje).getGetal())));
					dc.getSpel().verwijderSteentje(selectedSteentje);
//					gdpnSpeelbord.getChildren().add(oldbtn);
					eersteSteentje = false;

				} else if (isEerstBeurt) {

					lblUitleg.setText(msgbundle.getString("msgExplanation"));
					dc.legSteentjeEersteBeurt(selectedx,selectedy,selectedSteentje);
					dc.updateScoreBlad(currentplayer,selectedSteentje,selectedx,selectedy,ronde);
					oldbtn.setText((Integer.toString(dc.getSpel().getSpelerhand().get(selectedSteentje).getGetal())));
					dc.getSpel().verwijderSteentje(selectedSteentje);
					isEerstBeurt = false;

				}else {

					dc.legSteentje(selectedx,selectedy,selectedSteentje);
					dc.updateScoreBlad(currentplayer,selectedSteentje,selectedx,selectedy,ronde);
					oldbtn.setText((Integer.toString(dc.getSpel().getSpelerhand().get(selectedSteentje).getGetal())));
					dc.getSpel().verwijderSteentje(selectedSteentje);

				}
			} catch (Error e){
				Alert alert = new Alert(Alert.AlertType.ERROR, String.format("%s", e.getMessage()));
				alert.show();
			}finally {
				this.refreshSteentjes();
				if(gpSteentjes.getChildren().isEmpty()){
					updateBeurt();
				}
				if(dc.getSpel().getPotLeeg()){
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("%s", dc.getSpel().bepaalWinnaar().getNaam()));
					alert.show();
				}
			}
		});
		GridPane gpButtons = (GridPane) this.lookup("#gpButtons");
		gpButtons.add(btnTerug,0,0);
		gpButtons.add(btnLegSteentje,1,0);


		// TODO: moment dat steentje gelegd is checken of hand leeg is en zo ronde beeindigen/ spel beeindigen

			this.refreshSteentjes();
//			while (beurtBezig){
//				lblSpeler.setText(s.getNaam());
//				this.refreshSteentjes();
//				if(dc.getSpel().getSpelerhand().isEmpty()){
//					beurtBezig = false;
//				}
//			}
		}


	}
//	public void updateGridpane(Button btn, int x, int y)


