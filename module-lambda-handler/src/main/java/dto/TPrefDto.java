package dto;

import lombok.Data;

@Data
public class TPrefDto {
  public String code_me;
  public String name_me;
  public String kana_me;
  public String dbtable;
  public Integer children_exist;
}
