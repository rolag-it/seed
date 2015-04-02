INSERT INTO app_users (id,email,enabled,first_name,last_name,otp,password,pwd_time,username,version) VALUES (1,NULL,true,'Amministratore','Di Sistema',false,'d033e22ae348aeb5660fc2140aec35850c4da997',NULL,'admin',1);
INSERT INTO app_users (id,email,enabled,first_name,last_name,otp,password,pwd_time,username,version) VALUES (2,NULL,true,'Utente','Applicativo',false,'12dea96fec20593566ab75692c9949596833adc9',NULL,'user',1);
INSERT INTO roles (id,description,name) VALUES (1,'Amministratore Sistema','ROLE_ADMIN');
INSERT INTO roles (id,description,name) VALUES (2,'Utente Sistema','ROLE_USER');
INSERT INTO user_roles (user_id,role_id) VALUES (1,1);
INSERT INTO user_roles (user_id,role_id) VALUES (2,2);
INSERT INTO devices (device_id, device_code, device_lat, device_lon, device_loadfactor) VALUES (1,'RM0034',41.8565,12.4925,12.2400),(2,'RM1212',41.8455,12.4872,8.7500),(3,'RM1660',41.8584,12.4868,9.6400),(4,'RM2521',41.8563,12.4782,11.1200);
INSERT INTO measures (measure_id, measure_date, measure_value, device_id) VALUES (1,'2015-03-20',4852,1),(2,'2015-03-20',8524,2),(3,'2015-03-20',3654,3),(4,'2015-03-20',8445,4),(5,'2015-03-21',5028,1),(6,'2015-03-21',8588,2),(7,'2015-03-21',3721,3),(8,'2015-03-21',8664,4),(9,'2015-03-22',5144,1),(10,'2015-03-22',8755,2),(11,'2015-03-22',3860,3),(12,'2015-03-22',8948,4),(13,'2015-03-23',5233,1),(14,'2015-03-23',8888,2),(15,'2015-03-23',4101,3),(16,'2015-03-23',9122,4);
