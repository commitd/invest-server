package io.committed.invest.extensions;

import java.util.Collection;
import java.util.Collections;
import io.committed.invest.actions.ActionDefinition;

public interface InvestUiExtension extends InvestExtension {


  default String getStaticResourcePath() {
    return String.format("/ui/%s/", getId());
  }

  /**
   * A Material UI font icon to use in menu bars etc.
   *
   * @return string (non null)
   */
  default String getIcon() {
    return "browser";
  }

  /**
   * The list of roles the user needs to have in order to access these functions.
   * 
   * @return
   */
  default Collection<String> getRoles() {
    return Collections.emptyList();
  }

  default Collection<ActionDefinition> getActions() {
    return Collections.emptyList();
  }

  default Class<?> getSettings() {
    return null;
  }

}
