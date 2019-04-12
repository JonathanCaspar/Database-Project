package controleur;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * Classe ConnexonController, definit le controleur pour la connection de user
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class ConnexionController {

	@FXML
	private TextField usernameTF;

	@FXML
	private PasswordField passwordPF;

	@FXML
	private Button connect;

	@FXML
	private AnchorPane loginPane;
	
	private int userID;

	private boolean isloged = false;

	private String username, password;

	@FXML
	public void initialize() {

		loginPane.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				authentification();
			}
		});
	}

	public boolean validForm() {

		try {

			username = usernameTF.getText();
			password = passwordPF.getText();

			if (username.length() == 0 || password.length() == 0) {
				errorPopup("Données manquantes.", "Vous n'avez pas rempli tous les champs.");
				return false;
			}

			return true;

		} catch (NullPointerException e) {
			errorPopup("Données manquantes.", "Vous n'avez pas rempli tous les champs.");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Verifie si l'assocation username/password est dans la base de données
	 * 
	 */
	@FXML
	void authentification() {

		if (validForm()) {
			QueriesItr QT = new QueriesItr("SELECT userid FROM " + DbAdapter.DB_TABLES[0] + " WHERE username = '"
					+ username + "' AND password = '" + password + "' ;");
			ResultSet rs = QT.getResultSet();

			try {

				if (rs.next()) {
					System.out.println("userID = " + rs.getInt("userid"));
					userID = rs.getInt("userid");
					this.isloged = true;

					Stage stage = (Stage) connect.getScene().getWindow();
					stage.close();
				} else {

					QT.quitter();
					errorPopup("Problème identification",
							"Cette association nom d'utilisateur/mot de passe n'existe pas.");
				}

			} catch (SQLException e) {

				QT.quitter();
				errorPopup("Problème identification", "Cette association nom d'utilisateur/mot de passe n'existe pas.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Popup d'erreur customizable
	 * 
	 * @param typeError
	 * @param message
	 */
	public static void errorPopup(String typeError, String message) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("PROBLEME !");
		alert.setHeaderText(typeError);
		alert.setContentText(message);

		alert.showAndWait();
	}


	public int getUserID() {
		return this.userID;
	}

	public boolean isLoged() {
		return this.isloged;
	}

}
