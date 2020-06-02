package handler;

import com.amazon.rdsdata.client.RdsDataClient;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rdsdata.AWSRDSData;
import com.amazonaws.services.rdsdata.AWSRDSDataClientBuilder;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import contents.Util;
import dto.TAreaDto;

import dto.TByteSampleDto;
import dto.TTypeSampleDto;

/**
 * Data API用のクライアントライブラリ操作<br>
 * ビジネスロジックに集中できるので、余程の事が無い限りはこちらを使った方が安心
 */
public class RdsApiClientSample implements RequestHandler<Object, String> {
  static Logger log = LoggerFactory.getLogger(RdsApiClientSample.class);
  // Serverles RDB for PostgreSQLのリソースARN
  final String RESOURCE_ARN = "arn:aws:rds:XXXXX";
  // RDSに関連付けられたSecret ManagerのARN
  final String SECRET_ARN = "arn:aws:secretsmanager:XXXXX";
  // 接続先DB名
  final String DATABASE = "dataapi";
  // Jsonパーサ
  Gson gson = new Gson();
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
   * 利用例 List<DtoClass> records = getDtoRecords("select * from t_dto;", DtoClass.class);
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
   * 
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("Hello world");
    log.info("--- {} start ---", "main");
  }

  /**
   * 未定義
   */
  @Override
  public String handleRequest(Object arg0, Context arg1) {
    log.info("--- {} start ---", "handleRequest");


    log.info("--- {} end ---", "handleRequest");
    return "Success";
  }

  /**
   * 1レコード読込
   */
  public String selectSingle(Object arg0, Context arg1) {
    log.info("--- {} start ---", Util.getMethodName());

    // 主キー指定
    String SQL = String.format("select * from t_area where comp_id = %d", 1);
    TAreaDto t_area = client.forSql(SQL).execute().mapToSingle(TAreaDto.class);

    log.info("--- {} end ---", Util.getMethodName());
    return gson.toJson(t_area);
  }

  /**
   * 複数レコード読込
   */
  public String selectMulti(Object arg0, Context arg1) {
    log.info("--- {} start ---", Util.getMethodName());

    // 複数レコードの取得
    String SQL = "select * from t_area";
    List<TAreaDto> result_l = getDtoRecords(SQL,TAreaDto.class);
    for (TAreaDto item : result_l) {
      // JSON変換
      log.info(gson.toJson(item));
    }

    log.info("--- {} end ---", Util.getMethodName());
    return gson.toJson(result_l);
  }

  /**
   * 1レコード登録
   */
  public String insertSingle(Object arg0, Context arg1) {
    log.info("--- {} start ---", Util.getMethodName());

    // 値の設定は以下の方法から行う
    // ・直接valuesに記載
    // ・withParamSetsのクラス変数をマッピング
    TAreaDto areaDto = new TAreaDto();
    areaDto.setArea_name("Sample Area");
    String SQL_insert_emp = "insert into t_area(comp_id, area_cd, area_name)";
    String SQL_insert_emp_values = String.format("values( %d, '%s', :area_name)", new Random().nextInt(999), "99");

    // DataAPI呼び出し
    client.forSql(SQL_insert_emp + SQL_insert_emp_values)
      .withParamSets(areaDto)
      .execute();


    log.info("--- {} end ---", Util.getMethodName());
    return "Success";
  }


  /**
   * 型の対応確認
   */
  public String someTypeTable(Object arg0, Context arg1) {
    log.info("--- {} start ---", Util.getMethodName());

    // Select
    List<TTypeSampleDto> typesample = getDtoRecords("select * from t_typesample",TTypeSampleDto.class);
    log.info(gson.toJson(typesample));
    
    // Insert
    TTypeSampleDto insertDto = new TTypeSampleDto();
    insertDto.setBuilding_id(Integer.MIN_VALUE);
//    insertDto.setBuilding_name("あいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえおかきくけこさしすせそたちつてとなにぬけのあいうえお");
    insertDto.setBuilding_name("sample data");
    insertDto.setBuilding_floor(Integer.parseInt(String.format("%d",Short.MIN_VALUE)));
    insertDto.setBuilding_height(BigDecimal.valueOf(-999.9999999999));
    insertDto.setX1(Double.MAX_VALUE);
    insertDto.setY1(Double.MIN_VALUE);
    insertDto.setLastupdate(LocalDateTime.now());
    client.forSql("INSERT INTO t_typesample VALUES(:building_id, :building_name, :building_floor, :building_height, :x1, :y1, :lastupdate)")
      .withParamSets(insertDto)
      .execute();

    log.info("--- {} end ---", Util.getMethodName());
    return "Success";
  }

  /**
   * 型の対応確認(Byte型)
   */
  public String byteTable(Object arg0, Context arg1) {
    log.info("--- {} start ---", Util.getMethodName());

    // Select
    List<TByteSampleDto> typesample = getDtoRecords("select * from t_byte",TByteSampleDto.class);
    log.info(gson.toJson(typesample));
    
    // Insert
    TByteSampleDto insertDto = new TByteSampleDto();
    insertDto.setId(Integer.MIN_VALUE);
    insertDto.setImage("サンプル１".getBytes(StandardCharsets.UTF_8));
    log.info("-- Insert");
    client.forSql("INSERT INTO t_byte VALUES(:id, :image)")
      .withParamSets(insertDto)
      .execute();

    log.info("--- {} end ---", Util.getMethodName());
    return "Success";
  }

}