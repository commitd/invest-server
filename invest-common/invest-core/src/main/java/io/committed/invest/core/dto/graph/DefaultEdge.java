package io.committed.invest.core.dto.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/** An edge of a graph. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultEdge implements Edge {

  @JsonProperty("id")
  private long id;

  @JsonProperty("from")
  private long from;

  @JsonProperty("to")
  private long to;

  @JsonProperty("group")
  private String group;

  @JsonProperty("type")
  private String type;

  public DefaultEdge(final long from, final long to, final String group, final String type) {
    this.from = from;
    this.to = to;
    this.group = group;
    this.type = type;
  }
}
