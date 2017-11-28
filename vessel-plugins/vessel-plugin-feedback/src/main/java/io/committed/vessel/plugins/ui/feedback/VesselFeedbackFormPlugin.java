package io.committed.vessel.plugins.ui.feedback;

import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselUiExtension;

@Configuration
public class VesselFeedbackFormPlugin implements VesselUiExtension {

  @Override
  public String getName() {
    return "Feedback";
  }

  @Override
  public String getDescription() {
    return "Leave comments on existing functions or request new features";
  }

}
