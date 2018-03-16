package io.committed.invest.core.dto.system;

import lombok.Data;

/**
 * A message, usually for display to the user.
 */
@Data
public class Message {

  public enum Type {
    SUCCESS, INFO, WARNING, ERROR
  }

  private final Type type;
  private final String message;

}
