package controleur;

import application.OffreA;
import application.Produit;
import application.Achat;
import dbstuff.QueriesItr;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Classe Achats controlleur, definit le controleur pour la vue Mes Achats
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class AchatsController {

	// Tableau de gauche, les offres faites par l'utilisateur
	@FXML
	private TableView<OffreA> offreAView;
	@FXML
	private TableColumn<OffreA, String> produit;
	@FXML
	private TableColumn<OffreA, String> vendeur;
	@FXML
	private TableColumn<OffreA, String> prix;
	@FXML
	private TableColumn<OffreA, String> prixDemande;
	@FXML
	private TableColumn<OffreA, String> date;

	// Tableau de droite, achats effectués par l'utilisateur
	@FXML
	private TableView<Achat> achatView;
	@FXML
	private TableColumn<Achat, String> produitA;
	@FXML
	private TableColumn<Achat, String> vendeurA;
	@FXML
	private TableColumn<Achat, String> prixA;
	@FXML
	private TableColumn<Achat, String> dateA;

	/**
	 * Creer une table contenant la liste des offres passer en paramettre.
	 * 
	 * @param table La table d'offre.
	 */
	public void creatTableOffre(Iterable<OffreA> table) {
		offreAView.getItems().clear();

		for (OffreA o : table) {
			if (o != null) {
				offreAView.getItems().add(o);
			}
		}
//		offreAView.getSelectionModel().selectedItemProperty().addListener((observable, old_val, new_val) -> {
//			
//		});
	}

	private void creatTablecolmnsOffre() {

		produit.setCellValueFactory(new PropertyValueFactory("nomProduit"));
		vendeur.setCellValueFactory(new PropertyValueFactory("vendeur"));
		prix.setCellValueFactory(new PropertyValueFactory("prix"));
		prixDemande.setCellValueFactory(new PropertyValueFactory("prixDemande"));
		date.setCellValueFactory(new PropertyValueFactory("date"));
	}

	/**
	 * Mise en place du tableau contenant les offres faites par l'utilisateur.
	 */
	public void setTableOffre() {
		QueriesItr qt = new QueriesItr(
				"SELECT name, getUserFullName(sellerid) AS sellername, sellingprice, price ,offers.date AS dateO "
						+ "FROM products JOIN offers ON productid = refid WHERE buyerid = "
						+ MainControleur.getUtilisateur() + ";");
		creatTablecolmnsOffre();
		creatTableOffre(QueriesItr.iteratorOffreA(qt));
	}

	/**
	 * Creer une table contenant la liste des achats effectués passer en paramettre.
	 * 
	 * @param table La table d'achats.
	 */
	public void creatTableAchat(Iterable<Achat> table) {
		achatView.getItems().clear();

		for (Achat o : table) {
			if (o != null) {
				achatView.getItems().add(o);
			}
		}
		achatView.getSelectionModel().selectedItemProperty().addListener((observable, old_val, new_val) -> {
			System.out.println(achatView.getSelectionModel().getSelectedItem().getVendeur());
		});
	}

	private void creatTablecolmnsAchat() {

		produitA.setCellValueFactory(new PropertyValueFactory("nomProduit"));
		vendeurA.setCellValueFactory(new PropertyValueFactory("vendeur"));
		prixA.setCellValueFactory(new PropertyValueFactory("prixVente"));
		dateA.setCellValueFactory(new PropertyValueFactory("date"));
	}

	/**
	 * Mise en place du tableau des achats effectués par l'utilisateur. Produits
	 * pour lesquels les offres ont été acceptées.
	 */
	public void setTableAchat() {
		QueriesItr qt = new QueriesItr("SELECT name, getUserFullName(sellerid) AS sellername, getUserFullName("
				+ MainControleur.getUtilisateur()
				+ ") AS buyername, soldprice, datetransaction FROM soldproducts WHERE buyerid ="
				+ MainControleur.getUtilisateur() + ";");
		creatTablecolmnsAchat();
		creatTableAchat(QueriesItr.iteratorAchat(qt));
	}

}
