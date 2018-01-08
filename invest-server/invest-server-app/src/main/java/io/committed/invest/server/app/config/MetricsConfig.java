package io.committed.invest.server.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;

/**
 * Add additional metrics to Micrometer.
 *
 * There are other's available too but they need more specific configuration (eg the obejct which is
 * being monitored).
 *
 * Other metrics are already installed by MeterBindersConfiguration.
 *
 */
@Configuration
public class MetricsConfig {
  @Bean
  public ClassLoaderMetrics classLoaderMetrics() {
    return new ClassLoaderMetrics();
  }

  @Bean
  public JvmGcMetrics jvmGcMetrics() {
    return new JvmGcMetrics();
  }

  @Bean
  public JvmThreadMetrics jvmThreadMetrics() {
    return new JvmThreadMetrics();
  }

  @Bean
  public FileDescriptorMetrics fileDescriptorMetrics() {
    return new FileDescriptorMetrics();
  }


  @Bean
  public ProcessorMetrics processorMetrics() {
    return new ProcessorMetrics();
  }
}
