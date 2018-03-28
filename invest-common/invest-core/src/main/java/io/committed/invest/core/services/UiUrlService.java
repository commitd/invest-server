package io.committed.invest.core.services;

import io.committed.invest.extensions.InvestUiExtension;

/**
 * A service to provide the URL for a ui plugin from which it can be downloaded from the server
 *
 * <p>This is an interface to be implemented at the allow different server implementation to host
 * UI's files on specific context paths.
 */
public interface UiUrlService {

  /**
   * Gets the full path to the plugins files.
   *
   * <p>This may be relative or absolution (depending on server configuration).
   *
   * <p>For example /ui/my-ui-plugin
   *
   * @param extension the extension
   * @return the full path
   */
  default String getFullPath(final InvestUiExtension extension) {
    return getContextPath() + getContextRelativePath(extension);
  }

  /**
   * Gets the path to the extension relative to the getContextPath()
   *
   * <p>Foe xample '/my-ui-plugin.
   *
   * @param extension the extension
   * @return the context relative path
   */
  String getContextRelativePath(InvestUiExtension extension);

  /**
   * Gets the context path common to all plugins on this server.
   *
   * <p>For example "/ui"
   *
   * @return the context path
   */
  String getContextPath();

  /**
   * Checks it a path maps to the root directory of a plugin.
   *
   * <p>The alternative is that is links to a file (index.html) within that directory.
   *
   * @param path the path
   * @return true if its the root of plugin
   */
  boolean isPathForExtensionRoot(String path);
}
