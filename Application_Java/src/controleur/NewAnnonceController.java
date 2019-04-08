package controleur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class NewAnnonceController {
	
	@FXML
	private Button soumettre;
	@FXML
	private TextField nomTF;
	@FXML
	private TextField descriptionTF;
	@FXML
	private TextField prixTF;
	
	@FXML
	private ComboBox categoriesCB;
	
	private float estimation = 0;
	
	/**
	 * Initialise la liste des categories en allant chercher les noms dans la BD
	 */
	@FXML
	public void initialize() {
		
		ArrayList<String> categories = new ArrayList<String>();

		QueriesItr QT = new QueriesItr("SELECT catname FROM " + DbAdapter.DB_TABLES[2] + ";");
		ResultSet rs = QT.getResultSet();
		
		try {
			if(rs != null) {
				while (QT.next()) {
					categories.add(rs.getString("catname"));
				}
			}
		} catch (SQLException e) {
			QT.quitter();
			e.printStackTrace();
		}
		
		categoriesCB.getItems().addAll(categories);
		
	}
	
	/**
	 * Popup de l'expert, permet de donner manuellement l'estimation du prix
	 */
	public void expertPopup() {
		
		TextInputDialog dialog = new TextInputDialog("walter");
		dialog.setTitle("EXPERT");
		dialog.setHeaderText("Estimation du prix du produit.");
		dialog.setContentText("Entrez votre estimation :");
		
		Optional<String> resultat = dialog.showAndWait();
		
		if (resultat.isPresent()){
			estimation = Float.parseFloat(resultat.get());
		}
		
	}
	/**
	 * Popup pour que le vendeur accepte ou non l'offre
	 */
	public void acceptPopup() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Estimation par l'expert");
		alert.setHeaderText("Votre produit a été estimé à "+ estimation + "$.");
		alert.setContentText("Vous pouvez accepter ou refuser ce prix.");

		ButtonType accepter = new ButtonType("Accepter");
	
		ButtonType refuser = new ButtonType("Refuser", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(accepter, refuser);
		
		alert.showAndWait();
	}
	
	@FXML 
	void soumettre() {
		
		expertPopup();
		acceptPopup();
		
	}
	
	public float getEstimation() {
		return this.estimation;
	}
	

}
