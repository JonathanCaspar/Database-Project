package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Offre {

	private int produitID;
	private float prix;
	private Date date = null;
	private String buyer = null;
	
	
	public Offre(ResultSet temp) {
		try {
			
			this.buyer = temp.getString("buyer");
			this.produitID = temp.getInt("productid");
			this.prix = temp.getFloat("price");
			this.date = temp.getDate("date");
			
			System.out.println("offre : " + buyer);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}


	public int getProduitID() {
		return produitID;
	}


	public float getPrix() {
		return prix;
	}


	public String getDate() {
		return date.toString();
	}


	public String getBuyer() {
		return buyer;
	}
	
	
}
