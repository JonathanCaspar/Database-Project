package application;

import controleur.MainControleur;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe App
 *
 * @author La Classe Manteau (Frédérick Bonnelly, Tanahel Huot-Roberge, Vincent
 *         Boulet et Tommy Montreuil)
 *
 */
public class App extends Application {
	
//	@FXML
//	private ControleurCatalogue ctlPageTitre = null;
	@FXML
	private MainControleur mainCntrl = null;
	

	public static void main(String[] args) {
		launch(args);
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
//			mainCntrl.initialize();
//			ctlPageTitre.setStage(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

