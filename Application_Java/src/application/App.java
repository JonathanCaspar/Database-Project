package application;

import controleur.CatalogueController;
import controleur.MainControleur;
import dbstuff.DbAdapter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe Principale
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class App extends Application {

	@FXML
	private CatalogueController ctlPageTitre = null;
	@FXML
	private MainControleur mainCntrl = null;

	public static void main(String[] args) {
		DbAdapter db = new DbAdapter("jdbc:postgresql://postgres.iro.umontreal.ca:5432/casparjo", "casparjo_app",
				"projetdb2935");

		db.connecter();
		launch(args);
		db.deconnecter();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
			Scene scene = new Scene(loader.load());

			mainCntrl = (MainControleur) loader.getController();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Spend money");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
