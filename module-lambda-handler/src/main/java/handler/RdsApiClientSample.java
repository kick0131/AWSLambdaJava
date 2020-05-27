package handler;

import com.amazon.rdsdata.client.RdsDataClient;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rdsdata.AWSRDSData;
import com.amazonaws.services.rdsdata.AWSRDSDataClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import contents.ApiGatewayResult;
import dto.TEmpDto;
import dto.TOfficeDto;

/**
 * Data API用のクライアントライブラリ練習
 */
public class RdsApiClientSample implements RequestHandler<Object, String> {
  static Logger log = LoggerFactory.getLogger(RdsApiClientSample.class);
  // Serverles RDB for PostgreSQLのリソースARN
  final String RESOURCE_ARN = "arn:aws:rds:XXXXX";
  // RDSに関連付けられたSecret ManagerのARN
  final String SECRET_ARN = "arn:aws:secretsmanager:XXXXX";
  // 接続先DB名
  final String DATABASE = "postgres";
  // Json変換にはJacksonを利用
  ObjectMapper mapper = new ObjectMapper();
  // DataAPIリソース
  AWSRDSData rdsData;
  // DataAPIクライアントライブラリ
  RdsDataClient client;

  /**
   * コンストラクタ
   */
  public RdsApiClientSample() {
    // DataAPIクライアントライブラリの初期化
    rdsData = AWSRDSDataClientBuilder.defaultClient();
    client = RdsDataClient.builder().rdsDataService(rdsData).database(DATABASE).resourceArn(RESOURCE_ARN)
        .secretArn(SECRET_ARN).build();
  }

  /**
   * テーブル一覧取得
   * 
   * 利用例
   * List<DtoClass> records = getDtoRecords("select * from t_dto;", DtoClass.class);
   * 
   * @param sql 任意のテーブルレコードを取得するSQL文
   * @param obj 戻り値の型リテラル
   * @return 引数で指定したDTOリスト
   */
  public <T> List<T> getDtoRecords(String sql, Class<T> obj) {
    List<T> result = client.forSql(sql).execute().mapToList(obj);
    return result;
  }

  /**
   * コンソール確認用
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("Hello world");
    log.info("--- {} start ---", "main");
  }

  /**
   * 標準のLambdaハンドラでDataAPIを実行する例
   */
  @Override
  public String handleRequest(Object arg0, Context arg1) {
    String SQL = "select * from t_emp";

    System.out.println("called handleRequest");
    log.info("--- {} start ---", "handleRequest");
 
    // 複数レコードの取得
    List<TEmpDto> result_l = client.forSql(SQL).execute().mapToList(TEmpDto.class);
    for (TEmpDto item : result_l) {
      try {
        // JSON変換
        log.info(mapper.writeValueAsString(item));
      } catch (JsonProcessingException e) {
        log.error(e.getMessage());
        e.printStackTrace();
      }
    }

    // 主キー指定
    SQL = String.format("select * from t_office where office_id = %d", 1);
    TOfficeDto t_office = client.forSql(SQL).execute().mapToSingle(TOfficeDto.class);

    // JSON文字列として返す例
    String result = "";
    try {
      result = mapper.writeValueAsString(t_office);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }

    log.info("--- {} end ---", "handleRequest");
    return result;
  }

  /**
   * API Gatewayに応答データを返す場合のサンプル（GET）
   * @param arg0
   * @param arg1
   * @return
   * @throws JsonProcessingException
   */
  public ApiGatewayResult myhandler(Object arg0, Context arg1) throws JsonProcessingException {

    // 戻り値パラメータのマッピング
    ApiGatewayResult apiresult = new ApiGatewayResult();
    apiresult.setBody("{\"key1\":\"value1\",\"key2\":\"value2\"}");

    return apiresult;
  }

}