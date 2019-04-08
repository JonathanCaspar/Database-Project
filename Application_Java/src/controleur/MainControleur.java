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
	private static int userID = 1;
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
	public void initialize() {
		this.catalogueController.setStage();
		
	}
	
	@FXML
	void toOtherTab() {
		
		annoncesController.setTable();
		
		
//		if(userLoged == false) {
//		
//			goToLogin();
//			mainPane.getSelectionModel().select(catalogueT);
//		}
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
			
			
			//recupère ici l'ID de l'utilisateur authentifié
			if(connexionController.isLoged()) {
				setUtilisateur(connexionController.getUserID());
				System.out.println("userID in MAIN = " + userID);
				userLoged = true;
				deconnexion.setVisible(true);
				connexion.setVisible(false);
				inscription.setVisible(false);
				
//				this.annoncesController.setUtilisateur(this.userID);
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	public static void setUtilisateur(int userid) {
//		this.utilisateur = utilisateur;
		userID = userid;
	}
	
	public static int getUtilisateur() {
//		this.utilisateur = utilisateur;
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
