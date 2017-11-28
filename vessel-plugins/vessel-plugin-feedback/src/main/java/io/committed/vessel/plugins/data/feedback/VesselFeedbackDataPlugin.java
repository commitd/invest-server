package io.committed.vessel.plugins.data.feedback;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselDataExtension;
import io.committed.vessel.plugins.data.feedback.mongo.MongoFeedbackProviderFactory;

@Configuration
public class VesselFeedbackDataPlugin implements VesselDataExtension {


  @Bean
  public MongoFeedbackProviderFactory mongoFeedbackProviderFactory() {
    return new MongoFeedbackProviderFactory();
  }
}
