package io.committed.invest.server.graphql;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.VesselApiExtension;

@Configuration
@Import(GraphQlConfig.class)
public class GraphQlPlugin implements VesselApiExtension {

}
