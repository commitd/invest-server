
package io.committed.vesssel.plugins.data.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.vessel.extensions.VesselDataExtension;

@Configuration
@Import(ReactiveMongoConfiguration.class)
public class MongoPlugin implements VesselDataExtension {

}
