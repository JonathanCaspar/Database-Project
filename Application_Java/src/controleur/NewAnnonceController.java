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

/**
 * Classe NewAnnonceController, definit le controleur pour la vue de creation
 * d'annonce
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
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
	private ComboBox<String> categoriesCB;

	@FXML
	private ComboBox<String> mainCategoriesCB;

	private float estimation = 0;

	private String nom, description, prix, catName, mainCatName;

	/**
	 * Initialise la liste des categories principales en allant chercher les noms
	 * dans la BD
	 */
	@FXML
	public void initialize() {

		QueriesItr QT = new QueriesItr("SELECT maincatname FROM " + DbAdapter.DB_TABLES[1] + " ORDER BY maincatname;");
		ResultSet rs = QT.getResultSet();

		try {
			if (rs != null) {
				while (QT.next()) {
					mainCategoriesCB.getItems().add(rs.getString("maincatname"));
				}
			}
		} catch (SQLException e) {
			QT.quitter();
			e.printStackTrace();
		}
		mainCategoriesCB.setOnAction(e -> {
			catName = null;
			mainCatName = mainCategoriesCB.getSelectionModel().getSelectedItem();

			if (mainCatName != null) {
				categoriesCB.setDisable(false);
				// On retrouve le numero de la categorie principale
				String mainCatNumber = "WITH mainCatNumber AS (SELECT maincatid FROM " + DbAdapter.DB_TABLES[1]
						+ " WHERE maincatname = '" + conversion(mainCatName) + "')\n";
				// On trouve les sous categorie de cette categorie
				QueriesItr querryIter = new QueriesItr(mainCatNumber + "SELECT catname FROM " + DbAdapter.DB_TABLES[2]
						+ " NATURAL JOIN mainCatNumber ORDER BY catname;");

				construireCategorieCB(querryIter);
			}
		});

		annoncePane.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				soumettre();
			}
		});
	}

	/**
	 * Construit la liste de categorie selon l'ensemble donne dans QT
	 * 
	 * @param QT L'Iterable de requete
	 */
	private void construireCategorieCB(QueriesItr QT) {
		ResultSet rs = QT.getResultSet();
		categoriesCB.getItems().clear();
		try {
			if (rs != null) {
				while (QT.next()) {
					categoriesCB.getItems().add(rs.getString("catname"));
				}
			}
		} catch (SQLException e) {
			QT.quitter();
			e.printStackTrace();
		}
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
	 * 
	 * @return
	 */
	public boolean acceptPopup() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Estimation par l'expert");
		alert.setHeaderText("Votre produit a été estimé à " + this.estimation + "$.");
		alert.setContentText("Vous pouvez accepter ou refuser ce prix.");

		ButtonType accepter = new ButtonType("Accepter", ButtonData.CANCEL_CLOSE);

		ButtonType refuser = new ButtonType("Refuser", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(accepter, refuser);

		Optional<ButtonType> result = alert.showAndWait();

		return !(result.get() == refuser);
	}

	public String conversion(String texte) {
		return texte.replace("'", "''");
	}

	public boolean validForm() {

		try {

			nom = conversion(nomTF.getText());
			description = conversion(descriptionTA.getText());
			prix = prixTF.getText();
			mainCatName = mainCategoriesCB.getSelectionModel().getSelectedItem();
			catName = categoriesCB.getSelectionModel().getSelectedItem();

			if (nom.length() == 0 || description.length() == 0 || prix.length() == 0 || catName == null
					|| mainCatName == null) {
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

	private int getCategoryNbr() {
		int r = 0;
		if (mainCatName != null && catName != null) {

			// On retrouve le numero de la categorie principale
			String mainCatNumber = "WITH mainCatNumber AS (SELECT maincatid FROM " + DbAdapter.DB_TABLES[1]
					+ " WHERE maincatname = '" + conversion(mainCatName) + "'),\n";

			// On trouve les categories avec le nom de la categorie selectionne
			String catWithGoodName = "catwithgoodName AS (SELECT * FROM " + DbAdapter.DB_TABLES[2]
					+ " WHERE catname = '" + conversion(catName) + "')\n";

			// On trouve les sous categorie de cette categorie
			QueriesItr querryIter = new QueriesItr(
					mainCatNumber + catWithGoodName + "SELECT catid FROM catwithgoodName NATURAL JOIN mainCatNumber;");
			ResultSet rs = querryIter.getResultSet();

			if (rs != null) {
				try {
					querryIter.next();
					r = rs.getInt("catid");
					querryIter.quitter();
				} catch (SQLException e) {
					errorPopup("Probleme Base de Données", "L'insertion n'a pas pu être effectuée.");
					querryIter.quitter();
					e.printStackTrace();
				}
			}
		}
		return r;
	}

	@FXML
	void soumettre() {

		if (validForm()) {

			expertPopup();
			if (acceptPopup()) {

				try {

					Statement stmt = DbAdapter.con.createStatement();
					if (stmt != null) {
						stmt.executeUpdate(
								"INSERT INTO products (estimatedprice, sellingprice, sellerid, categoryid, description, name) VALUES"
										+ " ('" + this.estimation + "', '" + this.prix + "', "
										+ MainControleur.getUtilisateur() + ", " + getCategoryNbr() + ", '"
										+ this.description + "','" + this.nom + "')");
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
