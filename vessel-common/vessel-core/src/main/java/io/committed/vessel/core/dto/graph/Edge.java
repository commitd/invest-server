package io.committed.vessel.core.dto.graph;

public interface Edge {

  long getFrom();

  String getGroup();

  long getId();

  long getTo();

  String getType();

}
