package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Achat {

	private String nomProduit;
	private float prixVente = 0;
	private String date;
	private String vendeur, acheteur;
	
	
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
