package io.committed.invest.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.meanbean.factories.NoSuchFactoryException;
import io.committed.invest.test.data.BrokenGetterExample;

public class BeanTestSupportTest {

  @Test
  public void testGetRandomValueGenerator() {
    assertThat(BeanTestSupport.getRandomValueGenerator()).isNotNull();

  }

  @Test
  public void testAddFactory() {
    try {
      BeanTestSupport.generateValue(CantMakeMeWithoutFactory.class);
      fail("Made the unmakeable");
    } catch (final NoSuchFactoryException e) {
      // Ok
    }

    BeanTestSupport.addFactory(CantMakeMeWithoutFactory.class, () -> new CantMakeMeWithoutFactory("test"));
    final CantMakeMeWithoutFactory value = BeanTestSupport.generateValue(CantMakeMeWithoutFactory.class);
    assertThat(value).isNotNull();
  }

  @Test
  public void testGenerateValueClassOfString() {
    assertThat(BeanTestSupport.generateValue(String.class)).isNotBlank();
  }

  @Test
  public void testGenerateValueClassOfStringDefault() {
    try {
      BeanTestSupport.generateValue(CantMakeMe.class);
      fail("Made the unmakeable");
    } catch (final NoSuchFactoryException e) {
      // Ok
    }

    final CantMakeMe m = new CantMakeMe("hello");
    final CantMakeMe e = BeanTestSupport.generateValue(CantMakeMe.class, m);
    assertThat(e).isSameAs(m);
  }

  @Test(expected = AssertionError.class)
  public void testTest() {
    BeanTestSupport.test(BrokenGetterExample.class);
  }


  public class CantMakeMe {
    public CantMakeMe(final CantMakeMe me) {

    }

    public CantMakeMe(final String s) {

    }
  }

  public class CantMakeMeWithoutFactory {

    public CantMakeMeWithoutFactory(final String s) {

    }
  }
}
