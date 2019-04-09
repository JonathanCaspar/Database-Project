package controleur;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import application.Produit;
import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class ControleurOffrir {

	private Produit produit = null;

	private Stage stage = null;

	@FXML
	private Label nomProduit;

	@FXML
	private TextField offreTextField;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private Button offreButton;

	@FXML
	private Label nomVendeur;

	@FXML
	private Label dateCreation;

	@FXML
	private Label categorie;

	@FXML
	private Label prixDemande;

	@FXML
	private Label prixMaxActuel;

	@FXML
	void offreButtonAction(ActionEvent event) {
		Statement stmt = null;
		if (validForm()) {
			try {
				stmt = DbAdapter.con.createStatement();
				if (stmt != null) {
					Float prix = Float.valueOf(offreTextField.getText());

					if (prix < produit.getEstimation()) {
						// Si le prix offert est pus petit que l'estimation on fait juste ajouter
						// l'offre
						stmt.executeUpdate("INSERT INTO offers (buyerid, productid, price) VALUES" + " ("
								+ MainControleur.getUtilisateur() + ", " + produit.getRefId() + ", '" + prix + "')");
						stmt.close();
						acceptPopup(prix, "L'offre a été ajoutée à la liste.");

					}

					else {
						
						
						// Si l'offre est plus grande ou egale a l'estimation la vente est automatique
						stmt.executeUpdate(
								"INSERT INTO soldproducts (name, description, sellerid, buyerid, categoryid, estimatedprice, sellingprice, soldprice) "
										+ "SELECT name, description, sellerid,'" + MainControleur.getUtilisateur()
										+ "', categoryid, estimatedprice, sellingprice, '" + prix + "'"
										+ " FROM products WHERE refid = " + produit.getRefId());
						stmt.executeUpdate("DELETE FROM products WHERE refid = " + produit.getRefId());
						stmt.close();
						acceptPopup(prix, "Votre offre a été accepté. La vente est conclue.");

					}
				}
			} catch (SQLException e) {
				NewAnnonceController.errorPopup("Probleme Base de Données", "L'insertion n'a pas pu être effectuée.");
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Popup disant que l'offre de valeur prix a bien ete effectuee sur le produit.
	 * 
	 * @param prix Le prix offert.
	 * @param text Le texte de confirmation
	 */
	public void acceptPopup(float prix, String text) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Offre");
		alert.setHeaderText("Votre offre de : " + String.format("%.2f", prix) + " $ pour " + produit.getNomProduit()
				+ " a été éffectué avec succès.");
		alert.setContentText(text);

		ButtonType accepter = new ButtonType("OK", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(accepter);

		alert.showAndWait();
		stage.close();

	}

	/**
	 * Valide si l'offre est bien une offre valide et supperieure aux offres
	 * actuelle
	 * 
	 * @return Vrai, si l'offre est valide.
	 */
	public boolean validForm() {

		try {
			String prix = offreTextField.getText();

			if (!prix.trim().isEmpty() && prix.matches("^-?\\d*(\\.\\d{2}){0,1}$")) {

				if (Float.valueOf(prix) <= produit.getValueOMax()) {
					NewAnnonceController.errorPopup("Prix invalide",
							"Le prix saisi est sous l'offre maximale actuelle.");
					return false;
				}
				return true;
			} else {
				NewAnnonceController.errorPopup("Format du prix",
						"Le prix saisi n'est pas au bon format. \n Rappel: 10.99 .");
				return false;
			}

		} catch (NullPointerException e) {

			e.printStackTrace();
			NewAnnonceController.errorPopup("Donnée manquante", "Vous n'avez pas rempli tous les champs.");
			return false;
		}

	}

	/**
	 * Cree le stage selon le produit et le stage qui le contient
	 * 
	 * @param primaryStage Le stage contenant le tout.
	 * @param produit      Le produit a afficher
	 */
	public void setStage(Stage primaryStage, Produit produit) {
		this.produit = produit;

		nomProduit.setText(produit.getNomProduit());
		nomVendeur.setText(produit.getVendeur());
		dateCreation.setText(produit.getDate());
		categorie.setText(produit.getCategorie());
		prixDemande.setText(produit.getPrix());
		prixMaxActuel.setText(produit.getOMax());
		descriptionTextArea.setText(produit.getDescription());

		stage = primaryStage;

		// Si un user est connecter il peut faire une offre.
		if (MainControleur.getUtilisateur() >= 0) {
			offreButton.setDisable(false);
		}

	}

}
