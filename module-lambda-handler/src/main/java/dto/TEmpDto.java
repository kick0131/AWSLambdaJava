package dto;

import lombok.Data;

@Data
public class TEmpDto {
  public Integer comp_id;
  public String emp_cd;
  public String agreement_fg;
  public String state_cd;
  public String home_zip_cd;
  public String home_pref;
  public String home_city;
  public String home_town;
  public String home_apartment;
  public String home_lat;
  public String home_lon;
  public String office_id;
  public String area_cd;
  public String pj_zip_cd;
  public String pj_pref;
  public String pj_city;
  public String pj_town;
  public String pj_building;
  public String pj_name;
  public String pj_phone;
  public String pj_lat;
  public String pj_lon;
  public String home_change;
  public String office_change;
  public String del_plan;
  public String update_person;
  public String last_update;
  public String last_access;
  public Integer seq_no;
  public String self_access_fg;
  public String pdf_req_date;
  public Integer pdf_office_id_s;
  public Integer pdf_office_id_g;
}
