package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.stage.Stage;

public class Offre {

	private int produitID, offreID, buyerID;
	
	private float prix, prixO, estimation;
	private Date date;
	private String buyer, nomProduit;
	
	/**
	 * Constructeur d'une Offre pour "Mes Annonces"
	 * @param temp
	 */
	public Offre(ResultSet temp) {
		try {
			
			this.offreID = temp.getInt("offerid");
			this.buyer = temp.getString("buyername");
			this.buyerID = temp.getInt("buyerid");
			this.produitID = temp.getInt("productid");
			this.nomProduit = temp.getString("name");
			this.prix = temp.getFloat("sellingprice");
			this.prixO = temp.getFloat("price");
			this.estimation = temp.getFloat("estimatedprice");
			this.date = temp.getDate("dateO");
						
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	
	public String getPrixO() {
		return String.format("%.2f", prixO) + " $";
	}
	
	public float getValuePrixO() {
		return prixO;
	}


	public String getPrix() {
		return String.format("%.2f", prix) + " $";
	}
	
	public int getProduitID() {
		return produitID;
	}

	public int getOffreID() {
		return offreID;
	}

	public int getBuyerID() {
		return buyerID;
	}

	public float getEstimation() {
		return estimation;
	}

	public String getDate() {
		return date.toString();
	}
	
	public String getBuyer() {
		return buyer;
	}

	public String getNomProduit() {
		return nomProduit;
	}
	
}
