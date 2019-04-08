package controleur;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.User;
import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ConnexionController {

	@FXML
	private TextField usernameTF;
	
	@FXML
	private PasswordField passwordPF;
	
	@FXML
	private Button connect;
	
	private User utilisateur;
	
	private int userID;
		
	@FXML
	void authentification(ActionEvent event) {
		String username = usernameTF.getText();
		String password = passwordPF.getText();
		
		
		QueriesItr QT = new QueriesItr("SELECT userid FROM " + DbAdapter.DB_TABLES[0] + 
				" WHERE username = " +username + " AND password = "+ password+" ;");
		ResultSet rs = QT.getResultSet();
		
		
		try {
			userID = rs.getInt("userid");
		} catch (SQLException e) {
			QT.quitter();
			errorPopup("Problème identification", "Cette association nom d'utilisateur/mot de passe n'existe pas.");
			e.printStackTrace();
		}
			
			
			
			Stage stage = (Stage) connect.getScene().getWindow(); 
		    
		    stage.close();
		
		
		
	}
	
	
	public static void errorPopup(String typeError, String message) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("PROBLEME !");
		alert.setHeaderText(typeError);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	public User getUser() {
		return this.utilisateur;
	}
		
}
