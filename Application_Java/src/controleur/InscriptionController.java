package controleur;


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
