package io.committed.invest.core.exceptions;

public class InvestException extends Exception {

  private static final long serialVersionUID = 1L;

  public InvestException(final String msg) {
    super(msg);
  }

  public InvestException(final String msg, final Object... args) {
    super(String.format(msg, args));
  }

  public InvestException(final String msg, final Throwable t) {
    super(msg, t);
  }

}
