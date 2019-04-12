package controleur;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import application.Produit;
import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * Classe CatalogueController, definit le controleur pour la vue catalogue
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class CatalogueController {
	public static final String parent = "Catégories";

	private String mainCatActuelle = null;
	private String catActuelle = "Catégories";

	@FXML
	private Font x1;

	@FXML
	private Color x2;

	// TreeView pour les categories
	@FXML
	private TreeView<String> rightTreeView;

	// Table de produit et ses colonnes
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
	private TableColumn<Produit, String> oMax;
	@FXML
	private TableColumn<Produit, String> vendeur;

	// Vue a gauche contennant tout les restrictions
	@FXML
	private VBox rightVBox;
	@FXML
	private TextField rechercheTextField;
	@FXML
	private TextField prixMin;
	@FXML
	private TextField prixMax;
	@FXML
	private TextField prixOffertMin;
	@FXML
	private TextField prixOffertMax;
	@FXML
	private CheckBox cbDateMin;
	@FXML
	private DatePicker choiceDateMin;
	@FXML
	private CheckBox cbDateMax;
	@FXML
	private DatePicker choiceDateMax;
	@FXML
	private Button MettreAJour;

	@FXML
	private Font x3;
	@FXML
	private Color x4;

	private ConnexionController controleurConnexion;
	private AnnoncesController controleurAnnonces;

	/**
	 * Fonction appelee quand on clique sur le bouton mettre a jour
	 * 
	 * @param event L'event declancheur.
	 */
	@FXML
	void actionMettreAJour(ActionEvent event) {

		// Sauvegarde les criteres s'il ne sont pas vide et les garde a null sinon
		String recherche = null;
		if (!rechercheTextField.getText().trim().isEmpty()) {
			recherche = rechercheTextField.getText().trim();
		}
		Float prixMinimum = null;
		if (!prixMin.getText().trim().isEmpty()) {
			try {
				prixMinimum = Float.parseFloat(prixMin.getText());
			} catch (Exception e) {
				afficherErreur("Le prix minimum n'est pas valide, aucune limite est ajoutée.");
			}
		}
		Float prixMaximum = null;
		if (!prixMax.getText().trim().isEmpty()) {
			try {
				prixMaximum = Float.parseFloat(prixMax.getText());
			} catch (Exception e) {
				afficherErreur("Le prix maximum n'est pas valide, aucune limite est ajoutée.");
			}
		}
		Float prixOffertMinimum = null;
		if (!prixOffertMin.getText().trim().isEmpty()) {
			try {
				prixOffertMinimum = Float.parseFloat(prixOffertMin.getText());
			} catch (Exception e) {
				afficherErreur("Le prix offert minimum n'est pas valide, aucune limite est ajoutée.");
			}
		}
		Float prixOffertMaximum = null;
		if (!prixOffertMax.getText().trim().isEmpty()) {
			try {
				prixOffertMaximum = Float.parseFloat(prixOffertMax.getText());
			} catch (Exception e) {
				afficherErreur("Le prix offert maximum n'est pas valide, aucune limite est ajoutée.");
			}
		}
		Date minDate = null;
		if (cbDateMin.isSelected() && choiceDateMin.getValue() != null) {
			minDate = Date.valueOf(choiceDateMin.getValue());
		}

		Date maxDate = null;
		if (cbDateMax.isSelected() && choiceDateMax.getValue() != null) {
			maxDate = Date.valueOf(choiceDateMax.getValue());
		}

		// Cree la requetes selon les critere et les affiche dans le tableau
		QueriesItr qt = QueriesItr.creatListProductQuery(mainCatActuelle, catActuelle, recherche, prixMinimum,
				prixMaximum, prixOffertMinimum, prixOffertMaximum, minDate, maxDate);
		creatTable(QueriesItr.iteratorProduit(qt));
	}

	/**
	 * Affiche un message d'erreur passe en parametre.
	 * 
	 * @param message Le message a afficher.
	 */
	void afficherErreur(String message) {
		Alert boiteDerreur = new Alert(AlertType.ERROR);
		boiteDerreur.setContentText(message);
		DialogPane dialogPane = boiteDerreur.getDialogPane();
		boiteDerreur.setHeaderText(null);
		dialogPane.getStyleClass().add("pane");
		boiteDerreur.showAndWait();
		boiteDerreur.resultProperty().addListener(a -> {
			boiteDerreur.close();
		});
	}

	@FXML
	void actionCbDateMax(ActionEvent event) {
		if (((CheckBox) event.getSource()).isSelected()) {
			choiceDateMax.setDisable(false);
		} else {
			choiceDateMax.setDisable(true);
		}
	}

	@FXML
	void actionCbDateMin(ActionEvent event) {
		if (((CheckBox) event.getSource()).isSelected()) {
			choiceDateMin.setDisable(false);
		} else {
			choiceDateMin.setDisable(true);
		}
	}

	/**
	 * Creer une table contenant la liste de produit passer en paramettre.
	 * 
	 * @param table La table de produit.
	 */
	public void creatTable(Iterable<Produit> table) {
		objetsView.getItems().clear();
		for (Produit o : table) {
			if (o != null) {
				objetsView.getItems().add(o);
			}
		}
		objetsView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				if (objetsView.getSelectionModel().getSelectedItem() != null) {
					objetsView.getSelectionModel().getSelectedItem().OpenWindow();
					actionMettreAJour(null);
				}
			}
		});
	}

	/**
	 * Methode appeler a la creation de l'application
	 * 
	 */
	@FXML
	public void initialize() {
		creatTablecolmns();

		// Ajoute la liste de produit
		actionMettreAJour(null);

		createTreeView();
	}

	private void creatTablecolmns() {
		produits.setCellValueFactory(new PropertyValueFactory("nomProduit"));
		prix.setCellValueFactory(new PropertyValueFactory("prix"));
		date.setCellValueFactory(new PropertyValueFactory("date"));
		categorie.setCellValueFactory(new PropertyValueFactory("categorie"));
		vendeur.setCellValueFactory(new PropertyValueFactory("vendeur"));
		oMax.setCellValueFactory(new PropertyValueFactory("oMax"));
	}

	/**
	 * Cree le TreeView a gauche selon les list categories et souscategories
	 */
	private void createTreeView() {
		TreeItem<String> root = new TreeItem<String>(parent);

		ArrayList<TreeItem<String>> mainCats = new ArrayList<TreeItem<String>>();
		ArrayList<Integer> mainCatsId = new ArrayList<Integer>();

		// Viens chercher les categories principales
		QueriesItr QT = new QueriesItr("SELECT * FROM " + DbAdapter.DB_TABLES[1] + " ORDER BY maincatname;");
		ResultSet rs = QT.getResultSet();

		try {
			if (rs != null)
				while (QT.next()) {
					String maincatname = rs.getString("maincatname");
					int catid = rs.getInt("maincatid");

					TreeItem<String> rootItem = new TreeItem<String>(maincatname);
					mainCats.add(rootItem);
					mainCatsId.add(catid);

					rootItem.setExpanded(true);
					root.getChildren().add(rootItem);
				}

			// Vient chercher les sous categories de chaque categories principales
			for (int i = 0; i < mainCats.size(); i++) {

				QT = new QueriesItr("SELECT catname FROM " + DbAdapter.DB_TABLES[2] + " WHERE maincatid = "
						+ mainCatsId.get(i) + " ORDER BY catname;");
				ResultSet rs2 = QT.getResultSet();

				if (rs2 != null) {
					while (QT.next()) {
						String catname = rs2.getString("catname");
						TreeItem<String> childItem = new TreeItem<String>(catname);
						mainCats.get(i).getChildren().add(childItem);
					}
				}
			}
		} catch (SQLException e) {
			QT.quitter();
			e.printStackTrace();
		}

		root.setExpanded(true);
		rightTreeView.setRoot(root);

		// Action sur le changement de categorie
		rightTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> old_val,
					TreeItem<String> new_val) {
				
				// Vient chercher la categorie actuelle et parente
				catActuelle = new_val.getValue();
				if (new_val.getParent() != null) {
					mainCatActuelle = new_val.getParent().getValue();
				} else {
					mainCatActuelle = null;
				}
				
				// Update la liste de prouit
				actionMettreAJour(null);
			}

		});
	}
}
