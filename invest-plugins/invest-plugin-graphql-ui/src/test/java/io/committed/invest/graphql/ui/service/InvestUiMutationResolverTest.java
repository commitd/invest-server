package io.committed.invest.graphql.ui.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import io.committed.invest.graphql.ui.service.InvestUiMutationResolver.Navigate;
import io.committed.invest.graphql.ui.service.InvestUiMutationResolver.NavigateOutput;

public class InvestUiMutationResolverTest {

  @Test
  public void testNavigate() {
    final InvestUiMutationResolver resolver = new InvestUiMutationResolver();

    final NavigateOutput output = resolver.navigate(new Navigate());

    // It don't navigate, so technically its correct to say failure!
    assertThat(output.isSuccess()).isFalse();
  }

}
