package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLambdaHandler implements RequestHandler<Object, String> {
  static Logger log = LoggerFactory.getLogger(SimpleLambdaHandler.class);

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
}