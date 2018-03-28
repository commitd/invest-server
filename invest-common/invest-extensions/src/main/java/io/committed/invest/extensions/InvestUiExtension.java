/*
 *
 */
package io.committed.invest.extensions;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import io.committed.invest.extensions.actions.ActionDefinition;
import io.committed.invest.extensions.actions.SimpleActionDefinition;

/**
 * An Invest Extension which provides a UI to the client.
 *
 * <p>The UI is hosted within the plugin JAR under the /src/main/resources directory. By default
 * (configurable using getStaticResourcePath()) it is actually under src/main/resources/ui/{id}/
 * where id is the plugin id (ie that return by getId()).
 */
public interface InvestUiExtension extends InvestExtension {

  /**
   * Gets the static resource path on the classpath
   *
   * @return the static resource path
   */
  default String getStaticResourcePath() {
    return String.format("/ui/%s/", getId());
  }

  /**
   * A React Semantic UI font icon to use in menu bars etc.
   *
   * @return icon (non null)
   */
  default String getIcon() {
    return "browser";
  }

  /**
   * The list of Invest Authtories the user needs to have in order to access these functions.
   *
   * <p>See InvestAuthorties for a list.
   *
   * <p>Level black is anyone can use.
   *
   * @return the roles
   */
  default Collection<String> getRoles() {
    return Collections.emptyList();
  }

  /**
   * Gets the actions which this plugin accepts.
   *
   * <p>To help create the action list use {@link SimpleActionDefinition} which as a builder
   * interface and Arrays.asList().
   *
   * @return the actions
   */
  default Collection<ActionDefinition> getActions() {
    return Collections.emptyList();
  }

  /**
   * Provides an object which supplies settings to the UI
   *
   * <p>If you wish to read settings from a Yaml configuration, here you might want to Spring's
   * EnableConfigurationProperties and then Autowired the configuration bean into this to return
   * here.
   *
   * <p>Note that the class provided will need to be serialisable to the UI in JSON, thus use
   * Jackson annotations as appropriate to ensure the mapping is correct.
   *
   * @return the settings
   */
  @SuppressWarnings("squid:S1452")
  default Optional<?> getSettings() {
    return Optional.empty();
  }
}
