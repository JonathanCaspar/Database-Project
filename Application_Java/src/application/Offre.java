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
	private float prix;
	private Date date = null;
	private String buyer = null;
	
	
	public Offre(ResultSet temp) {
		try {
			
			this.offreID = temp.getInt("offerid");
			this.buyerID = temp.getInt("buyerid");
			this.produitID = temp.getInt("productid");
			this.prix = temp.getFloat("price");
			this.date = temp.getDate("date");
			
			getBuyerName(temp.getInt("buyerid"));
			
			System.out.println("offre : " + buyer);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	public void getBuyerName(int buyerID) {
		
		QueriesItr qt = new QueriesItr("SELECT getUserFullName(buyerid) AS buyer FROM offers WHERE buyerid = "+ buyerID+" ;"  );
		ResultSet rs = qt.getResultSet();
		
		try {
			if (rs.next()) {
				this.buyer = rs.getString("buyer");
			}
		} catch (SQLException e) {
			qt.quitter();
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


	public int getBuyerID() {
		return buyerID;
	}
	
	public String getBuyer() {
		return buyer;
	}
	
	
}
