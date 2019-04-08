package controleur;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class InscriptionController {

	@FXML
	TextField usernameTF;
	@FXML
	PasswordField passwordTF;
	@FXML
	TextField nomTF;
	@FXML
	TextField prenomTF;
	@FXML
	TextField telTF;
	@FXML
	Button inscription;
    private String username, password, prenom, nom, tel;
	
    
    private PreparedStatement stmt = null;
//	private ResultSet rs = null;

	/**
	 * Instancie un statement et un ResultSet selon la query si tout ce passe bien
	 * 
	 * @param query La query a execute
	 * @throws SQLException 
	 */
	public void insertUser() throws SQLException {
		try {
			stmt = DbAdapter.con.prepareStatement("INSERT INTO users ( username, password, firstname, lastname, phonenumber) VALUES (?,?,?,?,?,?)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, prenom);
			stmt.setString(4, nom);
			stmt.setString(5, tel);
			
			stmt.execute();
			
		} catch (SQLException e) {
			stmt.close();
			e.printStackTrace();
		}
	}
    
    
    
    public boolean validForm() {
    	
    	try {
			
			username = usernameTF.getText();
			password = passwordTF.getText();
			prenom = prenomTF.getText();
			nom = nomTF.getText();
			tel = telTF.getText();
			
			if(tel.matches("^\\d{3}-\\d{3}-\\d{4}$")) {
				return true;
			}
			else {
				errorPopup("Format numéro de téléphone", "Le numéro saisi n'est pas au bon format \n Rappel: 123-456-7777.");
				return false;
			}
		
		
		}catch(NullPointerException e) {
			
			e.printStackTrace();
			errorPopup("Données manquante", "Vous n'avez pas rempli tous les champs.");
			return false;
		}
    	
    }
    
	@FXML
	public void inscrire() {
		
		if(validForm()) {
			
//			try {
//				insertUser();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			QueriesItr QT = new QueriesItr("INSERT INTO users (username, password, firstname, lastname, phonenumber) VALUES"+
					" ('"+ username+"', '"+password+"', '"+prenom+"', '"+nom+"', '"+tel+"')");
			QT.quitter();
		}
	}
	
	public static void errorPopup(String typeError, String message) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("PROBLEME !");
		alert.setHeaderText(typeError);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
}
