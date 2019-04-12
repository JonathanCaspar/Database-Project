package dbstuff;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import application.Offre;
import application.OffreA;
import application.Produit;
import application.ProduitV;
import application.Achat;
import controleur.CatalogueController;
import controleur.MainControleur;

/**
 * Classe QueriesItr, instancie des liens vers la base de donnee selon les
 * queries, gere les erreur et creer des ietrable pour la creation des table
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
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
	 * Cree une "liste" iterable de produit sans sauvegarder tous les produits en
	 * memoire retourne null comme dernier element.
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
	 * Creer une "liste" iterable de produit (côté acheteur) sans sauvegarder tous
	 * les produits en memoire.
	 * 
	 * @param qt La querry a iterer
	 * @return Un iterable de produits
	 */
	public static Iterable<ProduitV> iteratorProduitV(QueriesItr qt) {
		return new Iterable<ProduitV>() {

			@Override
			public Iterator<ProduitV> iterator() {
				return new Iterator<ProduitV>() {
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
					public ProduitV next() {
						ProduitV p = null;
						if (qt.next()) {
							p = new ProduitV(temp);
						}
						return p;
					}
				};
			}
		};
	}

	/**
	 * Creer une "liste" iterable d'offres (coté vendeur) sans sauvegarder toutes
	 * les offres en memoire.
	 * 
	 * @param qt La querry a iterer
	 * @return Un iterable de Offre
	 */
	public static Iterable<Offre> iteratorOffre(QueriesItr qt) {
		return new Iterable<Offre>() {

			@Override
			public Iterator<Offre> iterator() {
				return new Iterator<Offre>() {
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
					public Offre next() {
						Offre p = null;
						if (qt.next()) {
							p = new Offre(temp);
						}
						return p;
					}
				};
			}
		};
	}

	/**
	 * Creer une "liste" iterable d'offres (coté acheteur) sans sauvegarder toutes
	 * les offres en memoire.
	 * 
	 * @param qt La querry a iterer
	 * @return Un iterable de Offre
	 */
	public static Iterable<OffreA> iteratorOffreA(QueriesItr qt) {
		return new Iterable<OffreA>() {

			@Override
			public Iterator<OffreA> iterator() {
				return new Iterator<OffreA>() {
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
					public OffreA next() {
						OffreA p = null;
						if (qt.next()) {
							p = new OffreA(temp);
						}
						return p;
					}
				};
			}
		};
	}

	/**
	 * Creer une "liste" iterable de produits vendus sans sauvegarder toutes les
	 * produits vendus en memoire.
	 * 
	 * @param qt La querry a iterer
	 * @return Un iterable de produits vendus
	 */
	public static Iterable<Achat> iteratorAchat(QueriesItr qt) {
		return new Iterable<Achat>() {

			@Override
			public Iterator<Achat> iterator() {
				return new Iterator<Achat>() {
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
					public Achat next() {
						Achat p = null;
						if (qt.next()) {
							p = new Achat(temp);
						}
						return p;
					}
				};
			}
		};
	}

	/**
	 * Cree une querry selon la categorie choisi, les prix minimums et maximums et
	 * les dates limites et n'affiche pas les produits du user actuel.
	 * 
	 * @param mainCatActuelle   La categorie superieur actuelle null si on est dans
	 *                          toutes les categorie.
	 * @param catActuelle       La categorie souhaitee actuelle.
	 * @param recherche         La string a chercher (on cherche tout les mots de
	 *                          longueur > 2 dans la phrase)
	 * @param prixMinimum       Le prix affiche minimum null s'il y en a pas
	 * @param prixMaximum       Le prix affiche maximum null s'il n'y en a pas
	 * @param prixOffertMinimum Le prix offert minimum null s'il n'y en a pas
	 * @param prixOffertMaximum Le prix offert maximum null s'il n'y en a pas
	 * @param minDate           La date minimale null s'il y en a pas
	 * @param maxDate           La date maximale null s'il y en a pas
	 * @return Le QueriesItr selon les criteres
	 */
	public static QueriesItr creatListProductQuery(String mainCatActuelle, String catActuelle, String recherche,
			Float prixMinimum, Float prixMaximum, Float prixOffertMinimum, Float prixOffertMaximum, Date minDate,
			Date maxDate) {

		String allProducts = "\nWITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,"
				+ " date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products)";

		String allCategorie = "";
		String joinCategorie = " JOIN categories ON categoryid = catid";

		String like = "";
		if (recherche != null) {
			// split la recherche en mots
			String[] words = recherche.split("[\\p{Punct}\\s]+");
			int i = 0;
			// Pour chaque mots de la recherche
			for (String w : words) {
				// Si la longueur du mot est plus grand que 2 on cherche s'il dans le nom ou la
				// description
				if (w.length() > 2) {
					w = w.toLowerCase();
					like += " LOWER( name ) LIKE '%" + w + "%' OR";
					like += " LOWER( description ) LIKE '%" + w + "%' OR";
					i++;
				}
			}

			if (i > 0) {
				like = "(" + like.substring(0, like.length() - 2) + ")";
			}
		}

		// Ajoute un prix min et max s'ils sont pas null
		String prixMin = "";
		if (prixMinimum != null) {
			prixMin = " sellingprice >= " + prixMinimum + " AND";
		}
		String prixMax = "";
		if (prixMaximum != null) {
			prixMax = " sellingprice <= " + prixMaximum + " AND";
		}

		// Ajoute une contrainte sur l'offre maximale s'ils ne sont pas null
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

		// Ajoute des contrainte sur les dates s'ils ne sont pas null
		String dateMin = "";
		if (minDate != null) {
			dateMin = " date >='" + minDate.toString() + "' AND";
		}
		String dateMax = "";
		if (maxDate != null) {
			dateMax = " date <='" + maxDate.toString() + "' AND";
		}

		// N'affiche pas les annonce du user si un user est connecte.
		String user = "";
		if (MainControleur.getUtilisateur() >= 0) {
			user = " sellerid <> " + MainControleur.getUtilisateur() + " AND";
		}

		// Si un des champs de restriction n'est pas vide on l'ajoute a la requete
		if (!(prixMin.isEmpty() && prixMax.isEmpty() && dateMin.isEmpty() && dateMax.isEmpty() && user.isEmpty()
				&& like.isEmpty())) {
			allProducts = "\nWITH allProducts AS (SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,"
					+ " date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products WHERE ";
			allProducts += prixMin;
			allProducts += prixMax;
			allProducts += dateMin;
			allProducts += dateMax;
			allProducts += user;

			// Si la recherche de mots est vide on enleve le denier et sinon on le garde
			if (like.isEmpty()) {
				allProducts = allProducts.substring(0, allProducts.length() - 3) + ")";
			} else {
				allProducts += like + ")";
			}

		}

		// Contraint les categories si elles ne sont pas null
		if (mainCatActuelle != null) {

			// S'assure de ne pas avoir de bug d'apostrophe
			mainCatActuelle = mainCatActuelle.replace("'", "''");
			catActuelle = catActuelle.replace("'", "''");

			// Si on est dans une categorie principale on regarde toutes ses enfants
			// sinon on se limite a la categorie
			if (mainCatActuelle == CatalogueController.parent) {
				allCategorie = ",\n mainCatActuelle AS (SELECT maincatid FROM maincategories WHERE maincatname = '"
						+ catActuelle + "')";
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
				+ "\n SELECT refid, name, description, sellingprice, sellername, date, maxoffer, categoryid, catname, date, estimatedprice  FROM allProducts"
				+ joinCategorie;
		query += prixOffertWhere;
		query += prixOffertMin;
		query += prixOffertAnd;
		query += prixOffertMax;
		query += ";";
		QueriesItr t = new QueriesItr(query);
		System.out.println("Requête crée = " + query);
		return t;
	}
}
