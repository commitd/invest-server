package io.committed.invest.plugins.ui.application;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.committed.invest.core.dto.collections.PropertiesMap;

import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;

/** Settings which relate (are provided to and used by) the application UI JS code. */
@Data
@ConfigurationProperties("invest.config")
public class UiApplicationSettings {

  private String title = "Invest";

  private String serverUrl = "http://localhost:8080";

  private Map<String, Object> settings = new HashMap<>();

  @GraphQLIgnore
  public Map<String, Object> getSettings() {
    return settings;
  }

  @GraphQLQuery(name = "settings")
  public PropertiesMap getProperties() {
    return new PropertiesMap(settings);
  }
}
