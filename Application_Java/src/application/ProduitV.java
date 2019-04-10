package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import controleur.ControleurOffrir;
import dbstuff.QueriesItr;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProduitV {
	private String nomProduit = null;
	private String description = "description";

	private float prix = 0;
	private float oMax = 0;
	private float estimation = 0;
	
	private int refid = 0;
	private int catID = 0;
	private int nbOffre = 0;

	private Date date = null;
	private String vendeur = null;

	
	public ProduitV (ResultSet temp) {
		try {
			
			nbOffre = temp.getInt("nbOffers");
			nomProduit = temp.getString("name");
			description = temp.getString("description");
			refid = temp.getInt("refid");
			prix = temp.getFloat("sellingprice");
			estimation = temp.getFloat("estimatedprice");
			date = temp.getDate("date");
			vendeur = temp.getString("sellername");
			catID = temp.getInt("categoryid");
			oMax = temp.getFloat("maxoffer");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String produit) {
		nomProduit = produit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public String getOMax() {
		return String.format("%.2f", oMax) + " $";
	}

	public float getValueOMax() {
		return oMax;
	}
	
	public void setOMax(float oMax) {
		this.oMax = oMax;
	}

	public String getDate() {
		return date.toString();
	}

	public String getVendeur() {
		return vendeur;
	}

	public void setVendeur(String vendeur) {
		this.vendeur = vendeur;
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
	
	public int getNbOffre() {
		
		return this.nbOffre;
	}

}
