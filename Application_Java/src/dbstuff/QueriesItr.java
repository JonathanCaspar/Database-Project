package dbstuff;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import application.Produit;
import controleur.CatalogueController;
import controleur.MainControleur;

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

	/**
	 * Creer une "liste" iterable de produit sans sauvegarder tous les produits en
	 * memoire.
	 * 
	 * @param qt La querry a iterer
	 * @return Un iterable de produits
	 */
	public static Iterable<Produit> iteratorProduit(QueriesItr qt) {
		return new Iterable<Produit>() {

			@Override
			public Iterator<Produit> iterator() {
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

	/**
	 * Cree une querry selon la categorie choisi, les prix minimums et maximums et
	 * les dates limites.
	 * 
	 * @param mainCatActuelle La categorie superieur actuelle null si on est dans toutes les categorie.
	 * @param catActuelle La categorie souhaitee actuelle.
	 * @param prixMinimum Le prixM
	 * @param prixMaximum
	 * @param prixOffertMinimum
	 * @param prixOffertMaximum
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static QueriesItr creatListProductQuery(String mainCatActuelle, String catActuelle, Float prixMinimum,
			Float prixMaximum, Float prixOffertMinimum, Float prixOffertMaximum, Date minDate, Date maxDate) {

		String allProducts = "\nWITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,"
				+ " date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products)";

		String allCategorie = "";
		String joinCategorie = " JOIN categories ON categoryid = catid";

		String prixMin = "";
		if (prixMinimum != null) {
			prixMin = " sellingprice >= " + prixMinimum + " AND";
		}
		String prixMax = "";
		if (prixMaximum != null) {
			prixMin = " sellingprice <= " + prixMaximum + " AND";
		}
		String prixOffertWhere = "";
		String prixOffertAnd = "";
		String prixOffertMin = "";
		if (prixOffertMinimum != null) {
			prixOffertWhere = " WHERE ";
			prixOffertMin = " maxoffer >= " + prixOffertMinimum;
		}

		String prixOffertMax = "";
		if (prixOffertMaximum != null) {
			if (prixOffertWhere.isEmpty()) {
				prixOffertWhere = " WHERE ";
			} else {
				prixOffertAnd = " AND ";
			}
			prixOffertMax = " maxoffer <= " + prixOffertMaximum;
		}
		String dateMin = "";
		if (minDate != null) {
			dateMin = " date >='" + minDate.toString() + "' AND";
		}
		String dateMax = "";
		if (maxDate != null) {
			dateMax = " date <='" + maxDate.toString() + "' AND";
		}

		String user = "";
		if (MainControleur.getUtilisateur() >= 0) {
			user = " sellerid <> " + MainControleur.getUtilisateur() + " AND";
		}

		if (!(prixMin.isEmpty() && prixMax.isEmpty() && dateMin.isEmpty() && dateMax.isEmpty() && user.isEmpty())) {
			allProducts = "\nWITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,"
					+ " date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products WHERE";
			allProducts += prixMin;
			allProducts += prixMax;
			allProducts += dateMin;
			allProducts += dateMax;
			allProducts += user;

			allProducts = allProducts.substring(0, allProducts.length() - 3) + ")";
		}

		if (mainCatActuelle != null) {
			if (mainCatActuelle == CatalogueController.parent) {
				allCategorie = ",\n mainCatActuelle AS (SELECT maincatid FROM maincategories WHERE maincatname = \""
						+ catActuelle + "\")";
				allCategorie += ",\n allCategorie AS (SELECT catid, catname FROM categories NATURAL JOIN mainCatActuelle)";
				joinCategorie = " JOIN allCategorie ON categoryid = catid";
			} else {
				allCategorie = ",\n mainCatActuelle AS (SELECT maincatid FROM maincategories WHERE maincatname = '"
						+ mainCatActuelle + "')";
				allCategorie += ",\n catActuelle AS (SELECT * FROM categories WHERE catname = '" + catActuelle + "')";
				allCategorie += ",\n allCategorie AS (SELECT catid, catname FROM catActuelle NATURAL JOIN mainCatActuelle)";
				joinCategorie = " JOIN allCategorie ON categoryid = catid";
			}
		}

		String query = allProducts + allCategorie
				+ "\n SELECT refid, name, description, sellingprice, sellername, date, maxoffer, catname, date, estimatedprice  FROM allProducts"
				+ joinCategorie;
		query += prixOffertWhere;
		query += prixOffertMin;
		query += prixOffertAnd;
		query += prixOffertMax;
		query += ";";
		QueriesItr t = new QueriesItr(query);
		return t;
	}
}
