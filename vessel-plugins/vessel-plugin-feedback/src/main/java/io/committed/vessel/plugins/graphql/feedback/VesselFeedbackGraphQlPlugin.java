package io.committed.vessel.plugins.graphql.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselGraphQlExtension;
import io.committed.vessel.server.data.services.DatasetProviders;

@Configuration
public class VesselFeedbackGraphQlPlugin implements VesselGraphQlExtension {


  @Bean
  public FeedbackGraphQlService feedbackGraphQlService(
      @Autowired(required = false) final DatasetProviders providers) {
    return new FeedbackGraphQlService(providers);
  }
}
