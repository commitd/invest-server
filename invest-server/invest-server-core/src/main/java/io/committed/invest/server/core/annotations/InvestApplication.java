package io.committed.invest.server.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.committed.invest.server.core.ServerCoreConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@Import({ServerCoreConfiguration.class})
@EnableAsync
@EnableScheduling
// Disable webflux security until it's actually enabled by profile
@EnableAutoConfiguration(
    exclude = {ReactiveSecurityAutoConfiguration.class, CassandraReactiveDataAutoConfiguration.class,
        CassandraReactiveRepositoriesAutoConfiguration.class,
        CassandraAutoConfiguration.class, CassandraDataAutoConfiguration.class})
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = ServerCoreConfiguration.class)
public @interface InvestApplication {

}
