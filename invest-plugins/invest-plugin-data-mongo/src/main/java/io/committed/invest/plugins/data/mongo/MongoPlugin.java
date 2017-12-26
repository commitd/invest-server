
package io.committed.invest.plugins.data.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.VesselDataExtension;

@Configuration
@Import(ReactiveMongoConfiguration.class)
public class MongoPlugin implements VesselDataExtension {

}
