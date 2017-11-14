package io.committed.vessel.server.graphql;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.vessel.extensions.VesselApiExtension;

@Configuration
@Import(GraphQlConfig.class)
public class GraphQlPlugin implements VesselApiExtension {

}
