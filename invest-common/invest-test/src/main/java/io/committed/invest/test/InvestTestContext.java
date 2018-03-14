package io.committed.invest.test;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * An configuration context to give you a basic Invest Application Context.
 *
 * <pre>
 * &#64;RunWith(SpringRunner.class)
 * &#64;WebFluxTest
 * &#64;ContextConfiguration(classes = {InvestTestContext.class})
 * &#64;Import({GraphQlConfig.class})
 * </pre>
 *
 */
@Import({JacksonAutoConfiguration.class})
public class InvestTestContext {

}
