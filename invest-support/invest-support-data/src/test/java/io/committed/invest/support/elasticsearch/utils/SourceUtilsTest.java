package io.committed.invest.support.elasticsearch.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import java.util.List;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

public class SourceUtilsTest {

  @Test
  public void test() {
    final ObjectMapper mapper = new ObjectMapper();


    final SearchHit[] hits = new SearchHit[] {
        newSearchHit("1"),
        newSearchHit("2"),
    };
    final SearchHits searchHits = new SearchHits(hits, 100, 1);

    final SearchResponse response = mock(SearchResponse.class);
    doReturn(searchHits).when(response).getHits();

    final List<Dto> convertHits = SourceUtils.convertHits(mapper, response, Dto.class).collectList().block();

    assertThat(convertHits).hasSize(2);
    assertThat(convertHits.get(0).getValue()).isEqualTo("1");
    assertThat(convertHits.get(1).getValue()).isEqualTo("2");
  }

  private SearchHit newSearchHit(final String string) {
    final SearchHit h = mock(SearchHit.class);
    doReturn(String.format("{\"value\": \"%s\"}", string)).when(h).getSourceAsString();
    return h;
  }

  @Data
  public static class Dto {
    private String value;
  }

}
