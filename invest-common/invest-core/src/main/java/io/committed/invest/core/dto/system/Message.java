package io.committed.invest.core.dto.system;

import lombok.Value;

@Value
public class Message {

  public enum Type {
    SUCCESS, INFO, WARNING, ERROR
  }

  private final Type type;
  private final String message;

}
