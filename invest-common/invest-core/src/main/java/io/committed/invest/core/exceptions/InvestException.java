package io.committed.invest.core.exceptions;

/**
 * Base class for all invest exceptions.
 *
 * <p>As a rule we dont not use highly specific checked exceptions unless they can be recovered
 * from.
 */
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
