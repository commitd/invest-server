package io.committed.invest.support.data.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SpringDataElasticsearchSupportServiceTest {
  private static final String TYPE = "type";
  private static final String INDEX = "index";

  private ObjectMapper mapper;
  private ElasticsearchTemplate elasticsearchTemplate;
  private SpringDataElasticsearchSupportService<Entity> sdess;
  private Client client;

  @Data
  public static class Entity {
    private String value;
  }

  @Before
  public void before() {
    client = mock(Client.class);
    mapper = new ObjectMapper();
    elasticsearchTemplate = mock(ElasticsearchTemplate.class);
    doReturn(client).when(elasticsearchTemplate).getClient();
    sdess =
        new SpringDataElasticsearchSupportService<>(
            mapper, elasticsearchTemplate, INDEX, TYPE, Entity.class);
  }

  @Test
  public void testGetElastic() {
    assertThat(sdess.getElastic()).isSameAs(elasticsearchTemplate);
  }

  @Test
  public void testQueryBuilder() {
    final NativeSearchQuery query = sdess.queryBuilder().build();
    assertThat(query.getIndices()).containsExactly(INDEX);
    assertThat(query.getTypes()).containsExactly(TYPE);
  }

  @Test
  public void testQuery() {
    final NativeSearchQueryBuilder nsqb = new NativeSearchQueryBuilder();
    doReturn("results")
        .when(elasticsearchTemplate)
        .query(ArgumentMatchers.any(SearchQuery.class), ArgumentMatchers.any());

    final String results = sdess.query(nsqb, response -> "not used");
    assertThat(results).isEqualTo("results");
  }

  @Test
  public void testCount() {
    final NativeSearchQueryBuilder nsqb = new NativeSearchQueryBuilder();
    doReturn(63L).when(elasticsearchTemplate).count(ArgumentMatchers.any(SearchQuery.class));
    assertThat(sdess.count(nsqb).block()).isEqualTo(63);
  }

  @Test
  public void testGetMapping() {
    final Map<String, Object> returned = new HashMap<>();
    doReturn(returned).when(elasticsearchTemplate).getMapping(INDEX, TYPE);

    assertThat(sdess.getMapping()).isSameAs(returned);
  }
}
