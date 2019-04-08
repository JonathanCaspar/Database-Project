package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AnnoncesController {

	@FXML
	private Button annoncer;
	@FXML
	private NewAnnonceController newAnnonceController;
	
//	private int userID;
	
//	public void setUtilisateur(int userID) {
//		this.userID = userID;
//	}
	
	
	/**
	 * Ouvre la fenetre de creation d'annonce
	 */
	@FXML
	void creerAnnonce() {
	
		try {

			// recuperer la stage, change la scene
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/creationAnnonce.fxml"));
			Scene scene = new Scene(loader.load());

			newAnnonceController = (NewAnnonceController) loader.getController();

			primaryStage.setScene(scene);
			primaryStage.setTitle("Nouvelle annonce");
			primaryStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	
}
