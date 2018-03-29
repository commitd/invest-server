package io.committed.invest.core.exceptions;

/**
 * Specific exception around configuration of Invest components.
 *
 * <p>These will usually be due to missing configuration or conflicting configuration.
 */
public class InvestConfigurationException extends InvestException {

  private static final long serialVersionUID = 1L;

  public InvestConfigurationException(final String msg) {
    super(msg);
  }

  public InvestConfigurationException(final String msg, final Object... args) {
    super(msg, args);
  }

  public InvestConfigurationException(final String msg, final Throwable t) {
    super(msg, t);
  }
}
