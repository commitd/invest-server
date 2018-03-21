package io.committed.invest.server.core.services;

import static org.mockito.Mockito.mock;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import io.committed.invest.extensions.InvestExtension;
import io.committed.invest.extensions.collections.InvestExtensions;

public class ExtensionLoggerTest {

  @Test
  public void testNull() {
    final ExtensionLogger extensionLogger = new ExtensionLogger(null, null);

    assertLogs(extensionLogger);

  }

  @Test
  public void testEmpty() {
    final ExtensionLogger extensionLogger = new ExtensionLogger(Collections.emptyList(), Collections.emptyList());

    assertLogs(extensionLogger);

  }

  @Test
  public void testPopulated() {
    final List<InvestExtensions> multi = Arrays.asList(mock(InvestExtensions.class));
    final List<InvestExtension> single = Arrays.asList(mock(InvestExtension.class));;
    final ExtensionLogger extensionLogger = new ExtensionLogger(single, multi);

    assertLogs(extensionLogger);

  }

  private void assertLogs(final ExtensionLogger extensionLogger) {
    extensionLogger.postConstruct();
    // Just testing it doesn't error!
  }

}
