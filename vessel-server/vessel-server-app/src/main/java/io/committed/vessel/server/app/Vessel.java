package io.committed.vessel.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.committed.vessel.server.graphql.GraphQlConfig;

@SpringBootApplication
@Import(GraphQlConfig.class)
@EnableAsync
@EnableScheduling
public class Vessel {

  public static void main(final String[] args) {
    SpringApplication.run(Vessel.class, args);
  }

}
