package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe achat, definit un produit vendu
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class Achat {

	private String nomProduit;
	private float prixVente = 0;
	private String date;
	private String vendeur, acheteur;

	/**
	 * Condtructeur d'un produit vendu selon un resultat de requete avec le nom, le
	 * vendeur, l'acheteur la date de la transaction et le prix de la vente
	 * 
	 * @param temp Le ResultSet d'une requête.
	 */
	public Achat(ResultSet temp) {
		try {
			nomProduit = temp.getString("name");
			vendeur = temp.getString("sellername");
			acheteur = temp.getString("buyername");
			date = temp.getDate("datetransaction").toString();
			prixVente = temp.getFloat("soldprice");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getNomProduit() {
		return nomProduit;
	}

	public String getPrixVente() {
		return String.format("%.2f", prixVente) + " $";
	}

	public String getDate() {
		return date;
	}

	public String getVendeur() {
		return vendeur;
	}

	public String getAcheteur() {
		return acheteur;
	}

}
