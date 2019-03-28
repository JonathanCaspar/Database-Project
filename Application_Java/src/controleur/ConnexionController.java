package controleur;

import javafx.scene.control.TextField;

import java.awt.Window;

import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ConnexionController {

	@FXML
	private TextField idUser;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button connect;
	
	private String dummyID = "123";
//	private String dummyPW = "123";
	
	private User utilisateur;
		
	@FXML
	void authentification(ActionEvent event) {
		String id = idUser.getText();
		String pssw = password.getText();
		
		if(id.equals(dummyID)) {
			
			
			
			Stage stage = (Stage) connect.getScene().getWindow(); 
		    // do what you have to do 
		    stage.close();
		}
		
		//TODO aller chercher info utilisateur dans BD
		//creer objet User
		
		
	}
	
	public User getUser() {
		return this.utilisateur;
	}
		
}
