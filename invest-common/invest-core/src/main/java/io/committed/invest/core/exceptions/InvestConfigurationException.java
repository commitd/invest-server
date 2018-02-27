package io.committed.invest.core.exceptions;

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
