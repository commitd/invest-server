package io.committed.invest.plugins.ui.host;

import static org.mockito.Mockito.mock;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;
import org.springframework.core.io.Resource;

import io.committed.invest.plugins.ui.host.data.InvestHostedUiExtensions;
import io.committed.invest.plugins.ui.host.data.PluginJson;
import io.committed.invest.plugins.ui.host.data.PluginOverride;
import io.committed.invest.test.BeanTestSupport;
import io.committed.invest.test.LombokDataTestSupport;

public class LombokTest {

  @Test
  public void testLombok() {
    BeanTestSupport.addFactory(Resource.class, () -> mock(Resource.class));

    final LombokDataTestSupport mt = new LombokDataTestSupport();
    mt.testClass(UiHostSettings.class);
    mt.testClasses(PluginOverride.class);

    // TODO: Strange issues with PluginJson.class with mbean
    // it seems to be filling the SimpleActionDefinition with a colleciton of strings
    EqualsVerifier.forClass(PluginJson.class)
        .withRedefinedSuperclass()
        .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
        .verify();

    EqualsVerifier.forClass(InvestHostedUiExtensions.class)
        .withRedefinedSuperclass()
        .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
        .verify();
  }
}
