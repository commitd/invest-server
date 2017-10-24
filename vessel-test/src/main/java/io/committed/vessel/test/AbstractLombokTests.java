package io.committed.vessel.test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractLombokTests {


  protected void testDataClasses(Class<?>... classes) {
    for (Class<?> clazz : classes) {
      assertThat(clazz, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
          hasValidBeanHashCode(), hasValidBeanEquals()));

      // Note we don't include hasValidBeanToString() since toString()
    }
  }

  protected void testImmutableDataClasses(Class<?>... classes) {
    for (Class<?> clazz : classes) {
      assertThat(clazz,
          allOf(hasValidGettersAndSetters(), hasValidBeanHashCode(), hasValidBeanEquals()));

      // Note we don't include hasValidBeanToString() since toString()
    }
  }

}
