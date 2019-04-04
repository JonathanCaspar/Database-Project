Projet final - Base de données
=======================================

## Sommaire :

1. [Diagramme Entité-Association](#section1)
2. [Modèle relationnel](#section2)
3. [Définition de la base de données](#section3)
4. [Ensemble des requêtes en SQL et explications des résultats attendus](#section4)
	1. [Insertion d'utilisateurs](#section4-1)
	2. [Insertion de catégories principales](#section4-2)
	3. [Insertion de sous-catégories](#section4-3)
	4. [Insertion de produits](#section4-4)
	5. [Insertion d'offres](#section4-5)
	6. [Insertion de produits déjà vendus](#section4-6)
	7. [Fonctions](#section4-7)
	8. [Requêtes-type utilisées par l'application](#section4-8)
	
5. [Guide utilisateur](#section5)

<a id="section1"></a>
## 1. Diagramme Entité-Association

![](modeleEA/modeleEA.png)

<a id="section2"></a>
## 2. Modèle relationnel

* User(__userID__, username, password, firstname, lastname, phone) 

* Product(__refID__, name, description, #sellerID, #categoryID, estimatedPrice, sellingPrice, date) 

* Offer(__offerID__, #buyerID, #productID, price, date) 

* MainCategory(__mainCategoryID__, name) 

* Category(__catID__, #mainCatName, catName) 

* SoldProducts(__id__, name, description, #sellerID, #buyerID, #categoryID, estimatedPrice, sellingPrice, soldPrice, dateTransaction) 

SoldProducts est une table de __log__ conservant l'historique des produits vendus.
  

<a id="section3"></a>
## 3. Définition de la base de données ([DDL.sql](DDL.sql))

~~~~sql
DROP TABLE IF EXISTS soldproducts, offers, products, categories, maincategories, 
users; 

CREATE TABLE users ( 
     userid      SERIAL, 
     username    VARCHAR(32) NOT NULL, 
     password    VARCHAR(32) NOT NULL, 
     firstname   VARCHAR(20) NOT NULL, 
     lastname    VARCHAR(20), 
     phonenumber VARCHAR(12), 
     PRIMARY KEY (userid), 
     UNIQUE (username) 
  ); 

CREATE TABLE maincategories ( 
     maincatid   SERIAL, 
     maincatname VARCHAR(40) NOT NULL, 
     PRIMARY KEY (maincatid),
     UNIQUE(maincatname)
  ); 

CREATE TABLE categories ( 
     catid       SERIAL, 
     catname     VARCHAR(40) NOT NULL, 
     maincatid   SERIAL NOT NULL, 
     PRIMARY KEY (catid),
     UNIQUE(catname, maincatid),
     FOREIGN KEY (maincatid) REFERENCES maincategories(maincatid) 
  ); 

CREATE TABLE products ( 
     refid          SERIAL, 
     name           VARCHAR(40) NOT NULL, 
     description    VARCHAR(150) NOT NULL, 
     sellerid       INT NOT NULL, 
     categoryid     INT NOT NULL, 
     estimatedprice NUMERIC(10, 2) NOT NULL, 
     sellingprice   NUMERIC(10, 2) NOT NULL, 
     date           TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
     PRIMARY KEY (refid), 
     FOREIGN KEY (sellerid) REFERENCES users(userid), 
     FOREIGN KEY (categoryid) REFERENCES categories(catid) 
  ); 

CREATE TABLE offers ( 
     offerid   SERIAL, 
     buyerid   INT NOT NULL, 
     productid INT NOT NULL, 
     price     NUMERIC(10, 2) NOT NULL, 
     date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
     PRIMARY KEY (offerid), 
     FOREIGN KEY (buyerid) REFERENCES users(userid), 
     FOREIGN KEY (productid) REFERENCES products(refid) ON DELETE CASCADE,
     UNIQUE(offerid, buyerid)
  ); 

CREATE TABLE soldproducts ( 
     id              SERIAL, 
     name            VARCHAR(40) NOT NULL, 
     description     VARCHAR(150) NOT NULL, 
     sellerid        INT NOT NULL, 
     buyerid         INT NOT NULL, 
     categoryid      INT NOT NULL, 
     estimatedprice  NUMERIC(10, 2) NOT NULL, 
     sellingprice    NUMERIC(10, 2) NOT NULL, 
     soldprice       NUMERIC(10, 2) NOT NULL, 
     datetransaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
     PRIMARY KEY (id), 
     FOREIGN KEY (sellerid) REFERENCES users(userid), 
     FOREIGN KEY (buyerid) REFERENCES users(userid), 
     FOREIGN KEY (categoryid) REFERENCES categories(catid) 
  ); 
~~~~

<a id="section4"></a>
## 4. L'ensemble des requêtes en SQL et explications des résultats attendus ([LMD.sql](LMD.sql))
<a id="section4-1"></a>
### Insertion d'utilisateurs
<details>
  	<summary>Cliquer ici pour tout afficher.</summary>
	
~~~~sql
INSERT INTO users (userid, username, password, firstname, lastname, phonenumber) VALUES
(1, 'rkirmond0', '6H7Znp5e', 'Roxi', 'Kirmond', '828-346-4552'),
(2, 'clevicount1', 'R2DK20yrhX', 'Costa', 'Levicount', '858-452-1250'),
(3, 'nscoular2', 'USZPh0l', 'Niel', 'Scoular', '468-513-7460'),
(4, 'acholdcroft3', 'D6u9ueH6s', 'Ashlan', 'Choldcroft', '195-876-3830'),
(5, 'mkubatsch4', 'FNbAyE', 'Maia', 'Kubatsch', '849-790-3079'),
(6, 'lgouly5', 'KOU2LR', 'Larina', 'Gouly', '313-885-1632'),
(7, 'jbartell6', 'WYXAzd', 'Julienne', 'Bartell', '960-523-6779'),
(8, 'dmaclennan7', 'h5mH7LJZ', 'Daphna', 'MacLennan', '428-671-8512'),
(9, 'sribeiro8', 'KdyipR', 'Seana', 'Ribeiro', '339-399-9872'),
(10, 'npargent9', 'wFCmBh3L0LZ6', 'Nat', 'Pargent', '692-440-3222'),
(11, 'oantoniewskia', '3szlipQ9df2c', 'Ogdon', 'Antoniewski', '467-523-8596'),
(12, 'aparellb', '1YvIBqYPnPZK', 'Aloin', 'Parell', '863-734-1771'),
(13, 'jcoulthurstc', 'FUSjwdh', 'Jannelle', 'Coulthurst', '449-969-9945'),
(14, 'stapleyd', '8Gaq5mPX8', 'Sherrie', 'Tapley', '818-446-4195'),
(15, 'ajeanenete', 'Tf84fhgqfE', 'Augustine', 'Jeanenet', '636-469-7862'),
(16, 'ebuttelf', 'iVb7P48S', 'Ermentrude', 'Buttel', '501-211-7881'),
(17, 'khartnupg', 'v0IZbJXjvz', 'Kyla', 'Hartnup', '759-645-1728'),
(18, 'cpeticanh', 'C1M7YSvCCMZx', 'Courtenay', 'Petican', '999-900-4577'),
(19, 'jtrengovei', '3DCswY', 'Joye', 'Trengove', '227-197-1445'),
(20, 'egolsthorpj', 'vqG7jtqUk', 'Ermin', 'Golsthorp', '246-712-8150'),
(21, 'jtomneyk', 'i8Wek2NCM', 'Jean', 'Tomney', '819-882-1404'),
(22, 'mmillierl', 'rFIjIq', 'Myer', 'Millier', '415-501-8105'),
(23, 'cstockneym', 'qzF6pGUQ', 'Camey', 'Stockney', '439-239-8069'),
(24, 'agrishechkinn', 'DyHnRTv', 'Angelle', 'Grishechkin', '556-383-3119'),
(25, 'drichardo', 'EesF2NOaOB', 'Deborah', 'Richard', '981-591-6161'),
(26, 'rrounsefellp', 'yGUxYKji', 'Rossie', 'Rounsefell', '848-957-1083'),
(27, 'emcterrellyq', 'QNnHiLibcjK', 'Eustace', 'McTerrelly', '534-653-2677'),
(28, 'dlomasnyr', 'TdL3ovqzRUJI', 'Dacey', 'Lomasny', '747-253-9572'),
(29, 'etremontanas', '3zVVK6i', 'Enid', 'Tremontana', '660-149-0263'),
(30, 'mferneyhought', 'TLTyfvlwdRo', 'Maison', 'Ferneyhough', '728-422-7418'),
(31, 'brispinu', 'e6lLP6dyzI', 'Berti', 'Rispin', '608-631-2810'),
(32, 'flemeryv', 'RuMdCx6b5Xi', 'Freeland', 'Lemery', '640-530-2309'),
(33, 'bhitchensw', 'ReNYw5OrHo', 'Bibbie', 'Hitchens', '114-478-1961'),
(34, 'hgrimshawx', 'aZtTAKlkHa', 'Hershel', 'Grimshaw', '639-588-1122'),
(35, 'kwhertony', '8roHmlo5gBO', 'Katrine', 'Wherton', '887-135-1180'),
(36, 'klissandriz', 'NEsgcRsp7U', 'Krissy', 'Lissandri', '247-468-9490'),
(37, 'gbradnocke10', 'lk1aZ6', 'Gustave', 'Bradnocke', '740-868-3093'),
(38, 'wrichens11', 'p2nqayG4mCA', 'Winn', 'Richens', '527-313-8070'),
(39, 'astoakley12', '4qcaxoP', 'Alia', 'Stoakley', '572-289-0194'),
(40, 'jhardistry13', 'ZozaZh3', 'Josy', 'Hardistry', '387-345-3819'),
(41, 'nsambedge14', 'dQ4pPhjRnhcH', 'Nichole', 'Sambedge', '389-394-7977'),
(42, 'jsarra15', 'bzUHzV4ud', 'Jasper', 'Sarra', '777-522-4500'),
(43, 'jfrie16', 'YIQCN6sm', 'Joshuah', 'Frie', '541-808-2629'),
(44, 'kvandermerwe17', 'CV1v0V', 'Kristal', 'Van der Merwe', '119-755-6390'),
(45, 'vutley18', 'uAWGtttVJFYs', 'Vlad', 'Utley', '851-509-9387'),
(46, 'lbudgett19', 'txpbfby', 'Lewes', 'Budgett', '500-267-3655'),
(47, 'gstancliffe1a', 'sHuEPnQ6o', 'Grange', 'Stancliffe', '722-504-3192'),
(48, 'lsacase1b', '5SB5Amql', 'Lavena', 'Sacase', '984-878-7071'),
(49, 'woakland1c', 'jiqfbp', 'Winifred', 'Oakland', '510-910-8743'),
(50, 'tdonett1d', 'MqmiI0z', 'Tiffani', 'Donett', '407-866-0534'),
(51, 'nkimberly1e', 'kEDEgY0Z76', 'Niels', 'Kimberly', '708-448-6700'),
(52, 'dheatley1f', 'YpsfYWCEBI', 'Dreddy', 'Heatley', '948-735-2567'),
(53, 'clong1g', 'sv70zppVP', 'Cleveland', 'Long', '151-611-0565'),
(54, 'eabramowitz1h', 'Gki0DIet', 'Etti', 'Abramowitz', '104-754-9319'),
(55, 'rburch1i', 'S93n3TizxZ', 'Raine', 'Burch', '460-158-5254'),
(56, 'jpurse1j', 'Q9TOsd', 'Jennette', 'Purse', '809-176-4168'),
(57, 'sgallimore1k', 'LGXyqMx84jpf', 'Sven', 'Gallimore', '644-402-7617'),
(58, 'rpiche1l', '4p89hPEZAgX', 'Rochell', 'Piche', '452-398-3611'),
(59, 'cdillistone1m', 'XFEp5ho', 'Carmencita', 'Dillistone', '892-969-2593'),
(60, 'greasun1n', 'oJmiGq3r', 'Gabbi', 'Reasun', '661-101-6496'),
(61, 'nhitschke1o', 'aZBwECIdzFH', 'Norris', 'Hitschke', '154-521-3801'),
(62, 'grawe1p', 'MVmdu5UGA20B', 'Gisella', 'Rawe', '960-283-8213'),
(63, 'ncolthard1q', 'aZMWDNB', 'Nicholas', 'Colthard', '796-239-2296'),
(64, 'ipowdrill1r', 'BNQgU9e', 'Ivette', 'Powdrill', '472-632-8378'),
(65, 'dwingar1s', 'tL5pJFgMSRs', 'Davine', 'Wingar', '399-190-5581'),
(66, 'cblackley1t', '74P4HQBcI', 'Catriona', 'Blackley', '232-802-5532'),
(67, 'kpegrum1u', 'ccSPpf', 'Konstanze', 'Pegrum', '977-501-4882'),
(68, 'kporkiss1v', 'Z1q25r', 'Karlen', 'Porkiss', '215-504-9035'),
(69, 'apurver1w', 'Vk24luyC3O', 'Ashlie', 'Purver', '969-695-9676'),
(70, 'jleworthy1x', 'nCz3fKvtH', 'Jeniffer', 'Leworthy', '815-807-7625'),
(71, 'credit1y', 'iavtwTSk', 'Celestine', 'Redit', '167-525-2794'),
(72, 'dreading1z', 'yYuIbHe', 'Drucy', 'Reading', '853-371-3954'),
(73, 'spenhalewick20', '3G12A8fba', 'Samson', 'Penhalewick', '490-523-0933'),
(74, 'gleat21', 'Ljf5hDhim', 'Gunilla', 'Leat', '427-852-4693'),
(75, 'ihrycek22', 'NPJMroIzvJRq', 'Irina', 'Hrycek', '778-648-9781'),
(76, 'rlaville23', '9H7yCVP', 'Rochester', 'Laville', '700-904-0141'),
(77, 'hagronski24', 'jLZEIzOXA4MW', 'Heddie', 'Agronski', '948-277-0215'),
(78, 'acreegan25', 'z9NyyIQKqwsb', 'Ashli', 'Creegan', '837-600-0535'),
(79, 'dmccromley26', 'Is9bnydN', 'Davide', 'McCromley', '124-191-3100'),
(80, 'edenington27', 'Fsp1e20YGxr', 'Edlin', 'Denington', '286-540-3932'),
(81, 'aruoss28', 'yqXai6uMQTm', 'Armin', 'Ruoss', '486-234-4441'),
(82, 'kspensly29', 'VNSv4MjJjt', 'Katrina', 'Spensly', '689-912-4236'),
(83, 'upadley2a', 'NtxC1UfGlYjT', 'Ulrick', 'Padley', '382-583-0942'),
(84, 'rcoffey2b', 'PMaPwzD4A', 'Raeann', 'Coffey', '738-622-9939'),
(85, 'ddelaharpe2c', 'pFp9kCUzd', 'Dewitt', 'De la Harpe', '294-926-5247'),
(86, 'lvinden2d', 'uShGF4mIn', 'Law', 'Vinden', '527-527-1176'),
(87, 'abilton2e', 'pJLdPIosV', 'Aldin', 'Bilton', '998-670-3641'),
(88, 'bheavy2f', 'DkHJn2KhQk4', 'Burke', 'Heavy', '923-373-0533'),
(89, 'lwillbraham2g', 'LlvwzZd', 'Laurie', 'Willbraham', '479-296-3607'),
(90, 'cbrickwood2h', 'BvibRwpo', 'Colette', 'Brickwood', '346-510-1159'),
(91, 'kpoulsom2i', '18yzgzYsvKi', 'Koren', 'Poulsom', '213-144-1393'),
(92, 'nkenington2j', 'Od5bZqYjBw', 'Nessa', 'Kenington', '521-413-9272'),
(93, 'swhatsize2k', 'xKPkIeSk06', 'Sallyann', 'Whatsize', '145-305-8250'),
(94, 'kgergher2l', 'cTYpVhld69', 'Kilian', 'Gergher', '706-102-5638'),
(95, 'pcunrado2m', 'Qqp8t3Z', 'Pauly', 'Cunrado', '457-189-9233'),
(96, 'mmccrea2n', '6A1p4IFN', 'Marillin', 'McCrea', '975-869-1131'),
(97, 'jblackler2o', 'ynW50ZnOkxn', 'Judi', 'Blackler', '990-144-8805'),
(98, 'emiddiff2p', 'emx4T34rW', 'Eulalie', 'Middiff', '373-882-3954'),
(99, 'bmyhan2q', 'SClyDi3As', 'Brock', 'Myhan', '706-407-2891'),
(100, 'lconyers2r', 'vW059AXw', 'Lucas', 'Conyers', '626-363-4727');
~~~~
</details>

<a id="section4-2"></a>
### Insertion de catégories principales
~~~~sql
INSERT INTO maincategories (maincatid, maincatname) VALUES
(1, 'Immobilier'),
(2, 'Audio'),
(3, 'Appareils électroménagers'),
(4, 'Vêtements'),
(5, 'Livres'),
(6, 'Meubles'),
(7, 'Téléphones'),
(8, 'Jeux vidéo et consoles'),
(9, 'Vélos'),
(10, 'Ecrans'),
(11, 'Maison - Intérieur'),
(12, 'Outils'),
(13, 'Ordinateurs et tablettes');
~~~~
<a id="section4-3"></a>
### Insertion de sous-catégories
~~~~sql
INSERT INTO categories (maincatid, catname) VALUES
(1, 'Condo'),
(1, 'Appartement'),
(1, 'Maison'),
(2, 'Haut-parleurs'),
(2, 'Chaînes stéréo'),
(2, 'Ecouteurs'),
(2, 'iPod et MP3'),
(3, 'Réfrigérateurs'),
(3, 'Laveuses et sécheuses'),
(3, 'Cuisinières, fours et fourneaux'),
(3, 'Machines à café'),
(3, 'Fours à micro-ondes'),
(3, 'Aspirateurs'),
(4, 'Femmes - Hauts'),
(4, 'Enfants'),
(4, 'Hommes'),
(4, 'Femmes - Sacs'),
(4, 'Chaussures pour femmes'),
(4, 'Chaussures pour hommes'),
(5, 'Manuels'),
(5, 'Bandes dessinéees'),
(5, 'Magazines'),
(5, 'Ouvrages de fiction'),
(5, 'Essais'),
(6, 'Chaises, Fauteuils'),
(6, 'Mobilier de cuisine et salle à manger'),
(6, 'Sofas'),
(6, 'Lits et matelas'),
(6, 'Commodes et armoires'),
(7, 'Téléphones cellulaires'),
(7, 'Accessoires pour cellulaires'),
(7, 'Téléphones résidentiels et répondeurs'),
(8, 'Sony PlayStation 4'),
(8, 'Consoles classiques'),
(8, 'XBOX One'),
(8, 'XBOX 360'),
(8, 'Sony PlayStation 3'),
(8, 'Nintendo Wii'),
(8, 'Nintendo DS'),
(9, 'De route'),
(9, 'Enfants'),
(9, 'Randonné, ville'),
(9, 'Vélos électriques'),
(9, 'Fixie'),
(10, 'Téléviseurs'),
(10, 'Ecrans d''ordinateur'),
(11, 'Décoration intérieure et accessoires'),
(11, 'Vaisselle et articles de cuisine'),
(11, 'Eclairage intérieur et plafonniers'),
(11, 'Literie'),
(11, 'Rangement et organisation'),
(11, 'Tapis et moquettes'),
(12, 'Outils électriques'),
(12, 'Outils à main'),
(12, 'Rangement pour outils et établis'),
(12, 'Echelles et échafaudages'),
(13, 'Ordinateurs Apple'),
(13, 'Ordinateurs Acer'),
(13, 'Ordinateurs Samsung'),
(13, 'Ordinateurs Lenovo'),
(13, 'Autres ordinateurs'),
(13, 'Tablettes');
~~~~
<a id="section4-4"></a>
### Insertion de produits
~~~~sql
INSERT INTO products (estimatedprice, sellingprice, sellerid, categoryid, description, name) VALUES
('54.10', '54.10', 1, 25, 'Chaises IKEA', 'Chaises IKEA'),
('201.34', '201.34', 1, 45, 'Téléviseur Sony', 'Téléviseur Sony'),
('68.17', '68.17', 1, 25, 'Fauteuil IKEA', 'Fauteuil IKEA'),
('112.09', '112.09', 1, 12, 'Four micro-ondes Samsung bon état', 'Four micro-ondes Samsung bon état'),
('174.88', '174.88', 2, 9, 'Laveuse abimée Samsung', 'Laveuse abimée Samsung'),
('139.88', '139.88', 2, 62, 'Tablette Amazon Fire 8', 'Tablette Amazon Fire 8'),
('179.68', '179.68', 3, 62, 'Samsung Galaxy Tab', 'Samsung Galaxy Tab'),
('75.84', '75.84', 4, 52, 'Tapis antique', 'Tapis antique'),
('219.65', '219.65', 5, 44, 'Fixie fibre de carbone', 'Fixie fibre de carbone'),
('11.99', '11.99', 6, 54, 'Tournevis', 'Tournevis'),
('237.09', '237.09', 7, 28, 'Matelas taille King', 'Matelas taille King'),
('113.93', '113.93', 7, 11, 'Machine Keurig 2 Tasses', 'Machine Keurig 2 Tasses'),
('65.28', '65.28', 8, 17, 'Sac en cuir véritable', 'Sac en cuir véritable'),
('15.69', '15.69', 9, 22, 'Magazine Sciences', 'Magazine Sciences'),
('93.74', '93.74', 10, 47, 'Tableau 12x28', 'Tableau 12x28'),
('171.39', '171.39', 10, 39, 'Nintendo 3DS blanche', 'Nintendo 3DS blanche'),
('213.23', '213.23', 11, 37, 'Playstation 3 non fonctionnelle', 'Playstation 3 non fonctionnelle'),
('165.49', '165.49', 15, 36, 'XBOX 360 neuve', 'XBOX 360 neuve'),
('500.00', '500.00', 19, 57, 'MacBook 2018', 'MacBook 2018'),
('94.08', '94.08', 26, 47, 'Rideau blanc', 'Rideau blanc'),
('92.30', '92.30', 28, 47, 'Rideau noir', 'Rideau noir'),
('131.43', '131.43', 28, 25, 'Chaise haute', 'Chaise haute'),
('132.27', '132.27', 29, 45, 'TV 60 pouces', 'TV 60 pouces'),
('170.70', '170.70', 30, 38, 'Wii avec 2 manettes', 'Wii avec 2 manettes'),
('31.67', '31.67', 45, 16, 'Chandail bleu', 'Chandail bleu'),
('5.66', '5.66', 56, 16, 'Tuque en laine rouge', 'Tuque en laine rouge'),
('810.00', '810.00', 57, 2, 'Appartement 4 1/2 ensoleillé', 'Appartement 4 1/2 ensoleillé'),
('560.00', '560.00', 57, 2, 'Studio à Verdun', 'Studio à Verdun'),
('750.00', '750.00', 57, 2, '3 1/2 avec balcon', '3 1/2 avec balcon'),
('1150.00', '1150.00', 58, 1, 'Condo Ile des Soeurs', 'Condo Ile des Soeurs'),
('1300.00', '1300.00', 58, 1, 'Condo centre ville', 'Condo centre ville'),
('1200.00', '1200.00', 67, 3, 'Maison 5 1/2 avec piscine', 'Maison 5 1/2 avec piscine'),
('1450.00', '1450.00', 67, 3, 'Maison Westmount avec mezannine', 'Maison Westmount avec mezannine'),
('1450.00', '1450.00', 68, 3, 'Maison avec demi sous-sol', 'Maison avec demi sous-sol'),
('299.64', '299.64', 75, 17, 'Sac à main Gucci', 'Sac à main Gucci'),
('1021.00', '1021.00', 79, 60, 'Laptop Lenovo 17 pouces', 'Laptop Lenovo 17 pouces'),
('400.00', '400.00', 85, 57, 'iMac', 'iMac'),
('349.00', '349.00', 87, 62, 'iPad 64 Gb', 'iPad 64 Gb'),
('25.49', '25.49', 87, 16, 'Jeans bleu pour homme', 'Jeans bleu pour homme'),
('75.70', '75.70', 92, 19, 'Chaussures Nike', 'Chaussures Nike'),
('80.11', '80.11', 92, 18, 'Talons hauts', 'Talons hauts'),
('16.00', '16.00', 93, 19, 'Gougounnes Homme', 'Gougounnes Homme'),
('40.56', '40.56', 94, 18, 'Gougounnes Femme', 'Gougounnes Femme'),
('850.00', '850.00', 96, 8, 'Frigo americain LG', 'Frigo americain LG'),
('900.00', '900.00', 98, 9, 'Laveuse Samsung', 'Laveuse Samsung'),
('15.00', '15.00', 98, 12, 'Four micro ondes defectueux', 'Four micro ondes defectueux'),
('120.79', '120.79', 98, 26, 'Comptoir blanc IKEA', 'Comptoir blanc IKEA'),
('188.41', '188.41', 98, 26, 'Table en bois massif', 'Table en bois massif'),
('299.00', '299.00', 99, 27, 'Sofa en cuir', 'Sofa en cuir'),
('38.69', '38.69', 100, 14, 'Robe blanche', 'Robe blanche');
~~~~
<a id="section4-5"></a>
### Insertion d'offres
~~~~sql
INSERT INTO offers (buyerid, productid, price) VALUES 
(25, 50, 33.0),
(26, 4, 99.37),
(93, 21, 80.16),
(92, 47, 116.63),
(96, 49, 238.25),
(29, 43, 35.24),
(56, 4, 102.24),
(23, 46, 11.1),
(59, 48, 172.04),
(7, 35, 219.53),
(36, 5, 117.84),
(100, 40, 45.1),
(80, 40, 54.14),
(28, 34, 1100),
(3, 34, 1050),
(16, 45, 750),
(49, 44, 700),
(74, 31, 1210),
(34, 27, 620),
(27, 28, 300),
(23, 37, 305),
(2, 35, 189.62),
(96, 11, 212.71),
(98, 27, 785),
(74, 18, 150),
(38, 22, 110.85),
(99, 21, 50.98),
(90, 20, 90.24),
(90, 19, 50.26),
(51, 18, 155.33),
(1, 17, 200.21),
(19, 16, 160.44),
(82, 15, 80.72),
(3, 14, 10.50),
(18, 13, 60.76),
(18, 12, 100.15),
(56, 11, 162.20),
(20, 10, 5.75),
(46, 9, 195.50),
(14, 8, 58.80),
(31, 7, 125.80),
(49, 6, 135.80),
(81, 5, 125.60),
(29, 5, 145.50),
(50, 4, 99.40),
(59, 3, 62.1),
(56, 2, 175.36),
(15, 1, 40),
(9, 31, 1175),
(17,32, 1080.50);
~~~~
<a id="section4-6"></a>
### Insertion de produits déjà vendus
~~~~sql
INSERT INTO soldproducts (estimatedprice, sellingprice, sellerid, categoryid, description, name, buyerid, soldprice) VALUES
('54.10', '54.10', 1, 25, 'Chaises IKEA', 'Chaises IKEA', 2, 55.10),
('201.34', '201.34', 1, 45, 'Téléviseur Sony', 'Téléviseur Sony', 2, 180),
('68.17', '68.17', 1, 25, 'Fauteuil IKEA', 'Fauteuil IKEA', 5, 60),
('112.09', '112.09', 1, 12, 'Four micro-ondes Samsung bon état', 'Four micro-ondes Samsung bon état', 7, 110),
('174.88', '174.88', 2, 9, 'Laveuse abimée Samsung', 'Laveuse abimée Samsung', 9, 175),
('139.88', '139.88', 2, 62, 'Tablette Amazon Fire 8', 'Tablette Amazon Fire 8', 11, 140),
('179.68', '179.68', 3, 62, 'Samsung Galaxy Tab', 'Samsung Galaxy Tab', 13, 180),
('75.84', '75.84', 4, 52, 'Tapis antique', 'Tapis antique', 15, 75),
('219.65', '219.65', 5, 44, 'Fixie fibre de carbone', 'Fixie fibre de carbone', 17, 215),
('11.99', '11.99', 6, 54, 'Tournevis', 'Tournevis', 19, 10),
('237.09', '237.09', 7, 28, 'Matelas taille King', 'Matelas taille King', 21, 230),
('113.93', '113.93', 7, 11, 'Machine Keurig 2 Tasses', 'Machine Keurig 2 Tasses', 23, 115),
('65.28', '65.28', 8, 17, 'Sac en cuir véritable', 'Sac en cuir véritable', 25, 60),
('15.69', '15.69', 9, 22, 'Magazine Sciences', 'Magazine Sciences', 27, 13.5),
('93.74', '93.74', 10, 47, 'Tableau 12x28', 'Tableau 12x28', 29, 90),
('171.39', '171.39', 10, 39, 'Nintendo 3DS blanche', 'Nintendo 3DS blanche', 31, 165),
('213.23', '213.23', 11, 37, 'Playstation 3 non fonctionnelle', 'Playstation 3 non fonctionnelle', 33, 205),
('165.49', '165.49', 15, 36, 'XBOX 360 neuve', 'XBOX 360 neuve', 35, 150),
('500.00', '500.00', 19, 57, 'MacBook 2018', 'MacBook 2018', 37, 400.00),
('94.08', '94.08', 26, 47, 'Rideau blanc', 'Rideau blanc', 39, 80),
('92.30', '92.30', 28, 47, 'Rideau noir', 'Rideau noir', 41, 70),
('131.43', '131.43', 28, 25, 'Chaise haute', 'Chaise haute', 43, 115),
('132.27', '132.27', 29, 45, 'TV 60 pouces', 'TV 60 pouces', 45, 120),
('170.70', '170.70', 30, 38, 'Wii avec 2 manettes', 'Wii avec 2 manettes', 47, 145),
('31.67', '31.67', 45, 16, 'Chandail bleu', 'Chandail bleu', 49, 25),
('5.66', '5.66', 56, 16, 'Tuque en laine rouge', 'Tuque en laine rouge', 51, 4),
('810.00', '810.00', 57, 2, 'Appartement 4 1/2 ensoleillé', 'Appartement 4 1/2 ensoleillé', 53, 800.00),
('560.00', '560.00', 57, 2, 'Studio à Verdun', 'Studio à Verdun', 55, 515.00),
('750.00', '750.00', 57, 2, '3 1/2 avec balcon', '3 1/2 avec balcon', 56, 690.00),
('1150.00', '1150.00', 58, 1, 'Condo Ile des Soeurs', 'Condo Ile des Soeurs', 57, 1100.0),
('1300.00', '1300.00', 58, 1, 'Condo centre ville', 'Condo centre ville', 57, 1200.0),
('1200.00', '1200.00', 67, 3, 'Maison 5 1/2 avec piscine', 'Maison 5 1/2 avec piscine', 59, 1150.0),
('1450.00', '1450.00', 67, 3, 'Maison Westmount avec mezannine', 'Maison Westmount avec mezannine', 61, 1425.0),
('1450.00', '1450.00', 68, 3, 'Maison avec demi sous-sol', 'Maison avec demi sous-sol', 63, 1350.0),
('299.64', '299.64', 75, 17, 'Sac à main Gucci', 'Sac à main Gucci', 65, 275),
('1021.00', '1021.00', 79, 60, 'Laptop Lenovo 17 pouces', 'Laptop Lenovo 17 pouces', 67, 995),
('400.00', '400.00', 85, 57, 'iMac', 'iMac', 69, 350.00),
('349.00', '349.00', 87, 62, 'iPad 64 Gb', 'iPad 64 Gb', 71, 249.00),
('25.49', '25.49', 87, 16, 'Jeans bleu pour homme', 'Jeans bleu pour homme', 73, 20.49),
('75.70', '75.70', 92, 19, 'Chaussures Nike', 'Chaussures Nike', 75, 55.70),
('80.11', '80.11', 92, 18, 'Talons hauts', 'Talons hauts', 77, 75.11),
('16.00', '16.00', 93, 19, 'Gougounnes Homme', 'Gougounnes Homme', 79, 20.00),
('40.56', '40.56', 94, 18, 'Gougounnes Femme', 'Gougounnes Femme', 81, 50.56),
('850.00', '850.00', 96, 8, 'Frigo americain LG', 'Frigo americain LG', 83, 800.00),
('900.00', '900.00', 98, 9, 'Laveuse Samsung', 'Laveuse Samsung', 85, 700.00),
('15.00', '15.00', 98, 12, 'Four micro ondes defectueux', 'Four micro ondes defectueux', 87, 5.00),
('120.79', '120.79', 98, 26, 'Comptoir blanc IKEA', 'Comptoir blanc IKEA', 89, 100),
('188.41', '188.41', 98, 26, 'Table en bois massif', 'Table en bois massif', 90, 180),
('299.00', '299.00', 99, 27, 'Sofa en cuir', 'Sofa en cuir', 92, 285.00),
('38.69', '38.69', 100, 14, 'Robe blanche', 'Robe blanche', 95, 33);
~~~~
<a id="section4-7"></a>
### Fonctions :

* Récupérer le nom et prénom concaténé selon l'id fourni :
~~~~sql
CREATE OR REPLACE FUNCTION getUserFullName(id integer)
	RETURNS varchar(60) AS $$
	DECLARE
	userfullname varchar(60);
BEGIN
	userfullname := (SELECT CONCAT(firstname, ', ', lastname) FROM users WHERE userid = id);
	RETURN userfullname;
END; $$
LANGUAGE plpgsql;
~~~~


* Comptabilise le nombre d'offres associées au produit en vente (avec refid)
~~~~sql
CREATE OR REPLACE FUNCTION getOffersCount(refid integer)
	RETURNS integer AS $$
	DECLARE
	offercount integer;
BEGIN
	offercount := (SELECT COUNT(*) FROM offers WHERE productid = refid);
	RETURN offercount;
END; $$
LANGUAGE plpgsql;
~~~~

* Retourne le montant de l'offre la plus elevée pour un produit en vente (avec refid)
~~~~sql
CREATE OR REPLACE FUNCTION getMaxOfferValue(refid integer)
	RETURNS NUMERIC(10, 2) AS $$
	DECLARE
	offercount NUMERIC(10, 2);
BEGIN
	offercount := (SELECT COALESCE(MAX(price), 0) FROM offers WHERE productid = refid);
	RETURN offercount;
END; $$
LANGUAGE plpgsql;
~~~~
<a id="section4-8"></a>
### Requêtes-type utilisées par l'application

#### 1) Catalogue
* Catégories sur colonne gauche :
~~~~sql
SELECT * FROM maincategories;
~~~~

Pour chaque catégorie principale : récupérer les sous-catégories et les ajouter dans la colonne de gauche (exemple avec Meubles - id = 6) :
~~~~sql
SELECT catid, catname FROM categories WHERE maincatid = 6 ORDER BY catname;
~~~~

* En cliquant sur une catégorie : afficher tous les produits (hormis ceux vendus par l'utilisateur lui même)
Exemple avec catégorie cliquée : Chaises, Fauteils (catID = 25) et utilisateur actuel = (id=28, Dacey, Lomasny)
~~~~sql
WITH allProducts AS (SELECT * FROM products WHERE categoryid = 25 AND sellerid <> 28)
SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername, date FROM allProducts;
~~~~

#### 2) Mes annonces (avec utilisateur actuel id=28)

* Objets en vente 
~~~~sql
SELECT getOffersCount(refid) as nboffers, name, refid AS productid, categoryid, sellingprice, estimatedprice, description 
FROM products WHERE sellerid=28;
~~~~

* Propositions liées à l'objet selectionné (exemple produitid = 21)
~~~~sql
SELECT * FROM offers WHERE productid = 21;
~~~~


#### 3) Mes achats (si utilisateur courant id = 28)

~~~~sql
SELECT * FROM soldproducts WHERE buyerid = 28;
~~~~

<a id="section5"></a>
## 5. Guide utilisateur
