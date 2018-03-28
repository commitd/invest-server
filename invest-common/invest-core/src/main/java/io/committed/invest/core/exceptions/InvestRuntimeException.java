package io.committed.invest.core.exceptions;

/** Base class for Invest Runtime Exceptions */
public class InvestRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvestRuntimeException(final String msg) {
    super(msg);
  }

  public InvestRuntimeException(final String msg, final Object... args) {
    super(String.format(msg, args));
  }

  public InvestRuntimeException(final String msg, final Throwable t) {
    super(msg, t);
  }
}
