package io.committed.invest.test;

import org.junit.Test;

import io.committed.invest.test.data.AbstractBrokenClass;
import io.committed.invest.test.data.AbstractClass;
import io.committed.invest.test.data.BrokenEqualsExample;
import io.committed.invest.test.data.BrokenGetterExample;
import io.committed.invest.test.data.BrokenHashcodeExample;
import io.committed.invest.test.data.BrokenSetterExample;
import io.committed.invest.test.data.LombokDataExample;

public class LombokDataTestSupportTest {

  @Test
  public void test() {
    final LombokDataTestSupport ldts = new LombokDataTestSupport();

    ldts.testClass(LombokDataExample.class);
  }

  @Test(expected = AssertionError.class)
  public void testBrokenEquals() {
    final LombokDataTestSupport ldts = new LombokDataTestSupport();

    ldts.testClass(BrokenEqualsExample.class);
  }

  @Test(expected = AssertionError.class)
  public void testBrokenHashcode() {
    final LombokDataTestSupport ldts = new LombokDataTestSupport();

    ldts.testClass(BrokenHashcodeExample.class);
  }

  @Test(expected = AssertionError.class)
  public void testBrokenSetter() {
    final LombokDataTestSupport ldts = new LombokDataTestSupport();

    ldts.testClass(BrokenSetterExample.class);
  }

  @Test(expected = AssertionError.class)
  public void testBrokenGetter() {
    final LombokDataTestSupport ldts = new LombokDataTestSupport();

    ldts.testClass(BrokenGetterExample.class);
  }

  @Test
  public void testOkAbstract() {
    final LombokDataTestSupport ldts = new LombokDataTestSupport();

    ldts.testClass(AbstractClass.class);
  }

  @Test(expected = AssertionError.class)
  public void testBrokenAbstract() {
    final LombokDataTestSupport ldts = new LombokDataTestSupport();
    ldts.testClass(AbstractBrokenClass.class);
  }

  @Test(expected = AssertionError.class)
  public void testPackage() {
    final LombokDataTestSupport ldts = new LombokDataTestSupport();
    ldts.testPackage(AbstractBrokenClass.class);
  }
}
