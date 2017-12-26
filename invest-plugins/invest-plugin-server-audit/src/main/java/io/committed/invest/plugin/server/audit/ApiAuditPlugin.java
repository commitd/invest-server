package io.committed.invest.plugin.server.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.VesselExtension;

@Configuration
@Import(ApiAuditConfig.class)
public class ApiAuditPlugin implements VesselExtension {



}
