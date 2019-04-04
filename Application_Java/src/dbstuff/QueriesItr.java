package dbstuff;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import application.Produit;
import controleur.CatalogueController;

public class QueriesItr {
	private Statement stmt = null;
	private ResultSet rs = null;

	/**
	 * Instancie un statement et un ResultSet selon la query si tout ce passe bien
	 * 
	 * @param query La query a execute
	 */
	public QueriesItr(String query) {
		try {
			stmt = DbAdapter.con.createStatement();
			if (stmt != null) {
				rs = stmt.executeQuery(query);
			}
		} catch (SQLException e) {
			quitter();
			e.printStackTrace();
		}
	}

	/**
	 * Le ResultSet de la query (null si l'instenciation n'a pas marcher) a utiiser
	 * pour les getString et autres.
	 *
	 */
	public ResultSet getResultSet() {
		return rs;
	}

	/**
	 * Passe au prochain resultat s'il y en a un en retournant vrais et retourne
	 * faux et ferme le statement et le ResultSet si une erreur se produit ou il n'y
	 * en a pas
	 * 
	 * @return Vrai s'il y a un prochain element a la query et faux sinon
	 */
	public boolean next() {
		boolean hasNext = false;
		try {
			hasNext = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (!hasNext) {
				quitter();
			}
		}

		return hasNext;
	}

	/**
	 * Ferme le statement et la query
	 */
	public void quitter() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {

				stmt.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static Iterable<Produit> iteratorProduit(QueriesItr qt) {
		return new Iterable<Produit>() {

			@Override
			public Iterator<Produit> iterator() {
				// TODO Auto-generated method stub
				return new Iterator<Produit>() {
					ResultSet temp = qt.getResultSet();
					@Override
					public boolean hasNext() {
						boolean hn = false;
						try {
							if (temp != null) {
								hn = !temp.isClosed();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						return hn;
					}

					@Override
					public Produit next() {
						Produit p = null;
						if (qt.next()) {
							p = new Produit(temp);
						}
						return p;
					}
				};
			}
		};

	}

	public static QueriesItr creatListProductQuery(String mainCatActuelle, String catActuelle, Float prixMinimum,
			Float prixMaximum, Date minDate, Date maxDate) {
		String allProducts = "WITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,"
				+ " date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products) \n";
		String prixMin = "";
		if (prixMinimum != null) {
			prixMin = " sellingprice >= " + prixMinimum + " AND";
		}
		String prixMax = "";
		if (prixMaximum != null) {
			prixMin = " sellingprice <= " + prixMaximum + " AND";
		}
		
		String dateMin = "";
		if (minDate != null) {
			dateMin = " date >='" + minDate.toString() + "' AND";
		}
		String dateMax = "";
		if (maxDate != null) {
			dateMax = " date >='" + maxDate.toString() + "' AND";
		}
		
		if (!(prixMin.isEmpty() && prixMax.isEmpty() && dateMin.isEmpty() && dateMax.isEmpty())) {
			allProducts = "WITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,"
					+ " date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products WHERE";
			allProducts += prixMin;
			allProducts += prixMax;
			allProducts += dateMin;
			allProducts += dateMax;
			
			allProducts = allProducts.substring(0, allProducts.length() - 3) + ")\n";
		}
		String query = allProducts + "SELECT refid, name, description, sellingprice, sellername, date, maxoffer, catname, date, estimatedprice  FROM allProducts JOIN categories ON categoryid = catid;";
		QueriesItr t = new QueriesItr(query);
		return t;
	}
}
