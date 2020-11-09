-- !Ups

create schema market;

CREATE TABLE market.carkind (
id BIGINT auto_increment,
name VARCHAR (20) NOT NULL ,
country VARCHAR (20) NOT NULL ,
PRIMARY KEY (id)
);

CREATE TABLE market.carmodel (
id BIGINT auto_increment,
name VARCHAR (20) NOT NULL ,
yearStart DATE NOT NULL ,
yearEnd DATE NOT NULL ,
PRIMARY KEY (id)
);

CREATE TABLE market.carposition (
id BIGINT auto_increment,
kindId BIGINT NOT NULL,
modelId BIGINT NOT NULL,
yearIssue DATE NOT NULL ,
od INT NOT NULL,
price INT NOT NULL,
PRIMARY KEY (id)
);

-- !Downs
drop table market.carkind
drop table market.carmodel
drop table market.carposition

--select CARKIND.name, CARMODEL.name, CARPOSITION.od, CARPOSITION.PRICE, CARPOSITION.YEARISSUE from MARKET.CARPOSITION, market.CARKIND, market.CARMODEL where CARKIND.ID = CARPOSITION.KINDID and CARMODEL.ID=CARPOSITION.MODELID