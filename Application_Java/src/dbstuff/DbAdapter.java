package dbstuff;

import java.sql.*;

public class DbAdapter {
	//variables de connexion a la db 
	String jdbUrl;
	String username;
	String password;
		

	Connection con = null;
	Statement stt = null; 
	ResultSet rs = null;
	
	//Constructeur
	public DbAdapter(String jdbUrl, String username, String password) {
		this.jdbUrl=jdbUrl;
		this.username=username;
		this.password=password;
		
	}
	
	
	
	
	public void connecter() {
		try {
			con = DriverManager.getConnection(jdbUrl, username, password);
			System.out.println("Connexion établie");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//les requetes se font par le RS (resultSet) et on peut parcourir l'ensemble de résultats dans le while pour y extraire les donnees
	
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
