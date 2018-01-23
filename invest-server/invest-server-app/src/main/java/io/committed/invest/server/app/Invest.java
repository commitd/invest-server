package io.committed.invest.server.app;

import org.springframework.boot.SpringApplication;
import io.committed.invest.server.core.annotations.investApplication;

@investApplication
public class Invest {

  public static void main(final String[] args) {
    SpringApplication.run(Invest.class, args);
  }

}
