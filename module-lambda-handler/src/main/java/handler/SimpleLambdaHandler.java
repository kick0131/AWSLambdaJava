package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import contents.ApiGatewayResult;

public class SimpleLambdaHandler implements RequestHandler<Object, String> {
  static Logger log = LoggerFactory.getLogger(SimpleLambdaHandler.class);
  // Json変換にはJacksonを利用
  ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {
    System.out.println("Hello world");
    log.info("--- {} start ---", "main");

  }

  @Override
  public String handleRequest(Object arg0, Context arg1) {
    System.out.println("called handleRequest");
    log.info("--- {} start ---", "handleRequest");

    log.info("--- {} end ---", "handleRequest");
    return "Success";
  }

  /**
   * プロキシ統合したAPIGatewayへの応答サンプル
   * 
   * @param arg0 APIGatewayからのリクエストパラメータを期待
   * @param arg1 Lambda関数の情報
     * @return リクエストパラメータをエコー応答する
   * @throws JsonProcessingException
   */
  public ApiGatewayResult apiGwEcho(Object arg0, Context arg1) throws JsonProcessingException {

    // 戻り値は引数のエコー応答とする
    // 
    // request path: {}
    // request query string: {key1=value1, key2=value2}
    // request headers: {Accept=Application/json}
    // request body before transformations: {
    // "aaa":"bbb",
    // "ccc":123
    // }
    // arg0にはbody部分が設定される
    ApiGatewayResult apiresult = new ApiGatewayResult();
    apiresult.setBody(mapper.writeValueAsString(arg0));

    return apiresult;
  }

}