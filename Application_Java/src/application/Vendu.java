package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vendu {

	private String nomProduit = null;
	private String description = "description";

	private float prix = 0;
	private float prixVente = 0;
	private float estimation = 0;
	
	private int refid = 0;

	private Date date = null;
	private String categorie = null;
	private String vendeur = null;
	private String acheteur = null;
	
	public Vendu(ResultSet temp) {
		try {
			nomProduit = temp.getString("name");
			description = temp.getString("description");
			refid = temp.getInt("refid");
			prix = temp.getFloat("sellingprice");
			estimation = temp.getFloat("estimatedprice");
			date = temp.getDate("date");
			vendeur = temp.getString("sellername");
			acheteur = temp.getString("buyerid");
			categorie = temp.getString("catname");
			prixVente = temp.getFloat("soldprice");
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

	public float getPrix() {
		return prix;
	}

	public float getPrixVente() {
		return prixVente;
	}

	public float getEstimation() {
		return estimation;
	}

	public int getRefid() {
		return refid;
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

	public String getAcheteur() {
		return acheteur;
	}
	
}
