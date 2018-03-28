package io.committed.invest.extensions;

import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

/**
 * A base class for all Invest extensions.
 *
 * <p>An extension should be declared by implementing this interface (or preferable one of the more
 * specialised sub-interface from it). You should annotate your class with the spring @Configuration
 * annotation.
 *
 * <p>In order to automatically register the extension, if it is found on the classpath, you should
 * use Spring Auto Configuration mechanism as discussed in
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html
 *
 * <p>In effect you need only add the file spring.factories to your /src/main/resources/META-INF
 * directory:
 *
 * <p>This file should contain:
 *
 * <pre>
 * org.springframework.boot.autoconfigure.EnableAutoConfiguration = full.qualified.path.to.your.Plugin
 * </pre>
 *
 * When your built JAR (or development version) is on the classpath, Spring will find your extension
 * through auto configuration and it will be made available in your Invest application.
 */
@Service
public interface InvestExtension {

  /**
   * A (ideally unique but friendly) id for the client.
   *
   * <p>Typically this will be a string taken from the name of the plugin or class name. For example
   * my-invest-plugin. You should use only limited characters eg a-zA-Z0-9_-]) as the id may be used
   * on the URL etc.
   *
   * <p>The default implementation uses the class name which is sufficient in most cases.
   *
   * @return the id
   */
  default String getId() {
    // Use getUserClass so that we don't need to worry about if Spring has proxied the class (eg its
    // a config bean)
    return ClassUtils.getUserClass(this.getClass()).getSimpleName().toLowerCase();
  }

  /**
   * A displayable name for the plugin to the user.
   *
   * <p>The default name is the class name which is usually sufficient initially. You may want to
   * add spacing etc.
   *
   * @return the name
   */
  default String getName() {
    return ClassUtils.getUserClass(this.getClass()).getSimpleName();
  }

  /**
   * A short description of the plugin.
   *
   * <p>The default is an empty string
   *
   * @return the descrirtion non-null
   */
  default String getDescription() {
    return "";
  }
}
