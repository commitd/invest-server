package io.committed.vessel.server.app.initialisation;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.committed.vessel.server.extensions.VesselExtension;
import io.committed.vessel.server.extensions.VesselUiExtension;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ExtensionInitializer implements
    ApplicationContextInitializer<AnnotationConfigApplicationContext> {


  @Override
  public void initialize(final AnnotationConfigApplicationContext applicationContext) {

    // TODO: Verbose should should be set from configuration or similar.
    final boolean verbose = false;
    final ExtensionFinder finder = new ExtensionFinder(verbose);
    final Flux<Class<? extends VesselExtension>> extensions = finder.find();

    extensions.subscribe(e -> {
      try {
        final VesselExtension instance = e.newInstance();
        Class<? extends VesselExtension> extensionPointClass = VesselExtension.class;

        // TODO: I don't know why I need to sort this JonahUiExtension out... seems to just work for
        // the Api plugins... At any rate we either separate out or not.
        if (VesselUiExtension.class.isInstance(instance)) {
          extensionPointClass = VesselUiExtension.class;

          final VesselUiExtension je = (VesselUiExtension) instance;
          applicationContext.registerBean(
              String.format("%s-%s", extensionPointClass.getSimpleName(), instance.getId()),
              VesselUiExtension.class, () -> je);
        } else {

          applicationContext.registerBean(
              String.format("je-%s", instance.getId()),
              VesselExtension.class, () -> instance);
        }

        final Class<?> configuration = instance.getConfiguration();
        if (configuration != null) {
          applicationContext.register(configuration);
        }
      } catch (final Exception ex) {
        log.error("Unable to inspect for plugins", ex);
      }
    });

  }
}
