Projet final - Base de données
=======================================

## Sommaire :

1. [Diagramme Entité-Association](#section1)
2. [Modèle relationnel](#section2)
3. [Définition de la base de données](#section3)
4. [Ensemble des requêtes en SQL et explications des résultats attendus](#section4)
5. [Guide utilisateur](#section5)

<a id="section1"></a>
## 1. Diagramme Entité-Association

![](modeleEA/modeleEA.png)

<a id="section2"></a>
## 2. Modèle relationnel

* User(__userID__, username, password, firstname, lastname, phone) 

* Product(__refID__, #userID, #categoryID, estimatedPrice, sellingPrice, name, description, date) 

* Offer(__offerID__, #userID, #productID, price, date) 

* MainCategory(__mainCategoryID__, name) 

* Category(__catID__, #mainCatName, catName) 

* SoldProducts(__id__, #sellerID, #buyerID, #categoryID, estimatedPrice, sellingPrice, soldPrice, dateTransaction) 

SoldProducts est une table de __log__ conservant l'historique des produits vendus.
  

<a id="section3"></a>
## 3. Définition de la base de données ([DDL.sql](DDL.sql))

~~~~sql
DROP TABLE IF EXISTS users, products, offers, maincategories, categories, soldproducts; 

CREATE TABLE users ( 
     userid      INT auto_increment, 
     username    VARCHAR(32) NOT NULL, 
     password    VARCHAR(32) NOT NULL, 
     firstname   VARCHAR(20) NOT NULL, 
     lastname    VARCHAR(20), 
     phonenumber VARCHAR(12), 
     PRIMARY KEY (userid), 
     UNIQUE (username) 
  ); 

CREATE TABLE products ( 
     refid          INT auto_increment, 
     estimatedprice NUMERIC(10, 2) NOT NULL, 
     sellingprice   NUMERIC(10, 2) NOT NULL, 
     sellerid       INT NOT NULL, 
     categoryid     INT NOT NULL, 
     description    VARCHAR(150) NOT NULL, 
     name           VARCHAR(40) NOT NULL, 
     date           TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
     PRIMARY KEY (refid), 
     FOREIGN KEY (sellerid) REFERENCES users(userid), 
     FOREIGN KEY (categoryid) REFERENCES categories(catid) 
  ); 

CREATE TABLE offers ( 
     offerid   INT auto_increment, 
     buyerid   INT NOT NULL, 
     productid INT NOT NULL, 
     price     NUMERIC(10, 2) NOT NULL, 
     date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
     PRIMARY KEY (offerid), 
     FOREIGN KEY (buyerid) REFERENCES users(userid), 
     FOREIGN KEY (productid) REFERENCES products(refid) 
  ); 

CREATE TABLE maincategories ( 
     maincatname VARCHAR(40) NOT NULL, 
     PRIMARY KEY (maincatname) 
  ); 

CREATE TABLE categories ( 
     catid       INT auto_increment, 
     catname     VARCHAR(40) NOT NULL, 
     maincatname VARCHAR(40) NOT NULL, 
     PRIMARY KEY (catid), 
     FOREIGN KEY (maincatname) REFERENCES maincategories(maincatname) 
  ); 

CREATE TABLE soldproducts ( 
     id              INT auto_increment, 
     sellerid        INT NOT NULL, 
     buyerid         INT NOT NULL, 
     name            VARCHAR(40) NOT NULL, 
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

### Insertion d'utilisateurs
~~~~sql
INSERT INTO `users` (`userid`, `username`, `password`, `firstname`, `lastname`, `phonenumber`) VALUES
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

### Insertion de catégories principales
~~~~sql
INSERT INTO `maincategories` (`mainCatName`) VALUES
('Immobilier'),
('Audio'),
('Appareils électroménagers'),
('Vêtements'),
('Livres'),
('Meubles'),
('Téléphones'),
('Jeux vidéo et consoles'),
('Vélos'),
('Ecrans'),
('Maison - Intérieur'),
('Outils'),
('Ordinateurs et tablettes');
~~~~

### Insertion de catégories
~~~~sql
INSERT INTO `categories` (`mainCatName`, `catName`) VALUES
('Immobilier', 'Condo'),
('Immobilier', 'Appartement'),
('Immobilier', 'Maison'),
('Audio', 'Haut-parleurs'),
('Audio', 'Chaînes stéréo'),
('Audio', 'Ecouteurs'),
('Audio', 'iPod et MP3'),
('Appareils électroménagers', 'Réfrigérateurs'),
('Appareils électroménagers', 'Laveuses et sécheuses'),
('Appareils électroménagers', 'Cuisinières, fours et fourneaux'),
('Appareils électroménagers', 'Machines à café'),
('Appareils électroménagers', 'Fours à micro-ondes'),
('Appareils électroménagers', 'Aspirateurs'),
('Vêtements', 'Femmes - Hauts'),
('Vêtements', 'Enfants'),
('Vêtements', 'Hommes'),
('Vêtements', 'Femmes - Sacs'),
('Vêtements', 'Chaussures pour femmes'),
('Vêtements', 'Chaussures pour hommes'),
('Livres', 'Manuels'),
('Livres', 'Bandes dessinéees'),
('Livres', 'Magazines'),
('Livres', 'Ouvrages de fiction'),
('Livres', 'Essais'),
('Meubles', 'Chaises, Fauteuils'),
('Meubles', 'Mobilier de cuisine et salle à manger'),
('Meubles', 'Sofas'),
('Meubles', 'Lits et matelas'),
('Meubles', 'Commodes et armoires'),
('Téléphones', 'Téléphones cellulaires'),
('Téléphones', 'Accessoires pour cellulaires'),
('Téléphones', 'Téléphones résidentiels et répondeurs'),
('Jeux vidéo et consoles', 'Sony PlayStation 4'),
('Jeux vidéo et consoles', 'Consoles classiques'),
('Jeux vidéo et consoles', 'XBOX One'),
('Jeux vidéo et consoles', 'XBOX 360'),
('Jeux vidéo et consoles', 'Sony PlayStation 3'),
('Jeux vidéo et consoles', 'Nintendo Wii'),
('Jeux vidéo et consoles', 'Nintendo DS'),
('Vélos', 'De route'),
('Vélos', 'Enfants'),
('Vélos', 'Randonné, ville'),
('Vélos', 'Vélos électriques'),
('Vélos', 'Fixie'),
('Écrans', 'Téléviseurs'),
('Écrans', 'Ecrans d\'ordinateur'),
('Maison - Intérieur', 'Décoration intérieure et accessoires'),
('Maison - Intérieur', 'Vaisselle et articles de cuisine'),
('Maison - Intérieur', 'Eclairage intérieur et plafonniers'),
('Maison - Intérieur', 'Literie'),
('Maison - Intérieur', 'Rangement et organisation'),
('Maison - Intérieur', 'Tapis et moquettes'),
('Outils', 'Outils électriques'),
('Outils', 'Outils à main'),
('Outils', 'Rangement pour outils et établis'),
('Outils', 'Echelles et échafaudages'),
('Ordinateurs et tablettes', 'Ordinateurs Apple'),
('Ordinateurs et tablettes', 'Ordinateurs Acer'),
('Ordinateurs et tablettes', 'Ordinateurs Samsung'),
('Ordinateurs et tablettes', 'Ordinateurs Lenovo'),
('Ordinateurs et tablettes', 'Autres ordinateurs'),
('Ordinateurs et tablettes', 'Tablettes');
~~~~

### Insertion de produits
~~~~sql
INSERT INTO `products` (`estimatedprice`, `sellingprice`, `sellerid`, `categoryid`, `description`, `name`) VALUES
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
('81.81', '500.00', 19, 57, 'MacBook 2018', 'MacBook 2018'),
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

### Requêtes-type utilisées par l'application
~~~~sql
SELECT * FROM offers;
~~~~

<a id="section5"></a>
## 5. Guide utilisateur
