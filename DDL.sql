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
     sellerid       INT             NOT NULL, 
     categoryid     VARCHAR(40)     NOT NULL,
     description    VARCHAR(150)    NOT NULL,
     name           VARCHAR(40)     NOT NULL,
     date           TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
     PRIMARY KEY (refid), 
     FOREIGN KEY (sellerid) REFERENCES users(userid), 
     FOREIGN KEY (categoryid) REFERENCES categories(catID) 
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
     mainCatName    VARCHAR(40) NOT NULL, 
     PRIMARY KEY (mainCatName) 
  ); 

CREATE TABLE categories (
     catID      INT auto_increment,                    
     catName     VARCHAR(40) NOT NULL, 
     mainCatName   VARCHAR(40) NOT NULL, 
     PRIMARY KEY (catID),
     FOREIGN KEY (mainCatName) REFERENCES maincategories(mainCatName) 
  ); 

CREATE TABLE soldproducts ( 
     id          		INT auto_increment, 
     sellerid    		INT NOT NULL,
     buyerid     		INT NOT NULL,
     name               VARCHAR(40) NOT NULL, 
     categoryid     	VARCHAR(40) NOT NULL,
     estimatedprice 	NUMERIC(10, 2) NOT NULL, 
     sellingprice   	NUMERIC(10, 2) NOT NULL, 
     soldprice   		NUMERIC(10, 2) NOT NULL, 
     dateTransaction	TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
     PRIMARY KEY (id), 
     FOREIGN KEY (sellerid) REFERENCES users(userid), 
     FOREIGN KEY (buyerid) 	REFERENCES users(userid), 
     FOREIGN KEY (categoryid) REFERENCES categories(catID) 
  );