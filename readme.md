Projet final - Base de données
=======================================

## 1. Diagramme Entité-Association

![](img/modeleEA.png)

## 2. Modèle relationnel

* Buyer (__buyerID__,  username, password, firstname, lastname, phone) 

* Seller(__sellerID__, username, password, firstname, lastname, phone) 

* Product(__refID__, #sellerID, #categoryID, estimatedPrice, sellingPrice, date) 

* Offer(__offerID__, #buyerID, #productID, price, date) 

* MainCategory(__mainCategoryID__, name) 

* Category(__categoryID__, #mainCategoryID, name) 
