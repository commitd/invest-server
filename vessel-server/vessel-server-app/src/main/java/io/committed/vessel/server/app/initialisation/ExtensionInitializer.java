package io.committed.vessel.server.app.initialisation;


import java.lang.reflect.Constructor;
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


  @Override
  public void initialize(final ConfigurableApplicationContext applicationContext) {


    // We don't have a common inheritence parent.. but the functions are the same in the end..
    final AnnotationContextProxy proxy = createAnnotationContextProxy(applicationContext);


    // TODO: Verbose should should be set from configuration or similar.
    final boolean verbose = false;
    final ExtensionFinder finder = new ExtensionFinder(verbose);
    final Flux<Class<? extends VesselExtension>> extensions = finder.find();

    extensions.subscribe(e -> {
      try {
        final Constructor<? extends VesselExtension> constructor = e.getConstructor();
        final VesselExtension instance = constructor.newInstance();
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
      } catch (final NoSuchMethodException error) {
        log.warn("No available constructor for {}", e.getSimpleName());
      } catch (final Exception ex) {
        log.error("Unable to inspect for plugins", ex);
      }
    });

  }

  private AnnotationContextProxy createAnnotationContextProxy(
      final ConfigurableApplicationContext applicationContext) {
    if (applicationContext instanceof AnnotationConfigApplicationContext) {
      final AnnotationConfigApplicationContext context =
          (AnnotationConfigApplicationContext) applicationContext;
      return new AnnotationContextProxy() {

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
      return new AnnotationContextProxy() {

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
  }
}
