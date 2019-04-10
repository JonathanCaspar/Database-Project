package controleur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class NewAnnonceController {

	@FXML
	AnchorPane annoncePane;
	@FXML
	private Button soumettre;
	@FXML
	private TextField nomTF;
	@FXML
	private TextArea descriptionTA;
	@FXML
	private TextField prixTF;

	@FXML
	private ComboBox categoriesCB;

	private float estimation = 0;

	private String nom, description, prix;
	private int catID;

	/**
	 * Initialise la liste des categories en allant chercher les noms dans la BD
	 */
	@FXML
	public void initialize() {

		ArrayList<String> categories = new ArrayList<String>();

		QueriesItr QT = new QueriesItr("SELECT catname FROM " + DbAdapter.DB_TABLES[2] + ";");
		ResultSet rs = QT.getResultSet();

		try {
			if (rs != null) {
				while (QT.next()) {
					categories.add(rs.getString("catname"));
				}
			}
		} catch (SQLException e) {
			QT.quitter();
			e.printStackTrace();
		}

		categoriesCB.getItems().addAll(categories);

		annoncePane.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				soumettre();
			}
		});

	}

	/**
	 * Popup de l'expert, permet de donner manuellement l'estimation du prix
	 */
	public void expertPopup() {

		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("EXPERT");
		dialog.setHeaderText("Estimation du prix du produit.");
		dialog.setContentText("Entrez votre estimation :");

		Optional<String> resultat = dialog.showAndWait();

		if (resultat.isPresent()) {
			estimation = Float.parseFloat(resultat.get());
		}

	}

	/**
	 * Popup pour que le vendeur accepte ou non l'offre
	 */
	public void acceptPopup() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Estimation par l'expert");
		alert.setHeaderText("Votre produit a été estimé à " + this.estimation + "$.");
		alert.setContentText("Vous pouvez accepter ou refuser ce prix.");

		ButtonType accepter = new ButtonType("Accepter", ButtonData.CANCEL_CLOSE);

		ButtonType refuser = new ButtonType("Refuser", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(accepter, refuser);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == refuser) {
			this.estimation = Float.parseFloat(this.prix);
		}

	}

	public String conversion(String texte) {
		texte = texte.replace('\'', '\"');
			
		return texte;
	}

	public boolean validForm() {

		try {

			nom = conversion(nomTF.getText());
			description = conversion(descriptionTA.getText());
			prix = prixTF.getText();
			catID = categoriesCB.getSelectionModel().getSelectedIndex() + 1;

			if (nom.length() == 0 || description.length() == 0 || prix.length() == 0) {
				errorPopup("Données manquante", "Vous n'avez pas rempli tous les champs.");
				return false;
			}

			if (prix.matches("^-?\\d*(\\.\\d{1,2}){0,1}$")) {
				return true;
			} else {
				errorPopup("Format du prix", "Le prix saisi n'est pas au bon format. \n Rappel: 10.99 .");
				return false;
			}

		} catch (NullPointerException e) {

			e.printStackTrace();
			errorPopup("Données manquante", "Vous n'avez pas rempli tous les champs.");
			return false;
		}

	}

	@FXML
	void soumettre() {

		if (validForm()) {
			
			expertPopup();
			acceptPopup();

			try {

				Statement stmt = DbAdapter.con.createStatement();
				if (stmt != null) {
					stmt.executeUpdate(
							"INSERT INTO products (estimatedprice, sellingprice, sellerid, categoryid, description, name) VALUES"
									+ " ('" + this.estimation + "', '" + this.prix + "', "
									+ MainControleur.getUtilisateur() + ", " + this.catID + ", '" + this.description
									+ "','" + this.nom + "')");
					stmt.close();
				}
			} catch (SQLException e) {
				errorPopup("Probleme Base de Données", "L'insertion n'a pas pu être effectuée.");
				e.printStackTrace();
			}

			Stage stage = (Stage) soumettre.getScene().getWindow();
			stage.close();
		}

	}

	public float getEstimation() {
		return this.estimation;
	}

	public static void errorPopup(String typeError, String message) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("PROBLEME !");
		alert.setHeaderText(typeError);
		alert.setContentText(message);

		alert.showAndWait();
	}

}
