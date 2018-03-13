package io.committed.invest.core.services;

import io.committed.invest.extensions.InvestUiExtension;

/**
 * A service to provide the URL for a ui plugin from which it can be downloaded from the server
 *
 * This is an interface to be implemented at the allow different server implementation to host UI's
 * files on specific context paths.
 *
 */
public interface UiUrlService {

  /**
   * Gets the full path to the plugins files.
   *
   * This may be relative or absolution (depending on server configuration).
   *
   * For example /ui/my-ui-plugin
   *
   * @param extension
   * @return
   */
  default String getFullPath(final InvestUiExtension extension) {
    return getContextPath() + getContextRelativePath(extension);
  }

  /**
   * Gets the path to the extension relative to the getContextPath()
   *
   * Foe xample '/my-ui-plugin
   *
   * @param extension
   * @return
   */
  String getContextRelativePath(InvestUiExtension extension);

  /**
   * Gets the context path common to all plugins on this server.
   *
   * For example "/ui"
   *
   * @return
   */
  String getContextPath();

  /**
   * Checks it a path maps to the root directory of a plugin.
   *
   * The alternative is that is links to a file (index.html) within that directory.
   *
   * @param path
   * @return true if its the root of plugin
   */
  boolean isPathForExtensionRoot(String path);

}
