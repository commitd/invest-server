package io.committed.invest.test;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InvestTestContext.class)
public class InvestTestContextTest {

  @Autowired
  ObjectMapper mapper;

  @Test
  public void test() {
    assertNotNull(mapper);
  }

}
