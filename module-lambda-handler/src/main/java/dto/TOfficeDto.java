package dto;

import lombok.Data;

@Data
public class TOfficeDto {
  public Integer office_id;
  public Integer comp_id;
  public String area_cd;
  public String office_name;
  public String pref;
  public String city;
  public String town;
  public String building;
  public String lat;
  public String lon;
  public String view_level;
  public Integer pilot_office_id;
  public String remarks;
  public String office_no;
  public String col_no;
  public String pos_update;
  public String last_update;
  public Integer update_comp_id;
  public String update_emp_cd;
  public String public_fg;
  public Integer order_col;  
}
