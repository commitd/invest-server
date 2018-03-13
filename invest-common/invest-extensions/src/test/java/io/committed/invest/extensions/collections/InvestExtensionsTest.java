package io.committed.invest.extensions.collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import io.committed.invest.extensions.InvestExtension;
import io.committed.invest.extensions.InvestUiExtension;

public class InvestExtensionsTest {

  @Test
  public void testEmpty() {
    final InvestExtensions extensions = new InvestExtensions(Collections.emptyList());

    assertThat(extensions.stream()).isEmpty();
    assertThat(extensions.stream(InvestExtension.class)).isEmpty();;
    assertThat(extensions.getExtensions()).hasSize(0);
  }

  @Test
  public void test() {

    final InvestExtension ie1 = mock(InvestExtension.class);
    final InvestExtension ie2 = mock(InvestExtension.class);
    final InvestUiExtension ie3 = mock(InvestUiExtension.class);

    final InvestExtensions extensions = new InvestExtensions(Arrays.asList(ie1, ie2, ie3));

    assertThat(extensions.stream()).contains(ie1, ie2, ie3);
    assertThat(extensions.stream(InvestExtension.class)).contains(ie1, ie2, ie3);
    assertThat(extensions.stream(InvestUiExtension.class)).contains(ie3);

  }

}
