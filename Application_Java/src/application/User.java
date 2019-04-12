package application;

/**
 * Classe User, definit un utilisatuer de l'application
 * 
 * @author Jonathan Caspar, Jules Cohen, Jean-Francois Blanchette et Tanahel
 *         Huot-Roberge
 *
 */
public class User {

	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String phone;

	/**
	 * Constructeur de l'utilisateur
	 * 
	 * @param firstname prénom de l'utilisateur
	 * @param lastname  nom de l'utilisateur
	 * @param username  ID de l'utilisateur
	 * @param password  mot de passe de l'utilisateur
	 * @param phone     numéro de téléphone de l'utilisateur
	 */
	public User(String firstname, String lastname, String username, String password, String phone) {

		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.phone = phone;
	}

}
