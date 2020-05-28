package contents;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * API Gateway リクエストデータ
 * 
 * Proxy統合時のリクエストフォーマット<br>
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class ApiGatewayRequest {
  Boolean isBase64Encoded = false;
  Map<String,String> headers = new HashMap<String,String> ();
  String body;
}