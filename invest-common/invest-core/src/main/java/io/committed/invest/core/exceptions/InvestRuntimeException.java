package io.committed.invest.core.exceptions;

public class InvestRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvestRuntimeException(final String msg) {
    super(msg);
  }

  public InvestRuntimeException(final String msg, final String... args) {
    super(String.format(msg, args));
  }

  public InvestRuntimeException(final String msg, final Throwable t) {
    super(msg, t);
  }
}
