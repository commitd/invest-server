package io.committed.invest.graphql.ui;

import org.junit.Test;
import io.committed.invest.graphql.ui.data.UiActionDefinition;
import io.committed.invest.graphql.ui.data.UiPlugin;
import io.committed.invest.graphql.ui.service.InvestUiMutationResolver;
import io.committed.invest.graphql.ui.service.InvestUiQueryResolver;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(UiPluginsSettings.class);
    mt.testClasses(UiActionDefinition.class, UiPlugin.class);

    mt.testClasses(InvestUiMutationResolver.Navigate.class, InvestUiMutationResolver.NavigateOutput.class);
    mt.testClasses(InvestUiQueryResolver.PluginActionDefinition.class, InvestUiQueryResolver.QueryActionInput.class,
        InvestUiQueryResolver.QueryActionOutput.class);

  }
}
