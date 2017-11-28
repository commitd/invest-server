package io.committed.vessel.plugins.ui.feedback;

import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselUiExtension;

@Configuration
public class VesselFeedbackReaderPlugin implements VesselUiExtension {

  @Override
  public String getName() {
    return "Read feedback";
  }

  @Override
  public String getDescription() {
    return "Read feedback";
  }
}
