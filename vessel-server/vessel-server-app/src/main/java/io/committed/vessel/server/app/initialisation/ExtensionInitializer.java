package io.committed.vessel.server.app.initialisation;


import java.util.function.Supplier;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.committed.vessel.extensions.VesselExtension;
import io.committed.vessel.extensions.VesselUiExtension;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ExtensionInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static interface AnnotationContextProxy {

    <T> void registerBean(String name, Class<T> clazz, Supplier<T> supplier);

    void register(Class<?>... classes);

  }

  @Override
  public void initialize(final ConfigurableApplicationContext applicationContext) {


    // We don't have a common inheritence parent.. but the functions are the same in the end..
    final AnnotationContextProxy proxy;
    if (applicationContext instanceof AnnotationConfigApplicationContext) {
      final AnnotationConfigApplicationContext context =
          (AnnotationConfigApplicationContext) applicationContext;
      proxy = new AnnotationContextProxy() {

        @Override
        public <T> void registerBean(final String beanName, final Class<T> beanClass,
            final Supplier<T> supplier) {
          context.registerBean(beanName, beanClass, supplier);
        }

        @Override
        public void register(final Class<?>... classes) {
          context.register(classes);
        }
      };
    } else if (applicationContext instanceof AnnotationConfigServletWebServerApplicationContext) {

      final AnnotationConfigServletWebServerApplicationContext context =
          (AnnotationConfigServletWebServerApplicationContext) applicationContext;
      proxy = new AnnotationContextProxy() {

        @Override
        public <T> void registerBean(final String beanName, final Class<T> beanClass,
            final Supplier<T> supplier) {
          context.registerBean(beanName, beanClass, supplier);
        }

        @Override
        public void register(final Class<?>... classes) {
          context.register(classes);
        }
      };

    } else {
      throw new RuntimeException(
          String.format("Context is not support: %s", applicationContext.getClass().getName()));
    }


    // TODO: Verbose should should be set from configuration or similar.
    final boolean verbose = false;
    final ExtensionFinder finder = new ExtensionFinder(verbose);
    final Flux<Class<? extends VesselExtension>> extensions = finder.find();

    extensions.subscribe(e -> {
      try {
        final VesselExtension instance = e.newInstance();
        Class<? extends VesselExtension> extensionPointClass = VesselExtension.class;

        // TODO: I don't know why I need to sort this VesselUiExtension out... seems to just work
        // for the Api plugins... At any rate we either separate out or not.
        if (VesselUiExtension.class.isInstance(instance)) {
          extensionPointClass = VesselUiExtension.class;

          final VesselUiExtension je = (VesselUiExtension) instance;
          proxy.registerBean(
              String.format("%s-%s", extensionPointClass.getSimpleName(), instance.getId()),
              VesselUiExtension.class, () -> je);
        } else {

          proxy.registerBean(
              String.format("je-%s", instance.getId()),
              VesselExtension.class, () -> instance);
        }

        final Class<?> configuration = instance.getConfiguration();
        if (configuration != null) {
          proxy.register(configuration);
        }
      } catch (final Exception ex) {
        log.error("Unable to inspect for plugins", ex);
      }
    });

  }
}
