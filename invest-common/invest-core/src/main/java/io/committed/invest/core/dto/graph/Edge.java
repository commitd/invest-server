package io.committed.invest.core.dto.graph;

/** Interface for a graph edge. */
public interface Edge {

  long getFrom();

  String getGroup();

  long getId();

  long getTo();

  String getType();
}
