INSERT INTO app_users (id,email,enabled,first_name,last_name,otp,password,pwd_time,username,version) VALUES (1,NULL,true,'Amministratore','Di Sistema',false,'d033e22ae348aeb5660fc2140aec35850c4da997',NULL,'admin',1);
INSERT INTO app_users (id,email,enabled,first_name,last_name,otp,password,pwd_time,username,version) VALUES (2,NULL,true,'Utente','Applicativo',false,'12dea96fec20593566ab75692c9949596833adc9',NULL,'user',1);
INSERT INTO roles (id,description,name) VALUES (1,'Amministratore Sistema','ROLE_ADMIN');
INSERT INTO roles (id,description,name) VALUES (2,'Utente Sistema','ROLE_USER');
INSERT INTO user_roles (user_id,role_id) VALUES (1,1);
INSERT INTO user_roles (user_id,role_id) VALUES (2,2);
INSERT INTO devices (device_id, device_code, device_lat, device_lon, device_loadfactor) VALUES (1,'RM0034',41.8565,12.4925,12.2400),(2,'RM1212',41.8455,12.4872,8.7500),(3,'RM1660',41.8584,12.4868,9.6400),(4,'RM2521',41.8563,12.4782,11.1200);
CREATE OR REPLACE 
VIEW daily_measures AS
    select 
        DATE(measure_date) AS measure_date,
        measures.device_id AS device_id,
        max(measures.measure_value) AS measure_value
    from
        measures
    group by measures.measure_date , measures.device_id;
DROP TABLE IF EXISTS daily_perfomances;
CREATE OR REPLACE
VIEW daily_perfomances AS
    select 
    	concat(date_format(d2.measure_date,'%Y%m%d_'), d2.device_id) AS performance_id,
        d2.measure_date AS performance_day,
        d2.device_id AS device_id,
        (d2.measure_value - d1.measure_value) AS performance_value,
        ((d2.measure_value - d1.measure_value) * dev.device_loadfactor) AS performance_volume
    from
        ((daily_measures d1
        join daily_measures d2 ON (((d1.device_id = d2.device_id)
            and (d1.measure_date = (d2.measure_date - 1)))))
        join devices dev ON ((d2.device_id = dev.device_id)));