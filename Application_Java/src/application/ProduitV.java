package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe ProduitV, definit un produit non vendu du cote de la vue du vendeur
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
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

	/**
	 * Constructeur de produit vendu du cote du vendeur selon un resultat de requete
	 * 
	 * @param temp Le ResultSet a la position du produit actuelle a creer
	 */
	public ProduitV(ResultSet temp) {
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

	public String getVendeur() {
		return vendeur;
	}

	
	public int getRefId() {
		return refid;
	}

	public String getEstimation() {
		return String.format("%.2f", estimation) + " $";
	}

	public int getCatID() {

		return this.catID;
	}

	public int getNbOffre() {

		return this.nbOffre;
	}

}
