package io.committed.invest.plugin.server.audit;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.server.WebFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.committed.invest.plugin.server.audit.filter.ApiActivityLoggingFilter;
import io.committed.invest.plugin.server.audit.services.AuditService;
import io.committed.invest.plugin.server.audit.services.NoAuditService;
import io.committed.invest.plugin.server.audit.services.Slf4jAuditService;

@Configuration
public class ApiAuditConfig {

  @Bean
  public WebFilter apiActivityFilter(final AuditService auditService) {
    return new ApiActivityLoggingFilter(auditService);
  }

  @Bean
  @Profile({ "audit-slf4j" })
  public AuditService loggingAuditService(final ObjectMapper objectMapper) {
    return new Slf4jAuditService(objectMapper);
  }

  @Bean
  @Profile({ "audit-none" })
  public AuditService noAuditService() {
    return new NoAuditService();
  }

  @Bean
  @ConditionalOnMissingBean(AuditService.class)
  public AuditService ifNothingAuditService() {
    return new NoAuditService();
  }

}
