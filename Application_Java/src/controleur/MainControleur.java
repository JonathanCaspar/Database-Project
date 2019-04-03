package controleur;

import application.User;
import controleur.CatalogueController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainControleur {
	
	private User utilisateur = null;
	private boolean userLoged = false;
	@FXML
	private MenuItem inscription;
	@FXML
	private MenuItem connexion;
	@FXML
	private MenuItem deconnexion;
	
	@FXML
	private CatalogueController catalogueController;
	@FXML
	private AnnoncesController annoncesController;
	@FXML
	private ConnexionController connexionController;
	
	@FXML
	public void initialize() {
		this.catalogueController.setStage();
	}
	
	@FXML
	void goToLogin() {
		
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			Scene scene = new Scene(loader.load());

			connexionController = (ConnexionController) loader.getController();

			primaryStage.setScene(scene);
			primaryStage.setTitle("AUTHENTIFICATION");
			primaryStage.showAndWait();
			
			//recupère ici l'utilisateur authentifié
			setUtilisateur(connexionController.getUser());
			userLoged = true;
		
			deconnexion.setVisible(true);
			connexion.setVisible(false);
			inscription.setVisible(false);
			
			
		
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	public void setUtilisateur(User utilisateur) {
		this.utilisateur = utilisateur;
	}
}
