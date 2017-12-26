
package io.committed.invest.plugins.data.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.InvestDataExtension;

@Configuration
@Import(ReactiveMongoConfiguration.class)
public class MongoExtension implements InvestDataExtension {

}
