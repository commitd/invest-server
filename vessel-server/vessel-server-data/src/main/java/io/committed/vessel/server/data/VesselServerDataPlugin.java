package io.committed.vessel.server.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.committed.vessel.extensions.VesselServiceExtension;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ComponentScan(basePackageClasses = VesselServerDataPlugin.class)
@Slf4j
public class VesselServerDataPlugin implements VesselServiceExtension {



}
