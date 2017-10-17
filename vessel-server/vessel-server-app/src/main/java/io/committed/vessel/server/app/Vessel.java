package io.committed.vessel.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.committed.vessel.server.graphql.GraphQlConfig;

@SpringBootApplication
@Import(GraphQlConfig.class)
public class Vessel {

  public static void main(final String[] args) {
    SpringApplication.run(Vessel.class, args);
  }

}
