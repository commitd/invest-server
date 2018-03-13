package io.committed.invest.extensions.actions;

/**
 * Defines an action that a UI plugin accepts.
 *
 * Each plugin can declare that it responds  actions. This are akin to Android Intents / Flux
 * actions being a string (the action) for example 'data.view" and a payload which is data sent
 * along with the action.
 *
 * The
 */
public interface ActionDefinition {

  /**
   * The action supported
   *
   * @return
   */
  String getAction();

  /**
   * Optionally (and uncurrently unused) java class which represents the payload shape.
   *
   * @return
   */
  default Class<?> getPayload() {
    return null;
  }

  /**
   * A title which is displayed the user corresponding for the specific plugin and this action.
   *
   * For example if the action is 'data.view' but the UI plugin will actually display some form of
   * count the title might be 'Count data'. Another plugin might also support also 'data.view' but
   * provide a simple summary of the information so its title may be 'Summary" or "Overview".
   *
   * @return
   */
  String getTitle();

  /**
   * A description of what the plugin will do with this action - for example 'Displays a summary of
   * the data'.
   *
   * Default is empty.
   *
   * @return the description
   */
  default String getDescription() {
    return "";
  }

}
