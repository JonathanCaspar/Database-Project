package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbstuff.DbAdapter;
import dbstuff.QueriesItr;
import javafx.stage.Stage;

/**
 * Classe OffreA, definit une offre pour "Mes Achats"
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class OffreA {

	private String nomProduit, vendeur;
	private Float prix;
	private Float prixDemande;
	private Date date;

	/**
	 * Constructeur d'une Offre pour "Mes Achats"
	 * 
	 * @param temp Le resultset contenant tous les attributs de l'offreA
	 */
	public OffreA(ResultSet temp) {
		try {

			this.nomProduit = temp.getString("name");
			this.vendeur = temp.getString("sellername");
			this.prix = temp.getFloat("sellingprice");
			this.prixDemande = temp.getFloat("price");
			this.date = temp.getDate("dateO");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getNomProduit() {
		return nomProduit;
	}

	public String getVendeur() {
		return vendeur;
	}

	public Float getPrix() {
		return prix;
	}

	public String getPrixDemande() {
		return String.format("%.2f", prixDemande) + " $";
	}

	public String getDate() {
		return date.toString();
	}

}
