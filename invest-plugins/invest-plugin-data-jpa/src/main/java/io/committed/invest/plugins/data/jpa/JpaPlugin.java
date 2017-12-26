package io.committed.invest.plugins.data.jpa;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.VesselDataExtension;

@Configuration
@Import(JpaConfiguration.class)
public class JpaPlugin implements VesselDataExtension {

}
