package io.committed.invest.plugins.data.elasticsearch;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.committed.invest.extensions.InvestDataExtension;

@Configuration
@Import(ElasticsearchConfiguration.class)
public class ElasticsearchExtension implements InvestDataExtension {


}
