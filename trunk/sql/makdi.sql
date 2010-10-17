                                                                                                                                                    
-- 
-- DB for makdi spider
-- 
-- 

--
-- word about created_on and updated on timestamp columns
--
-- MySQL is weird in the sense that it assumes ON UPDATE clause if you do not
-- supply a default timestamp !!!
--
-- With neither DEFAULT nor ON UPDATE clauses
-- it is the same as DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP.
-- http://dev.mysql.com/doc/refman/5.0/en/timestamp.html
--
--

drop table if exists page_data;
create table page_data(
	id int(11) NOT NULL auto_increment,
	app_id varchar(64) not null ,
	page_id varchar(128) not null,
	html TEXT ,
	driver varchar(64) not null,
	title varchar(256) not null,
	created_on timestamp default '0000-00-00 00:00:00',
	updated_on timestamp default '0000-00-00 00:00:00' ,
	PRIMARY KEY (id)) ENGINE = MYISAM;
    


