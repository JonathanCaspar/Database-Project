package controleur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import application.App;
import application.Produit;
import application.User;
import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;

public class CatalogueController {

	private static final String[] categories = { "Immobilier", "Informatique", "Musique" };

	private static final String[][] sousCategories = { { "Maison", "Appartement", "Condo" },
			{ "Ordinateur", "Telephone", "Accessoires" }, { "Guitare", "Piano", "Ukulele" } };
	private static final String parent = "Catégories";

	@FXML
	private Font x1;

	@FXML
	private Color x2;

	@FXML
	private TreeView<String> rightTreeView;

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

	@FXML
	private VBox rightVBox;
	@FXML
	private TextField prixMin;
	@FXML
	private TextField prixMax;
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

	@FXML
	private MenuItem inscription;
	@FXML
	private MenuItem connexion;
	@FXML
	private MenuItem deconnexion;
	@FXML
	private MenuItem annonces;
	@FXML
	private Menu vendre;
	private ConnexionController controleurConnexion;
	private AnnoncesController controleurAnnonces;

	private User utilisateur = null;

	@FXML
	void goToLogin(ActionEvent event) {

		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			Scene scene = new Scene(loader.load());

			controleurConnexion = (ConnexionController) loader.getController();

			primaryStage.setScene(scene);
			primaryStage.setTitle("AUTHENTIFICATION");
			primaryStage.showAndWait();

			// recupère ici l'utilisateur authentifié
			setUtilisateur(controleurConnexion.getUser());

			vendre.setVisible(true);
			deconnexion.setVisible(true);
			connexion.setVisible(false);
			inscription.setVisible(false);

			if (this.utilisateur != null) {

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@FXML
	void afficherAnnonces(ActionEvent event) {
		try {

			// recuperer la stage, change la scene
			Stage primaryStage = (Stage) rightVBox.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_annonces.fxml"));
			Scene scene = new Scene(loader.load());

			controleurAnnonces = (AnnoncesController) loader.getController();

			primaryStage.setScene(scene);
			primaryStage.setTitle("Annonces");
			primaryStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void setUtilisateur(User utilisateur) {
		this.utilisateur = utilisateur;
	}

	@FXML
	void actionMettreAJour(ActionEvent event) {
		Float prixMinimum = null;
		if (!prixMin.getText().trim().isEmpty()) {
			try {
				prixMinimum = Float.parseFloat(prixMin.getText());
			} catch (Exception e) {
				afficherErreur("Le prix minimum n'est pas valide, aucune limite est ajouter.");
			}
		}
		Float prixMaximum = null;
		if (!prixMax.getText().trim().isEmpty()) {
			try {
				prixMaximum = Float.parseFloat(prixMax.getText());
			} catch (Exception e) {
				afficherErreur("Le prix maximum n'est pas valide, aucune limite est ajouter.");
			}
		}
		LocalDate minDate = null;
		if (cbDateMin.isSelected()) {
			minDate = choiceDateMin.getValue();
		}

		LocalDate maxDate = null;
		if (cbDateMin.isSelected()) {
			maxDate = choiceDateMax.getValue();
		}

		// TODO Ajouter les querry d'update
	}

	/**
	 * Affiche un message d'erreur passer ne parametre.
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
			objetsView.getItems().add(o);
		}

		objetsView.getSelectionModel().selectedItemProperty().addListener((observable, old_val, new_val) -> {
			Float offre = new_val.OpenWindow();
		});
	}

	/**
	 * Methode appeler a la creation de l'application
	 * 
	 * @param primaryStage Le stage.
	 */
	public void setStage(Stage primaryStage) {
		QueriesItr qt = new QueriesItr(
				"WITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,"
						+ " date, getMaxOfferValue(refid) AS maxoffer, categoryid  FROM products,) "
						+ "SELECT refid, name, description, sellingprice, sellername, date, maxoffer, catname date FROM allProducts JOIN categories ON categoryid = catid;");
		creatTablecolmns();
		creatTable(QueriesItr.iteratorProduit(qt));
		createTreeView();
	}

	/**
	 * Methode appeler a la creation de l'application
	 * 
	 * @param primaryStage Le stage.
	 */
	public void setStage() {
		QueriesItr qt = new QueriesItr(
				"WITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,"
						+ " date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products) "
						+ "SELECT refid, name, description, sellingprice, sellername, date, maxoffer, estimatedprice, catname, date FROM allProducts JOIN categories ON categoryid = catid;");
		creatTablecolmns();
		creatTable(QueriesItr.iteratorProduit(qt));
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
	 * Fonction appeler lors de la selection d'une categories.
	 * 
	 * @param name Le nom de la categorie selectionner.
	 */
	protected void handleCategories(String name) {
		switch (name) {
		case parent:
			creatCategoriesView();
			break;
		default:
			break;
		}
	}

	/**
	 * Cree la view lorsque l'on ait dans la categorie la plus generale.
	 */
	private void creatCategoriesView() {
		Object[] temp = rightVBox.getChildren().toArray();
		Node[] t = new Node[temp.length - 6];
		for (int i = 0; i < t.length; i++) {
			t[i] = (Node) temp[i + 6];
		}
		rightVBox.getChildren().removeAll(t);
	}

	/**
	 * Cree le TreeView a gauche selon les list categories et souscategories
	 */
	private void createTreeView() {
		TreeItem<String> root = new TreeItem<String>("Catégories");

		ArrayList<TreeItem<String>> mainCats = new ArrayList<TreeItem<String>>();
		ArrayList<Integer> mainCatsId = new ArrayList<Integer>();

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

		rightTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> old_val,
					TreeItem<String> new_val) {
				handleCategories(new_val.getValue());
			}

		});
	}

	/**
	 * Ajoute la liste d'objet visuel au VBox en gardant les prix et la date
	 * 
	 * @param list La liste de noeud a ajouter
	 */
	private void createRightVBox(Node[] list) {
		Object[] temp = rightVBox.getChildren().toArray();
		Node[] t = new Node[temp.length - 6];
		for (int i = 0; i < t.length; i++) {
			t[i] = (Node) temp[i + 6];
		}
		rightVBox.getChildren().removeAll(t);

		rightVBox.getChildren().addAll(list);
	}

	/**
	 * Crée un textField de taille largeur, hauteur et a comme texte text et comme
	 * texte transparent promptText
	 * 
	 * @param text       Le texte dans le textfield.
	 * @param promptText Le texte transparent.
	 * @param hauteur    La hauteur.
	 * @param largeur    La largeur
	 * @return Le textefield
	 */
	public TextField createTextField(String text, String promptText, double hauteur, double largeur) {
		TextField reponse = new TextField();
		reponse.setPromptText(promptText);
		reponse.setPrefSize(largeur, hauteur);
		return reponse;
	}

	/**
	 * Creer un label
	 * 
	 * @param text Le text du label
	 * @return Le Label
	 */
	public Label createLabel(String text) {
		Label reponse = new Label(text);
		return reponse;
	}

	/**
	 * Crée une liste de CheckBox ayant en odre les noms dans la liste et lorsque
	 * cliquer va effectuer L'action
	 * 
	 * @param list   La liste de noms de checkBox en ordre
	 * @param action L'action a effectuer si un est cliquer
	 * @return Retourne la liste de CheckBox
	 */
	public CheckBox[] CreateCheckBoxList(String[] list, EventHandler<ActionEvent> action) {
		CheckBox[] checkBoxList = new CheckBox[list.length];

		for (int i = 0; i < list.length; i++) {
			checkBoxList[i] = new CheckBox(list[i]);
			checkBoxList[i].setOnAction(action);
		}

		return checkBoxList;
	}

	/**
	 * Crée une liste de RadioButton ayant en odre les noms dans la liste et lorsque
	 * cliquer va effectuer L'action
	 * 
	 * @param list   La liste de noms de checkBox en ordre
	 * @param action L'action a effectuer si un est cliquer
	 * @return Retourne la liste de CheckBox
	 */
	public RadioButton[] CreateRadioButtonList(String[] list, EventHandler<ActionEvent> action) {
		RadioButton[] radioBList = new RadioButton[list.length];

		for (int i = 0; i < list.length; i++) {
			radioBList[i] = new RadioButton(list[i]);
			radioBList[i].setOnAction(action);
		}

		Group group = new Group(radioBList);

		return radioBList;
	}

	/**
	 * Retourne le nom d'un checkBox
	 * 
	 * @param cb Le checkBox
	 * @return Le nom du checkBox
	 */
	public String CheckBoxString(CheckBox cb) {
		return cb.getAccessibleText();
	}
}
