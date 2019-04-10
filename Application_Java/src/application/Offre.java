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
	
	/**
	 * Constructeur d'une Offre pour "Mes Annonces"
	 * @param temp
	 */
	public Offre(ResultSet temp) {
		try {
			
			this.offreID = temp.getInt("offerid");
			this.buyerID = temp.getInt("buyerid");
			this.produitID = temp.getInt("productid");
			this.prix = temp.getFloat("price");
			this.date = temp.getDate("date");
			
			setBuyerName(temp.getInt("buyerid"));
			
			System.out.println("offre : " + buyer);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	public String getBuyerName(int buyerID) {
		return this.buyer;
	}
	
	/**
	 * Recupere le nom d'un vendeur dans la base de données selon son id.
	 * @param buyerID
	 */
	public void setBuyerName(int buyerID) {
		
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
	
 
	//GETTERS
	
	public int getProduitID() {
		return produitID;
	}

	public String getPrix() {
		return String.format("%.2f", prix) + " $";
	}
	
	public float getPrixF() {
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
