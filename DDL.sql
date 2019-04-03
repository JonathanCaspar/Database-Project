START TRANSACTION;
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

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA casparjo TO casparjo_app;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA casparjo TO casparjo_app;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA casparjo TO casparjo_app;
GRANT ALL PRIVILEGES ON SCHEMA casparjo TO casparjo_app;

COMMIT;