package io.committed.invest.server.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.committed.invest.test.InvestTestContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {InvestServerDataExtension.class, InvestTestContext.class})
public class DataExtensionTest {

  @Autowired InvestServerDataExtension extension;

  @Test
  public void test() {
    assertThat(extension).isNotNull();

    assertThat(extension.getName()).isNotBlank();
    assertThat(extension.getDescription()).isNotBlank();
    assertThat(extension.getId()).isNotBlank();
  }
}
