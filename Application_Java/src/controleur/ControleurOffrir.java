package controleur;

import application.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControleurOffrir {

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
		// TODO Ajouter l'action de fare une offre
	}

	/**
	 * Cree le stage selon le prouit et le satge qui le contient
	 * 
	 * @param primaryStage Le stage contenant le tout.
	 * @param produit      Le produit a afficher
	 */
	public void setStage(Stage primaryStage, Produit produit) {
		nomProduit.setText(produit.getNomProduit());
		nomVendeur.setText(produit.getVendeur());
		dateCreation.setText(produit.getDate());
		categorie.setText(produit.getCategorie());
		prixDemande.setText(produit.getPrix());
		prixMaxActuel.setText(produit.getOMax());
		descriptionTextArea.setText(produit.getDescription());

	}

}
