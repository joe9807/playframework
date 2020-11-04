-- !Ups

ALTER TABLE market.carmodel ALTER COLUMN yearStart SET DATA TYPE VARCHAR (4);
ALTER TABLE market.carmodel ALTER COLUMN yearEnd SET DATA TYPE VARCHAR (4);

ALTER TABLE market.carposition ALTER COLUMN yearIssue SET DATA TYPE VARCHAR (4);