package io.committed.invest.core.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class InvestExceptionTest {

  @Test
  public void testConfigurationException() {
    final InvestConfigurationException msgEx = new InvestConfigurationException("msg");
    final InvestConfigurationException formattedMsg = new InvestConfigurationException("msg %d", 1);
    final InvestConfigurationException msgWithThrowable =
        new InvestConfigurationException("msg", new Exception());

    assertException(msgEx, formattedMsg, msgWithThrowable);
  }



  @Test
  public void testException() {
    final InvestException msgEx = new InvestException("msg");
    final InvestException formattedMsg = new InvestException("msg %d", 1);
    final InvestException msgWithThrowable =
        new InvestException("msg", new Exception());

    assertException(msgEx, formattedMsg, msgWithThrowable);
  }

  @Test
  public void testRuntimeException() {
    final InvestRuntimeException msgEx = new InvestRuntimeException("msg");
    final InvestRuntimeException formattedMsg = new InvestRuntimeException("msg %d", 1);
    final InvestRuntimeException msgWithThrowable =
        new InvestRuntimeException("msg", new Exception());

    assertRuntimeException(msgEx, formattedMsg, msgWithThrowable);
  }


  private void assertException(final InvestException msgEx,
      final InvestException formattedMsg,
      final InvestException msgWithThrowable) {

    assertEquals("msg", msgEx.getMessage());

    assertEquals("msg 1", formattedMsg.getMessage());

    assertEquals("msg", msgWithThrowable.getMessage());
    assertNotNull(msgWithThrowable.getCause());
  }

  private void assertRuntimeException(final InvestRuntimeException msgEx,
      final InvestRuntimeException formattedMsg,
      final InvestRuntimeException msgWithThrowable) {

    assertEquals("msg", msgEx.getMessage());

    assertEquals("msg 1", formattedMsg.getMessage());

    assertEquals("msg", msgWithThrowable.getMessage());
    assertNotNull(msgWithThrowable.getCause());
  }
}
