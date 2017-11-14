package io.committed.vessel.plugins.data.jpa;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.vessel.extensions.VesselDataExtension;

@Configuration
@Import(JpaConfiguration.class)
public class JpaPlugin implements VesselDataExtension {

}
