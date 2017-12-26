package io.committed.invest.plugin.server.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.InvestExtension;

@Configuration
@Import(ApiAuditConfig.class)
public class ApiAuditExtension implements InvestExtension {



}
