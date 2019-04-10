package controleur;

import java.sql.SQLException;
import java.sql.Statement;

import application.Offre;
import application.Achat;
import application.Produit;
import application.ProduitV;
import dbstuff.DbAdapter;
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
	private Button accepter;
	@FXML
	private NewAnnonceController newAnnonceController;
	
	//Tableau de gauche, contenant les annonces de l'utilisateur authentifié
	@FXML
	private TableView<ProduitV> objetsView;
	@FXML
	private TableColumn<ProduitV, String> nbOffre;
	@FXML
	private TableColumn<ProduitV, String> produits;
	@FXML
	private TableColumn<ProduitV, String> prix;
	@FXML
	private TableColumn<ProduitV, String> estimation;
	@FXML
	private TableColumn<ProduitV, String> maxOffre;
	@FXML
	private TableColumn<ProduitV, String> date;
	
	
	/**
	 * Tableau de droite contenant les proposition faite pour 
	 * les produits de l'utilisateur authentifié
	 */
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
	
	/**
	 * Tableau de droite contenant les proposition faite pour 
	 * les produits de l'utilisateur authentifié
	 */
	@FXML
	private TableView<Achat> venteView;
	@FXML
	private TableColumn<Achat, String> produitV;
	@FXML
	private TableColumn<Achat, String> acheteurV;
	@FXML
	private TableColumn<Achat, String> prixV;
	@FXML
	private TableColumn<Achat, String> dateV;
	
	private int productid, offreid;
	private float prixVente;
	private ProduitV produit;
	private Offre offre;

	
	@FXML
	public void initialize() {
		this.setTableAnnonces();
		this.setTableVente();
		offreView.getItems().clear();
	}
		
	
	
	
	
	/**
	 * Accepte une offre on inserant le produit vendu dans la table soldeproducts
	 * et en supprimant le produit de la table products (ainsi que ses autres offres)
	 */
	@FXML
	public void accepterOffre() {
		
		Statement stmt = null; 
		try {
			
			stmt = DbAdapter.con.createStatement();
			stmt.executeUpdate(
					"INSERT INTO soldproducts (name, description, sellerid, buyerid, categoryid, estimatedprice, sellingprice, soldprice) VALUES"
					+ "('"+ produit.getNomProduit()+"', '"+produit.getDescription()+"', '"+ MainControleur.getUtilisateur()+"' ,'"+offre.getBuyerID()+"' ,"
					+ " '"+produit.getCatID()+ "', '"+produit.getEstimation()+ "', '"+ produit.getPrixF()+ "' , '"+offre.getPrixF()+ "');");
					
			stmt.executeUpdate("DELETE FROM products WHERE refid = " + offre.getProduitID() );
			stmt.close();
			
			this.initialize();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Creer une table contenant la liste de produit passer en paramettre.
	 * 
	 * @param table La table de produit.
	 */
	public void creatTableAnnonces(Iterable<ProduitV> table) {
		objetsView.getItems().clear();
		for (ProduitV o : table) {
			objetsView.getItems().add(o);
		}

		objetsView.getSelectionModel().selectedItemProperty().addListener((observable, old_val, new_val) -> {
		    
			produit = objetsView.getSelectionModel().getSelectedItem();
			prixExpert.setText(""+ objetsView.getSelectionModel().getSelectedItem().getEstimation() + " $");
			setTableOffres();
		});
	}
	
	private void creatTablecolmnsAnnonces() {
		
		nbOffre.setCellValueFactory(new PropertyValueFactory("nbOffre"));
		produits.setCellValueFactory(new PropertyValueFactory("nomProduit"));
		prix.setCellValueFactory(new PropertyValueFactory("prix"));
		estimation.setCellValueFactory(new PropertyValueFactory("estimation"));
		maxOffre.setCellValueFactory(new PropertyValueFactory("oMax"));
		date.setCellValueFactory(new PropertyValueFactory("date"));
	}

	/**
	 * Mise en place du tableau contenant les annonces de l'utilisateur authentifié
	 */
	public void setTableAnnonces() {
		QueriesItr qt = new QueriesItr(" SELECT getOffersCount(refid) as nboffers, refid, name, description ,sellingprice, getUserFullName(sellerid) AS sellername, "
										+"date, getMaxOfferValue(refid) AS maxoffer, estimatedprice, categoryid "
										+"FROM products WHERE sellerid = '"+MainControleur.getUtilisateur()+"';");
		creatTablecolmnsAnnonces();
		creatTableAnnonces(QueriesItr.iteratorProduitV(qt));
	}
	
	/**
	 * Creer une table contenant la liste d'offres passée en paramètre.
	 * 
	 * @param table La table d'offres.
	 */
	public void creatTableOffres(Iterable<Offre> table) {
		offreView.getItems().clear();
		for (Offre o : table) {
			offreView.getItems().add(o);
		}

		offreView.getSelectionModel().selectedItemProperty().addListener((observable, old_val, new_val) -> {
		    
			offre = offreView.getSelectionModel().getSelectedItem();

		});
	}
	
	private void creatTablecolmnsOffres() {
		acheteur.setCellValueFactory(new PropertyValueFactory("buyer"));
		prixO.setCellValueFactory(new PropertyValueFactory("prix"));
		dateO.setCellValueFactory(new PropertyValueFactory("date"));
	}
	/**
	 * Mise en place du tableau contenant les offres faites pour les produits de l'utilisateur authentifié
	 */
	public void setTableOffres() {
		QueriesItr qt = new QueriesItr("SELECT offerid, buyerid, productid, price, date FROM offers WHERE productid = "+ this.produit.getRefId() +" ;");
		creatTablecolmnsOffres();
		creatTableOffres(QueriesItr.iteratorOffre(qt));
	}
	
	
	/**
	 * Ouvre la fenetre de creation d'annonce pour l'utilisateur authentifié
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
	
	/**
	 * Creer une table contenant la liste des achats effectués passer en paramettre.
	 * 
	 * @param table La table de produit.
	 */
	public void creatTableVente(Iterable<Achat> table) {
		venteView.getItems().clear();
		
		for (Achat o : table) {
			venteView.getItems().add(o);
		}
		venteView.getSelectionModel().selectedItemProperty().addListener((observable, old_val, new_val) -> {
			System.out.println(venteView.getSelectionModel().getSelectedItem().getVendeur()); 
		});
	}
	
	private void creatTablecolmnsVente() {
		
		produitV.setCellValueFactory(new PropertyValueFactory("nomProduit"));
		acheteurV.setCellValueFactory(new PropertyValueFactory("acheteur"));
		prixV.setCellValueFactory(new PropertyValueFactory("prixVente"));
		dateV.setCellValueFactory(new PropertyValueFactory("date"));
	}

	/**
	 * Mise en place du tableau des achats effectués par l'utilisateur.
	 * Produits pour lesquels les offres ont été acceptées.
	 */
	public void setTableVente() {
		QueriesItr qt = new QueriesItr("SELECT name, getUserFullName(sellerid) AS sellername ,getUserFullName(buyerid) AS buyername, soldprice, datetransaction FROM soldproducts WHERE sellerid =" + MainControleur.getUtilisateur()+ ";");
		creatTablecolmnsVente();
		creatTableVente(QueriesItr.iteratorAchat(qt));
	}
}
