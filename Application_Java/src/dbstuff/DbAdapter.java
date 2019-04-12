package dbstuff;

import java.sql.*;

/**
 * Classe DbAdapteur, definit la connnection au serveur sql.
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class DbAdapter {
	public static final String[] DB_TABLES = { "users", "maincategories", "categories", "products", "offers",
			"soldproducts" };
	// variables de connexion a la db
	String jdbUrl;
	String username;
	String password;

	public static Connection con = null;
	Statement stt = null;
	ResultSet rs = null;

	// Constructeur
	public DbAdapter(String jdbUrl, String username, String password) {
		this.jdbUrl = jdbUrl;
		this.username = username;
		this.password = password;
	}

	public void connecter() {
		try {
			con = DriverManager.getConnection(jdbUrl, username, password);
			System.out.println("Connexion etablie");

			stt = con.createStatement();
			stt.execute("set search_path to casparjo ;");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stt != null) {
				try {
					stt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// les requetes se font par le RS (resultSet) et on peut parcourir l'ensemble de
	// r�sultats dans le while pour y extraire les donnees

	public void afficher() {
		try {
			rs = stt.executeQuery("Select * from users");
			while (rs.next()) {
				System.out.println(rs);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deconnecter() {
		if (stt != null)
			try {
				stt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
