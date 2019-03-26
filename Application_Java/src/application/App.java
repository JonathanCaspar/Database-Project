package application;

import controleur.ControleurAchat;
import javafx.application.Application;
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
	private ControleurAchat ctlPageTitre = null;

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_achat.fxml"));
			Scene scene = new Scene(loader.load());

			ctlPageTitre = (ControleurAchat) loader.getController();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Spend money");
			primaryStage.show();
			ctlPageTitre.setStage(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}

