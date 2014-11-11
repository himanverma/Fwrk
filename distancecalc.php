<?php
DELIMITER $$

DROP FUNCTION IF EXISTS `get_d` $$
# MySQL returned an empty result set (i.e. zero rows).

CREATE FUNCTION get_d(geo1_latitude decimal(10,6), geo1_longitude decimal(10,6), geo2_latitude decimal(10,6), geo2_longitude decimal(10,6)) 
returns decimal(10,3) DETERMINISTIC
BEGIN
return ((ACOS(SIN(geo1_latitude * PI() / 180) * SIN(geo2_latitude * PI() / 180) + COS(geo1_latitude * PI() / 180) * COS(geo2_latitude * PI() / 180) * COS((geo1_longitude - geo2_longitude) * PI() / 180)) * 180 / PI()) * 60 * 1.1515);
END $$

?>