package io.committed.invest.server.app;

import org.springframework.boot.SpringApplication;
import io.committed.invest.server.core.annotations.InvestApplication;

@InvestApplication
public class Invest {

  public static void main(final String[] args) {
    SpringApplication.run(Invest.class, args);
  }

}
