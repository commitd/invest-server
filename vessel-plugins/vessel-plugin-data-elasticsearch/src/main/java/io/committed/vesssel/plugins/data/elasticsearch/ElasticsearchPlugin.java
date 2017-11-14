package io.committed.vesssel.plugins.data.elasticsearch;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.vessel.extensions.VesselDataExtension;

@Configuration
@Import(ElasticsearchConfiguration.class)
public class ElasticsearchPlugin implements VesselDataExtension {


}
