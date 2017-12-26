package io.committed.invest.core.dto.graph;

public interface Edge {

  long getFrom();

  String getGroup();

  long getId();

  long getTo();

  String getType();

}
