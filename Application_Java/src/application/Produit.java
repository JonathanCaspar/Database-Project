package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import controleur.ControleurOffrir;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe produit, definit un produit non vendu
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class Produit {
	private String nomProduit = null;
	private String description = "description";

	private float prix = 0;
	private float oMax = 0;
	private float estimation = 0;

	private int refid = 0;
	private int catID = 0;

	private Date date = null;
	private String categorie = null;
	private String vendeur = null;

	/**
	 * Contructeur d'un produit selon le resultat d'une requete
	 * 
	 * @param temp Le ResultSet d'une requete avec tous les attributs d'un produit.
	 */
	public Produit(ResultSet temp) {
		try {
			nomProduit = temp.getString("name");
			description = temp.getString("description");
			refid = temp.getInt("refid");
			prix = temp.getFloat("sellingprice");
			estimation = temp.getFloat("estimatedprice");
			date = temp.getDate("date");
			vendeur = temp.getString("sellername");
			categorie = temp.getString("catname");
			oMax = temp.getFloat("maxoffer");
			catID = temp.getInt("categoryid");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String getNomProduit() {
		return nomProduit;
	}

	public String getDescription() {
		return description;
	}

	public String getPrix() {
		return String.format("%.2f", prix) + " $";
	}

	public float getPrixF() {
		return prix;
	}

	public float getValuePrix() {
		return prix;
	}


	public String getOMax() {
		return String.format("%.2f", oMax) + " $";
	}

	public float getValueOMax() {
		return oMax;
	}

	public String getDate() {
		return date.toString();
	}

	public String getCategorie() {
		return categorie;
	}

	public String getVendeur() {
		return vendeur;
	}

	public int getRefId() {
		return refid;
	}

	public float getEstimation() {
		return estimation;
	}

	public int getCatID() {
		return this.catID;
	}

	/*
	 * Ouvre la fenetre pour faire une offre a un produit et attend sa fermeture.
	 */
	public void OpenWindow() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_Item.fxml"));
			Scene scene = new Scene(loader.load());

			ControleurOffrir ctl = (ControleurOffrir) loader.getController();
			ctl.setStage(primaryStage, this);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Offre");
			primaryStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
