package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rdsdata.AWSRDSData;
import com.amazonaws.services.rdsdata.AWSRDSDataClient;
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;
import com.amazonaws.services.rdsdata.model.Field;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DataAPI サンプルコード<br>
 * 細かい制御が必要な場合に利用を検討する。
 */
public class RdsApiSample implements RequestHandler<Object, String> {
  static Logger log = LoggerFactory.getLogger(RdsApiSample.class);

  public static void main(String[] args) {
    System.out.println("Hello world");
    log.info("--- {} start ---", "main");

  }

  @Override
  public String handleRequest(Object arg0, Context arg1) {
    final String RESOURCE_ARN = "arn:aws:rds:ap-northeast-1:XXXXX";
    final String SECRET_ARN = "arn:aws:secretsmanager:ap-northeast-1:XXXXX";
    final String DATABASE = "postgres";
    final String SQL = "select * from t_office";

    System.out.println("called handleRequest");
    log.info("--- {} start ---", "handleRequest");

    AWSRDSData rdsData = AWSRDSDataClient.builder().build();

    ExecuteStatementRequest request = new ExecuteStatementRequest().withResourceArn(RESOURCE_ARN)
        .withSecretArn(SECRET_ARN).withDatabase(DATABASE).withSql(SQL);

    ExecuteStatementResult result = rdsData.executeStatement(request);

    for (List<Field> fields : result.getRecords()) {
      long p01 = fields.get(0).getLongValue();
      long p02 = fields.get(1).getLongValue();
      String p03 = fields.get(2).getStringValue();
      String p04 = fields.get(3).getStringValue();

      log.info(String.format("Fetched row: %d, %d, %s, %s", p01, p02, p03, p04));
    }

    log.info("--- {} end ---", "handleRequest");
    return "Success";
  }
}