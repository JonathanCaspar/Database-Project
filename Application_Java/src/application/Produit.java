package application;

import java.time.LocalDate;

import controleur.ControleurOffrir;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Produit {
	private String nomProduit = null;
	private String description = "description";

	private float prix = 0;
	private float oMax = 0;
	private float estimation = 0;

	private LocalDate date = null;
	private String categorie = null;
	private String vendeur = null;

	/**
	 * Constructeur de produit selon la date en tant que LocalDate
	 * 
	 * @param produit     Le nom du produit.
	 * @param description La description du produit.
	 * @param prix        Le prix proposer du produit.
	 * @param oMax        L'offre maximale actuelle.
	 * @param estiomation L'estimation de l'expert.
	 * @param date        La date d'apparition de produit.
	 * @param categorie   La categorie du produit.
	 * @param vendeur     Le nom du vendeur.
	 */
	public Produit(String produit, String description, float prix, float oMax, float estimation, LocalDate date,
			String categorie, String vendeur) {
		super();
		this.nomProduit = produit;
		this.prix = prix;
		this.oMax = oMax;
		this.estimation = estimation;
		this.date = date;
		this.categorie = categorie;
		this.description = description;
		this.vendeur = vendeur;
	}

	/**
	 * Constructeur de produit selon la date en tant que 3 Integers.
	 * 
	 * @param produit     Le nom du produit.
	 * @param description La description du produit.
	 * @param prix        Le prix proposer du produit.
	 * @param oMax        L'offre maximale actuelle.
	 * @param estiomation L'estimation de l'expert.
	 * @param jour        Le jour de la date d'apparution.
	 * @param mois        Le mois de la date d'apparution.
	 * @param annee       L'annee de la date d'apparution. La categorie du produit.
	 * @param vendeur     Le nom du vendeur.
	 */
	public Produit(String produit, String description, float prix, float oMax, float estimation, int jour, int mois,
			int annee, String categorie, String vendeur) {
		super();
		this.nomProduit = produit;
		this.prix = prix;
		this.oMax = oMax;
		this.estimation = estimation;
		this.date = LocalDate.of(annee, mois, jour);
		this.description = description;
		this.categorie = categorie;
		this.vendeur = vendeur;
	}

	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String produit) {
		nomProduit = produit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrix() {
		return String.format("%.2f", prix) + " $";
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public String getOMax() {
		return String.format("%.2f", oMax) + " $";
	}

	public void setOMax(float oMax) {
		this.oMax = oMax;
	}

	public String getDate() {
		return date.toString();
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		categorie = categorie;
	}

	public String getVendeur() {
		return vendeur;
	}

	public void setVendeur(String vendeur) {
		this.vendeur = vendeur;
	}

	/*
	 * Ouvre la fenetre du produit et attend sa fereture.
	 */
	public Float OpenWindow() {
		Float offre = null;

		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_Item.fxml"));
			Scene scene = new Scene(loader.load());

			ControleurOffrir ctl = (ControleurOffrir) loader.getController();
			ctl.setStage(primaryStage, this);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Offre");
			primaryStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return offre;
	}
}
