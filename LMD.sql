START TRANSACTION;

--1) Catalogue
--Afficher les catégories principales sur la colonne gauche :
SELECT * FROM maincategories ORDER BY maincatname;

--Pour chaque catégorie principale : récupérer les sous-catégories et les ajouter dans la colonne de gauche (exemple avec Meubles : id = 6) :
SELECT catid, catname FROM categories WHERE maincatid = 6 ORDER BY catname;

--En cliquant sur une catégorie : afficher tous les produits (hormis ceux vendus par l'utilisateur lui même) Exemple avec toutes catégories confondues, on applique un filtre suivant : prix annoncé compris entre 100$ et 250$, prix offert compris entre 100$ et 250$, date de publication comprise entre le 08-04-2019 et le 10-04-2019
WITH allProducts AS 
(SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername,
 date, getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice 
 FROM products WHERE 
 sellingPrice >= 100 AND sellingprice <= 250.0 
 AND date >='2019-04-08' AND date <='2019-04-10' 
 AND sellerid <> 18 )
 
SELECT refid, name, description, sellingprice, sellername, date, maxoffer, catname, date, estimatedprice 
FROM allProducts JOIN categories ON categoryid = catid 
WHERE maxoffer >= 100.0 AND maxoffer <= 250.0;

--2) Mes annonces
--Afficher les produits en vente par l'utilisateur (id=18) en précisant le montant de l'offre maximale pour chaque produit
WITH allProducts AS 
(SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername, date, 
 getMaxOfferValue(refid) AS maxoffer, categoryid, estimatedprice  FROM products WHERE sellerid = 18)

SELECT refid, name, description, sellingprice, sellername, date, maxoffer, catname, date, estimatedprice
FROM allProducts JOIN categories ON categoryid = catid;

--Afficher les offres liées à l'objet selectionné (exemple produitid = 21)
SELECT * FROM offers WHERE productid = 21;

--Accepter l'offre de l'acheteur (id=25) de 33$ sur le produit "Robe Blanche" (description = nom) classé dans la catégorie "Femmes-Haut" (catid=14) vendu par le vendeur d'id=100 au prix initial de 38.69$ et estimé à 38.69$. On insère le produit vendu dans la table soldproducts et on supprime le produit de la table products (ainsi que ses autres offres grâce au ON DELETE CASCADE)
INSERT INTO soldproducts(name, description, sellerid, buyerid, categoryid, estimatedprice, sellingprice, soldprice) 
VALUES ('Robe blanche', 'Robe blanche', 100, 25, 14, 38.69, 38.69, 33);

--3) Mes achats
--Retourne la liste détaillée des offres actives de l'utilisateur connecté
SELECT name, getUserFullName(sellerid) AS sellername, sellingprice, price ,offers.date AS dateO 
FROM products JOIN offers ON productid = refid WHERE buyerid = 18;

--Retourne la liste détaillée des produits déjà achetés par l'utilisateur (pour lesquels son offre a été acceptée)
SELECT name, getUserFullName(sellerid) AS sellername, soldprice, datetransaction 
FROM soldproducts WHERE buyerid =18;

--4) Autres requêtes spéficiques
--Trouve les produits les plus en demande (ceux avec le plus d'offres) par mainCategories
WITH nombreOfrreParProduit AS 
      (SELECT count(*) AS nbrOffre, productid
          FROM offers GROUP BY productid),
  mainCatWithChildren AS
      (SELECT catid, maincatid, maincatname
          FROM categories NATURAL JOIN maincategories),
  productsWithNbrOffers AS
      (SELECT *
          FROM products JOIN nombreOfrreParProduit
          ON refid = productid),
  mainCatWithProduct AS
    (SELECT  maincatname, refid, name, description, sellerid, 
             categoryid, estimatedprice, sellingprice, nbrOffre
          FROM productsWithNbrOffers JOIN mainCatWithChildren ON catid = categoryid),
  maxByMainCategory AS
      (SELECT  maincatname, MAX(ALL nbrOffre) as nbrOffre
          FROM mainCatWithProduct
          GROUP BY maincatname)
  SELECT * 
      FROM mainCatWithProduct NATURAL JOIN maxByMainCategory;

COMMIT;