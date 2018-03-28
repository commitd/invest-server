package io.committed.invest.plugin.server.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.committed.invest.extensions.InvestExtension;
import io.committed.invest.plugin.server.audit.services.AuditService;

/**
 * An extension which provides logging for api activity.
 *
 * <p>Two implemenation are included and controlled by Springs profiles. These are simple
 * implementations, one (the default) does not log and the other will log the the console.
 *
 * <p>This are 'toy examples' (though the logging could be used with Logstash in a production
 * environment). It is likely that in a production environment a custom implementation of {@link
 * AuditService} would be created.
 */
@Configuration
@Import(ApiAuditConfig.class)
public class ApiAuditExtension implements InvestExtension {}
