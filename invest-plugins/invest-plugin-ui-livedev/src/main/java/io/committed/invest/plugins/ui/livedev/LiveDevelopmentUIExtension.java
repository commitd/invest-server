package io.committed.invest.plugins.ui.livedev;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.core.auth.InvestRoles;
import io.committed.invest.extensions.InvestUiExtension;

/**
 * Extension which forwards any HTTP requests it receives to http://localhost:3001.
 *
 * <p>This means you can run a live reload version of a plugin and access it within the application
 * as it it is running natively.
 *
 * <p>Note that this has some limitations - for example the live development plugin can not expose
 * the actions / roles that the proxied plugin supports.
 *
 * <p>You will also see some errors in the console, depending on your development environment,
 * because the sandbox nature of the application plugin view can conflict with the requirements of
 * your development environment. Most of these errors can be ignored - if you plugin is displayed
 * and functioning!
 */
@Configuration
@Import({LiveDevelopmentUIConfig.class})
public class LiveDevelopmentUIExtension implements InvestUiExtension {

  @Override
  public String getName() {
    return "Live Development";
  }

  @Override
  public String getDescription() {
    return "Support live coding of a UI plugins with automatic reloading";
  }

  @Override
  public String getIcon() {
    return "code";
  }

  @Override
  public Collection<String> getRoles() {
    return Collections.singleton(InvestRoles.DEV);
  }

  @Override
  public String getStaticResourcePath() {
    // We have no static resource, and want to handle the endpoint ourselves
    return null;
  }
}
