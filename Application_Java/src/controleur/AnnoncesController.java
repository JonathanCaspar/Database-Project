package controleur;

import application.Offre;
import application.Produit;
import dbstuff.QueriesItr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AnnoncesController {

	@FXML
	private Button annoncer;
	@FXML
	private NewAnnonceController newAnnonceController;
	
	@FXML
	private TableView<Produit> objetsView;
	@FXML
	private TableColumn<Produit, String> produits;
	@FXML
	private TableColumn<Produit, String> prix;
	@FXML
	private TableColumn<Produit, String> date;
	@FXML
	private TableColumn<Produit, String> categorie;
	
	@FXML
	private TableView<Offre> offreView;
	@FXML
	private TableColumn<Offre, String> acheteur;
	@FXML
	private TableColumn<Offre, String> prixO;
	@FXML
	private TableColumn<Offre, String> dateO;
	@FXML
	private Label prixExpert;
	private int productid;

	
	/**
	 * Creer une table contenant la liste de produit passer en paramettre.
	 * 
	 * @param table La table de produit.
	 */
	public void creatTableAnnonces(Iterable<Produit> table) {
		objetsView.getItems().clear();
		for (Produit o : table) {
			objetsView.getItems().add(o);
		}

		objetsView.getSelectionModel().selectedItemProperty().addListener((observable, old_val, new_val) -> {
		    
			productid = objetsView.getSelectionModel().getSelectedItem().getRefId();
			prixExpert.setText(""+ objetsView.getSelectionModel().getSelectedItem().getEstimation() + " $");
			setTableOffres();
		});
	}
	
	private void creatTablecolmnsAnnonces() {
		produits.setCellValueFactory(new PropertyValueFactory("nomProduit"));
		prix.setCellValueFactory(new PropertyValueFactory("prix"));
		date.setCellValueFactory(new PropertyValueFactory("date"));
		categorie.setCellValueFactory(new PropertyValueFactory("categorie"));

	}

	public void setTableAnnonces() {
		QueriesItr qt = new QueriesItr(
				"WITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername, date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products WHERE sellerid = '"+MainControleur.getUtilisateur()+"')\n" + 
				" SELECT refid, name, description, sellingprice, sellername, date, maxoffer, catname, date, estimatedprice  FROM allProducts JOIN categories ON categoryid = catid;");
		creatTablecolmnsAnnonces();
		creatTableAnnonces(QueriesItr.iteratorProduit(qt));
	}
	
	/**
	 * Creer une table contenant la liste de produit passer en paramettre.
	 * 
	 * @param table La table de produit.
	 */
	public void creatTableOffres(Iterable<Offre> table) {
		offreView.getItems().clear();
		for (Offre o : table) {
			offreView.getItems().add(o);
		}

		offreView.getSelectionModel().selectedItemProperty().addListener((observable, old_val, new_val) -> {
		    
		
		});
	}
	
	private void creatTablecolmnsOffres() {
		acheteur.setCellValueFactory(new PropertyValueFactory("buyer"));
		prixO.setCellValueFactory(new PropertyValueFactory("prix"));
		dateO.setCellValueFactory(new PropertyValueFactory("date"));

	}
	
	public void setTableOffres() {
		QueriesItr qt = new QueriesItr("SELECT offerid, getUserFullName(buyerid) AS buyer, productid, price, date FROM offers WHERE productid = "+ this.productid +" ;");
				
		creatTablecolmnsOffres();
		creatTableOffres(QueriesItr.iteratorOffre(qt));
	}
	
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
			
			setTableAnnonces();
		

		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	
	
	
	
}
