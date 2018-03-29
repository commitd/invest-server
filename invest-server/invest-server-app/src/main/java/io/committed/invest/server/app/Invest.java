package io.committed.invest.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.committed.invest.server.core.annotations.InvestApplication;

/**
 * Entry point for an Invest application.
 *
 * <p>You can define your own entry point, in your own Maven module, simply use the {@link
 * InvestApplication} annotation where you otherwise would use {@link SpringBootApplication}
 * annotation.
 *
 * <p>Defining your own as the benefits you can pre-bundle plugins using Maven shade.
 */
@InvestApplication
public class Invest {

  public static void main(final String[] args) {
    SpringApplication.run(Invest.class, args);
  }
}
