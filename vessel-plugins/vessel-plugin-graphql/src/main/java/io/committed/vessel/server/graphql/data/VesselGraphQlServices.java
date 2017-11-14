package io.committed.vessel.server.graphql.data;

import java.util.Collection;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VesselGraphQlServices {
  private final Collection<Object> services;
}
