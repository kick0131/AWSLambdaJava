package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 *                 Table "public.t_typesample"
     Column      |            Type             | Modifiers
-----------------+-----------------------------+-----------
 building_id     | integer                     |
 building_name   | character varying           |
 building_floor  | smallint                    |
 building_height | numeric(13,10)              |
 x1              | double precision            |
 y1              | double precision            |
 lastupdate      | timestamp without time zone |
 */

@Data
public class TTypeSampleDto {
 Integer building_id;
 String building_name; 
 Integer building_floor;
 BigDecimal  building_height;
 Double x1;
 Double y1;
 LocalDateTime lastupdate; 
}
