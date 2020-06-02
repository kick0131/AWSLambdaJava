package dto;

import lombok.Data;

/**
    Table "public.t_byte"
 Column |  Type   | Modifiers
--------+---------+-----------
 id     | integer |
 image  | bytea   |
*/

@Data
public class TByteSampleDto {
 Integer id;
 byte[] image;
}
