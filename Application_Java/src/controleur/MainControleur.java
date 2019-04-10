package controleur;

import application.User;
import controleur.CatalogueController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainControleur {
	
	private User utilisateur = null;
	private static int userID = -1;
	private boolean userLoged = false;
	@FXML
	private MenuItem inscription;
	@FXML
	private MenuItem connexion;
	@FXML
	private MenuItem deconnexion;
	@FXML
	private TabPane mainPane;
	@FXML
	private Tab catalogueT;
	@FXML
	private Tab annoncesT;
	@FXML
	private Tab achatsT;
	
	@FXML
	private CatalogueController catalogueController;
	@FXML
	private AnnoncesController annoncesController;
	@FXML
	private ConnexionController connexionController;
	@FXML
	private InscriptionController inscriptionController;
	@FXML
	private AchatsController achatsController;
	
	@FXML
	public void initialize() {
		//this.catalogueController.setStage();
	}
	
	/**
	 * Verifie si l'utilisateur est authentifié pour accéder à ses annonces.
	 * Si non connecté, propose de d'authentifié
	 */
	@FXML
	void toAnnonces() {
		
		if(userID < 0) {
			goToLogin();
			mainPane.getSelectionModel().select(catalogueT);
		}
		else {
			annoncesController.setTableAnnonces();
		}
	}
	
	/**
	 * Verifie si l'utilisateur est authentifié pour accéder à ses achats
	 * Si non connecté, propose de d'authentifié
	 */
	@FXML
	void toAchats() {
		
		if(userID < 0) {
			goToLogin();
			mainPane.getSelectionModel().select(catalogueT);
		}else {
			achatsController.setTableOffre();
			achatsController.setTableAchat();
		}
	}
	
	/**
	 * Deconnecte l'utilisateur en mettant le userID à -1
	 * Retourne au catalogue
	 */
	@FXML
	void deconnexion() {
		
		mainPane.getSelectionModel().select(catalogueT);
		setUtilisateur(-1);
		deconnexion.setVisible(false);
		connexion.setVisible(true);
		inscription.setVisible(true);
	
		this.catalogueController.initialize();
	}
	
	/**
	 * Ouvre la page permettant à l'utilisateur de s'authentifier
	 * Récupère le nouvel userID
	 */
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
			
			
			//recupère ici l'ID de l'utilisateur authentifié
			if(connexionController.isLoged()) {
				setUtilisateur(connexionController.getUserID());
				System.out.println("userID in MAIN = " + userID);
				
				deconnexion.setVisible(true);
				connexion.setVisible(false);
				inscription.setVisible(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void setUtilisateur(int userid) {
		userID = userid;
	}
	
	public static int getUtilisateur() {

		return userID;
	}
	
	@FXML
	void inscription() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inscription.fxml"));
			Scene scene = new Scene(loader.load());

			inscriptionController = (InscriptionController) loader.getController();

			primaryStage.setScene(scene);
			primaryStage.setTitle("Inscription");
			primaryStage.showAndWait();
			
			//recupère ici l'utilisateur authentifié
//			setUtilisateur(connexionController.getUser());
//			userLoged = true;
		
//			deconnexion.setVisible(true);
//			connexion.setVisible(false);
//			inscription.setVisible(false);
			
			
		
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
