package contents;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * API Gateway 応答データ
 * 
 * Proxy統合時の応答フォーマット<br>
 * bodyはJson文字列を設定する事
 */
@Data
public class ApiGatewayResult {
  Boolean isBase64Encoded = false;
  Integer statusCode = 200;
  Map<String,String> headers = new HashMap<String,String> ();
  String body;
  public ApiGatewayResult(){
    // CORS
    headers.put("Access-Control-Allow-Origin" , "*");
  }
}