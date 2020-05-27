package client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ignore.SimpleLambdaClient;

public class ConsoleMain {
  static Logger log = LoggerFactory.getLogger(ConsoleMain.class);

  public static void main(String[] args) {
    log.debug("=== main start");
    SimpleLambdaClient client_v1 = new SimpleLambdaClient();
    try {
      client_v1.execute(new String[] { "", "" });

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    log.debug("=== main end");
  }
}