package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import contents.ApiGatewayResult;

public class SimpleLambdaHandler implements RequestHandler<Object, String> {
  static Logger log = LoggerFactory.getLogger(SimpleLambdaHandler.class);
  // Jsonパーサ
  Gson gson = new Gson();

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
   * @throws Exception
   */
  public ApiGatewayResult apiGwEcho(Object arg0, Context arg1) throws Exception {

    // JsonObject型にパースしてからbody部を取得。
    // body部はString型であることが仕様で決まっている。
    JsonObject jsonobj = gson.fromJson(gson.toJson(arg0), JsonObject.class);
    if (jsonobj.get("body").isJsonNull()) {
      throw new Exception("Json value body is null");
    }
    if (jsonobj.get("body").isJsonObject()) {
      throw new Exception("Json value is not APIGateway format");
    }

    // body部のJSONをDTOクラスにマッピングするサンプル
    // TPrefDto registApiRequest = gson.fromJson(jsonobj.get("body").getAsString(), TPrefDto.class);

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
    apiresult.setBody(gson.toJson(arg0));

    return apiresult;
  }

}