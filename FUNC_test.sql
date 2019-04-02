CREATE OR REPLACE FUNCTION getUserFullName(id integer)
RETURNS varchar(60) AS $$
DECLARE
userfullname varchar(60);
BEGIN
userfullname := (SELECT CONCAT(firstname, ', ', lastname) FROM users WHERE userid = id);
RETURN userfullname;
END; $$
LANGUAGE plpgsql;

WITH allProducts AS (SELECT * FROM products WHERE categoryid = 25 AND sellerid <> 28)
SELECT refid, name, description, sellingprice, getUserFullName(sellerid) AS sellername, date FROM allProducts;
